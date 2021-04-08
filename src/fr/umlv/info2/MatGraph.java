package fr.umlv.info2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MatGraph implements Graph {
    private final int[][] mat;
    private final int n; // number of vertices
    private int nbOfEdges;

    public MatGraph(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.mat = new int[n][n];
        this.nbOfEdges = 0;
    }

    @Override
    public int numberOfEdges() {
        return nbOfEdges;
    }

    @Override
    public int numberOfVertices() {
        return n;
    }

    @Override
    public void addEdge(int i, int j, int value) {
        if (i < 0 || i > n || j < 0 || j > n) {
            throw new IllegalArgumentException();
        }

        nbOfEdges++;
        mat[i][j] = value;
    }

    @Override
    public boolean isEdge(int i, int j) {
        if (i < 0 || i > n || j < 0 || j > n) {
            throw new IllegalArgumentException();
        }
        return mat[i][j] != 0;
    }

    @Override
    public int getWeight(int i, int j) {
        if (i < 0 || i > n || j < 0 || j > n) {
            throw new IllegalArgumentException();
        }
        return mat[i][j];
    }

    @Override
    public Iterator<Edge> edgeIterator(int i) {
        if (i < 0 || i > n) {
            throw new IllegalArgumentException();
        }
        return new Iterator<>() {
            private int index = 0;
            private Optional<Edge> edge = getEdge();

            @Override
            public boolean hasNext() {
                return edge.isPresent();
            }

            private Optional<Edge> getEdge() {
                for (int idx = index; idx < n; idx++) {
                    if (mat[i][idx] != 0) {
                        var edge = new Edge(i, index, mat[i][idx]);
                        index = idx + 1;
                        return Optional.of(edge);
                    }
                }

                return Optional.empty();
            }

            @Override
            public Edge next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                var value = edge;
                edge = getEdge();
                return value.orElseThrow();
            }
        };
    }

    @Override
    public void forEachEdge(int i, Consumer<Edge> consumer) {
        for (var j = 0; j < n; j++) {
            if (mat[i][j] != 0) {
                var edge = new Edge(i, j, mat[i][j]);
                consumer.accept(edge);
            }
        }
    }

    // dot -Tpng digraph.dot -o output.png
    @Override
    public String toGraphviz() {
        var builder = new StringBuilder("digraph G {\n\t");
        for (int i = 0; i < n; i++) {
            builder.append(i).append(";\n\t");
            forEachEdge(i, (edge) -> {
                builder
                        .append(edge.getStart())
                        .append(" -> ")
                        .append(edge.getEnd())
                        .append("[ label=\"")
                        .append(edge.getValue())
                        .append("\" ] ;\n\t");
            });
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append("}");
        return builder.toString();
    }

    public static MatGraph makeGraphFromMatrixFile(Path path) throws IOException {
        var lines = Files.lines(path).collect(Collectors.toList());
        var size = Integer.parseInt(lines.get(0));
        var matrix = new MatGraph(size);

        for (int i = 1; i < lines.size(); i++) {
            var values = lines.get(i).split(" ");
            for (int j = 0; j < values.length; j++) {
                matrix.addEdge(i - 1, j, Integer.parseInt(values[j]));
            }
        }
        return matrix;
    }
}
