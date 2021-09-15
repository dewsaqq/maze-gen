package maze;

//import generator.AldousBroderGenerator;
//import generator.BacktrackingGenerator;
//import generator.Generator;

import generator.KruskalGenerator;
import generator.PrimGenerator;
import generator.RecursiveBacktrackerGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
//        for (Integer size : Arrays.asList(10, 50, 100, 200, 500)) {
//            KruskalGenerator kg1 = new KruskalGenerator(size);
//
//            long startTime = System.nanoTime();
//            kg1.generateMaze();
//            long endTime = System.nanoTime();
//            long duration = (endTime - startTime)/1000000;
//            System.out.println("HashMap time for size " + size + ":");
//            System.out.println(duration);
//        }

        PrimGenerator pg = new PrimGenerator(10);
        System.out.println(pg.generateMaze());
    }
}
