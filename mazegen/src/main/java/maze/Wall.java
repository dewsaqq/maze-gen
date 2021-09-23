package maze;

import java.util.ArrayList;

public class Wall {
    private final ArrayList<Cell> adjacentCells;
    private final Orientation orientation;
    private boolean isOpen = false;

    public Wall(Cell firstCell, Cell secondCell, Orientation orientation) {
        this.orientation = orientation;
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

    public Orientation getOrientation() {
        return orientation;
    }

    public boolean isOpen() {
        return isOpen;
    }

    enum Position {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }
}
