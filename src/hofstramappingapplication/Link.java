package hofstramappingapplication;

/* This class consists of a reference to the target node, as well as the distance to that node.
 * This storage makes it easier to calculate the distance.
 */

public class Link {
	protected Node alpha;											// Can reference database entry OR
	protected Node beta;												// Link can be mimicked on the database itself
	protected double dist;
																
	public Link(){
		alpha = null;
		beta = null;
		dist = -1;													// Negative values are illegal. Default / null value of -1.
	}
	public Link(Node a, Node b, double d){
		alpha = a;
		beta = b;
		dist = d;
	}
	
	public Node from(int id) {
		if(beta.id == id)
			return alpha;
		else if(alpha.id == id)
			return beta;
		else
			return null;
	}
	
	public Node to(int id) {
		if(alpha.id == id)
			return alpha;
		else if(beta.id == id)
			return beta;
		else
			return null;
	}
	
	public double dist() {
		return dist;
	}
	
	public String toString() {
		String out = alpha.id + " :: " + beta.id + ", " + dist + " m";
		
		return out;
	}
	
	public String toString(int from){
		String out = "" + from(from).id;
		
		return out;
	}
}
