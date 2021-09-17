package analyzer;

import generator.*;

import java.util.Arrays;
import java.util.List;

public abstract class Analyzer {
    protected static final List<Integer> MAZE_SIZES = Arrays.asList(10, 25, 50, 100, 200);
    protected static final List<Generator> GENERATORS = Arrays.asList(
            new RecursiveBacktrackerGenerator(),
            new KruskalGenerator(),
            new PrimGenerator(),
            new AldousBroderGenerator(),
            new WilsonGenerator(),
            new BoruvkaGenerator()
    );

    public abstract void analyze();
}
