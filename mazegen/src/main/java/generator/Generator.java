package generator;

import maze.Grid;
import maze.Maze;

public abstract class Generator {
    public abstract Maze generateMaze(Grid grid);
}
