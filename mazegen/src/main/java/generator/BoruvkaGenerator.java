package generator;

import maze.Cell;
import maze.Grid;
import maze.Wall;

import java.util.*;

public class BoruvkaGenerator extends Generator {
    private ArrayList<HashSet<Cell>> cellForest;

    public BoruvkaGenerator(int size) {
        this(size, size);
    }

    public BoruvkaGenerator(int gridHeight, int gridWidth) {
        super(gridHeight, gridWidth);
        initializeGenerator();
    }

    @Override
    public Grid generateMaze() {
        while (cellForest.size() != 1) {
            HashSet<Wall> chosenWallsToOpen = new HashSet<>();

            for (HashSet<Cell> cellTree : cellForest) {
                Cell chosenCell;
                Wall chosenWall = null;

                while (chosenWall == null) {
                    chosenCell = Generator.getRandomSetElement(cellTree);
                    chosenWall = chosenCell.getRandomClosedWall();
                    if (isWallConnectingSingleSet(chosenWall)) chosenWall = null;
                }

                chosenWallsToOpen.add(chosenWall);
            }

            for (Wall wall : chosenWallsToOpen) {
                if (!isWallConnectingSingleSet(wall)) {
                    wall.openWall();
                    unionTwoSets(wall);
                }
            }
        }

        return grid;
    }

    private void initializeGenerator() {
        cellForest = new ArrayList<>();

        for (Cell cell : grid.getCellsList()) {
            cellForest.add(new HashSet<>(Collections.singletonList(cell)));
        }
    }

    private boolean isWallConnectingSingleSet(Wall wall) {
        if (wall == null) return false;
        Cell firstCell = wall.getAdjacentCells().get(0);
        Cell secondCell = wall.getAdjacentCells().get(1);

        for (HashSet<Cell> cellTree : cellForest) {
            if (cellTree.contains(firstCell)) {
                return cellTree.contains(secondCell);
            }
        }

        return false;
    }

    private void unionTwoSets(Wall wall) {
        Cell firstCell = wall.getAdjacentCells().get(0);
        Cell secondCell = wall.getAdjacentCells().get(1);

        HashSet<Cell> firstCellTree = null;
        HashSet<Cell> secondCellTree = null;

        for (HashSet<Cell> cellTree : cellForest) {
            if (cellTree.contains(firstCell)) {
                firstCellTree = cellTree;
            }
            if (cellTree.contains(secondCell)) {
                secondCellTree = cellTree;
            }
            if (firstCellTree != null && secondCellTree != null) break;
        }

        firstCellTree.addAll(secondCellTree);
        cellForest.remove(secondCellTree);
    }
}
