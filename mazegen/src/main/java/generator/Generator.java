package generator;

import maze.Grid;

public abstract class Generator {
    protected final Grid grid;
    protected final int height;
    protected final int width;

    public Generator(int size) {
        this(size, size);
    }

    public Generator(int height, int width) {
        this.grid = new Grid(height, width);
        this.height = height;
        this.width = width;
    }

    public Grid generateMaze() {
        return grid;
    }
}
