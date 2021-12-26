package day25;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day25 {

	public static void printMap(char[][] map) {
		for (int r=0; r<map.length; r++) {
			for (int c=0; c<map[r].length; c++) {
				System.out.print(map[r][c]);
			}
			System.out.println();
		}
		System.out.println("---------------------");
	}
	
	public static boolean process(char[][] map1, char[][] map2){
		boolean changed = false;
		
		// Read map1, into map 2
		// First, check the east bound cukes
		for (int r=0; r<map1.length; r++) {
			for (int c=0; c<map1[r].length; c++) {
				if (map1[r][c] == '>') {
					// Check if the next space is open
					int nextc = c + 1;
					if (nextc >= map1[r].length) nextc=0;
					
					if (map1[r][nextc] == '.') {
						// we can move, so mark the change
						changed = true;
						// Make the change
						map2[r][c] = '.';
						map2[r][nextc] = '>';
					}
				}
			}
		}
		
		// Copy map2 to map1
		for (int r=0; r<map1.length; r++)
			for (int c=0; c<map1[r].length; c++)
				map1[r][c] = map2[r][c];
		
//		printMap(map2);

		// Then, check the south bound cukes
		for (int r=0; r<map2.length; r++) {
			for (int c=0; c<map2[r].length; c++) {
				if (map2[r][c] == 'v') {
					// Check if the next space is open
					int nextr = r + 1;
					if (nextr >= map2.length) nextr=0;
					
					if (map2[nextr][c] == '.') {
						// we can move, so mark the change
						changed = true;
						// Make the change
						map1[r][c] = '.';
						map1[nextr][c] = 'v';
					}
				}
			}
		}
//		printMap(map1);
		
		// Copy map1 to map2
		for (int r=0; r<map1.length; r++)
			for (int c=0; c<map1[r].length; c++)
				map2[r][c] = map1[r][c];
		
		
		return changed;
	}
	
	public static int part1(char[][] map1) {
		// First make a copy of the map
		char[][] map2 = new char[map1.length][map1[0].length];
		for (int r=0; r<map1.length; r++)
			for (int c=0; c<map1[r].length; c++)
				map2[r][c] = map1[r][c];
		
		// How many iterations have we done 
		int count = 0;
		
		// Did we make any changes?
		boolean changed = true;
		
		// Keep going until we make a change
		while (changed) {
			// Set changed to false
			changed = false;
			
			// Process the map
			changed = process(map1, map2);
			count += 1;	
		}
		return count;
	}
	
	public static int part2(ArrayList<String> lines) {
		return 0;
	}
	
	public static char[][] parseMap(ArrayList<String> lines) {
		char[][] map = new char[lines.size()][lines.get(0).length()];
		
		for (int row=0; row < lines.size(); row++)
			for (int col=0; col < lines.get(row).length(); col++)
				map[row][col] = lines.get(row).charAt(col);
		
		return map;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day25/input";
//		String filename="src/day25/sample";
		
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
		
		char[][] map = parseMap(lines);

		System.out.println("Part 1: " + part1(map));
		System.out.println("Part 2: " + part2(lines));

	}
}
