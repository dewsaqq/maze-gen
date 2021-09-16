package maze;

//import generator.AldousBroderGenerator;
//import generator.BacktrackingGenerator;
//import generator.Generator;

import generator.*;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        for (Integer size : Arrays.asList(10, 50, 100, 200, 500)) {
            WilsonGenerator kg1 = new WilsonGenerator(size);

            long startTime = System.nanoTime();
            kg1.generateMaze();
            long endTime = System.nanoTime();
            long duration = (endTime - startTime)/1000000;
            System.out.println("ArrayList time for size " + size + ":");
            System.out.println(duration);
        }

    }
}
