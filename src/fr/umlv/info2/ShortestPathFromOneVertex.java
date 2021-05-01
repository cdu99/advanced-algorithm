package fr.umlv.info2;

import java.util.ArrayList;
import java.util.Arrays;

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
		System.out.println("Shortest path to " + destination + " is " + d[destination]);
	}
}
