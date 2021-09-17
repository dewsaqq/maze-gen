package generator;

import helper.CollectionHelper;
import maze.Cell;
import maze.Grid;
import maze.Wall;

import java.util.*;

public class WilsonGenerator extends Generator {
    public WilsonGenerator(int size) {
        this(size, size);
    }

    public WilsonGenerator(int gridHeight, int gridWidth) {
        super(gridHeight, gridWidth);
    }

    @Override
    public Grid generateMaze() {
        List<Cell> unvisitedCells = grid.getCellsList();
        HashSet<Cell> visitedCells = new HashSet<>();

        Cell firstRandomCell = CollectionHelper.getRandomListElement(unvisitedCells);
        visitedCells.add(firstRandomCell);
        unvisitedCells.remove(firstRandomCell);

        while (visitedCells.size() != grid.getNumberOfCells()) {
            Cell activeCell = CollectionHelper.getRandomListElement(unvisitedCells);
            HashMap<Cell, Wall> randomWalkPath = generateRandomWalk(activeCell, visitedCells);

            while (!visitedCells.contains(activeCell)) {
                Wall selectedWall = randomWalkPath.get(activeCell);
                selectedWall.openWall();

                visitedCells.add(activeCell);
                unvisitedCells.remove(activeCell);

                activeCell = selectedWall.getAdjacentCell(activeCell);
            }

        }

        return grid;
    }

    private HashMap<Cell, Wall> generateRandomWalk(Cell startCell, Set<Cell> stopCells) {
        HashMap<Cell, Wall> randomWalk = new HashMap<>();

        Wall randomWall = startCell.getRandomWall();
        randomWalk.put(startCell, randomWall);

        Cell nextCell = randomWall.getAdjacentCell(startCell);

        while (!stopCells.contains(nextCell)) {
            randomWall = nextCell.getRandomWall();
            randomWalk.put(nextCell, randomWall);

            nextCell = randomWall.getAdjacentCell(nextCell);
        }

        return randomWalk;
    }
}
