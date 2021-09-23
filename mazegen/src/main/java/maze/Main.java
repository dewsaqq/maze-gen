package maze;

import analyzer.Analyzer;
import analyzer.DeadEndAnalyzer;
import analyzer.GenerationTimeAnalyzer;
import generator.BoruvkaGenerator;
import generator.Generator;
import generator.KruskalGenerator;
import generator.WilsonGenerator;
import org.jgrapht.Graph;
import org.jgrapht.GraphMetrics;
import org.jgrapht.alg.util.UnionFind;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
//        Analyzer analyzer = new GenerationTimeAnalyzer();
        Analyzer analyzer = new DeadEndAnalyzer();

        analyzer.analyze();
        analyzer.printResults();

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
