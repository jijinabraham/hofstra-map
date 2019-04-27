package test_pck;

import java.io.*;
import java.util.*;
import hofstramappingapplication.*;
import mod_3.*;

public class Main {
	
	private static String rw(String in) {
		String out = in;
		
		while(out.contains(" ")) {
			System.out.println(out + " contains blank space at index " + out.indexOf(" "));
			out = out.substring(0, out.indexOf(" ")) + out.substring(out.indexOf(" ") + 1);
		}
		
		System.out.println(out + " contains no more blank spaces");
		
		return out;
	}
	
	private static void printConversion(String hex) {
		String bStr = Integer.toBinaryString(Integer.parseInt(hex, 16));
		int len = bStr.length();
		for(int i = len - 1; i > 0; i--) {
			if((len - i) % 4 == 0) {
				bStr = bStr.substring(0, i) + " " + bStr.substring(i);
			}
		}
		while(bStr.indexOf(' ') < 4) {
			bStr = '0' + bStr;
		}
		
		System.out.println(hex + " : " + bStr + " : " + Integer.parseInt(hex, 16));
	}
	
	//Get cost from current to a specific node
	public static double getEstimatedCost(LocationData from, LocationData to) {
		//h(n) = 6(shortest distance between two points) * shortest # of nodes to goal
		double r = 6378100.0;
		
		double theta = from.getLat() - to.getLat();				// Degree diff
		theta = Math.toRadians(theta); 
			
		double phi = from.getLon() - to.getLon();				// Degree diff
		phi = Math.toRadians(phi);
			
		double ct = 2 * r * Math.sin(theta / 2);								// 2r sin(theta / 2)
		double cp = 2 * r * Math.sin(phi / 2);									// 2r sin(phi / 2)
			
		double c = Math.sqrt(Math.pow(ct, 2) + Math.pow(cp, 2));				// c = sqrt( ct^2 + cp^2 )
			
		double delta = Math.toRadians(60);
		if(c > r) {
			delta = Math.asin(r / c);
		}
		else if (c < r) {
			delta = Math.asin(c / r);
		}
			
		//lat lon diff -> meters (double)
		double est = r * delta;
			
		return est;
	}
	
	
	public static void main(String[]args) {
//		String s = "3 1 01 001";
//		rw(s);
		
/*		String txt = "";
		try {
		     FileInputStream fis = new FileInputStream(new File("node-hor.json"));
		     InputStreamReader isr = new InputStreamReader(fis);
		     BufferedReader br = new BufferedReader(isr);
				
		     while (br.ready()) {
		    	 txt = txt + br.readLine();
		     }
		     
		     fis.close();
		     isr.close();
		     br.close();  
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(txt);
*/		
		
//		String hex = "3101001";
//		printConversion(hex);
		printConversion("3101001");
		printConversion("31E10AC");
		printConversion("E1510DB");
		printConversion("CE3");
		
		NodeList nL = new NodeList();

		System.out.println(nL.toString());
		
		for(int i = 0; i < 3; i++)
			System.out.println();
		
		
		int idFrom = Integer.parseInt("310a004", 16);
		int idTo = Integer.parseInt("3108008", 16);
		Node from = nL.find(idFrom);
		Node to = nL.find(idTo);
		
		NodeSearch start = new NodeSearch(from);
		NodeSearch end = new NodeSearch(to);
		
		AstarSearch aS = new AstarSearch(start, end);
		aS.search();

		System.out.println(aS.toString());
		
		
		/* Estimated Cost Testing
		 * 
		 */
/*		LocationData from = new LocationData(40, -70);
		LocationData to = new LocationData(50, -76);
		double est = getEstimatedCost(from, to);
		double real = 1206.01 * Math.pow(10, 3);
		double prc_error = (est - real) / real;
		System.out.println("The estimated distance between " + from + " and " + to + " was " + est + " m");
		System.out.println("The real distance between " + from + " and " + to + " was " + real + " m");
		System.out.println("The percent error was " + prc_error + "%");
		
		System.out.println("Adjusted value for estimated distance is " + (est * .90) + " m");
*/		
		
	}
}
