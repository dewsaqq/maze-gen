package maze;

import generator.AldousBroderGenerator;
import generator.BacktrackingGenerator;
import generator.Generator;

public class Main {
    public static void main(String[] args){
        Generator gen = new BacktrackingGenerator(15, 15);
        System.out.println(gen.generateMaze());

        Generator genAB = new AldousBroderGenerator(15);
        System.out.println(genAB.generateMaze());
    }
}
