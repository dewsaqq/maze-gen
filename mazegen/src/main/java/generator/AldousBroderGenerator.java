package generator;

import maze.Cell;
import maze.Grid;

import java.util.HashSet;

public class AldousBroderGenerator extends Generator {
    public AldousBroderGenerator(int size) {
        super(size);
    }

    public AldousBroderGenerator(int gridHeight, int gridWidth) {
        super(gridHeight, gridWidth);
    }

    @Override
    public Grid generateMaze() {
        Cell activeCell = grid.getRandomCell();
        HashSet<Cell> visitedCell = new HashSet<>();
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

        return grid;
    }
}
