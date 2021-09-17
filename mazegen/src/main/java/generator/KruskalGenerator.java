package generator;

import helper.CollectionHelper;
import maze.Cell;
import maze.Grid;
import maze.Wall;

import java.util.*;

public class KruskalGenerator extends SetBasedGenerator {
    public KruskalGenerator(int size) {
        super(size);
    }

    public KruskalGenerator(int gridHeight, int gridWidth) {
        super(gridHeight, gridWidth);
    }

    @Override
    public Grid generateMaze() {
        ArrayList<Wall> walls = grid.getWalls();

        createSetForEachCell();

        Cell controlCell = cellForest.keySet().iterator().next(); //controlCell to check if all cells are in single set
        while (cellForest.get(controlCell).size() != grid.getNumberOfCells()) {
            Wall drawnWall = CollectionHelper.getRandomListElement(walls);
            ArrayList<Cell> adjacentCells = drawnWall.getAdjacentCells();
            Cell firstCell = adjacentCells.get(0);
            Cell secondCell = adjacentCells.get(1);

            if (!areCellsInSingleSet(firstCell, secondCell)) {
                drawnWall.openWall();
                unionSets(firstCell, secondCell);
            }

            walls.remove(drawnWall);
        }

        return grid;
    }
}
