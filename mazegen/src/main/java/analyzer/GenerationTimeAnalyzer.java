package analyzer;

import generator.Generator;
import maze.Grid;

public class GenerationTimeAnalyzer extends Analyzer {
    @Override
    public void analyze() {
        for (Integer mazeSize : MAZE_SIZES) {
            System.out.println("MAZE SIZE: " + mazeSize);
            for (Generator generator : GENERATORS) {
                generatorAnalysis(generator, mazeSize);
            }
        }
    }

    private void generatorAnalysis(Generator generator, int mazeSize) {
        Grid testGrid = new Grid(mazeSize);
        long startTime = System.nanoTime();
        generator.generateMaze(testGrid);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;
        System.out.println(generator.getClass().getName().split("\\.")[1] + " generation time in ms: " + duration);
    }
}
