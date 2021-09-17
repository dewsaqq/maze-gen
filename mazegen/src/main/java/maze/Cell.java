package maze;

import helper.CollectionHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class Cell {
    private final HashMap<WallPosition, Wall> walls;
    private boolean isVisited = false;

    public Cell() {
        walls = new HashMap<>();
    }

    public void addWall(WallPosition position, Wall wall) {
        walls.put(position, wall);
    }

    public void visitCell() {
        isVisited = true;
    }

    public Collection<Wall> getWalls() {
        return walls.values();
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

    public List<Wall> getClosedWalls() {
        return walls.values().stream()
                    .filter(w -> !w.isOpen())
                    .collect(Collectors.toList());
    }

    public Wall getRandomClosedWall() {
        List<Wall> closedWalls = getClosedWalls();
        if (closedWalls.isEmpty()) return null;
        return CollectionHelper.getRandomListElement(closedWalls);
    }

    public boolean isVisited() {
        return isVisited;
    }
}
