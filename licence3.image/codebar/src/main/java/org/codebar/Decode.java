package org.codebar;

import java.util.HashMap;

public class Decode {
	
	
	
	public static String decode(String code) {
		
		//The code must be 95 characters long
		if(code.length() != 95) {
			return("Not a valid EAN-13 barcode (pas le bon nb de bits)");
		}

		//We define a StringBuffer to facilitate (read: enable) 
		//operating on the string
		StringBuffer buf = new StringBuffer(code);
		
		//An EAN-13 barcode begins with 101
		if(buf.substring(0, 3).toString().compareTo("101") != 0) {
			System.out.println(buf.substring(0, 3).toString().compareTo("101"));
			System.out.println(buf.substring(0, 3).toString().compareTo("101") == 0);
			return("Not a valid EAN-13 barcode at the beginning (" + buf.substring(0, 3).toString() + ")");
		}
		//Ends in 101
		if(buf.substring(92, 95).toString().compareTo("101") != 0) {
			System.out.println(buf.substring(92, 95).toString().compareTo("101"));
			System.out.println(buf.substring(92, 95).toString().compareTo("101") == 0);
			return("Not a valid EAN-13 barcode at the end (" + buf.substring(92, 95).toString() + ")");
		}
		//And has 01010 after the first 6 coded numbers
		if(buf.substring(45,50).toString().compareTo("01010") != 0) {
			return("Not a valid EAN-13 barcode at the middle (" + buf.substring(45,50).toString() + ")");
		}
		
		//If all the verifications have been passed, we create a dictionary,
		//which contains all the possible combinations and their 
		//corresponding numbers
		HashMap<String, int[]> dictionnaire = new HashMap<>();
		dictionnaire.put("0001101", new int[] {0,1});
		dictionnaire.put("0100111", new int[] {0,2});
		dictionnaire.put("1110010", new int[] {0,3});
		dictionnaire.put("0011001", new int[] {1,1});
		dictionnaire.put("0110011", new int[] {1,2});
		dictionnaire.put("1100110", new int[] {1,3});
		dictionnaire.put("0010011", new int[] {2,1});
		dictionnaire.put("0011011", new int[] {2,2});
		dictionnaire.put("1101100", new int[] {2,3});
		dictionnaire.put("0111101", new int[] {3,1});
		dictionnaire.put("0100001", new int[] {3,2});
		dictionnaire.put("1000010", new int[] {3,3});
		dictionnaire.put("0100011", new int[] {4,1});
		dictionnaire.put("0011101", new int[] {4,2});
		dictionnaire.put("1011100", new int[] {4,3});
		dictionnaire.put("0110001", new int[] {5,1});
		dictionnaire.put("0111001", new int[] {5,2});
		dictionnaire.put("1001110", new int[] {5,3});
		dictionnaire.put("0101111", new int[] {6,1});
		dictionnaire.put("0000101", new int[] {6,2});
		dictionnaire.put("1010000", new int[] {6,3});
		dictionnaire.put("0111011", new int[] {7,1});
		dictionnaire.put("0010001", new int[] {7,2});
		dictionnaire.put("1000100", new int[] {7,3});
		dictionnaire.put("0110111", new int[] {8,1});
		dictionnaire.put("0001001", new int[] {8,2});
		dictionnaire.put("1001000", new int[] {8,3});
		dictionnaire.put("0001011", new int[] {9,1});
		dictionnaire.put("0010111", new int[] {9,2});
		dictionnaire.put("1110100", new int[] {9,3});
		
		//We define another dictionary to get the parity number
		HashMap<String,Integer> parity=new HashMap<>();
		parity.put("111111", new Integer(0));
		parity.put("112122", new Integer(1));
		parity.put("112212", new Integer(2));
		parity.put("112221", new Integer(3));
		parity.put("121122", new Integer(4));
		parity.put("122112", new Integer(5));
		parity.put("122211", new Integer(6));
		parity.put("121212", new Integer(7));
		parity.put("121221", new Integer(8));
		parity.put("122121", new Integer(9));
		
		//Once again we define StringBuffer variables
		StringBuffer output = new StringBuffer("");
		StringBuffer pnumber = new StringBuffer("");
		
		//We decode second to seventh elements using our dictionary
		//and append them to our output
		for(int i=0;i<6;i++) {
			String s_buffer = buf.substring(3+i*7, 10+i*7);
			System.out.println(s_buffer);
			System.out.println(dictionnaire.get(s_buffer));
			System.out.println(dictionnaire.get("0001011"));
			output.append(dictionnaire.get(s_buffer)[0]);
			pnumber.append(dictionnaire.get(s_buffer)[1]);
		}
		System.out.println(output.toString());
		if(pnumber.toString()=="222222") {
			return("Inverted");
		}
		//We insert the parity number at the beginning of the output
		output.insert(0, parity.get(pnumber.toString()));
		System.out.println(pnumber.toString());
		System.out.println(parity.get(pnumber.toString()));
		System.out.println(output.toString());
		
		//We decode the last 6 numbers and append them to our output
		for(int i=6;i<12;i++) {
			String s_buffer = buf.substring(8+i*7,15+i*7);
			System.out.println(s_buffer);
			System.out.println(dictionnaire.get(s_buffer));
			output.append(dictionnaire.get(s_buffer)[0]);
		}		
		System.out.println(output.toString());
		
		//We return the output
		return output.toString();
	}
}
