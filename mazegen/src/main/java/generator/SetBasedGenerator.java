package generator;

import maze.Cell;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public abstract class SetBasedGenerator extends Generator {
    protected HashMap<Cell, HashSet<Cell>> cellForest;

    protected void createSetForEachCell(List<Cell> cells) {
        for (Cell cell : cells) {
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
