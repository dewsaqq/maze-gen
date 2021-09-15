package generator;

import maze.Cell;
import maze.Grid;
import maze.Wall;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class PrimGenerator extends Generator {
    public PrimGenerator(int size) {
        this(size, size);
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

        Wall nextWallToRemove;
        while (visitedCells.size() != grid.getNumberOfCells()) {
            nextWallToRemove = wallsToCheck.get(random.nextInt(wallsToCheck.size()));
            wallsToCheck.remove(nextWallToRemove);

            ArrayList<Cell> adjacentCells = nextWallToRemove.getAdjacentCells();
            Cell firstCell = adjacentCells.get(0);
            Cell secondCell = adjacentCells.get(1);

            if (visitedCells.contains(firstCell)) {
                if (visitedCells.contains(secondCell)) continue;
                activeCell = secondCell;
            } else activeCell = firstCell;

            nextWallToRemove.openWall();
            visitedCells.add(activeCell);

            for (Wall wall : activeCell.getAllWalls()) {
                if (!wall.isOpen() && !wallsToCheck.contains(wall)) wallsToCheck.add(wall);
            }
        }

        return grid;
    }
}
