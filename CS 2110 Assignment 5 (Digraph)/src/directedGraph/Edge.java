package directedGraph;

public class Edge implements Comparable<Object>{

	private int weight;	//Edge Weight
	private Vertex tail;	//Start
	private Vertex tip;	//End
	private Edge pair;	//pair for undirected
	
	/** Constructor for class Edge
	 * 
	 * @param ta Tailing Vertex
	 * @param ti Tipping Vertex
	 * @param w Edge Weight
	 */
	public Edge(Vertex ta, Vertex ti, int w, Edge p) {
		weight = w;
		tail = ta;
		tip = ti;
		pair = p;
		ta.add(this);	//Tell the tail that it can follow this edge
		
		/* If you wanted undirected graph you could create an opposite edge and tell the tip about it. */
	}
	
	/**
	 * Compares the weight of edges
	 *
	 * @return 0 if the edges weight is equal, 1 if less, and -1 if bigger
	 */
	public int compareTo(Object o) {
        	Edge e1 = (Edge) o;
        	if(e1.weight == this.weight) {
            		return 0;
		}
        	return e1.weight < this.weight ? 1 : -1;
    	}
	
	/**
	 * Setter-Method to set the weight.
	 */
	public void setWeight(int w) {
		weight = w;
	}
	/**
	 * Getter-Method for the weight.
	 *
	 * @return Returns the weight.
	 */
	public int weight() {
		return weight;
	}
	
	/**
	 * Getter-Method for the tip of an edge
	 *
	 * @return Returns the tip vertex of an edge.
	 */
	public Vertex tip() {
		return tip;
	}
	/**
	 * Getter-Method for the tail of an edge
	 *
	 * @return Returns the tail vertex of an edge.
	 */
	public Vertex tail() {
		return tail;
	}
	/**
	 * Getter-Method for the edge pair
	 *
	 * @return Returns the edge
	 */
	public Edge pair() {
		return pair;
	}
	
	/*
	 * Checks if the edge has a specific tail.
	 *
	 * @param ta The tail vertex that should be compared to
	 * @return returns the edge if the tails are equal, returns null if not
	 */
	public Edge find(Vertex ta) {
		if (tail == ta) {
			return this;
		}
		return null;	
	}
	/*
	 * Converts the edge into a String.
	 *
	 * @return A String representing the Edge.
	 */
	public String toString() {
		return "[" + tail + "->" + tip + " Weight:" + weight + "]";
	}
}
