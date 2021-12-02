package day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day02 {
	
	public static int part1(ArrayList<String> lines) {
		int hpos=0;
		int depth=0;
		
		for (String line: lines) {
			String [] parts = line.split(" ");
			if (parts[0].compareTo("forward") == 0)
				hpos += Integer.parseInt(parts[1]);
			else if (parts[0].compareTo("down") == 0)
				depth += Integer.parseInt(parts[1]);
			else
				depth -= Integer.parseInt(parts[1]);
		}
		
		return hpos*depth;
	}
	
	public static int part2(ArrayList<String> lines) {
		int hpos=0;
		int depth=0;
		int aim=0;
		
		for (String line: lines) {
			String [] parts = line.split(" ");
			if (parts[0].compareTo("forward") == 0) {
				hpos += Integer.parseInt(parts[1]);
				depth += Integer.parseInt(parts[1]) * aim;
			}
			else if (parts[0].compareTo("down") == 0)
				aim += Integer.parseInt(parts[1]);
			else
				aim -= Integer.parseInt(parts[1]);
		}
		
		return hpos*depth;	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day02/input";
		
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
