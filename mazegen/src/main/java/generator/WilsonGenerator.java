package generator;

import helper.CollectionHelper;
import maze.Cell;
import maze.Grid;
import maze.Maze;
import maze.Wall;

import java.util.*;

public class WilsonGenerator extends Generator {
    private List<Cell> unvisitedCells;
    private HashSet<Cell> visitedCells;

    @Override
    public Maze generateMaze(Grid grid) {
        unvisitedCells = grid.getCellsList();
        visitedCells = new HashSet<>();

        Cell firstRandomCell = CollectionHelper.getRandomListElement(unvisitedCells);
        visitCell(firstRandomCell);

        while (visitedCells.size() != grid.getNumberOfCells()) {
            Cell activeCell = CollectionHelper.getRandomListElement(unvisitedCells);
            HashMap<Cell, Wall> randomWalkPath = generateRandomWalk(activeCell);
            openWallsFromRandomWalk(randomWalkPath, activeCell);
        }

        return new Maze(grid);
    }

    private HashMap<Cell, Wall> generateRandomWalk(Cell startCell) {
        HashMap<Cell, Wall> randomWalk = new HashMap<>();

        Wall randomWall = startCell.getRandomWall();
        randomWalk.put(startCell, randomWall);

        Cell nextCell = randomWall.getAdjacentCell(startCell);

        while (!visitedCells.contains(nextCell)) {
            randomWall = nextCell.getRandomWall();
            randomWalk.put(nextCell, randomWall);

            nextCell = randomWall.getAdjacentCell(nextCell);
        }

        return randomWalk;
    }

    private void openWallsFromRandomWalk(HashMap<Cell, Wall> randomWalkPath, Cell activeCell) {
        while (!visitedCells.contains(activeCell)) {
            Wall selectedWall = randomWalkPath.get(activeCell);
            selectedWall.openWall();

            visitCell(activeCell);

            activeCell = selectedWall.getAdjacentCell(activeCell);
        }
    }

    private void visitCell(Cell cell) {
        visitedCells.add(cell);
        unvisitedCells.remove(cell);
    }
}
