package graph;

import maze.Cell;
import maze.Grid;
import maze.Maze;
import maze.Wall;
import org.jgrapht.Graph;
import org.jgrapht.GraphMetrics;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.GraphMeasurer;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.util.*;
import java.util.stream.Collectors;

public class MazeGraph {
    private final Maze maze;
    private final Grid grid;

    public Graph<Vertex, DefaultEdge> getMazeGraph() {
        return mazeGraph;
    }

    private final Graph<Vertex, DefaultEdge> mazeGraph;
    private final Graph<Vertex, DefaultWeightedEdge> mazeDifficultyGraph;
    private final Cell startCell;
    private final Cell endCell;

    private Map<Cell, Vertex> cellVertexMap;
    private Map<DefaultEdge, Wall> defaultEdgeWallMap;

    public MazeGraph(Maze maze) {
        this.maze = maze;
        grid = maze.getGrid();
        startCell = grid.getCell(0, 0);
        endCell = grid.getCell(grid.getHeight() - 1, grid.getWidth() - 1);
        mazeGraph = new SimpleGraph<>(DefaultEdge.class);
        mazeDifficultyGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        initializeMazeGraph();
        initializeMazeDifficultyGraph();
    }

    private void initializeMazeGraph() {
        cellVertexMap = new LinkedHashMap<>();
        Cell[][] cells = grid.getCells();
        int height = grid.getHeight();
        int width = grid.getWidth();

        int addedRowValue;
        int addedColumnValue;
        for (int row = 0; row < height; row++) {
            if (row == 0 || row == height - 1) addedRowValue = 1;
            else addedRowValue = 0;

            for (int column = 0; column < width; column++) {
                if (column == 0 || column == width - 1) addedColumnValue = 1;
                else addedColumnValue = 0;

                if (row == 0 && column == 0) cellVertexMap.put(cells[row][column], new Vertex(Vertex.Type.START));
                else if (row == height - 1 && column == width -1) cellVertexMap.put(cells[row][column], new Vertex(Vertex.Type.END));
                else {
                    int numberOfClosedWalls = cells[row][column].getClosedWalls().size() + addedRowValue + addedColumnValue;
                    if (numberOfClosedWalls == 2) cellVertexMap.put(cells[row][column], new Vertex(Vertex.Type.PLAIN));
                    else if (numberOfClosedWalls == 3) cellVertexMap.put(cells[row][column], new Vertex(Vertex.Type.DEAD_END));
                    else if (numberOfClosedWalls == 1) cellVertexMap.put(cells[row][column], new Vertex(Vertex.Type.THREE_WAY_INTERSECTION));
                    else if (numberOfClosedWalls == 0) cellVertexMap.put(cells[row][column], new Vertex(Vertex.Type.FOUR_WAY_INTERSECTION));

                }
            }
        }

        constructMazeGraph();
    }

    public double getStartEndPathLength() {
        BFSShortestPath<Vertex, DefaultEdge> bfs = new BFSShortestPath<>(mazeGraph);
        return bfs.getPath(cellVertexMap.get(startCell), cellVertexMap.get(endCell)).getLength() + 1;
    }

    public double getStartEndPathCorridorDifficulty() {
        BFSShortestPath<Vertex, DefaultWeightedEdge> bfs = new BFSShortestPath<>(mazeDifficultyGraph);
        double startEndPathCorridorDifficulty = 0.0;

        for (DefaultWeightedEdge dwe : bfs.getPath(cellVertexMap.get(startCell), cellVertexMap.get(endCell)).getEdgeList()) {
            startEndPathCorridorDifficulty += mazeDifficultyGraph.getEdgeWeight(dwe);
        }

        return startEndPathCorridorDifficulty;
    }

    public double getCorridorsDifficultySum() {
        double corridorsDifficulty = 0.0;

        for (DefaultWeightedEdge dwe : mazeDifficultyGraph.edgeSet()) {
            corridorsDifficulty += mazeDifficultyGraph.getEdgeWeight(dwe);
        }

        return corridorsDifficulty;
    }

    public double getNumberOfDeadEnds() {
        return mazeGraph.vertexSet().stream()
                .filter(v -> v.getVertexType() == Vertex.Type.DEAD_END)
                .count();
    }

    public double getNumberOfThreeWayIntersections() {
        return mazeGraph.vertexSet().stream()
                .filter(v -> v.getVertexType() == Vertex.Type.THREE_WAY_INTERSECTION)
                .count();
    }

    public double getNumberOfFourWayIntersections() {
        return mazeGraph.vertexSet().stream()
                .filter(v -> v.getVertexType() == Vertex.Type.FOUR_WAY_INTERSECTION)
                .count();
    }

    private void constructMazeGraph() {
        for (Vertex vertex : cellVertexMap.values()) {
            mazeGraph.addVertex(vertex);
        }

        defaultEdgeWallMap = new HashMap<>();

        for (Wall wall : maze.getGrid().getOpenWalls()) {
            ArrayList<Cell> adjacentCells = wall.getAdjacentCells();
            Cell firstCell = adjacentCells.get(0);
            Cell secondCell = adjacentCells.get(1);

            DefaultEdge de = mazeGraph.addEdge(cellVertexMap.get(firstCell), cellVertexMap.get(secondCell));
            defaultEdgeWallMap.put(de, wall);
        }
    }

    private List<Vertex> getSpecialTypeVertices() {
        return mazeGraph.vertexSet().stream()
                .filter(v -> v.getVertexType() != Vertex.Type.PLAIN)
                .collect(Collectors.toList());
    }


    private void initializeMazeDifficultyGraph() {
        for (Vertex specialVertex : getSpecialTypeVertices()) {
            mazeDifficultyGraph.addVertex(specialVertex);
        }

        for (Vertex v : getSpecialTypeVertices()) {
            for (Vertex neighbour : Graphs.neighborListOf(mazeGraph, v)) {
                Vertex currentV = neighbour;
                List<Vertex> path = new ArrayList<>();
                path.add(v);

                while (currentV != null) {
                    path.add(currentV);
                    if (currentV.getVertexType() != Vertex.Type.PLAIN) {
                        break;
                    }
                    currentV = Graphs.neighborListOf(mazeGraph, currentV).stream().filter(vertex -> !path.contains(vertex)).findFirst().orElse(null);
                }

                if (!mazeDifficultyGraph.containsEdge(path.get(0), path.get(path.size() - 1))) {
                    DefaultWeightedEdge dwe = mazeDifficultyGraph.addEdge(path.get(0), path.get(path.size() - 1));
                    mazeDifficultyGraph.setEdgeWeight(dwe, calculatePathWeight(path));
                }
            }
        }
    }

    private double calculatePathWeight(List<Vertex> path) {
        double calculatedWeight = 0.0;

        Wall.Orientation lastOrientation = defaultEdgeWallMap.get(mazeGraph.getEdge(path.get(0), path.get(1))).getOrientation();

        for (int i = 1; i < path.size() - 1; i++) {
            Wall.Orientation currentOrientation = defaultEdgeWallMap.get(mazeGraph.getEdge(path.get(i), path.get(i + 1))).getOrientation();
            if (currentOrientation != lastOrientation) {
                lastOrientation = currentOrientation;
                calculatedWeight++;
            }
        }

        return calculatedWeight;
    }

    public double getLongestPathFromStartLength() {
        BreadthFirstIterator<Vertex, DefaultEdge> iterator = new BreadthFirstIterator<>(mazeGraph, cellVertexMap.get(startCell));

        int pathLength = iterator.getDepth(iterator.next());
        while (iterator.hasNext()) {
            int lengthToCheck = iterator.getDepth(iterator.next());
            if (lengthToCheck > pathLength) pathLength = lengthToCheck;
        }

        return pathLength + 1;
    }
}
