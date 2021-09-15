package maze;

import java.awt.*;

public class Maze {
    public static final int CELL_WIDTH = 20; // maze square size
    public static final int MARGIN = 50; // buffer between window edge and maze
    private final Grid grid;

    public Maze(int size) {
        grid = new Grid(size);
    }

    public Maze(Grid grid) {
        this.grid = grid;
    }

    @Override
    public String toString() {
        String mazeString = grid.toString();
        mazeString = mazeString.replaceAll("1", "#");
        mazeString = mazeString.replaceAll("0", " ");
        return mazeString;
    }

    public void draw(Graphics g) // draws a maze and its solution
    {
        g.setColor(Color.BLACK);
        for (int i = 0; i < grid.width; i++) {
            for (int j = 0; j < grid.height; j++) {
                if (!grid.getCell(j, i).getWall(Wall.NORTH).isOpen()) // if there exists a wall to the
                // north
                {
                    g.drawLine((i * CELL_WIDTH + MARGIN), (j * CELL_WIDTH + MARGIN),
                            ((i + 1) * CELL_WIDTH + MARGIN), (j * CELL_WIDTH + MARGIN));
                }

                if (!grid.getCell(j, i).getWall(Wall.SOUTH).isOpen()) // if there exists a wall to the
                // south
                {
                    g.drawLine(i * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH
                            + MARGIN, (i + 1) * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH
                            + MARGIN);
                }

                if (!grid.getCell(j, i).getWall(Wall.EAST).isOpen()) // if there exists a wall to the
                // east
                {
                    g.drawLine((i + 1) * CELL_WIDTH + MARGIN, j * CELL_WIDTH
                            + MARGIN, (i + 1) * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH
                            + MARGIN);
                }

                if (!grid.getCell(j, i).getWall(Wall.WEST).isOpen()) // if there exists a wall to the
                // west
                {
                    g.drawLine(i * CELL_WIDTH + MARGIN, j * CELL_WIDTH + MARGIN, i
                            * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH + MARGIN);
                }
            }
        }
    }

    public Dimension windowSize() // returns the ideal size of the window (for
    // JScrollPanes)
    {
        return new Dimension(grid.width * CELL_WIDTH + MARGIN * 2, grid.height * CELL_WIDTH + MARGIN
                * 2);
    }
}
