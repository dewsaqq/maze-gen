package generator;

import maze.Cell;
import maze.Grid;
import maze.Maze;

import java.util.HashSet;

public class AldousBroderGenerator extends Generator {
    @Override
    public Maze generateMaze(Grid grid) {
        HashSet<Cell> visitedCell = new HashSet<>();

        Cell activeCell = grid.getRandomCell();
        activeCell.visitCell();
        visitedCell.add(activeCell);

        Cell nextCell = grid.getRandomNeighbour(activeCell);

        while (visitedCell.size() != grid.getNumberOfCells()) {
            if (!nextCell.isVisited()) {
                nextCell.visitCell();
                visitedCell.add(nextCell);
                activeCell.getWall(nextCell).openWall();
            }

            activeCell = nextCell;
            nextCell = grid.getRandomNeighbour(activeCell);
        }

        return new Maze(grid);
    }
}
