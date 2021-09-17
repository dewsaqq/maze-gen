package maze;

import java.util.ArrayList;

public class Wall {
    private final ArrayList<Cell> adjacentCells;
    private boolean isOpen = false;

    public Wall(Cell firstCell, Cell secondCell) {
        adjacentCells = new ArrayList<>();
        adjacentCells.add(firstCell);
        adjacentCells.add(secondCell);
    }

    public void openWall() {
        isOpen = true;
    }

    public ArrayList<Cell> getAdjacentCells() {
        return adjacentCells;
    }

    public Cell getAdjacentCell(Cell cell) {
        if (adjacentCells.get(0).equals(cell)) return adjacentCells.get(1);
        else return adjacentCells.get(0);
    }

    public boolean isOpen() {
        return isOpen;
    }
}
