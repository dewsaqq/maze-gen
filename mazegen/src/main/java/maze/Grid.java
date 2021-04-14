package maze;

import java.util.ArrayList;

public class Grid {
    private final Cell[][] cells;
    private final int height;
    private final int width;

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

    private void initGrid() {
        initCells();
        initBorderWalls();
        initWalls();
    }

    private void initCells() {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    private void initBorderWalls() {
        for(int j = 0; j < width; j++) {
            cells[0][j].setWall(Wall.NORTH, new Wall(true));
            cells[height - 1][j].setWall(Wall.SOUTH, new Wall(true));
        }

        for(int i = 0; i < height; i++) {
            cells[i][0].setWall(Wall.WEST, new Wall(true));
            cells[i][width - 1].setWall(Wall.EAST, new Wall(true));
        }
    }

    private void initWalls() {
        for (int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(cells[i][j].getWall(Wall.NORTH) == null) cells[i][j].setWall(Wall.NORTH, new Wall(cells[i - 1][j]));
                if(cells[i][j].getWall(Wall.SOUTH) == null) cells[i][j].setWall(Wall.SOUTH, new Wall(cells[i + 1][j]));
                if(cells[i][j].getWall(Wall.EAST) == null) cells[i][j].setWall(Wall.EAST, new Wall(cells[i][j + 1]));
                if(cells[i][j].getWall(Wall.WEST) == null) cells[i][j].setWall(Wall.WEST, new Wall(cells[i][j - 1]));
            }
        }
    }

    public ArrayList<Cell> getNeighbours(Cell cell, boolean isVisited) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            if(!cell.getWall(i).isBorder() && cell.getWall(i).getAdjacentCell().isVisited() == isVisited)
                neighbours.add(cell.getWall(i).getAdjacentCell());
        }

        return neighbours;
    }


    @Override
    public String toString() {
        StringBuilder gridString = new StringBuilder();

        for(int j = 0; j < width*2+1; j++) {
            gridString.append("#");
        }
        gridString.append("\n");

        for(int i = 0; i < height; i++) {
            gridString.append("#");
            for(int j = 0; j < width; j++) {
                gridString.append(" ");
                if(cells[i][j].getWall(Wall.EAST).isOpen()) gridString.append(" ");
                else gridString.append("#");
            }
            gridString.append("\n");

            for(int j = 0; j < width; j++) {
                gridString.append("#");
                if(cells[i][j].getWall(Wall.SOUTH).isOpen()) gridString.append(" ");
                else gridString.append("#");
            }
            gridString.append("#\n");
        }

        return gridString.toString();
    }
}
