package hofstramappingapplication;

import java.util.LinkedHashMap;

public class Struct{
	
	protected LinkedHashMap<String, String> relevant;				// Need standard keys (e.g. 'id', 'name', etc.)
		// id		= unique identifier for system use		// May be a shorthand version of 'Name', or some numerical system
		// name		= Name of structure						// This is what will be displayed
		// hour		= hours of operation					// 
		// phone	= phone number							//
	
	protected LinkedHashMap<Integer, Node> nodes;
	
	public Struct() {
		relevant = null;
	}
	public Struct(LinkedHashMap<String, String> map) {
		relevant = map;
	}
	

	public void setRelevant(LinkedHashMap<String, String> map){
		relevant = map;
	}
	
}
