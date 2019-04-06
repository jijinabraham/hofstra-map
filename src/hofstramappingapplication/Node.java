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
		if(other != null) {
			if(other.neighbours.get(this.id) != null) {								// If link already exists between the two nodes, 
				this.neighbours.put(other.id, other.neighbours.get(this.id));		// add existing link to neighbours.
			}
			else {																	// Otherwise, create new link and store in 
				this.neighbours.put(other.id, new Link(this, other, dist));			// neighbours.
			}
		}
		
	}
	public void addStruct(Struct other) {
		if(other != null) {
			this.struct.put(other.relevant.get("id"), other);
			if(!other.nodes.get(this.id).equals(this)) {
				other.nodes.put(this.id, this);
			}
		}
	}
	
	public String toString() {
		String out = "" + id;
		
		if(coord != null) {
			out = out + " : " + coord.toString() + "\n";
		}
		else {
			out = out + " : null" + "\n";
		}
		
		out = out + "\t" + "neighbours:";
		
		if(neighbours != null) {
			if(neighbours.keySet().size() < 1) {
				out = out + "\n\t\t" + "empty";
			}
			else {
				for(int k = 0; k < this.neighbours.keySet().size(); k++) {
					if(k%4 == 0) {
						out = out + "\n\t";
					}
					out = out + "\t" + k + ", " + neighbours.get(neighbours.keySet().toArray()[k]).toString(id);
				}	
			}
		}
		else {
			out = out + "\t" + "null";
		}
		
		out = out + "\n\t" + "structs:" + "\n";
		
		if(struct != null) {
			if(struct.keySet().size() < 1) {
				out = out + "\t\t" + "empty";
			}
			else {
				for(int k = 0; k < this.struct.keySet().size(); k++) {
					if(k%4 == 0) {
						out = out + "\n\t";
					}
					out = out + "\t" + k + ", " + struct.get(struct.keySet().toArray()[k]);
				}	
			}
		}
		else {
			out = out + "\t" + "null";
		}
		
		return out;
	}
	
}
