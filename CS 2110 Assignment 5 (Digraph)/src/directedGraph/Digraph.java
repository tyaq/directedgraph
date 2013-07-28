package directedGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class Digraph {

	private HashMap<String,Vertex> vertices;//Storage of Vertices in Graph
	private LinkedList<Edge> edges;//Storage of edges in graph (Nobody knows why its a linked list))
	
	/**
	 * Constructor creates vertices and edges storage.
	 * As those are the two things a graph should know about itself.
	 */
	public Digraph(){
		this(new HashMap<String,Vertex>(),new LinkedList<Edge>());
	}//End Default Constructor
	
	public Digraph(HashMap<String,Vertex> v,LinkedList<Edge> e){
		this.vertices=v;
		this.edges=e;
	}//End Constructor
	/**
	 * Parses Strings input to edge format
	 * Adds an edge to the graph.
	 * If the vertices being connected are not in the graph already
	 * then create them and add them.
	 * 
	 * @param tail Where an edge starts from
	 * @param tip Where it goes to
	 * @param weight The edge's weight
	 */
	public void addEdge(String tail,String tip,String weight) {
		Vertex tailV=null, tipV=null;//Used for converting String to Node
		
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
		
		addEdge(tipV,tailV,w,null);//Call actual add Method
	}//End addEdge
	
	/**
	 * Adds an edge to the graph.
	 * If the vertices being connected are not in the graph already
	 * then create them and add them.
	 * 
	 * @param tail Where an edge starts from
	 * @param tip Where it goes to
	 * @param weight The edge's weight
	 */
	private void addEdge(Vertex tip, Vertex tail, int weight,Edge p) {
		// TODO Auto-generated method stub
		
		//Enter parsed data into data structures
		Edge e = new Edge(tail,tip,weight,p);//Create new edge
		edges.add(e);
	}//End addEdge
	
	/**
	 * Adds an edge to the graph.
	 * If the vertices being connected are not in the graph already
	 * then create them and add them.
	 * 
	 * @param tail Where an edge starts from
	 * @param tip Where it goes to
	 * @param weight The edge's weight
	 */
	private void addEdge(Edge edge) {
		// TODO Auto-generated method stub

		//Enter parsed data into data structures
		Edge e = edge;
		edges.add(e);
	}//End addEdge
	
	/**
	 * Gets the Max Flow to a vertex given a starting vertex.
	 * Calls calculation method.
	 * 
	 * @param start Origin Vertex
	 * @param end Destination Vertex
	 * @return A number with the maximum flow between those points.
	 */
	public int maxFlow(Object start,Object end) {
		//Cast the objects to strings
		start=start.toString();
		end=end.toString();
		
		//Create a flow and get its flow, then pass that into finding the max flow
		HashMap<Edge,Integer> flow = getFlow(vertices().get(start),vertices().get(end));
		return maxFlow(flow, vertices.get(start));
	}//End maxFlow method
	
	/**
	 * Uses a search and gets the shortest path between two vertices.
	 * @param start Origin Vertex
	 * @param end Destination Vertex
	 * @return A List of edges to follow
	 */
	public LinkedList<Edge> getPath(Object start,Object end) {
		//Cast the objects to strings
		start=start.toString();
		end=end.toString();
		
		//Create a dummy flow with dummy values for edges
		HashMap<Edge,Integer> dummyFlow = new HashMap<Edge,Integer>();
		for(Edge e:edges) {
			dummyFlow.put(e,0);
		}//End for
		
		//Get the shortest path using Breadth First Search
		LinkedList<Edge> path = bfSearch(vertices().get(start),vertices().get(end),dummyFlow);
		
		//Tell user that there is no path if one could not be found
		if (path==null) {
			System.out.println("Sorry there is no path between "+start+" and "+ end);
			return path;
		} else {
		return bfSearch(vertices().get(start),vertices().get(end),dummyFlow);
		}//End if else
	}//End getPAth
	
	/**
	 * Follows different paths and calculates
	 * which ones can contribute to max flow without overloading edge capacity.
	 * @param flow Flow for given paths to a point
	 * @param start The vertex at which all the flows begin from
	 * @return A number with the maximum flow from all flows.
	 */
	private int maxFlow(HashMap<Edge,Integer> flow, Vertex start) {
		int maxFlow= 0;//There must be zero flow to begin with
		
		//Add the maximum flow from traversing any edge with a path to maxFlow
		for (int i=0;i<start.edges().size();i++) {
			//System.out.println(flow.get(start.edges().get(i)));
			maxFlow += flow.get(start.edges().get(i));
		}//end for
		return maxFlow;
	}//End maxFlow method
	
	/**
	 * Calculates the flow from all paths from start to end using Ford-Fulkerson Flow Algorithm
	 * @param start Origin Vertex
	 * @param end Destination Vertex
	 * @return Paths with corresponding flows
	 */
	private HashMap<Edge, Integer> getFlow(Vertex start,Vertex end){
		LinkedList<Edge> path;//Keeps track of path being followed
		HashMap<Edge, Integer> flow = new HashMap<Edge,Integer>();//Keeps track of how much capacity has been used
		//if (start.equals(end)) return path;
		
		//Populate flow data structure, assume any path has no flow,
		//because it may not connect with the end vertex
		for(Edge e:edges) {
			flow.put(e,0);
		}//End for
		
		//Calculates the flow for any path leading to END using Ford-Fulkerson.
		
		//This BFSearch uses flows to get alternative paths
		//every loop, until it can't find any.
		while ((path = bfSearch(start,end,flow)) != null) {
			//System.out.println(path);
			int minCapacity = Integer.MAX_VALUE;//If its ridiculously high, everything is smaller than it.
			Vertex lastNode = start;
			
			//Finds bottleneck of flow along the path
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
			
			//Places the flow associated with an edge coming off of the START in HashMap flow
			//this is where the algo optimizes from the one in class
			lastNode = start;
			for (Edge edge : path) {
				if (edge.tail().equals(lastNode)) {
					//flow is incremented in edges returning to start
					flow.put(edge, flow.get(edge) + minCapacity);
					lastNode = edge.tip();
				}//End if
				else {
					//flow is removed from every other edge
					flow.put(edge, flow.get(edge) - minCapacity);
					lastNode = edge.tail();
				}//end if else
			}//End for
			//System.out.println(flow.get(path.get(0))+" Max flow in path: "+path);
		}//End while
		return flow;
	}//End getPath
	
	/**
	 * Breadth First Search from vertex Start to Vertex End.
	 * @param start Origin Vertex
	 * @param end Destination Vertex
	 * @param flow Used to target path to search, useful for finding alternate paths
	 * @return A path of edges from Vertex Start to End
	 */
	private LinkedList<Edge> bfSearch(Vertex start,Vertex end, HashMap<Edge,Integer> flow) {
		HashMap<Vertex,Edge> parent = new HashMap<Vertex,Edge>();//For recreating graph as it searches
		LinkedList<Vertex> siblings = new LinkedList<Vertex>();//For storing where its been
		
		//Enter in the start
		parent.put(start, null);
		siblings.add(start);
		
		//Travels until it can search no more, all label is used when end is found
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
					else if (e.tip().equals(vertexID) && !parent.containsKey(e.tail()) && flow.get(e) > 0) {
						parent.put(e.tail(), e);
						if (e.tail().equals(end)) {
							break all;
						}//end if
						newSibling.add(e.tail());
					}//end else if
				}//End for
			}///End for
			
			//Stores where it traveled
			siblings = newSibling;
		}//End while
		
		if (siblings.isEmpty()) {
			return null;
		}//End if
		
		//Recreates the paths and stores them 
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
	/**
	 * 
	 * @return Copies graph and turns directed edges into undirected edges
	 */
	public Digraph toUndirected(){
		Digraph undirected = new Digraph(vertices,edges);//Copy digraph
		
		//Adds reverse edges to graph
		for (Edge e:edges) {
			Vertex tip=e.tail();
			Vertex tail=e.tip();
			int weight=e.weight();
			undirected.addEdge(tip, tail, weight,e);
		}//end For
		
		return undirected;
	}//end to undirected
	
	public Digraph kruskal() {
		//Digraph udg=toUndirected();
		
		LinkedList<Edge> sorted = new LinkedList<Edge>(edges);
		Collections.sort(sorted);
		HashMap<String,Vertex> cVertices=new HashMap<String,Vertex>(vertices);
		HashMap<Vertex,Set<Vertex>> forest = new HashMap<Vertex,Set<Vertex>>();
		
		for (Vertex v:cVertices.values()) {
			Set<Vertex> vs = new HashSet<Vertex>();
			vs.add(v);
			forest.put(v, vs);
		}//Populate forest
		
		Digraph mst = new Digraph(cVertices,new LinkedList<Edge>());
		
		while(true) {
			Edge temp=sorted.remove(0);
			
			Set<Vertex> visted1 = forest.get(temp.tail());
			Set<Vertex> visted2 = forest.get(temp.tip());
			if (visted1.equals(visted2)){
				continue;
			}//End if
			mst.addEdge(temp);
			visted1.addAll(visted2);
			for (Vertex v:visted1) {
				forest.put(v, visted1);
			}//End for
			if (visted1.size()==cVertices.size()) {
				break;
			}//End if
		}//End While
		return mst;
	}//End Kruskal
	
	private LinkedList<Edge> quickSort(LinkedList<Edge> list){
		int pivot = list.size()/2;
		System.out.println(pivot);
		LinkedList<Edge> less,more;
		less=new LinkedList<Edge>();
		more=new LinkedList<Edge>();
		if (list.size()==1) {
			return list;
		}
		for(Edge e:edges) {
			if (e.compareTo(list.get(pivot))<0) {
				less.add(e);
			}//End if
			else {
				more.add(e);
			}//End else
		}//end for
		less= quickSort(less);
		more=quickSort(more);
		list=addLists(less,more);
		return list;
	}//End method
	
	private LinkedList<Edge> addLists(LinkedList<Edge> one, LinkedList<Edge> two) {
		for (int i = 0;i<two.size();i++) {
			one.add(two.get(i));//add it up
		}//end for
		return one;
	}//End method
	
	public int size(){
		return vertices.size();
	}
	
	/**
	 * 
	 * @return List of vertices in graph
	 */
	public HashMap<String,Vertex> vertices(){
		return vertices;
	}//End getVertacies
	
	/**
	 * 
	 * @return A list of edges in graph
	 */
	public LinkedList<Edge> edges(){
		return edges;
	}//End getEdges
	
	/**
	 * For printing a graph
	 */
	public String toString() {
		String s = "Graph:\n";
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
