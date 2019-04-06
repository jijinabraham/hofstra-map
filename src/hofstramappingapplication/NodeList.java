package hofstramappingapplication;

import java.util.*;
import java.io.*;

 

public class NodeList {
	
	protected LinkedHashMap<Integer, Node> nodes;
	protected LinkedHashMap<String, Struct> struct;
	
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
	
	public void parse_json(String in) {
		//parse_struct( "struct.txt" );
		parse_node( "node-hor.txt" );
		parse_node( "node-ver.txt" );
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
				
		    while (br.ready()) {
		    	txt = txt + br.readLine();
		    }
		     
		    fis.close();
		    isr.close();
		    br.close();
		     
		    try {
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
			int iL = in.indexOf('}', iR);
			if(iL != -1) {
				entries.add(in.substring(iR, iL + 1));
				iR = in.indexOf('{', iR);
			}
		}
		
		//First Parse - data into new Nodes
		//	- id
		//	- struct
		// *- pos (latitude, longitude)
		for(String entry : entries) {
			int id = parse_id(get_val(entry, "id"));
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
				int iX = in.indexOf(prefix, i);
				if(iX >= 0) {
					int ix = iX + prefix.length();
					String suffix = in.substring(ix, ix + 2);
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
				
				int iX = in.indexOf(prefix, i);
				if(iX >= 0) {
					int ix = iX + prefix.length() + 2;
					String suffix = in.substring(ix, ix + 2);
					String key = prefix + suffix;
					String key2 = prefix2 + suffix;
					
					nodes.get(parse_id( get_val(entry, "id") ))
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
//			while((in.charAt(iR) >= '0' && in.charAt(iR) <= '9')
//					|| in.charAt(iR) != '.') {
//				iR++;
//			}
			for(iR = iC + 1; in.charAt(iR) < '0' 
							 || in.charAt(iR) > '9'
							 || in.charAt(iR) != '.'; iR++){
						iR++;
					}
			
			int iL = iR + 1;
			for(iL = iR + 1; (in.charAt(iR) >= '0' && in.charAt(iR) <= '9')
					 		 || in.charAt(iR) == '.'; iR++){
				iR++;
			}
			
			String out = in.substring(iR, iL); 
			return out;
		}
		else
			return null;
	}

}
