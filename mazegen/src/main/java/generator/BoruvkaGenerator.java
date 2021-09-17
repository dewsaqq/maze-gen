package generator;

import maze.Cell;
import maze.Grid;
import maze.Maze;
import maze.Wall;
import org.jgrapht.alg.util.UnionFind;

import java.util.*;

public class BoruvkaGenerator extends Generator {
    @Override
    public Maze generateMaze(Grid grid) {
        UnionFind<Cell> cellForest = new UnionFind<>(Collections.emptySet());
        Map<Cell, Wall> wallsToOpen = new LinkedHashMap<>();
        List<Wall> walls;

        for (Cell cell : grid.getCellsList()) {
            cellForest.addElement(cell);
        }

        do {
            wallsToOpen.clear();
            walls = grid.getClosedWalls();
            for (Wall wall : walls) {
                ArrayList<Cell> adjacentCells = wall.getAdjacentCells();
                Cell firstCell = adjacentCells.get(0);
                Cell secondCell = adjacentCells.get(1);

                if (cellForest.inSameSet(firstCell, secondCell)) {
                    continue;
                }

                if (wallsToOpen.get(firstCell) == null) {
                    wallsToOpen.put(firstCell, firstCell.getRandomClosedWall());
                }

                if (wallsToOpen.get(secondCell) == null) {
                    wallsToOpen.put(secondCell, secondCell.getRandomClosedWall());
                }
            }

            for (Wall wall : wallsToOpen.values()) {
                ArrayList<Cell> adjacentCells = wall.getAdjacentCells();
                Cell firstCell = adjacentCells.get(0);
                Cell secondCell = adjacentCells.get(1);

                if (cellForest.inSameSet(firstCell, secondCell)) {
                    continue;
                }

                wall.openWall();
                cellForest.union(firstCell, secondCell);
            }
        } while(cellForest.numberOfSets() != 1);

        return new Maze(grid);
    }
}
