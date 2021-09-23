package analyzer;

import generator.Generator;
import graph.MazeGraph;
import maze.Grid;
import maze.Maze;

public class StartEndPathLength extends Analyzer {
    public StartEndPathLength() {
        super();
    }

    @Override
    public double generatorAnalysis(Generator generator, int mazeSize) {
        Grid testGrid = new Grid(mazeSize);
        Maze maze = generator.generateMaze(testGrid);
        MazeGraph mazeGraph = new MazeGraph(maze);
        return mazeGraph.getStartEndPathLength();
    }
}
