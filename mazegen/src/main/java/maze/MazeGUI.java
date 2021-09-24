package maze;

/**
 * Source: https://github.com/irealva/maze-gui/blob/master/Maze.java
 * MazeGUI.java
 * Simple program that constructs a maze panel to display a maze
 * and its solution.
 * Base code to construct a mazeGUI, taken from supplemented
 * homework files, with some modifications
 */


import generator.*;
import graph.MazeGraph;

import javax.swing.*;
import java.awt.*;

public class MazeGUI {
    public static void main(String[] args) {
        try {
            int size = 256;

            Generator generator;
//            generator = new RecursiveBacktrackerGenerator();
//            generator = new AldousBroderGenerator();
//            generator = new KruskalGenerator();
//            generator = new PrimGenerator();
            generator = new BoruvkaGenerator();
//            generator = new WilsonGenerator();
            Maze maze = generator.generateMaze(new Grid(size));
            MyMaze myMaze = new MyMaze(maze.getGrid()); // Constructs the maze object
            System.out.println(maze.getGrid());
            MazeGraph mazeGraph = new MazeGraph(maze);

            System.out.println(mazeGraph.getStartEndPathCorridorDifficulty() + " start end difficulty");
            System.out.println(mazeGraph.getCorridorsDifficultySum() + " difficulty sum");
            System.out.println(mazeGraph.getLongestPathFromStartLength() + " longest path length");
            System.out.println(mazeGraph.getNumberOfDeadEnds() + " dead ends");
            System.out.println(mazeGraph.getNumberOfFourWayIntersections() + " 4-way intersections");
            System.out.println(mazeGraph.getNumberOfThreeWayIntersections() + " 3-way intersections");
            System.out.println(mazeGraph.getStartEndPathLength() + " start-end path length");



            JFrame frame = new JFrame("Maze");
            MazePanel panel = new MazePanel(myMaze); // Constructs the panel to hold the
            // maze
            JScrollPane scrollPane = new JScrollPane(panel);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(myMaze.windowSize().width, myMaze.windowSize().height);
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.setVisible(true);
        } catch (NumberFormatException exception) {
            System.out.println("The input number for the maze size must be an integer");
        }
    }
}

class MyMaze {
    public static final int CELL_WIDTH = 20; // maze square size
    public static final int MARGIN = 50; // buffer between window edge and maze
    private final Grid grid;


    public MyMaze(Grid grid) {
        this.grid = grid;
    }

    public void draw(Graphics g) // draws a maze and its solution
    {
        g.setColor(Color.BLACK);
        for (int i = 0; i < grid.getWidth(); i++) {
            //north
            g.drawLine((i * CELL_WIDTH + MARGIN), (MARGIN),
                    ((i + 1) * CELL_WIDTH + MARGIN), (MARGIN));

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
            g.drawLine(MARGIN, j * CELL_WIDTH + MARGIN, MARGIN, (j + 1) * CELL_WIDTH + MARGIN);
        }

        for (int i = 0; i < grid.getWidth(); i++) {
            for (int j = 0; j < grid.getHeight(); j++) {
                if (grid.getCell(j, i).getWall(Wall.Position.NORTH) != null && !grid.getCell(j, i).getWall(Wall.Position.NORTH).isOpen()) // if there exists a wall to the
                // north
                {
                    g.drawLine((i * CELL_WIDTH + MARGIN), (j * CELL_WIDTH + MARGIN),
                            ((i + 1) * CELL_WIDTH + MARGIN), (j * CELL_WIDTH + MARGIN));
                }

                if (grid.getCell(j, i).getWall(Wall.Position.SOUTH) != null && !grid.getCell(j, i).getWall(Wall.Position.SOUTH).isOpen()) // if there exists a wall to the
                // south
                {
                    g.drawLine(i * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH
                            + MARGIN, (i + 1) * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH
                            + MARGIN);
                }

                if (grid.getCell(j, i).getWall(Wall.Position.EAST) != null && !grid.getCell(j, i).getWall(Wall.Position.EAST).isOpen()) // if there exists a wall to the
                // east
                {
                    g.drawLine((i + 1) * CELL_WIDTH + MARGIN, j * CELL_WIDTH
                            + MARGIN, (i + 1) * CELL_WIDTH + MARGIN, (j + 1) * CELL_WIDTH
                            + MARGIN);
                }

                if (grid.getCell(j, i).getWall(Wall.Position.WEST) != null && !grid.getCell(j, i).getWall(Wall.Position.WEST).isOpen()) // if there exists a wall to the
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

// This is the JPanel replacement for mazes that stores as a data
// element the maze and calls the mazes's drawing function
class MazePanel extends JPanel {
    private final MyMaze maze; // the maze object

    public MazePanel(MyMaze theMaze) {
        maze = theMaze;
    }

    // The paintComponent method is called every time
    // that the panel needs to be displayed or refreshed.
    // Anything you want drawn on the panel should be drawn
    // in this method.
    public void paintComponent(Graphics page) {
        super.paintComponent(page);

        setBackground(Color.white); // set preferredSize for JScrollPane

        this.setPreferredSize(maze.windowSize()); // draw the maze and the solution
        // path in red points

        maze.draw(page);
    }
}