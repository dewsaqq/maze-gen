package maze;

public class Maze {
    private final Grid grid;

    public Maze(int size) {
        grid = new Grid(size);
    }

    public Maze(Grid grid) {
        this.grid = grid;
    }

    @Override
    public String toString() {
        String mazeString = grid.toString();
        mazeString = mazeString.replaceAll("1", "#");
        mazeString = mazeString.replaceAll("0", " ");
        return mazeString;
    }
}
