package day07;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day07 {
	
	public static int calcCost2(int position, int[] posList) {
		int cost = 0;
		for (int i = 0; i<posList.length; i++) {
			int n = Math.abs(position - posList[i]);
			cost += ((n+1) * n) / 2;
		}
		return cost;
	}
	
	public static int calcCost(int position, int[] posList) {
		int cost = 0;
		for (int i = 0; i<posList.length; i++)
			cost += Math.abs(posList[i] - position);
		return cost;
	}
	
	public static int part1(int[] pos) {		
		// Find the biggest and smallest
		int min = IntStream.of(pos).min().getAsInt();
		int max = IntStream.of(pos).max().getAsInt();
		int cost = Integer.MAX_VALUE;
		
		for (int i=min; i<=max; i++) {
			int newCost = calcCost(i, pos);
			cost = Math.min(cost,  newCost);
		}
		return cost;
	}
	
	public static int part2(int[] pos) {
		// Find the biggest and smallest
		int min = IntStream.of(pos).min().getAsInt();
		int max = IntStream.of(pos).max().getAsInt();
		int cost = Integer.MAX_VALUE;
		
		for (int i=min; i<=max; i++) {
			int newCost = calcCost2(i, pos);
			cost = Math.min(cost,  newCost);
		}
		return cost;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day07/input";
//		String filename="src/day07/sample";
		
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

		// Convert to an array of ints
		String[] strPos = lines.get(0).split(",");
		
		int[] pos = new int[strPos.length];
		for (int i=0; i<strPos.length; i++)
			pos[i] = Integer.parseInt(strPos[i]);
		
		// Get the answers
		System.out.println("Part 1: " + part1(pos));
		System.out.println("Part 2: " + part2(pos));

	}
}
