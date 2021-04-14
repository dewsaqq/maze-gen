package maze;

public class Wall {
    public static final int NORTH = 0;
    public static final int SOUTH = 2;
    public static final int EAST = 1;
    public static final int WEST = 3;
    public static final int NUMBER_OF_POSITIONS = 4;

    private boolean isOpen = false;
    private final boolean isBorder;
    private Cell adjacentCell = null;

    public Wall(boolean isBorder) {
        this.isBorder = isBorder;
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
