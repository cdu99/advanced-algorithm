package fr.umlv.info2.main;

import fr.umlv.info2.AdjGraph;
import fr.umlv.info2.Graphs;
import fr.umlv.info2.MatGraph;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        var matrixFromMat = MatGraph.makeGraphFromMatrixFile(Path.of("src/fr/umlv/info2/main/my_graph.mat"));

        try (FileWriter writer = new FileWriter("digraph.dot")) {
            writer.write(matrixFromMat.toGraphviz());

//            var adjGraph = new AdjGraph(9);
//            adjGraph.addEdge(0, 1, 1);
//            adjGraph.addEdge(0, 2, 1);
//            adjGraph.addEdge(2, 1, 1);
//            adjGraph.addEdge(2, 4, 1);
//            adjGraph.addEdge(3, 2, 1);
//            adjGraph.addEdge(3, 7, 1);
//            adjGraph.addEdge(4, 6, 1);
//            adjGraph.addEdge(5, 4, 1);
//            adjGraph.addEdge(5, 0, 1);
//            adjGraph.addEdge(6, 5, 1);
//            adjGraph.addEdge(7, 8, 1);
//            adjGraph.addEdge(8, 3, 1);

            System.out.println("RUNNING BFS ON THE GRAPH:");
            var vertices = Graphs.BFS(matrixFromMat, 0);
            System.out.println(vertices);

            System.out.println("RUNNING DFS ON THE GRAPH:");
            vertices = Graphs.DFS(matrixFromMat, 0);
            System.out.println(vertices);

            System.out.println("RUNNING TIMED DFS ON THE GRAPH:");
            var time = Graphs.timedDFS(matrixFromMat, 0);
            for (var i = 0; i < time.length; i++) {
                System.out.println(i + " (" + time[i][0] + "|" + time[i][1] + ")");
            }

            System.out.println("TOPOLOGICAL SORT:");
            var topological = Graphs.topologicalSort(matrixFromMat, false);
            System.out.println(topological);

            System.out.println("SCC:");
            System.out.println(Graphs.scc(matrixFromMat));
        }
    }
}
