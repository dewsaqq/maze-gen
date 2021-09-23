package maze;

import helper.CollectionHelper;

import java.util.*;
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
                cells[row][column] = new Cell();
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
        Wall verticalWall = new Wall(firstCell, secondCell, Wall.Orientation.VERTICAL);
        walls.add(verticalWall);
        firstCell.addWall(Wall.Position.EAST, verticalWall);
        secondCell.addWall(Wall.Position.WEST, verticalWall);
    }

    private void createHorizontalWall(Cell firstCell, Cell secondCell) {
        Wall horizontalWall = new Wall(firstCell, secondCell, Wall.Orientation.HORIZONTAL);
        walls.add(horizontalWall);
        firstCell.addWall(Wall.Position.SOUTH, horizontalWall);
        secondCell.addWall(Wall.Position.NORTH, horizontalWall);
    }

    public Cell getCell(int row, int column) {
        return cells[row][column];
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getRandomCell() {
        return cells[random.nextInt(height)][random.nextInt(width)];
    }

    public Cell getRandomNeighbour(Cell cell) {
        return CollectionHelper.getRandomListElement(getNeighbours(cell));
    }

    public Cell getRandomUnvisitedNeighbour(Cell cell) {
        if (getUnvisitedNeighbours(cell).isEmpty()) return null;
        return CollectionHelper.getRandomListElement(getUnvisitedNeighbours(cell));
    }

    private ArrayList<Cell> getNeighbours(Cell cell) {
        ArrayList<Cell> cellNeighbours = new ArrayList<>();

        for (Wall wall : cell.getWalls()) {
            cellNeighbours.add(wall.getAdjacentCell(cell));
        }

        return cellNeighbours;
    }

    private List<Cell> getUnvisitedNeighbours(Cell cell) {
        return getNeighbours(cell).stream()
                    .filter(c -> !c.isVisited())
                    .collect(Collectors.toList());
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

    public List<Wall> getClosedWalls() {
        return getWalls().stream()
                .filter(w -> !w.isOpen())
                .collect(Collectors.toList());
    }

    public List<Wall> getOpenWalls() {
        return getWalls().stream()
                .filter(Wall::isOpen)
                .collect(Collectors.toList());
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
                if (cells[i][j].getWall(Wall.Position.EAST) != null && cells[i][j].getWall(Wall.Position.EAST).isOpen())
                    gridString.append(" ");
                else gridString.append("#");
            }
            gridString.append("\n");

            for (int j = 0; j < width; j++) {
                gridString.append("#");
                if (cells[i][j].getWall(Wall.Position.SOUTH) != null && cells[i][j].getWall(Wall.Position.SOUTH).isOpen())
                    gridString.append(" ");
                else gridString.append("#");
            }
            gridString.append("#\n");
        }

        return gridString.toString();
    }
}
