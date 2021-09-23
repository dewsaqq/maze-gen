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
        Map<Cell, Wall> chosenWalls = new HashMap<>();
        List<Wall> walls;

        for (Cell cell : grid.getCellsList()) {
            cellForest.addElement(cell);
        }

        do {
            chosenWalls.clear();
            walls = grid.getClosedWalls();
            for (Wall wall : walls) {
                ArrayList<Cell> adjacentCells = wall.getAdjacentCells();
                Cell firstCell = adjacentCells.get(0);
                Cell secondCell = adjacentCells.get(1);

                if (cellForest.inSameSet(firstCell, secondCell)) {
                    continue;
                }

                if (chosenWalls.get(firstCell) == null) {
                    chosenWalls.put(firstCell, firstCell.getRandomClosedWall());
                }

                if (chosenWalls.get(secondCell) == null) {
                    chosenWalls.put(secondCell, secondCell.getRandomClosedWall());
                }
            }

            List<Wall> wallsToOpen = new ArrayList<>(chosenWalls.values());
            Collections.shuffle(wallsToOpen);

            for (Wall wall : wallsToOpen) {
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
