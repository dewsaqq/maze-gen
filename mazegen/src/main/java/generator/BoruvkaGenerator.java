package generator;

import maze.Cell;
import maze.Grid;
import maze.Wall;

import java.util.*;
import java.util.stream.Collectors;

public class BoruvkaGenerator extends Generator {
    private final HashMap<Cell, HashSet<Cell>> cellForest = new HashMap<>();

    public BoruvkaGenerator(int size) {
        this(size, size);
    }

    public BoruvkaGenerator(int gridHeight, int gridWidth) {
        super(gridHeight, gridWidth);
    }

    @Override
    public Grid generateMaze() {
        List<HashSet<Cell>> distinctSets = generateDistinctSets();

        for (Cell cell : grid.getCellsList()) {
            cellForest.put(cell, new HashSet<>(Collections.singletonList(cell)));
        }

        while (distinctSets.size() != 1) {
            HashSet<Wall> chosenWallsToOpen = new HashSet<>();
            System.out.println(distinctSets.size());

            for (HashSet<Cell> cellTree : distinctSets) {
                Cell chosenCell;
                Wall chosenWall = null;

                while (chosenWall == null) {
                    chosenCell = Generator.getRandomSetElement(cellTree);
                    chosenWall = chosenCell.getRandomClosedWall();
                    if (isWallWithinSingleSet(chosenWall)) chosenWall = null;
                }

                chosenWallsToOpen.add(chosenWall);
            }

            openChosenWalls(chosenWallsToOpen);

            distinctSets = generateDistinctSets();
        }

        return grid;
    }

    private boolean isWallWithinSingleSet(Wall wall) {
        if (wall == null) return false;
        ArrayList<Cell> adjacentCells = wall.getAdjacentCells();
        Cell firstCell = adjacentCells.get(0);
        Cell secondCell = adjacentCells.get(1);

        return areCellsInSingleSet(firstCell, secondCell);
    }

    private void openChosenWalls(HashSet<Wall> walls) {
        for (Wall wall : walls) {
            ArrayList<Cell> adjacentCells = wall.getAdjacentCells();
            Cell firstCell = adjacentCells.get(0);
            Cell secondCell = adjacentCells.get(1);

            if (!areCellsInSingleSet(firstCell, secondCell)) {
                wall.openWall();
                unionSets(firstCell, secondCell);
            }
        }
    }

    private void unionSets(Cell firstCell, Cell secondCell) {
        cellForest.get(firstCell).addAll(cellForest.get(secondCell));
        for (Cell cell : cellForest.get(firstCell)) {
            cellForest.replace(cell, cellForest.get(firstCell));
        }
    }

    private boolean areCellsInSingleSet(Cell firstCell, Cell secondCell) {
        return cellForest.get(firstCell).contains(secondCell);
    }

    private List<HashSet<Cell>> generateDistinctSets() {
        return cellForest.values().stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
