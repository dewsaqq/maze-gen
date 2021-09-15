package generator;

import maze.Cell;
import maze.Grid;

import java.util.ArrayList;
import java.util.Random;

public class AldousBroderGenerator extends Generator {
    public AldousBroderGenerator(int size) {
        super(size);
    }

    public AldousBroderGenerator(int gridHeight, int gridWidth) {
        super(gridHeight, gridWidth);
    }

    @Override
    public Grid generateMaze() {
        Random random = new Random();

        Cell activeCell = grid.getRandomCell();
        activeCell.visitCell();
        int numberOfCellsVisited = 1;
        int numberOfCells = grid.getHeight() * grid.getWidth();

        ArrayList<Cell> activeCellNeighbours = grid.getNeighbours(activeCell);
        Cell nextCell = activeCellNeighbours.get(random.nextInt(activeCellNeighbours.size()));

        while (numberOfCellsVisited < numberOfCells) {
            if (!nextCell.isVisited()) {
                nextCell.visitCell();
                numberOfCellsVisited++;
                activeCell.getWall(nextCell).openWall();
            }

            activeCell = nextCell;
            activeCellNeighbours = grid.getNeighbours(activeCell);
            nextCell = activeCellNeighbours.get(random.nextInt(activeCellNeighbours.size()));
        }


        return grid;
    }
}
