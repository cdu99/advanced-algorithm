package fr.umlv.info2;

import java.util.*;

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

//   public static int[][] timedDepthFirstSearch(Graph g, int v0) {
//      boolean[] visited = new boolean[g.numberOfVertices()];
//      List<Integer> result = new ArrayList<>();
//      recDFS(g, v0, visited, result);
//      for (var i = 0; i < g.numberOfVertices(); i++) {
//         if (!visited[i]) {
//            recDFS(g, i, visited, result);
//         }
//      }
//      return result;
//   }
//
//   private static void recTDFS(Graph g, int v, boolean[] visited, List<Integer> result) {
//      visited[v] = true;
//      result.add(v);
//      Iterator<Edge> iterator = g.edgeIterator(v);
//      while (iterator.hasNext()) {
//         var next = iterator.next();
//         if (!visited[next.getEnd()]) {
//            recDFS(g, next.getEnd(), visited, result);
//         }
//      }
//   }

   public static MatGraph matrixRandomizer(int nbOfVertices) {
      var mat = new MatGraph(nbOfVertices);
      Random rand = new Random();
      for (var i = 0; i < (nbOfVertices * nbOfVertices) / 2; i++) {
         mat.addEdge(rand.nextInt(nbOfVertices), rand.nextInt(nbOfVertices), rand.nextInt(nbOfVertices));
      }
      return mat;
   }
}
