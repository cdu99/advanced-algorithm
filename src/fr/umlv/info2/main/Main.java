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
            writer.close();

            var vertices = Graphs.BFS(matrixFromMat, 0);
            System.out.println(vertices);

            vertices = Graphs.DFS(matrixFromMat, 3);
            System.out.println(vertices);

            var adjList = new AdjGraph(4);
            adjList.addEdge(0, 3, 1);
            adjList.addEdge(1, 2, 4);
            adjList.addEdge(2, 3, 2);

            vertices = Graphs.DFS(adjList, 0);
            System.out.println(vertices);
        }
    }
}
