package directedGraph;

public class Edge {

	private int weight;//Edge Weight
	private Vertex tail;//Start
	private Vertex tip;//End
	private Edge pair;//pair for undirected
	
	/**
	 * 
	 * @param ta Tailing Vertex
	 * @param ti Tipping Vertex
	 * @param w Edge Weight
	 */
	public Edge(Vertex ta,Vertex ti,int w,Edge p){
		weight = w;
		tail = ta;
		tip = ti;
		pair=p;
		ta.add(this);//Tell the tail that it can follow this edge
		//If you wanted undirected graph you could create an opposite edge and tell the tip about it.
	}//End Constructor
	
	public void setWeight(int w){
		weight=w;
	}//End setWeight
	
	public int weight(){
		return weight;
	}//End getWeight
	
	public Vertex tip(){
		return tip;
	}
	
	public Vertex tail(){
		return tail;
	}
	
	public String toString(){
		return "["+tail+"->"+tip+" Weight:"+weight+"]";
	}
}
