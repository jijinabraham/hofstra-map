package hofstramappingapplication;

/* This class consists of a reference to the target node, as well as the distance to that node.
 * This storage makes it easier to calculate the distance.
 */

public class LinkOld {
	protected Node target;												// Can reference database entry OR
																		// Link can be mimicked on the database itself
	protected double dist;
																
	public LinkOld(){
		target = null;
		dist = -1;													// Negative values are illegal. Default / null value of -1.
	}
	public LinkOld(Node t, double d){
		target = t;
		dist = d;
	}
	
	public Node from(int id) {
		return target;
	}
	
	public Node to(int id) {
		return null;
	}
}
