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

    public void draw(Graphics g) // draws a maze and its solution
    {
        g.setColor(Color.BLACK);
        for (int i = 0; i < grid.getWidth(); i++) {
            //north
            g.drawLine((i * CELL_WIDTH + MARGIN), (0 * CELL_WIDTH + MARGIN),
                    ((i + 1) * CELL_WIDTH + MARGIN), (0 * CELL_WIDTH + MARGIN));

            //south
            g.drawLine(i * CELL_WIDTH + MARGIN, grid.getHeight() * CELL_WIDTH
                    + MARGIN, (i + 1) * CELL_WIDTH + MARGIN, grid.getHeight() * CELL_WIDTH
                    + MARGIN);
        }
        for (int j = 0; j < grid.getHeight(); j++) {
            //east
            g.drawLine((grid.getWidth()) * CELL_WIDTH + MARGIN, j * CELL_WIDTH
                    + MARGIN, (grid.getWidth()) * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH
                    + MARGIN);

            //west
            g.drawLine(0 * CELL_WIDTH + MARGIN, j * CELL_WIDTH + MARGIN, 0
                    * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH + MARGIN);
        }

        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                if (grid.getCell(j, i).getWall(WallPosition.NORTH) != null && !grid.getCell(j, i).getWall(WallPosition.NORTH).isOpen()) // if there exists a wall to the
                // north
                {
                    g.drawLine((i * CELL_WIDTH + MARGIN), (j * CELL_WIDTH + MARGIN),
                            ((i + 1) * CELL_WIDTH + MARGIN), (j * CELL_WIDTH + MARGIN));
                }

                if (grid.getCell(j, i).getWall(WallPosition.SOUTH) != null && !grid.getCell(j, i).getWall(WallPosition.SOUTH).isOpen()) // if there exists a wall to the
                // south
                {
                    g.drawLine(i * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH
                            + MARGIN, (i + 1) * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH
                            + MARGIN);
                }

                if (grid.getCell(j, i).getWall(WallPosition.EAST) != null && !grid.getCell(j, i).getWall(WallPosition.EAST).isOpen()) // if there exists a wall to the
                // east
                {
                    g.drawLine((i + 1) * CELL_WIDTH + MARGIN, j * CELL_WIDTH
                            + MARGIN, (i + 1) * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH
                            + MARGIN);
                }

                if (grid.getCell(j, i).getWall(WallPosition.WEST) != null && !grid.getCell(j, i).getWall(WallPosition.WEST).isOpen()) // if there exists a wall to the
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
        return new Dimension(grid.getWidth() * CELL_WIDTH + MARGIN * 2, grid.getHeight() * CELL_WIDTH + MARGIN
                * 2);
    }
}
