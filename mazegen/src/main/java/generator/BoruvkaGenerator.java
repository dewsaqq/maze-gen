package generator;

import helper.CollectionHelper;
import maze.Cell;
import maze.Grid;
import maze.Maze;
import maze.Wall;

import java.util.*;
import java.util.stream.Collectors;

public class BoruvkaGenerator extends SetBasedGenerator {
    @Override
    public Maze generateMaze(Grid grid) {
        cellForest = new HashMap<>();
        List<HashSet<Cell>> distinctSets;

        createSetForEachCell(grid.getCellsList());
        distinctSets = getDistinctCellSets();

        while (distinctSets.size() != 1) {
            HashSet<Wall> wallsToOpen = new HashSet<>();

            for (HashSet<Cell> cellTree : distinctSets) {
                Wall selectedWall = null;

                while (selectedWall == null) {
                    Cell selectedCell = CollectionHelper.getRandomSetElement(cellTree);
                    selectedWall = selectedCell.getRandomClosedWall();
                    if (isWallWithinSingleSet(selectedWall)) selectedWall = null;
                }

                wallsToOpen.add(selectedWall);
            }

            openSelectedWalls(wallsToOpen);
            distinctSets = getDistinctCellSets();
        }

        return new Maze(grid);
    }

    private boolean isWallWithinSingleSet(Wall wall) {
        if (wall == null) return false;
        ArrayList<Cell> adjacentCells = wall.getAdjacentCells();
        Cell firstCell = adjacentCells.get(0);
        Cell secondCell = adjacentCells.get(1);

        return areCellsInSingleSet(firstCell, secondCell);
    }

    private void openSelectedWalls(HashSet<Wall> walls) {
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

    private List<HashSet<Cell>> getDistinctCellSets() {
        return cellForest.values().stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
