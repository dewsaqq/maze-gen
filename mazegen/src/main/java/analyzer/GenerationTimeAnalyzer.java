package analyzer;

import generator.Generator;
import maze.Grid;

public class GenerationTimeAnalyzer extends Analyzer {
    public GenerationTimeAnalyzer() {
        super();
        isWarmUpRequired = true;
    }

    @Override
    public double generatorAnalysis(Generator generator, int mazeSize) {
        Grid testGrid = new Grid(mazeSize);
        long startTime = System.nanoTime();
        generator.generateMaze(testGrid);
        long endTime = System.nanoTime();
        return (endTime - startTime) / 1000000.0;
    }
}
