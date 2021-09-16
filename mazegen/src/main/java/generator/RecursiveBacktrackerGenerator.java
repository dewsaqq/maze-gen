package generator;

import helper.CollectionHelper;
import maze.Cell;
import maze.Grid;

import java.util.ArrayList;
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
            ArrayList<Cell> activeCellUnvisitedNeighbours = grid.getUnvisitedNeighbours(activeCell);

            if (activeCellUnvisitedNeighbours.isEmpty()) continue;
            cellTracker.push(activeCell);

            Cell nextCell = CollectionHelper.getRandomListElement(activeCellUnvisitedNeighbours);
            activeCell.getWall(nextCell).openWall();
            nextCell.visitCell();
            cellTracker.push(nextCell);
        }

        return grid;
    }
}
