package generator;

import maze.Cell;
import maze.Grid;
import maze.Wall;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class PrimGenerator extends Generator {
    public PrimGenerator(int size) {
        super(size);
    }

    public PrimGenerator(int gridHeight, int gridWidth) {
        super(gridHeight, gridWidth);
    }

    @Override
    public Grid generateMaze() {
        Random random = new Random();
        HashSet<Cell> visitedCells = new HashSet<>();
        Cell activeCell = grid.getRandomCell();
        ArrayList<Wall> wallsToCheck = new ArrayList<>(activeCell.getAllWalls());
        visitedCells.add(activeCell);

        while (visitedCells.size() != grid.getNumberOfCells()) {
            Wall nextWallToOpen = wallsToCheck.get(random.nextInt(wallsToCheck.size()));
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

        return grid;
    }
}
