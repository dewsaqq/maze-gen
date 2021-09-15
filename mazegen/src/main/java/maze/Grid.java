package maze;

import java.util.ArrayList;
import java.util.Random;

public class Grid {
    private final Cell[][] cells;
    private final Random random = new Random();
    public final int height;
    public final int width;

    public Grid(int size) {
        this(size, size);
    }

    public Grid(int height, int width) {
        this.cells = new Cell[height][width];
        this.height = height;
        this.width = width;

        initGrid();
    }

    public Cell getCell(int row, int column) {
        return cells[row][column];
    }

    public Cell getRandomCell() {
        return cells[random.nextInt(height)][random.nextInt(width)];
    }

    private void initGrid() {
        initCells();
        initBordersWalls();
        initCellsWalls();
    }

    private void initCells() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    private void initBordersWalls() {
        for (int j = 0; j < width; j++) {
            cells[0][j].initWall(Wall.NORTH, new Wall());
            cells[height - 1][j].initWall(Wall.SOUTH, new Wall());
        }

        for (int i = 0; i < height; i++) {
            cells[i][0].initWall(Wall.WEST, new Wall());
            cells[i][width - 1].initWall(Wall.EAST, new Wall());
        }
    }

    private void initCellsWalls() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (cells[i][j].getWall(Wall.NORTH) == null)
                    cells[i][j].initWall(Wall.NORTH, new Wall(cells[i - 1][j]));
                if (cells[i][j].getWall(Wall.SOUTH) == null)
                    cells[i][j].initWall(Wall.SOUTH, new Wall(cells[i + 1][j]));
                if (cells[i][j].getWall(Wall.EAST) == null) cells[i][j].initWall(Wall.EAST, new Wall(cells[i][j + 1]));
                if (cells[i][j].getWall(Wall.WEST) == null) cells[i][j].initWall(Wall.WEST, new Wall(cells[i][j - 1]));
            }
        }
    }

    public ArrayList<Cell> getNeighbours(Cell cell, boolean isVisited) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        for (int i = 0; i < Wall.NUMBER_OF_DIRECTIONS; i++) {
            if (!cell.getWall(i).isBorder() && cell.getWall(i).getAdjacentCell().isVisited() == isVisited)
                neighbours.add(cell.getWall(i).getAdjacentCell());
        }

        return neighbours;
    }

    public ArrayList<Cell> getAllNeighbours(Cell cell) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        for (int i = 0; i < Wall.NUMBER_OF_DIRECTIONS; i++) {
            if (!cell.getWall(i).isBorder()) neighbours.add(cell.getWall(i).getAdjacentCell());
        }

        return neighbours;
    }

    @Override
    public String toString() {
        StringBuilder gridString = new StringBuilder();

        gridString.append("#".repeat(Math.max(0, width * 2 + 1)));
        gridString.append("\n");

        for (int i = 0; i < height; i++) {
            gridString.append("#");
            for (int j = 0; j < width; j++) {
                gridString.append(" ");
                if (cells[i][j].getWall(Wall.EAST).isOpen()) gridString.append(" ");
                else gridString.append("#");
            }
            gridString.append("\n");

            for (int j = 0; j < width; j++) {
                gridString.append("#");
                if (cells[i][j].getWall(Wall.SOUTH).isOpen()) gridString.append(" ");
                else gridString.append("#");
            }
            gridString.append("#\n");
        }

        return gridString.toString();
    }
}
