package generator;

import helper.CollectionHelper;
import maze.Cell;
import maze.Grid;
import maze.Maze;
import maze.Wall;

import java.util.ArrayList;
import java.util.HashSet;

public class PrimGenerator extends Generator {
    @Override
    public Maze generateMaze(Grid grid) {
        HashSet<Cell> visitedCells = new HashSet<>();
        Cell activeCell = grid.getRandomCell();
        ArrayList<Wall> wallsToCheck = new ArrayList<>(activeCell.getWalls());
        visitedCells.add(activeCell);

        while (visitedCells.size() != grid.getNumberOfCells()) {
            Wall nextWallToOpen = CollectionHelper.getRandomListElement(wallsToCheck);
            wallsToCheck.remove(nextWallToOpen);

            ArrayList<Cell> adjacentCells = nextWallToOpen.getAdjacentCells();
            Cell firstCell = adjacentCells.get(0);
            Cell secondCell = adjacentCells.get(1);

            if (visitedCells.contains(firstCell)) {
                if (visitedCells.contains(secondCell)) continue;
                activeCell = secondCell;
            } else activeCell = firstCell;

            nextWallToOpen.openWall();
            visitedCells.add(activeCell);

            for (Wall wall : activeCell.getClosedWalls()) {
                if (!wallsToCheck.contains(wall)) wallsToCheck.add(wall);
            }
        }

        return new Maze(grid);
    }
}
