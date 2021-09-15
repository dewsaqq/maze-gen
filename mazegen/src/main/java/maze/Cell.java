package maze;

import java.util.HashMap;

public class Cell {
    private final HashMap<WallPosition, Wall> walls;
    private boolean isVisited = false;

    public Cell() {
        walls = new HashMap<>();
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void visitCell() {
        this.isVisited = true;
    }

    public void addWall(WallPosition position, Wall wall) {
        walls.put(position, wall);
    }

    public Wall getWall(WallPosition position) {
        return walls.get(position);
    }

//    public void initWall(int direction, Wall wall) {
//        walls[direction] = wall;
//    }
//
//    public Wall getWall(int direction) {
//        return walls[direction];
//    }
//
//    public void openWall(int direction) {
//        walls[direction].setOpen();
//        walls[direction].getAdjacentCell().getWall(Wall.getOppositeDirection(direction)).setOpen();
//    }
//
//    public void openWallWithCell(Cell cell) {
//        for (int i = 0; i < Wall.NUMBER_OF_DIRECTIONS; i++) {
//            if (walls[i].getAdjacentCell() == cell) openWall(i);
//        }
//    }
}
