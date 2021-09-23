package generator;

import maze.Cell;
import maze.Grid;
import maze.Maze;
import maze.Wall;
import org.jgrapht.alg.util.UnionFind;

import java.util.*;

public class KruskalGenerator extends Generator {
    @Override
    public Maze generateMaze(Grid grid) {
        UnionFind<Cell> cellForest = new UnionFind<>(Collections.emptySet());
        List<Wall> walls = new ArrayList<>(grid.getWalls());
        Collections.shuffle(walls);

        for (Cell cell : grid.getCellsList()) {
            cellForest.addElement(cell);
        }

        while (cellForest.numberOfSets() != 1) {
            Wall drawnWall = walls.get(0);
            ArrayList<Cell> adjacentCells = drawnWall.getAdjacentCells();
            Cell firstCell = adjacentCells.get(0);
            Cell secondCell = adjacentCells.get(1);

            if (!cellForest.inSameSet(firstCell, secondCell)) {
                drawnWall.openWall();
                cellForest.union(firstCell, secondCell);
            }

            walls.remove(drawnWall);
        }

        return new Maze(grid);
    }
}
