package day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day01 {
	
	public static int part1(ArrayList<Integer> numbers) {
		int increases = 0;
		int prev = 0;
		int curr;
		for (int num: numbers) {
			if (prev == 0) {
				prev = num;
			} else {
				curr = num;
				if (curr > prev)
					increases ++;
				prev = curr;
			}
		}
		
		return increases;
	}
	
	public static int part2(ArrayList<Integer> numbers) {
		int increases = 0;
		int prev = 0;
		int curr;
		for (int i=0; i<numbers.size()-2; i++) {
			if (prev == 0) {
				prev = numbers.get(i) + numbers.get(i+1) + numbers.get(i+2);
			} else {
				curr = numbers.get(i) + numbers.get(i+1) + numbers.get(i+2);
				if (curr > prev) increases++;
				prev = curr;
			}
		}
		
		return increases;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day01/input";
		
		ArrayList<Integer> lines = new ArrayList<>();
		
		try {
			// Open the input file
			input = new BufferedReader(new FileReader(filename));
			
			// Read every line
			String line;
			while ((line = input.readLine()) != null)
				if (line.length()>0)
					lines.add(Integer.parseInt(line));
		} catch (Exception e) {
			System.out.println("Problem reading input");
			System.exit(1);
		}

		System.out.println("Part 1: " + part1(lines));
		System.out.println("Part 2: " + part2(lines));

	}
}
