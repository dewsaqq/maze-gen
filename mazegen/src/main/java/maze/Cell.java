package maze;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    public Collection<Wall> getAllWalls() {
        return walls.values();
    }
}
