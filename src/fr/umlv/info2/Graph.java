package fr.umlv.info2;

import java.util.Iterator;
import java.util.function.Consumer;

public interface Graph {
    int numberOfEdges();

    int numberOfVertices();

    void addEdge(int i, int j, int value);

    boolean isEdge(int i, int j);

    int getWeight(int i, int j);

    Iterator<Edge> edgeIterator(int i);

    void forEachEdge(int i, Consumer<Edge> consumer);

    String toGraphviz();

    default Graph transpose() {
        var transposedGraph = new MatGraph(numberOfVertices());
        for (var i = 0; i < numberOfVertices(); i++) {
            for (var j = 0; j < numberOfVertices(); j++) {
                if (isEdge(i, j)) {
                    transposedGraph.addEdge(j, i, getWeight(i, j));
                }
            }
        }
        return transposedGraph;
    }
}
