	package day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day18 {
	
	public static int getNumber(StringBuilder snum, int index) {
		int i = index+1;
		if ("0123456789".contains(snum.subSequence(i, i+1)))
			i+=1;
		return Integer.parseInt(snum.subSequence(index, i).toString());
	}
	
	public static String reduce(StringBuilder snum) {
		int index = 0;
		boolean changed = false;
		int level = 0;
		
		do {
			switch (snum.charAt(index)) {
			case '[': level++; break;
			case ',': break;
			case ']': level--; break;
			default:
				// It's a number, so get the number
				int num = getNumber(snum, index);
				
				// Check for an explode
				if (level > 4) {
					// Get the number after the comma
					while (snum.charAt(index) != ',') index++;
					int second = getNumber(snum, index+1);
					
				} else if (num >= 10) {
					// It's a split
				}
				
			}
			
			index++;
		} while ((index < snum.length()) && (!changed));
		
		return snum.toString();
	}
	
	public static int part1(ArrayList<String> lines) {
		int currentLine = 1;
		String left = lines.get(0);
		
		while (currentLine < lines.size()){
			// Setup a StringBuilder to accumulate everything
			StringBuilder newLine = new StringBuilder();
			
			// Get the right string
			String right = lines.get(currentLine++);
			
			// Build a new string
			newLine = newLine.append('[')
							 .append(left)
							 .append(",")
							 .append(right)
							 .append(']');
			
			// Reduce this line
			left = reduce(newLine);
			
			// Get the next line
			
		}
		System.out.println(left);
		return 0;
	}
	
	public static int part2(ArrayList<String> lines) {
		return 0;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
//		String filename="src/day18/input";
		String filename="src/day18/sample1";
//		String filename="src/day18/sample2";
//		String filename="src/day18/sample3";
//		String filename="src/day18/sample4";
//		String filename="src/day18/sample5";
		
		
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

		System.out.println("Part 1: " + part1(lines));
		System.out.println("Part 2: " + part2(lines));

	}
}
