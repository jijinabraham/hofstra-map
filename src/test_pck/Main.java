package test_pck;

import java.io.*;
import hofstramappingapplication.*;

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
	
	public static void main(String[]args) {
		String s = "3 1 01 001";
		rw(s);
		
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
		
		
	}
}
