package fr.umlv.info2;

import java.util.Objects;

public class Edge {
    private final int start;
    private final int end;
    private final int value;

    public Edge(int start, int end, int value) {
        this.start = start;
        this.end = end;
        this.value = value;
    }

    public Edge(int start, int end) {
        this(start, end, 1);
    }

    public int getValue() {
        return value;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return start + " -- " + end + " ( " + value + " )";
    }

    @Override
    public boolean equals(Object obj) {
        boolean result;
        if ((obj == null) || (getClass() != obj.getClass())) {
            result = false;
        } else {
            Edge edge = (Edge) obj;
            result = start == edge.getStart() && end == edge.getEnd() && value == edge.getValue();
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, value);
    }
}
