package day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day18 {
	
	public static String reduce(String snum) {
		return snum;
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
			newLine = newLine.append('[').append(left).append(",").append(right).append(']');
			
			// Reduce this line
			left = reduce(newLine.toString());
			
			// Get the next line
//			currentLine += 1;
			
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
