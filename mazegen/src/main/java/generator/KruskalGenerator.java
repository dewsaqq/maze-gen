package generator;

import maze.Cell;
import maze.Grid;
import maze.Wall;

import java.util.*;

public class KruskalGenerator extends Generator {
    private ArrayList<Wall> walls;
    private HashMap<Cell, HashSet<Cell>> cellForest;

    public KruskalGenerator(int size) {
        this(size, size);
    }

    public KruskalGenerator(int gridHeight, int gridWidth) {
        super(gridHeight, gridWidth);
        initializeGenerator();
    }

    @Override
    public Grid generateMaze() {
        Random random = new Random();
        Wall drawnWall;
        Cell controlCell = cellForest.keySet().iterator().next();

        while (cellForest.get(controlCell).size() != grid.getNumberOfCells()) {
            drawnWall = walls.get(random.nextInt(walls.size()));

            ArrayList<Cell> adjacentCells = drawnWall.getAdjacentCells();
            Cell firstCell = adjacentCells.get(0);
            Cell secondCell = adjacentCells.get(1);

            if (!cellForest.get(firstCell).contains(secondCell)) {
                drawnWall.openWall();
                cellForest.get(firstCell).addAll(cellForest.get(secondCell));
                for (Cell cell : cellForest.get(firstCell)) {
                    cellForest.replace(cell, cellForest.get(firstCell));
                }
            }

            walls.remove(drawnWall);
        }

        return grid;
    }

    private void initializeGenerator() {
        walls = grid.getWalls();
        cellForest = new HashMap<>();

        for (Cell cell : grid.getCellsList()) {
            cellForest.put(cell, new HashSet<>(Collections.singletonList(cell)));
        }
    }
}
