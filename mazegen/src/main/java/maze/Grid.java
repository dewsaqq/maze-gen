package maze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Grid {
    private final Cell[][] cells;
    private final ArrayList<Wall> walls = new ArrayList<>();
    private final Random random = new Random();
    private final int height;
    private final int width;
    private final int numberOfCells;

    public Grid(int size) {
        this(size, size);
    }

    public Grid(int height, int width) {
        this.cells = new Cell[height][width];
        this.height = height;
        this.width = width;
        numberOfCells = height * width;

        initGrid();
    }

    private void initGrid() {
        initCells();
        initCellsWalls();
    }

    private void initCells() {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
            }
        }
    }

    private void initCellsWalls() {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (row == height - 1 && column == width - 1) {
                    continue;
                }
                if (column == width - 1) {
                    createHorizontalWall(cells[row][column], cells[row + 1][column]);
                } else if (row == height - 1) {
                    createVerticalWall(cells[row][column], cells[row][column + 1]);
                } else {
                    createVerticalWall(cells[row][column], cells[row][column + 1]);
                    createHorizontalWall(cells[row][column], cells[row + 1][column]);
                }
            }
        }
    }

    private void createVerticalWall(Cell firstCell, Cell secondCell) {
        Wall verticalWall = new Wall(firstCell, secondCell);
        walls.add(verticalWall);
        firstCell.addWall(WallPosition.EAST, verticalWall);
        secondCell.addWall(WallPosition.WEST, verticalWall);
    }

    private void createHorizontalWall(Cell firstCell, Cell secondCell) {
        Wall horizontalWall = new Wall(firstCell, secondCell);
        walls.add(horizontalWall);
        firstCell.addWall(WallPosition.SOUTH, horizontalWall);
        secondCell.addWall(WallPosition.NORTH, horizontalWall);
    }

    public Cell getCell(int row, int column) {
        return cells[row][column];
    }

    public Cell getRandomCell() {
        return cells[random.nextInt(height)][random.nextInt(width)];
    }

    public ArrayList<Cell> getNeighbours(Cell cell) {
        ArrayList<Cell> cellNeighbours = new ArrayList<>();

        for (Wall wall : cell.getAllWalls()) {
            cellNeighbours.add(wall.getAdjacentCell(cell));
        }

        return cellNeighbours;
    }

    public ArrayList<Cell> getUnvisitedNeighbours(Cell cell) {
        ArrayList<Cell> cellUnvisitedNeighbours = new ArrayList<>();

        for (Wall wall : cell.getAllWalls()) {
            Cell neighbour = wall.getAdjacentCell(cell);
            if (!neighbour.isVisited()) cellUnvisitedNeighbours.add(neighbour);
        }

        return cellUnvisitedNeighbours;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getNumberOfCells() {
        return numberOfCells;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public List<Cell> getCellsList() {
        return Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
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
                if (cells[i][j].getWall(WallPosition.EAST) != null && cells[i][j].getWall(WallPosition.EAST).isOpen()) gridString.append(" ");
                else gridString.append("#");
            }
            gridString.append("\n");

            for (int j = 0; j < width; j++) {
                gridString.append("#");
                if (cells[i][j].getWall(WallPosition.SOUTH) != null && cells[i][j].getWall(WallPosition.SOUTH).isOpen()) gridString.append(" ");
                else gridString.append("#");
            }
            gridString.append("#\n");
        }

        return gridString.toString();
    }
}
