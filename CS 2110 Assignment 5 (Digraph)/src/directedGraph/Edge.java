package directedGraph;

public class Edge implements Comparable<Object>{

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
	
	public int compareTo(Object o) {
        Edge e1 = (Edge)o;
        if(e1.weight==this.weight)
            return 0;
        return e1.weight < this.weight ? 1 : -1;
    }//End Compare
	
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
	
	public Edge pair(){
		return pair;
	}//end getPair
	
	public Edge find(Vertex ta){
		if (tail==ta) {
			return this;
		}//end if
		return null;
		
	}
	public String toString(){
		return "["+tail+"->"+tip+" Weight:"+weight+"]";
	}
}
