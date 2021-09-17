package generator;

import maze.Cell;
import maze.Grid;
import maze.Maze;

import java.util.Stack;

public class RecursiveBacktrackerGenerator extends Generator {
    @Override
    public Maze generateMaze(Grid grid) {
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

        return new Maze(grid);
    }
}
