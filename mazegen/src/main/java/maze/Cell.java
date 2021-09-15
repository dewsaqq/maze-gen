package maze;

import java.util.ArrayList;
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

    public Wall getWall(Cell adjacentCell) {
        for (Wall wall : walls.values()) {
            if (wall.getAdjacentCell(this).equals(adjacentCell)) return wall;
        }

        return null;
    }

    public ArrayList<Cell> getNeighbours() {
        ArrayList<Cell> cellNeighbours = new ArrayList<>();

        for (Wall wall : walls.values()) {
            cellNeighbours.add(wall.getAdjacentCell(this));
        }

        return cellNeighbours;
    }

    public ArrayList<Cell> getUnvisitedNeighbours() {
        ArrayList<Cell> cellUnvisitedNeighbours = new ArrayList<>();

        for (Wall wall : walls.values()) {
            Cell neighbour = wall.getAdjacentCell(this);
            if (!neighbour.isVisited()) cellUnvisitedNeighbours.add(neighbour);
        }

        return cellUnvisitedNeighbours;
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
