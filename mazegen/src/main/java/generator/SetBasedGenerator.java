package generator;

import maze.Cell;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public abstract class SetBasedGenerator extends Generator {
    protected final HashMap<Cell, HashSet<Cell>> cellForest = new HashMap<>();

    public SetBasedGenerator(int size) {
        super(size);
    }

    public SetBasedGenerator(int gridHeight, int gridWidth) {
        super(gridHeight, gridWidth);
    }

    protected void createSetsForEachCell() {
        for (Cell cell : grid.getCellsList()) {
            cellForest.put(cell, new HashSet<>(Collections.singletonList(cell)));
        }
    }

    protected void unionSets(Cell firstCell, Cell secondCell) {
        cellForest.get(firstCell).addAll(cellForest.get(secondCell));
        for (Cell cell : cellForest.get(firstCell)) {
            cellForest.replace(cell, cellForest.get(firstCell));
        }
    }

    protected boolean areCellsInSingleSet(Cell firstCell, Cell secondCell) {
        return cellForest.get(firstCell).contains(secondCell);
    }
}
