package maze;

import analyzer.Analyzer;
import analyzer.GenerationTimeAnalyzer;
import generator.Generator;
import generator.KruskalGenerator;
import generator.WilsonGenerator;
import org.jgrapht.alg.util.UnionFind;

import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        Analyzer analyzer = new GenerationTimeAnalyzer();

        analyzer.analyze();

//        KruskalGenerator generator = new KruskalGenerator();
//
//        for (Integer size : Arrays.asList(10, 25, 50, 100, 200)) {
//            System.out.println("Size: " + size);
//            Grid testGrid = new Grid(size);
//            long startTime = System.nanoTime();
//            generator.generateMaze(testGrid);
//            long endTime = System.nanoTime();
//            long duration = (endTime - startTime) / 1000000;
//            System.out.println(duration);
//        }

    }
}
