package com.example.myapplication.mod1;

import java.util.*;
import java.io.*;


// Module 2 

public class NodeList {
	
	protected LinkedHashMap<Integer, Node> nodes;
	protected LinkedHashMap<String, Struct> struct;
	
	//Functional Flags
	boolean useHor = true;													// Set to true to use node_hor.json
	boolean useVer = true;													// Set to true to use node_ver.json
	boolean useStruct = true;												// Set to true to use struct.json
	
	//Debug Flags
	boolean debugLines = false;											// Set to true if you want to see debug output
	boolean searchOut = true;											// Set to true to see debug search messages
	boolean valueOut = false;											// Set to true to see values for debug purposes
	boolean primOut = false;											// Set to true to see output from get_primitive()
	
	public NodeList() {
		nodes = new LinkedHashMap<Integer, Node>();
		struct = new LinkedHashMap<String, Struct>();
		parse_all();
	}
	
	public NodeList(File hori, File vert, File stru) {
		nodes = new LinkedHashMap<Integer, Node>();
		struct = new LinkedHashMap<String, Struct>();
		
		if(debugLines)
			System.out.println("Parsing node_hor.json...");
		String hor = parse_file(hori);
		parse_node(parse_entries(hor));
		
		if(debugLines)
			System.out.println("Parsing node_ver.json...");
		String ver = parse_file(vert);
		parse_node(parse_entries(ver));
		
		if(debugLines)
			System.out.println("Parsing struct.json...");
		String str = parse_file(stru);
		parse_struct(parse_entries(str));
	}

	public NodeList(InputStream hori, InputStream vert, InputStream stru) {
		nodes = new LinkedHashMap<Integer, Node>();
		struct = new LinkedHashMap<String, Struct>();

		if(debugLines)
			System.out.println("Parsing node_hor.json...");
		String hor = parse_stream(hori);
		parse_node(parse_entries(hor));

		if(debugLines)
			System.out.println("Parsing node_ver.json...");
		String ver = parse_stream(vert);
		parse_node(parse_entries(ver));

		if(debugLines)
			System.out.println("Parsing struct.json...");
		String str = parse_stream(stru);
		parse_struct(parse_entries(str));
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
	
	public void parse_all() {
		
		if(useHor) {
			if(debugLines)
				System.out.println("Parsing node_hor.json...");
			String hor = parse_file(new File("raw/node_hor.json") );
//			InputStream is = this.getResources().openRawResource(R.node_hor.json);
//			String hor = parse_stream(this.getResources().openRawResource(R.node_hor.json));
			parse_node(parse_entries(hor));
		}
		if(useVer) {
			if(debugLines)
				System.out.println("Parsing node_ver.json...");
			String ver = parse_file(new File("raw/node_hor.json") );
			parse_node(parse_entries(ver));
		}
		if(useStruct) {
			if(debugLines)
				System.out.println("Parsing struct.json...");
			String str = parse_file(new File("raw/struct.json") );
			parse_struct(parse_entries(str));
		}
	}
	
	public LinkedList<String> parse_entries(String in){
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
		
		return entries;
	}
	
	public void parse_struct(LinkedList<String> entries) {
		
		//First Parse - data into new Nodes
		//	- id
		//	- struct
		// *- pos (latitude, longitude)
		for(String entry : entries) {
			if(debugLines && valueOut) {
				System.out.println(entry);
			}
			String id = get_val(entry, "id");
			
			double lat = Double.parseDouble( get_primitive(entry, "lat") );
			double lon = Double.parseDouble( get_primitive(entry, "lng") );
			LocationData loc = new LocationData(lat, lon);
			
			struct.put(id, new Struct(id, loc));
		
			for(int i = 0; i < entry.length(); i++) {
				String prefix = "name";
				int iX = entry.indexOf(prefix, i);
				if(iX >= 0) {
					int ix = iX + prefix.length();
					String suffix = entry.substring(ix, ix + 2);
					String key = prefix + suffix;
				
					struct.get(id).addName(get_val(entry, key));
					
					i = ix + 2;
				}
				else {
					break;
				}
			}
			
			for(int i = 0; i < entry.length(); i++) {
				String prefix = "path";
				int iX = entry.indexOf(prefix, i);
				if(iX >= 0) {
					int ix = iX + prefix.length();
					String suffix = entry.substring(ix, ix + 2);
					String key = prefix + suffix;
				
					struct.get(id).addNode(this.nodes.get(parse_id(get_val(entry, key))));
					
					i = ix + 2;
				}
				else {
					break;
				}
			}
			
		}
	}
	
	/**
	 * 
	 * @param in
	 * @return
	 */
	public String parse_file(File in) {
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

		    return txt;
		     
		}
		catch (FileNotFoundException e) {
			System.out.println("File Not Found.");
			e.printStackTrace();
			return null;
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 *
	 * @param in
	 * @return
	 */
	public String parse_stream(InputStream in) {
		//Temporary Implementation
		String txt = "";
		try {
			InputStreamReader isr = new InputStreamReader(in);
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

			//fis.close();
			isr.close();
			br.close();

			return txt;

		}
		catch (FileNotFoundException e) {
			System.out.println("File Not Found.");
			e.printStackTrace();
			return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public void parse_node(LinkedList<String> entries) {
		
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
			if(debugLines && valueOut)
				System.out.println(get_val(entry, "id") + " : " + id);
			
			double lat = Double.parseDouble( get_primitive(entry, "lat") );
			double lon = Double.parseDouble( get_primitive(entry, "lng") );
			LocationData loc = new LocationData(lat, lon);

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
				
					nodes.get(id).addStruct(this.struct.get(get_val(entry, key)));
					
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
						if(/*valueOut = */true) {
							System.out.println(entry);
						}
						
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
			
			if(in.charAt(iR - 1) == '-') {
				iR = iR - 1;
			}
			
			String out = in.substring(iR, iL); 
			return out;
		}
		else
			return null;
	}
	
	public LinkedList<String> structGUI(){
		LinkedList<String> out = new LinkedList<String>();

		for(String s : struct.keySet()) {
			out.add(struct.get(s).names.get(0));
		}
		
		return out;
	}

	public Struct searchStruct(String name){
		for(String k : struct.keySet()){
			for(String v : struct.get(k).names){
				if(name.equalsIgnoreCase(v)){
					return struct.get(k);
				}
			}
		}
		return null;
	}

	public LinkedList<String> searchDepartment(){
		LinkedList<String> list = new LinkedList<String>();
		for(String k : struct.keySet()){
			for(String v : struct.get(k).names){
				if(v != null && v.toLowerCase().contains("department")){
					list.add(v);
				}
			}
		}
		if(list.isEmpty()){
			return null;
		}
		return list;
	}

	public String toString() {
		String out = "";
		for(Integer k : nodes.keySet()) {
			out = out + nodes.get(k).toString() + "\n";
		}
		for(String k : struct.keySet()) {
			out = out + struct.get(k).toString() + "\n";
		}
		
		return out;
	}

}
