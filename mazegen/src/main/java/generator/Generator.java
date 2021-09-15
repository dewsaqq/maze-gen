package generator;

import maze.Grid;

import java.util.Random;
import java.util.Set;

public abstract class Generator {
    protected final Grid grid;

    public Generator(int size) {
        this(size, size);
    }

    public Generator(int gridHeight, int gridWidth) {
        this.grid = new Grid(gridHeight, gridWidth);
    }

    public Grid generateMaze() {
        return grid;
    }

    public static <E> E getRandomSetElement(Set<E> set) {
        return set.stream().skip(new Random().nextInt(set.size())).findFirst().orElse(null);
    }
}
