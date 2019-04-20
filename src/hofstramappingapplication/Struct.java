package hofstramappingapplication;

import java.util.*;

public class Struct{
	
	protected String id;
//	protected LinkedHashMap<String, String> relevant;		// Need standard keys (e.g. 'id', 'name', etc.)
		// id		= unique identifier for system use		// May be a shorthand version of 'Name', or some numerical system
		// name		= Name of structure						// This is what will be displayed
		// hour		= hours of operation					// 
		// phone	= phone number							//
	protected LinkedList<String> names;
	protected LinkedList<Node> path;
	protected LocationData pos;
	
	
	protected LinkedHashMap<Integer, Node> nodes;
	
	public Struct() {
		id = null;
	}
	public Struct(String i) {
		id = i;
	}
	
}