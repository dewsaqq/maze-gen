package generator;

import maze.Cell;
import maze.Grid;

import java.util.Stack;

public class RecursiveBacktrackerGenerator extends Generator {
    public RecursiveBacktrackerGenerator(int size) {
        super(size);
    }

    public RecursiveBacktrackerGenerator(int gridHeight, int gridWidth) {
        super(gridHeight, gridWidth);
    }

    @Override
    public Grid generateMaze() {
        Stack<Cell> cellTracker = new Stack<>();

        Cell activeCell = grid.getRandomCell();
        activeCell.visitCell();
        cellTracker.push(activeCell);

        while (!cellTracker.empty()) {
            activeCell = cellTracker.pop();
            Cell nextCell = grid.getRandomUnvisitedNeighbour(activeCell);

            if (nextCell == null) continue;
            cellTracker.push(activeCell);

            activeCell.getWall(nextCell).openWall();
            nextCell.visitCell();
            cellTracker.push(nextCell);
        }

        return grid;
    }
}
