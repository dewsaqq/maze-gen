//package generator;
//
//import maze.Cell;
//import maze.Grid;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//public class AldousBroderGenerator extends Generator {
//    public AldousBroderGenerator(int size) {
//        super(size);
//    }
//
//    public AldousBroderGenerator(int height, int width) {
//        super(height, width);
//    }
//
//    @Override
//    public Grid generateMaze() {
//        Random random = new Random();
//
//        Cell activeCell = grid.getRandomCell();
//        activeCell.visitCell();
//        int numberOfCellsVisited = 1;
//
//        ArrayList<Cell> activeCellNeighbours = grid.getAllNeighbours(activeCell);
//        Cell nextCell = activeCellNeighbours.get(random.nextInt(activeCellNeighbours.size()));
//
//        while (numberOfCellsVisited < height * width) {
//            if (!nextCell.isVisited()) {
//                nextCell.visitCell();
//                numberOfCellsVisited++;
//                activeCell.openWallWithCell(nextCell);
//            }
//            activeCell = nextCell;
//            activeCellNeighbours = grid.getAllNeighbours(activeCell);
//            nextCell = activeCellNeighbours.get(random.nextInt(activeCellNeighbours.size()));
//        }
//
//
//        return grid;
//    }
//}
