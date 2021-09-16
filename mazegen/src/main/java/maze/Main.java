package maze;

//import generator.AldousBroderGenerator;
//import generator.BacktrackingGenerator;
//import generator.Generator;

import generator.*;

import java.util.*;
import java.util.stream.Collectors;

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

        Generator pg = new BoruvkaGenerator(4);
        System.out.println(pg.generateMaze());

    }
}
