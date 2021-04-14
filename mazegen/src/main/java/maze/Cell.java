package maze;

public class Cell {
    private boolean isVisited = false;
    private final Wall[] walls;

    public Cell() {
        walls = new Wall[4];
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void visitCell() {
        this.isVisited = true;
    }

    public void setWall(int direction, Wall wall) {
        walls[direction] = wall;
    }

    public Wall getWall(int direction) {
        return walls[direction];
    }

    public void openWall(int direction) {
        walls[direction].setOpen();
        walls[direction].getAdjacentCell().getWall(Wall.getOppositeDirection(direction)).setOpen();
    }

    public void openWallWithCell(Cell cell) {
        for(int i = 0; i < Wall.NUMBER_OF_POSITIONS; i++) {
            if(walls[i].getAdjacentCell() == cell) openWall(i);
        }
    }
}
