package analyzer;

import generator.Generator;
import graph.MazeGraph;
import maze.Grid;
import maze.Maze;

public class DeadEndAnalyzer extends Analyzer {
    public DeadEndAnalyzer() {
        super();
    }

    @Override
    public void analyze() {
        long startTime = System.nanoTime();
        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            for (Integer mazeSize : MAZE_SIZES) {
                for (Generator generator : GENERATORS) {
                    RESULTS.get(generator).get(mazeSize).add(deadEndAnalysis(generator, mazeSize));
                }
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Execution time (seconds): " + (endTime - startTime)/1000000000.0);
    }

    private double deadEndAnalysis(Generator generator, int mazeSize) {
        Grid testGrid = new Grid(mazeSize);
        Maze maze = generator.generateMaze(testGrid);
        MazeGraph mazeGraph = new MazeGraph(maze);
        return mazeGraph.getNumberOfDeadEnds();
    }
}
