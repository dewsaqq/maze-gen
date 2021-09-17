package maze;

import analyzer.Analyzer;
import analyzer.GenerationTimeAnalyzer;

public class Main {
    public static void main(String[] args) {
        Analyzer analyzer = new GenerationTimeAnalyzer();

        analyzer.analyze();

    }
}
