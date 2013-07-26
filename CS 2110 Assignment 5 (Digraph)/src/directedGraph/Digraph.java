package directedGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Digraph {

	private HashMap<String,Vertex> vertices;
	
	public Digraph(){
		vertices = new HashMap<String,Vertex>();
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
	}//End addEdge
	
	public void maxFlow(Vertex start, Vertex end) {
		ArrayList<Edge> path = getPath(start,end,null);
		while(path!=null) {
			
		}//end while
	}
	
	public ArrayList<Edge> getPath(Vertex start,Vertex end,ArrayList<Edge> path){
		System.out.println(start.equals(end));
		if (start.equals(end)) return path;
		int residual;//For finding remaining capacity
		int flow = 0;//Flow begins at 0
		for (int i=0; i<start.edges().size();i++) {
			residual = start.edges().get(i).weight() - flow;
			System.out.println(residual);
			if (residual>0 && !path.contains(start.edges().get(i))){
				ArrayList<Edge> result = getPath(start.edges().get(i).tip(),end,path);
				if (result != null) {
					path.add(start.edges().get(i));
					return result;
				}
			}//End if
		}//End for
		return null;
			
	}//End getPath
	
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
