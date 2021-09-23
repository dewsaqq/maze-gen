package analyzer;

import generator.*;
import graph.MazeGraph;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public abstract class Analyzer {
    protected static Map<Generator, Map<Integer, ArrayList<Double>>> RESULTS;
    protected static final int NUMBER_OF_ITERATIONS = 100;
    protected static final List<Integer> MAZE_SIZES = Arrays.asList(16, 32, 64, 128, 256);
    protected static final List<Generator> GENERATORS = Arrays.asList(
            new RecursiveBacktrackerGenerator(),
            new KruskalGenerator(),
            new PrimGenerator(),
            new AldousBroderGenerator(),
            new WilsonGenerator(),
            new BoruvkaGenerator()
    );

    protected Analyzer() {
        initializeResultMap();
    }

    public abstract void analyze();

    public Map<Generator, Map<Integer, Double>> getResultsMean() {
        Map<Generator, Map<Integer, Double>> resultsMean = new LinkedHashMap<>();

        for (Generator generator : GENERATORS) {
            Map<Integer, Double> generatorMeanValue = new HashMap<>();
            for (Integer size : MAZE_SIZES) {
                double mean = RESULTS.get(generator).get(size).stream()
                        .mapToDouble(d -> d)
                        .average()
                        .orElse(0.0);
                generatorMeanValue.put(size, round(mean, 2));
            }
            resultsMean.put(generator, generatorMeanValue);
        }

        return resultsMean;
    }

    public void printResults() {
        Map<Generator, Map<Integer, Double>> resultsMean = getResultsMean();

        for (Generator generator : GENERATORS) {
            System.out.println(generator.getClass().getSimpleName());
            System.out.println(resultsMean.get(generator));
            System.out.println();
        }
    }

    private void initializeResultMap() {
        RESULTS = new LinkedHashMap<>();

        for (Generator generator : GENERATORS) {
            RESULTS.put(generator, new HashMap<>());
            for (Integer size : MAZE_SIZES) {
                RESULTS.get(generator).put(size, new ArrayList<>());
            }
        }
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
