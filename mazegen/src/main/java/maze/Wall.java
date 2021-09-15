package maze;

public class Wall {
    public static final int NORTH = 0;
    public static final int SOUTH = 2;
    public static final int EAST = 1;
    public static final int WEST = 3;
    public static final int NUMBER_OF_DIRECTIONS = 4;

    private boolean isOpen = false;
    private final boolean isBorder;
    private final Cell adjacentCell;

    public Wall() {
        this.adjacentCell = null;
        this.isBorder = true;
    }

    public Wall(Cell adjacentCell) {
        this.adjacentCell = adjacentCell;
        this.isBorder = false;
    }

    public boolean isBorder() {
        return isBorder;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen() {
        isOpen = true;
    }

    public Cell getAdjacentCell() {
        return adjacentCell;
    }

    public static int getOppositeDirection(int direction) {
        return (direction + 2) % 4;
    }
}
