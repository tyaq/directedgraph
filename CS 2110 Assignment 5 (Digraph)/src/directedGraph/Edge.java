package directedGraph;

public class Edge {

	private int weight;
	private Vertex tail;
	private Vertex tip;
	
	/**
	 * 
	 * @param ta Tailing Vertex
	 * @param ti Tipping Vertex
	 * @param w Edge Weight
	 */
	public Edge(Vertex ta,Vertex ti,int w){
		weight = w;
		tail = ta;
		tip = ti;
		ta.add(this);
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
	
	public String toString(){
		return "["+tail+"->"+tip+" Weight:"+weight+"]";
	}
}
