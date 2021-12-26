package day19;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

class Scanner{
	int id;			// Which scanner
	int x, y, z;	// Current position in space
	ArrayList<Integer[]> beacons;
	
}

public class Day19 {
	
	public static int part1(ArrayList<String> lines) {
		return 0;
	}
	
	public static int part2(ArrayList<String> lines) {
		return 0;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
//		String filename="src/day19/input";
		String filename="src/day19/sample";
		
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
