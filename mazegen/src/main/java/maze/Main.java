package maze;

//import generator.AldousBroderGenerator;
//import generator.BacktrackingGenerator;
//import generator.Generator;

import generator.RecursiveBacktrackerGenerator;

import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        RecursiveBacktrackerGenerator rbg = new RecursiveBacktrackerGenerator(10);
        System.out.println(rbg.generateMaze());

//        Generator gen = new BacktrackingGenerator(15, 15);
//        System.out.println(gen.generateMaze());
//
//        Generator genAB = new AldousBroderGenerator(15);
//        System.out.println(genAB.generateMaze());

//        String cell1 = "cell1";
//        String cell2 = "cell2";
//        String cell3 = "cell3";
//        String cell4 = "cell4";
//        String cell5 = "cell5";
//
//        HashSet<String> cells1 = new HashSet<>();
//        cells1.add(cell1);
//        cells1.add(cell2);
//
//        HashSet<String> cells2 = new HashSet<>();
//        cells2.add(cell2);
//        cells2.add(cell3);
//        cells2.add(cell4);
//
//        System.out.println(cells1);
//        System.out.println(cells2);
//        cells1.addAll(cells2);
//        System.out.println(cells1);
    }
}
