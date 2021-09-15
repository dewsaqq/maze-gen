package generator;

import maze.Grid;

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
}
