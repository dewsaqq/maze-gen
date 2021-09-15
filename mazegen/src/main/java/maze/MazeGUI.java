package maze;

/**
 * Source: https://github.com/irealva/maze-gui/blob/master/Maze.java
 * MazeGUI.java
 * Simple program that constructs a maze panel to display a maze
 * and its solution.
 * Base code to construct a mazeGUI, taken from supplemented
 * homework files, with some modifications
 */


import generator.Generator;
import generator.RecursiveBacktrackerGenerator;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MazeGUI {
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            int size = 10;

//            Grid grid = new Grid(size);
//            Generator generator = new BacktrackingGenerator(size);
            Generator generator = new RecursiveBacktrackerGenerator(size);
            Grid grid = generator.generateMaze();
            Maze maze = new Maze(grid); // Constructs the maze object
            System.out.println(grid);

            JFrame frame = new JFrame("Maze");
            MazePanel panel = new MazePanel(maze); // Constructs the panel to hold the
            // maze
            JScrollPane scrollPane = new JScrollPane(panel);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(maze.windowSize().width, maze.windowSize().height);
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.setVisible(true);
        } catch (NumberFormatException exception) {
            System.out.println("The input number for the maze size must be an integer");
        }
    }
}

// This is the JPanel replacement for mazes that stores as a data
// element the maze and calls the mazes's drawing function
class MazePanel extends JPanel {
    private Maze maze; // the maze object

    public MazePanel(Maze theMaze) {
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