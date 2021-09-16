package generator;

import maze.Cell;
import maze.Grid;
import maze.Wall;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class WilsonGenerator extends Generator {
    public WilsonGenerator(int size) {
        this(size, size);
    }

    public WilsonGenerator(int gridHeight, int gridWidth) {
        super(gridHeight, gridWidth);
    }

    @Override
    public Grid generateMaze() {
        Random random = new Random();
        List<Cell> unvisitedCells = grid.getCellsList();
        HashSet<Cell> visitedCells = new HashSet<>();

        Cell activeCell = unvisitedCells.get(random.nextInt(unvisitedCells.size()));
        visitedCells.add(activeCell);
        unvisitedCells.remove(activeCell);

        while (visitedCells.size() != grid.getNumberOfCells()) {
            HashMap<Cell, Wall> path = new HashMap<>();
            activeCell = unvisitedCells.get(random.nextInt(unvisitedCells.size()));

            Wall selectedWall = activeCell.getRandomWall();
            path.put(activeCell, selectedWall);

            Cell selectedCell = selectedWall.getAdjacentCell(activeCell);

            while (!visitedCells.contains(selectedCell)) {
                selectedWall = selectedCell.getRandomWall();
                path.put(selectedCell, selectedWall);

                selectedCell = selectedWall.getAdjacentCell(selectedCell);
            }

            while (!activeCell.equals(selectedCell)) {
                Wall wallToOpen = path.get(activeCell);
                wallToOpen.openWall();

                visitedCells.add(activeCell);
                unvisitedCells.remove(activeCell);

                activeCell = wallToOpen.getAdjacentCell(activeCell);

            }

        }


        return grid;
    }
}
