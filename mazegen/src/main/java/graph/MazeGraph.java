package graph;

import maze.Cell;
import maze.Grid;
import maze.Maze;
import maze.Wall;
import org.jgrapht.Graph;
import org.jgrapht.GraphMetrics;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.GraphMeasurer;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;
import java.util.stream.Collectors;

public class MazeGraph {
    private final Maze maze;
    private final Grid grid;
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
        //initializeMazeDifficultyGraph();
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

    private void initializeMazeDifficultyGraph() {
        BFSShortestPath<Vertex, DefaultEdge> bfs = new BFSShortestPath<>(mazeGraph);
        List<Vertex> specialTypeVertices = getSpecialTypeVertices();
        List<GraphPath<Vertex, DefaultEdge>> graphPathsToCalculateDifficulty = new ArrayList<>();

        for (int i = 0; i < specialTypeVertices.size() - 1; i++) {
            for (int j = i + 1; j < specialTypeVertices.size(); j++) {
                boolean fault = false;
                GraphPath<Vertex, DefaultEdge> graphPath = bfs.getPath(specialTypeVertices.get(i), specialTypeVertices.get(j));
                for (int k = 1; k < graphPath.getVertexList().size() - 1; k++) {
                    if (graphPath.getVertexList().get(k).getVertexType() != Vertex.Type.PLAIN) {
                        fault = true;
                        break;
                    }
                }

                if (!fault) graphPathsToCalculateDifficulty.add(graphPath);
            }
        }

        System.out.println("1. Done");
        for (GraphPath<Vertex, DefaultEdge> graphPath : graphPathsToCalculateDifficulty) {
            Vertex startVertex = graphPath.getStartVertex();
            Vertex endVertex = graphPath.getEndVertex();

            mazeDifficultyGraph.addVertex(startVertex);
            mazeDifficultyGraph.addVertex(endVertex);

            DefaultWeightedEdge dwe = mazeDifficultyGraph.addEdge(startVertex, endVertex);
            mazeDifficultyGraph.setEdgeWeight(dwe, calculatePathWeight(graphPath));
        }
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

    public double getLongestPathLength() {
        long startTime = System.nanoTime();
        double diameter = Double.NEGATIVE_INFINITY;
        for(Vertex v : mazeGraph.vertexSet()) {
            ShortestPathAlgorithm<Vertex, DefaultEdge> alg = new BFSShortestPath<>(mazeGraph);

            ShortestPathAlgorithm.SingleSourcePaths<Vertex, DefaultEdge> paths = alg.getPaths(v);
            for(Vertex u : mazeGraph.vertexSet()) {
                diameter = Math.max(diameter, paths.getPath(u).getLength());
            }
        }

        System.out.println("Graph diameter = " + diameter);



//        ShortestPathAlgorithm<Vertex, DefaultEdge> alg = new BFSShortestPath<>(mazeGraph);
//        GraphMeasurer<Vertex, DefaultEdge> gm = new GraphMeasurer<>(mazeGraph, alg);


        long endTime = System.nanoTime();
        System.out.println("Execution time (seconds): " + (endTime - startTime)/1000000000.0);
//        return gm.getDiameter();
        return diameter;
//        return GraphMetrics.getDiameter(mazeGraph) + 1.0;
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

    private double calculatePathWeight(GraphPath<Vertex, DefaultEdge> graphPath) {
        double calculatedWeight = 0.0;
        Wall.Orientation lastOrientation = defaultEdgeWallMap.get(graphPath.getEdgeList().get(0)).getOrientation();

        for (int i = 1; i < graphPath.getEdgeList().size(); i++) {
            Wall.Orientation currentOrientation = defaultEdgeWallMap.get(graphPath.getEdgeList().get(i)).getOrientation();
            if (currentOrientation != lastOrientation) {
                lastOrientation = currentOrientation;
                calculatedWeight++;
            }
        }

        return calculatedWeight;
    }
}
