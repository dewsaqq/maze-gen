package generator;

import helper.CollectionHelper;
import maze.Cell;
import maze.Grid;
import maze.Maze;
import maze.Wall;

import java.util.*;

public class KruskalGenerator extends SetBasedGenerator {
    @Override
    public Maze generateMaze(Grid grid) {
        cellForest = new HashMap<>();
        ArrayList<Wall> walls = grid.getWalls();

        createSetForEachCell(grid.getCellsList());

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

        return new Maze(grid);
    }
}
