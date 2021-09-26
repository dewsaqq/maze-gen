package maze;

import analyzer.*;

public class Main {
    public static void main(String[] args) {
        Analyzer analyzer;

        analyzer = new GenerationTimeAnalyzer();
        analyzer.analyze();
        analyzer.printResults();

        analyzer = new DeadEndAnalyzer();
        analyzer.analyze();
        analyzer.printResults();

        analyzer = new ThreeWayIntersectionAnalyzer();
        analyzer.analyze();
        System.out.println(analyzer.getClass().getSimpleName());
        analyzer.printResults();

        analyzer = new FourWayIntersectionAnalyzer();
        analyzer.analyze();
        System.out.println(analyzer.getClass().getSimpleName());
        analyzer.printResults();

        analyzer = new StartEndPathLengthAnalyzer();
        analyzer.analyze();
        System.out.println(analyzer.getClass().getSimpleName());
        analyzer.printResults();

        analyzer = new StartEndPathDifficultyAnalyzer();
        analyzer.analyze();
        System.out.println(analyzer.getClass().getSimpleName());
        analyzer.printResults();

        analyzer = new MazeGraphDifficultyAnalyzer();
        analyzer.analyze();
        System.out.println(analyzer.getClass().getSimpleName());
        analyzer.printResults();

        analyzer = new LongestPathFromStartLengthAnalyzer();
        analyzer.analyze();
        System.out.println(analyzer.getClass().getSimpleName());
        analyzer.printResults();
    }
}
