package hofstramappingapplication;

import java.util.*;

public class Struct{
	
	protected String id;
	protected LocationData pos;
	
//	protected LinkedHashMap<String, String> relevant;		// Need standard keys (e.g. 'id', 'name', etc.)
		// id		= unique identifier for system use		// May be a shorthand version of 'Name', or some numerical system
		// name		= Name of structure						// This is what will be displayed
		// hour		= hours of operation					// 
		// phone	= phone number							//
	
	protected LinkedList<String> names;
	protected LinkedHashMap<Integer, Node> nodes;
	
	public Struct() {
		id = null;
		pos = null;
		
		names = null;
		nodes = null;
	}
	public Struct(String id, LocationData pos) {
		this.id = id;
		this.pos = pos;
		
		names = new LinkedList<String>();
		nodes = new LinkedHashMap<Integer, Node>();
	}
	
	public void addName(String name) {
		names.add(name);
	}
	
	public boolean addNode(Node other) {
		if(other != null) {		
			this.nodes.put(other.id, other);
			if(!this.equals(other.struct.get(this.id))) {
				other.struct.put(this.id, this);
			}
			return true;
		}
		return false;
	}
	
	public String toString() {
		String out = "" + id;
		
		if(pos != null) {
			out = out + " : " + pos.toString() + "\n";
		}
		else {
			out = out + " : null" + "\n";
		}
		
		out = out + "\t" + "names:";
		
		if(names != null) {
			if(names.size() < 1) {
				out = out + "\n\t\t" + "empty";
			}
			else {
				for(int k = 0; k < this.names.size(); k++) {
					out = out + "\n\t\t" + k + ", " + names.get(k);
				}	
			}
		}
		else {
			out = out + "\t" + "null";
		}
		
		out = out + "\n\t" + "nodes:";
		
		if(nodes != null) {
			if(nodes.keySet().size() < 1) {
				out = out + "\n\t\t" + "empty";
			}
			else {
				for(int k = 0; k < this.nodes.keySet().size(); k++) {
					if(k%4 == 0) {
						out = out + "\n\t";
					}
					out = out + "\t" + k + ", " + nodes.get(nodes.keySet().toArray()[k]).id;
				}	
			}
		}
		else {
			out = out + "\t" + "null";
		}
		
		return out;
	}
	
}