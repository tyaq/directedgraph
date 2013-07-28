package directedGraph;

import java.util.ArrayList;

public class Vertex {

	private String data;//Holds that actual data, does not need to be String Could have been generic
	private ArrayList<Edge> edges;//Adjacency List
	
	public Vertex(String d){
		data=d;
		edges = new ArrayList<Edge>();
	}//End constructor
	
	public void add(Edge v){
		if (edges != null && edges.contains(v) ) return;
		edges.add(v);
	}//End add method
	
	public void setData(String d){
		data=d;
	}//End setData
	
	public String data(){
		return data;
	}//End getData
	
	public ArrayList<Edge> edges(){
		return edges;
	}//End getEdges
	
	public String toString(){
		return data;
	}
}//End Vertex class
