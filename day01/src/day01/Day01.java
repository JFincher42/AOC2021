package day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day01 {
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day01/input";
		
		ArrayList<String> lines = new ArrayList<>();
		
		try {
			// Open the input file
			input = new BufferedReader(new FileReader(filename));
			
			// Now we'll get into a loop, reading every line
			String line;
			while ((line = input.readLine()) != null)
				if (line.length()>0)
					lines.add(line);
		} catch (Exception e) {
			System.out.println("Problem reading input");
			System.exit(1);
		}

		int increases = 0;
		int prev = 0;
		int curr;
		for (String line: lines) {
			if (prev == 0) {
				prev = Integer.parseInt(line);
			} else {
				curr = Integer.parseInt(line);
				if (curr > prev)
					increases ++;
				prev = curr;
			}
		}
		
		System.out.println(increases);
		
		increases = 0;
		prev = 0;
		curr = 0;
		for (int i=0; i<lines.size()-2; i++) {
			if (prev == 0) {
				prev = Integer.parseInt(lines.get(i)) + Integer.parseInt(lines.get(i+1)) + Integer.parseInt(lines.get(i+2));
			} else {
				curr = Integer.parseInt(lines.get(i)) + Integer.parseInt(lines.get(i+1)) + Integer.parseInt(lines.get(i+2));
				if (curr > prev) increases++;
				prev = curr;
			}
		}
		
		System.out.println(increases);
	}
}
