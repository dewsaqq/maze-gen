package maze;

import helper.CollectionHelper;

import java.util.ArrayList;
import java.util.Collection;
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

    public Wall getRandomWall() {
        return CollectionHelper.getRandomListElement(new ArrayList<>(walls.values()));
    }

    public Wall getRandomClosedWall() {
        ArrayList<Wall> closedWalls = getClosedWalls();
        if (closedWalls.isEmpty()) return null;
        return CollectionHelper.getRandomListElement(closedWalls);
    }

    public Collection<Wall> getWalls() {
        return walls.values();
    }

    public ArrayList<Wall> getClosedWalls() {
        ArrayList<Wall> closedWalls = new ArrayList<>();
        for (Wall wall : walls.values()) {
            if (!wall.isOpen()) closedWalls.add(wall);
        }

        return closedWalls;
    }
}
