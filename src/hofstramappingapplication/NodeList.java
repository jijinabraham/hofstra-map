package hofstramappingapplication;

import java.util.*;
import java.io.*;

 

public class NodeList {
	
	protected LinkedHashMap<Integer, Node> nodes;
	protected LinkedHashMap<String, Struct> struct;
	
	boolean debugLines = true;
	boolean searchOut = true;
	boolean valueOut = false;
	boolean primOut = false;
	
	public NodeList() {
		nodes = new LinkedHashMap<Integer, Node>();
		struct = new LinkedHashMap<String, Struct>();
		parse_json();
	}
	
	public NodeList(LinkedHashMap<Integer, Node> nd, LinkedHashMap<String, Struct> st) {
		nodes = nd;
		struct = st;
	}
	
	
	/**
	 * Searches list of path nodes for a node with matching id.
	 * @param id
	 * @return
	 * 		Node n s.t. n.id == id, else null
	 */
	public Node find(int id) {
//		for(Node n : nodes) {
//			if(n.id == id)
//				return n;
//		}
//		return null;
		return nodes.get(id);
	}
	
	public void parse_json() {
		//parse_struct( "struct.txt" );
		if(debugLines)
			System.out.println("Parsing node-hor.json...");
		parse_node(new File("node-hor.json") );
		if(debugLines)
			System.out.println("Parsing node-ver.json...");
		parse_node(new File("node-ver.json") );
	}
	
	public void parse_struct(String in) {
		
	}
	
	/**
	 * 
	 * @param in
	 * @return
	 */
	public boolean parse_node(File in) {
		//Temporary Implementation
		String txt = "";
		try {
		    FileInputStream fis = new FileInputStream(in);
		    InputStreamReader isr = new InputStreamReader(fis);
		    BufferedReader br = new BufferedReader(isr);
		    
		    int debug = 0;
		    while (br.ready()) {
		    	if(debugLines) {
		    		if(debug % 3 == 0) {
			    		System.out.print("\tReading file");
			    		for(int i = ((debug/3)%3) + 1; i > 0; i--)
			    			System.out.print(".");
			    		System.out.println();
		    		}
		    	}
		    	txt = txt + br.readLine();
		    	debug++;
		    }
		     
		    fis.close();
		    isr.close();
		    br.close();
		     
		    try {
		    	if(debugLines)
		    		System.out.println("Parsing String...");
		    	parse_node(txt);
		    	return true;
		    }
		    catch(Error e) {
		    	 return false;
		    }
		     
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} 
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public void parse_node(String in) {
		LinkedList<String> entries = new LinkedList<String>();
		
		//Type A	-	Separates .json into entries; Each entry begins and ends with '{' and '}' respectively.
		int iR = 0;
		while(iR > -1) {
			if(debugLines && searchOut)
				System.out.println("Searching for entry start...");
			iR = in.indexOf('{', iR + 1);
			
			if(debugLines && searchOut){
				if(iR > -1)
					System.out.println("'{' found at index " + iR);
				else
					System.out.println("'{' not found");
			}
			
			if(iR > -1) {
				int iL = in.indexOf('}', iR);
				
				if(debugLines && searchOut){
					if(iL > -1)
						System.out.println("'}' found at index " + iL);
					else
						System.out.println("'}' not found");
				}
				
				if(iL != -1) {
					entries.add(in.substring(iR, iL + 1));
				}
			}
		}
		
		//First Parse - data into new Nodes
		//	- id
		//	- struct
		// *- pos (latitude, longitude)
		for(String entry : entries) {
			if(debugLines && valueOut) {
				System.out.println(entry);
			}
			if(debugLines)
				System.out.println("Parsing id...");
			int id = parse_id(get_val(entry, "id"));
			if(debugLines)
				System.out.println(get_val(entry, "id") + " : " + id);
			/*
			double lat = Double.parseDouble( get_val(entry, "lat") );
			double lon = Double.parseDouble( get_val(entry, "lng") );
			LocationData loc = new LocationData(lat, lng);
			 */
			LocationData loc = null;

			nodes.put(id, new Node(loc, id, 
					   new LinkedHashMap<Integer, Link>(),
					   new LinkedHashMap<String, Struct>()));
			
			for(int i = 0; i < entry.length(); i++) {
				String prefix = "stru_";
				int iX = entry.indexOf(prefix, i);
				if(iX >= 0) {
					int ix = iX + prefix.length();
					String suffix = entry.substring(ix, ix + 2);
					String key = prefix + suffix;
				
					nodes.get(id).addStruct(this.struct.get(get_val(in, key)));
					
					i = ix + 2;
				}
				else {
					break;
				}
			}
			
			for(int i = 0; i < entry.length(); i++) {
				String prefix = "ref_";
				String prefix2 = "dist_";
				
				int iX = entry.indexOf(prefix, i + 1);
				if(debugLines && searchOut) {
					if(iX > -1) {
						System.out.println("\t" + prefix + " found at index " + iX);
					}
				}
				
				
				if(iX >= 0) {
					int ix = iX + prefix.length();
					
					if(debugLines && valueOut) {
						System.out.println("\t" + i + " : " + iX + " :: " + ix + " : " + entry.charAt(ix)
																					   + entry.charAt(ix + 1));
					}
					
					String suffix = entry.substring(ix, ix + 2);
					
					String key = prefix + suffix;
					String key2 = prefix2 + suffix;
					
					if(debugLines) {
						System.out.println("\t" + key + " : " + get_val(entry, key));
						System.out.println("\t" + key2 + " : " + get_primitive(entry, key2));
					}
					
					
					nodes.get(id)
						 .addNeighbour(nodes.get(parse_id( get_val(entry, key))), 
									   Double.parseDouble( get_primitive(entry, key2)) );
					i = ix + 2;
				}
				else {
					break;
				}
			}		
		}	
		
		
		
	}

	/**
	 *	1 - Quadrant	- 0000 / 0	- null 
	 *					- 0001 to 1111 / 1 to f
	 *
	 *	2 - Orientation	- 0000 / 0	- null
	 *					- 0001 / 1	- horizontal
	 *					- 0010 / 2	- vertical
	 *					- 0011 / 3	- oblique
	 *
	 *	3 - Line no.	- 0000 0000 / 00 - null
	 * 					- 0000 0001 to ffff ffff / 01-FF
	 * 
	 *	4 - Street no.	- 0000 0000 0000 / 000 - null
	 * 					- 0000 0000 0001 to ffff ffff ffff / 001-FFF
	 * @param in
	 * @return
	 */
	private int parse_id(String in) {
		int out = 0;
		try {
			out = Integer.parseInt(in, 16);
		}
		catch(Error e) {
			e.printStackTrace();
		}
		return out;
	}
	
	/**
	 * Finds the value for the given field.
	 * Returns null if the value does not terminate.
	 * @param in : .json entry
	 * @param key : target field
	 * @return
	 */
	private String get_val(String in, String key) {
		if(in.contains("\"" + key + "\"")) {
			int index = in.indexOf("\"" + key + "\"");
			int iC = in.indexOf(':', index);
			int iR = in.indexOf('"', iC);
			int iL = in.indexOf('"', iR + 1);
			
			if(iL < 0) {
				return null;
			}
			
			String out = in.substring(iR + 1, iL); 
			return out;
		}
		else
			return null;
	}
	
	/**
	 * 
	 * @param in
	 * @param key
	 * @return
	 */
	private String get_primitive(String in, String key) {
		if(in.contains("\"" + key + "\"")) {
			int index = in.indexOf("\"" + key + "\"");
			int iC = in.indexOf(':', index);
			int iR = iC + 1;
			
			for(iR = iC + 1; 
						iR < in.length()
						&& (in.charAt(iR) < '0' 
							|| in.charAt(iR) > '9')
						&& in.charAt(iR) != '.';
								iR++){
						if(debugLines && primOut && searchOut)
							System.out.println("\t" + "Searching for number start at index " + iR + "..." + " (  " + in.charAt(iR) + "  )");
					}
			
			int iL = iR + 1;
			for(iL = iR + 1; iL < in.length()
							&& (	(in.charAt(iL) >= '0' && in.charAt(iL) <= '9')
									|| in.charAt(iL) == '.');
								iL++){
				if(debugLines && primOut && searchOut)
					System.out.println("\t" + "Searching for number end at index " + iL + "..." + " (  " + in.charAt(iL) + "  )");
			}
			
			if(debugLines && primOut && valueOut) {
				System.out.println("\t" + in.substring(iR, iL) + "(" + in.charAt(iL) + ")");
			}
			
			String out = in.substring(iR, iL); 
			return out;
		}
		else
			return null;
	}
	
	public String toString() {
		String out = "";
		for(Integer k : nodes.keySet()) {
			out = out + nodes.get(k).toString() + "\n";
		}
		
		return out;
	}

}
