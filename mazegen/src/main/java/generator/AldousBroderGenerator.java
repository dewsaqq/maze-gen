package generator;

import helper.CollectionHelper;
import maze.Cell;
import maze.Grid;

import java.util.ArrayList;

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
        activeCell.visitCell();
        int numberOfCellsVisited = 1;
        int numberOfCells = grid.getHeight() * grid.getWidth();

        ArrayList<Cell> activeCellNeighbours = grid.getNeighbours(activeCell);
        Cell nextCell = CollectionHelper.getRandomListElement(activeCellNeighbours);

        while (numberOfCellsVisited < numberOfCells) {
            if (!nextCell.isVisited()) {
                nextCell.visitCell();
                numberOfCellsVisited++;
                activeCell.getWall(nextCell).openWall();
            }

            activeCell = nextCell;
            activeCellNeighbours = grid.getNeighbours(activeCell);
            nextCell = CollectionHelper.getRandomListElement(activeCellNeighbours);
        }


        return grid;
    }
}
