package day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Day16 {
		
	public static String parseTransmission(String trans) {
		// We need to convert everything from hex to binary
		StringBuilder decoded = new StringBuilder();
		
		for (int i=0; i<trans.length(); i++) {
			String digits = Integer.toBinaryString(Integer.parseInt(trans.substring(i, i+1), 16));

			// Pad it with leading zeros to four bits
			decoded.append("0000".substring(0,4-digits.length()));
			decoded.append(digits);
		}
		
		return decoded.toString();
	}
		
	public static int part1(String line) {
		String decoded = parseTransmission(line);
		System.out.println(line + " = " + decoded);
		
		int literal = 0;
		int version = 0;
		int type = 0;
		
		int currentChar = 0;

		while (currentChar < line.length()) {
			// Get the version and type of the 
			version = Integer.parseInt(decoded.substring(currentChar, currentChar+3), 2);
			type = Integer.parseInt(decoded.substring(currentChar+3, currentChar+6), 2);
			
			currentChar += 6;
			
			if (type == 4) {
				// This is a literal
				StringBuilder strLit = new StringBuilder();
				String next = decoded.substring(currentChar, currentChar+5);
				currentChar += 5;
				while (next.charAt(0) == '1') {
					strLit.append(next.substring(1));
					next = decoded.substring(currentChar, currentChar+5);
					currentChar += 5;
				}
				strLit.append(next.substring(1));
				literal = Integer.parseInt(strLit.toString(), 2);

			} else {
				// This is an operator
			}
			
		}
		
		System.out.println("V=" + version + ", T=" + type + ", Literal=" + literal);
		
		return version;
	}
	
	public static int part2(ArrayList<String> lines) {
		return 0;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
//		String filename="src/day16/input";
		String filename="src/day16/sample1";
//		String filename="src/day16/sample2";
//		String filename="src/day16/sample3";
//		String filename="src/day16/sample4";
//		String filename="src/day16/sample5";
//		String filename="src/day16/sample6";
//		String filename="src/day16/sample7";
		
		ArrayList<String> lines = new ArrayList<>();
		
		try {
			// Open the input file
			input = new BufferedReader(new FileReader(filename));
			
			// Read every line
			String line;
			while ((line = input.readLine()) != null)
				if (line.length()>0)
					lines.add(line);
		} catch (Exception e) {
			System.out.println("Problem reading input");
			System.exit(1);
		}

		System.out.println("Part 1: " + part1(lines.get(0)));
		System.out.println("Part 2: " + part2(lines));

	}
}
