package directedGraph;

import java.util.ArrayList;

public class Vertex {

	private String data;		// Holds that actual data, does not need to be String could have been generic
	private ArrayList<Edge> edges;	// Adjacency List
	
	public Vertex(String d) {
		data = d;
		edges = new ArrayList<Edge>();
	}
	
	public void add(Edge v) {
		if (edges != null && edges.contains(v)) {
			return;
		}
		edges.add(v);
	}
	
	public void setData(String d) {
		data = d;
	}
	
	public String data() {
		return data;
	}
	
	public ArrayList<Edge> edges() {
		return edges;
	}
	
	public String toString() {
		return data;
	}
}
