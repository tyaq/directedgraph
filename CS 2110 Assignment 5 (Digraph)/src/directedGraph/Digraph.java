package directedGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Digraph {

	private HashMap<String,Vertex> vertices;
	private LinkedList<Edge> edges;
	
	public Digraph(){
		vertices = new HashMap<String,Vertex>();
		edges = new LinkedList<Edge>();
	}
	
	public void addEdge(String tail,String tip,String weight) {
		Vertex tailV=null, tipV=null;
		//Create new vertices if they do not exist or else fetch them
		if (vertices==null || !(vertices.containsKey(tail))){
			tailV = new Vertex(tail);
			vertices.put(tail, tailV);//Put new vertex in know vertices
		}//End if
		else {
			tailV = vertices.get(tail);
		}//End else
		if (!(vertices.containsKey(tip))){
			tipV =new Vertex(tip);
			vertices.put(tip, tipV);//Put new vertex in know vertices
		}//End if
		else {
			tipV = vertices.get(tip);
		}//End else
		int w=Integer.parseInt(weight);
		Edge e = new Edge(tailV,tipV,w);//Create new edge
		edges.add(e);
	}//End addEdge
	
	public int maxFlow(HashMap<Edge,Integer> flow, Vertex start) {
		int maxFlow= 0;
		for (int i=0;i<start.edges().size();i++) {
			maxFlow += flow.get(start.edges().get(i));
		}//end for
		return maxFlow;
	}//End maxFlow method
	
	public HashMap<Edge, Integer> getPath(Vertex start,Vertex end){
	System.out.println(start);
	System.out.println(end);
		LinkedList<Edge> path;//Keeps track of path being followed
		HashMap<Edge, Integer> flow = new HashMap<Edge,Integer>();//Keeps track of how much capacity has been used
		//if (start.equals(end)) return path;
		for(Edge e:edges) {
			flow.put(e,0);
	System.out.println("in for");
		}//End for
	System.out.println("did for");
		while ((path = bfSearch(start,end,flow)) != null) {
			System.out.println(path);
			int minCapacity = Integer.MAX_VALUE;
			Vertex lastNode = start;
	//System.out.println("in while");
			for (Edge edge : path) {
				int c;
				if (edge.tail().equals(lastNode)) {
					c=edge.weight() - flow.get(edge);
					lastNode=edge.tip();
				} else {
					c=flow.get(edge);
					lastNode= edge.tail();
				}//end if else
				if (c<minCapacity) {
					minCapacity=c;
				}//end if
			}//End for
		}//End while
	System.out.println("out while");
		return flow;
	}//End getPath
	
	public LinkedList<Edge> bfSearch(Vertex start,Vertex end, HashMap<Edge,Integer> flow) {
		HashMap<Vertex,Edge> parent = new HashMap<Vertex,Edge>();
		LinkedList<Vertex> siblings = new LinkedList<Vertex>();
	System.out.println("Search");
		parent.put(start, null);
		siblings.add(start);
		all: while(!siblings.isEmpty()) {
			LinkedList<Vertex> newSibling = new LinkedList<Vertex>();
			for(Vertex vertexID : siblings) {
				for(int i=0;i<vertexID.edges().size();i++){
					Edge e = vertexID.edges().get(i);
					if (e.tail().equals(vertexID) && !parent.containsKey(e.tip()) && flow.get(e) < e.weight()) {
						parent.put(e.tip(), e);
						if (e.tip().equals(end)) {
							break all;
						}//End if
						newSibling.add(e.tip());
					}//End if
					else if (e.tip().equals(vertexID) && parent.containsKey(e.tail()) && flow.get(e) > 0) {
						parent.put(e.tail(), e);
						if (e.tail().equals(end)) {
							break all;
						}//end if
						newSibling.add(e.tail());
					}//end else if
				}//End for
			}///End for
			siblings = newSibling;
		}//End while
		if (siblings.isEmpty()) {
			return null;
		}//End if
		
		Vertex node = end;
		LinkedList<Edge> path = new LinkedList<Edge>();
		while (!node.equals(start)) {
			Edge e = parent.get(node);
			path.addFirst(e);
			if (e.tail().equals(node)) {
				node= e.tip();
			} else {
				node= e.tail();
			}//End if else
		}//End while
		return path;
	}//End BFSearch
	
	public HashMap<String,Vertex> vertices(){
		return vertices;
	}//End getVertacies
	
	public String toString() {
		String s = "";
		 Iterator<Vertex> v = vertices.values().iterator();
		for(int i=0;i<vertices.size();i++){
			Vertex vertex=v.next();
			s+=vertex+": ";
			for(int j=0;j<vertex.edges().size();j++)
					s+=vertex.edges().get(j).tip()+" ";
			s+="\n";
		}//End for
		return s;
	}//End toString
}//End Digraph Class
