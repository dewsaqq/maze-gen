package analyzer;

import generator.Generator;
import maze.Grid;

public class GenerationTimeAnalyzer extends Analyzer {
    private static final int WARMUP_SIZE = 10;

    public GenerationTimeAnalyzer() {
        super();
    }

    @Override
    public void analyze() {
        warmUp();
        long startTime = System.nanoTime();
        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            for (Integer mazeSize : MAZE_SIZES) {
                for (Generator generator : GENERATORS) {
                    RESULTS.get(generator).get(mazeSize).add(timeAnalysis(generator, mazeSize));
                }
            }
        }
        long endTime = System.nanoTime();
        System.out.println("Execution time (seconds): " + (endTime - startTime)/1000000000.0);
    }

    private double timeAnalysis(Generator generator, int mazeSize) {
        Grid testGrid = new Grid(mazeSize);
        long startTime = System.nanoTime();
        generator.generateMaze(testGrid);
        long endTime = System.nanoTime();
        return (endTime - startTime)/1000000.0;
    }

    private void warmUp() {
        for (int i = 0; i < WARMUP_SIZE; i++) {
            for (Integer mazeSize : MAZE_SIZES) {
                for (Generator generator : GENERATORS) {
                    timeAnalysis(generator, mazeSize);
                }
            }
        }
    }
}
