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
    public boolean isOpen() {
        return isOpen;
    }

    public void openWall() {
        isOpen = true;
    }

    public ArrayList<Cell> getAdjacentCells() {
        return adjacentCells;
    }
}
