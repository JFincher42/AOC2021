package day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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
	
	public static void changeLiteral(Integer literal) {
		literal += 100;
	}
	
	public static int part1(String line) {
		String decoded = parseTransmission(line);
		System.out.println(line + " = " + decoded);
		
		int version = Integer.parseInt(decoded.substring(0, 3), 2);
		int type = Integer.parseInt(decoded.substring(3, 6), 2);
		int literal = 0;
		
		if (type == 4) {
			StringBuilder strLit = new StringBuilder();
			int start = 6;
			String next = decoded.substring(start, start+5);
			while (next.charAt(0) == '1') {
				strLit.append(next.substring(1));
				start += 5;
				next = decoded.substring(start, start+5);
			}
			strLit.append(next.substring(1));
			literal = Integer.parseInt(strLit.toString(), 2);
		}
		
		System.out.println("V=" + version + ", T=" + type + ", Literal=" + literal);
		changeLiteral(literal);
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
