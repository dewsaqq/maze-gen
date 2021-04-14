package maze;

import generator.BacktrackingGenerator;

public class Main {
    public static void main(String[] args){
        BacktrackingGenerator gen = new BacktrackingGenerator(15, 15);
        System.out.println(gen.generateMaze());
    }
}
