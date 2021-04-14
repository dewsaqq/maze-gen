package generator;

import maze.Cell;
import maze.Grid;

import java.util.ArrayList;
import java.util.Random;

public class BacktrackingGenerator {
    private final Grid grid;
    private final int height;
    private final int width;

    public BacktrackingGenerator(int height, int width) {
        this.grid = new Grid(height, width);
        this.height = height;
        this.width = width;
    }

    public Grid generateMaze() {
        Random random = new Random();

        Cell activeCell = grid.getCell(random.nextInt(height), random.nextInt(width));

        ArrayList<Cell> cellTracker = new ArrayList<>();
        ArrayList<Cell> activeCellNeighbours = grid.getNeighbours(activeCell, false);
        Cell nextCell = activeCellNeighbours.get(random.nextInt(activeCellNeighbours.size()));
        cellTracker.add(nextCell);

        while (cellTracker.size() != 0) {
            nextCell.visitCell();
            activeCell.openWallWithCell(nextCell);
            activeCell = nextCell;

            activeCellNeighbours = grid.getNeighbours(activeCell, false);
            if(activeCellNeighbours.size() == 0) {
                nextCell = cellTracker.get(cellTracker.size() - 1);
                cellTracker.remove(cellTracker.size() - 1);
            }
            else {
                nextCell = activeCellNeighbours.get(random.nextInt(activeCellNeighbours.size()));
                cellTracker.add(nextCell);
            }
        }

        return grid;
    }
}
