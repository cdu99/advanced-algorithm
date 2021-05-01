package fr.umlv.info2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class ShortestPathFromOneVertex {
    private final int source;
    private final int[] d;
    private final int[] pi;

    ShortestPathFromOneVertex(int vertex, int[] d, int[] pi) {
        this.source = vertex;
        this.d = d;
        this.pi = pi;
    }

    @Override
    public String toString() {
        return source + " " + Arrays.toString(d) + " " + Arrays.toString(pi);
    }

    public void printShortestPathTo(int destination) {
        ArrayList<Integer> path = new ArrayList<>(d.length);

        if (pi[destination] == -1) {
            System.out.println("Unreachable");
            return;
        }

        var current = destination;

        while (current != source) {
            path.add(current);
            current = pi[current];
        }
        path.add(current);

        Collections.reverse(path);
        System.out.println(path.stream().map(Object::toString).collect(Collectors.joining(" --> ")));
    }
}
