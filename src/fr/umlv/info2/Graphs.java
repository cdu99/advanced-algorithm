package fr.umlv.info2;

import java.util.*;
import java.util.concurrent.atomic.LongAdder;

public class Graphs {
    public static List<Integer> DFS(Graph g, int v0) {
        boolean[] visited = new boolean[g.numberOfVertices()];
        List<Integer> result = new ArrayList<>();
        recDFS(g, v0, visited, result);
        return result;
    }

    private static void recDFS(Graph g, int v, boolean[] visited, List<Integer> result) {
        visited[v] = true;
        result.add(v);
        Iterator<Edge> iterator = g.edgeIterator(v);
        while (iterator.hasNext()) {
            var next = iterator.next();
            if (!visited[next.getEnd()]) {
                recDFS(g, next.getEnd(), visited, result);
            }
        }
    }

    public static List<Integer> BFS(Graph g, int v0) {
        boolean[] visited = new boolean[g.numberOfVertices()];
        LinkedList<Integer> queue = new LinkedList<>();
        List<Integer> result = new ArrayList<>();
        queue.add(v0);
        visited[v0] = true;
        while (queue.size() != 0) {
            var v = queue.poll();
            result.add(v);
            Iterator<Edge> iterator = g.edgeIterator(v);
            while (iterator.hasNext()) {
                var next = iterator.next().getEnd();
                if (!visited[next]) {
                    visited[next] = true;
                    queue.add(next);
                }
            }
        }
        return result;
    }

    // -1 means not visited
    public static int[][] timedDFS(Graph g, int v0) {
        var longAdder = new LongAdder();
        var result = new int[g.numberOfVertices()][2];
        for (var i = 0; i < g.numberOfVertices(); i++) {
            result[i][0] = -1;
            result[i][1] = -1;
        }
        recTDFS(g, v0, result, longAdder);
        return result;
    }

    private static void recTDFS(Graph g, int v, int[][] result, LongAdder longAdder) {
        result[v][0] = longAdder.intValue();
        longAdder.increment();
        Iterator<Edge> iterator = g.edgeIterator(v);
        while (iterator.hasNext()) {
            var next = iterator.next();
            if (result[next.getEnd()][0] == -1) {
                recTDFS(g, next.getEnd(), result, longAdder);
            }
        }
        result[v][1] = longAdder.intValue();
        longAdder.increment();
    }

    public static List<Integer> topologicalSort(Graph g, boolean cycleDetect) {
        if (cycleDetect) {
            throw new IllegalArgumentException("There is a cycle in the graph");
        }
        var visited = new boolean[g.numberOfVertices()];
        var result = new ArrayList<Integer>();
        for (var i = 0; i < g.numberOfVertices(); i++) {
            if (!visited[i]) {
                if (!topologicalSortRec(g, i, visited, result)) {
                    throw new IllegalArgumentException("There is a cycle in the graph");
                }
            }
        }
        Collections.reverse(result);
        return result;
    }

    public static boolean topologicalSortRec(Graph g, int v, boolean[] visited, List<Integer> result) {
        visited[v] = true;
        Iterator<Edge> iterator = g.edgeIterator(v);
        while (iterator.hasNext()) {
            var next = iterator.next().getEnd();
            if (!visited[next]) {
                topologicalSortRec(g, next, visited, result);
            }
        }
        result.add(v);
        return true;
    }

    // This visit all vertices
    public static int[][] timedDFS(Graph g) {
        var visited = new boolean[g.numberOfVertices()];
        var longAdder = new LongAdder();
        var result = new int[g.numberOfVertices()][2];
        for (var i = 0; i < g.numberOfVertices(); i++) {
            if (!visited[i]) {
                recTDFS(g, i, result, longAdder, visited);
            }
        }
        return result;
    }

    private static void recTDFS(Graph g, int v, int[][] result, LongAdder longAdder, boolean[] visited) {
        visited[v] = true;
        result[v][0] = longAdder.intValue();
        longAdder.increment();
        Iterator<Edge> iterator = g.edgeIterator(v);
        while (iterator.hasNext()) {
            var next = iterator.next();
            if (!visited[next.getEnd()]) {
                recTDFS(g, next.getEnd(), result, longAdder, visited);
            }
        }
        result[v][1] = longAdder.intValue();
        longAdder.increment();
    }

    public static List<List<Integer>> scc(Graph g) {
        var time = timedDFS(g);
        var queue = new LinkedList<Integer>();
        var map = new HashMap<Integer, Integer>();
        var visited = new boolean[g.numberOfVertices()];
        var scc = new ArrayList<List<Integer>>();

        for (var i = 0; i < time.length; i++) {
            Integer endingTime = time[i][1];
            map.put(endingTime, i);
            queue.add(endingTime);
        }
        Collections.sort(queue, Collections.reverseOrder());
        queue.replaceAll(map::get);

        g = g.transpose();

        while (!queue.isEmpty()) {
            var v = queue.poll();
            if (!visited[v]) {
                scc.add(kosarajuStepThree(g, v, visited, null));
            }
        }
        return scc;

    }

    private static List<Integer> kosarajuStepThree(Graph g, int v, boolean[] visited, List<Integer> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        visited[v] = true;
        list.add(v);
        Iterator<Edge> iterator = g.edgeIterator(v);
        while (iterator.hasNext()) {
            var next = iterator.next().getEnd();
            if (!visited[next]) {
                kosarajuStepThree(g, next, visited, list);
            }
        }
        return list;
    }


    public static MatGraph matrixRandomizer(int nbOfVertices) {
        var mat = new MatGraph(nbOfVertices);
        Random rand = new Random();
        for (var i = 0; i < (nbOfVertices * nbOfVertices) / 2; i++) {
            mat.addEdge(rand.nextInt(nbOfVertices), rand.nextInt(nbOfVertices), rand.nextInt(nbOfVertices));
        }
        return mat;
    }
}
