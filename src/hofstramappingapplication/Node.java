package hofstramappingapplication;

import java.util.*;

public class Node{
	protected LocationData coord;
	protected int id;
	protected LinkedHashMap<Integer, Link> neighbours;
	protected LinkedHashMap<String, Struct> struct;
	// protected LatLng pos;
	
	public Node() {
		coord = null;
		id = -1;
		neighbours = null;
		struct = null;
	}
	public Node(LocationData d, int i, LinkedHashMap<Integer, Link> n, LinkedHashMap<String, Struct> s) {
		coord = d;
		id = i;
		neighbours = n;
		struct = s;
	}
	
	/**
	 * Simple comparison. Checks the ID of this against that of other.
	 * @param other is the target of comparison.
	 * @return True if both IDs match; False otherwise.
	 */
	public boolean equals(Node other) {	
		if(other != null)
			return this.id == other.id;
		return false;
	}
	
	/**
	 * Gets the cost to traverse between this and other.
	 * @param other is the target node.
	 * @return Distance between the two nodes.
	 */
	public double getCost(Node other) {
		return neighbours.get(other.id).dist;
	}
	
	/**
	 * Constructs and returns a list of neighbours.
	 * @return LinkedList of Nodes.
	 */
	public List<Node> getNeighbors() {
		List<Node> nb = new LinkedList<Node>();
		
		for(Integer i : neighbours.keySet()) {
			nb.add(neighbours.get(i).from(id));
		}
		
		return nb;
	}
	
	/**
	 * 
	 * @param other is the node to Link to.
	 * @param d is the distance between the two Nodes.
	 */
	public void addNeighbour(Node other, double dist) {
		if(other.neighbours.get(this.id) != null) {								// If link already exists between the two nodes, 
			this.neighbours.put(other.id, other.neighbours.get(this.id));		// add existing link to neighbours.
		}
		else if(other != null){													// Otherwise, create new link and store in 
			this.neighbours.put(other.id, new Link(this, other, dist));			// neighbours.
		}
	}
	
	public void addStruct(Struct other) {
		this.struct.put(other.relevant.get("id"), other);
		if(!other.nodes.get(this.id).equals(this)) {
			other.nodes.put(this.id, this);
		}
	}
}
