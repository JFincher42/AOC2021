package day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day11 {
	
	public static void increment(int[][] map, int r, int c) {
		if (map[r][c] == -1) return;
		map[r][c]++;
	}

	public static int flash(int[][] map) {
		int flashCount = 0;

		// First, increment everything
		for (int r = 0; r < 10; r++)
			for (int c = 0; c < 10; c++)
				map[r][c]++;

		// Now, rescan and check for numbers == 10
		// We need to keep doing this until we don't process anymore
		boolean flashed = true;
		while (flashed) {
			// We haven't flashed this round
			flashed = false;

			// New flashes will be == 10
			// Old ones will be > 10, so we can ignore those
			for (int r = 0; r < 10; r++)
				for (int c = 0; c < 10; c++)
					if (map[r][c] > 9) {
						// We've flashed, so count it and mark it
						flashCount++;  flashed = true;  map[r][c] = -1;

						// Now we need to increment all the neighbors
						// Are we in one of corners
						if (r == 0 && c == 0)		{ increment(map, r+1, c);	increment(map, r, c+1);	increment(map, r+1, c+1); }
						else if (r == 0 && c == 9)	{ increment(map, r+1, c);	increment(map, r, c-1);	increment(map, r+1, c-1); }
						else if (r == 9 && c == 0)	{ increment(map, r-1, c);	increment(map, r, c+1);	increment(map, r-1, c+1); }
						else if (r == 9 && c == 9)	{ increment(map, r-1, c);	increment(map, r, c-1);	increment(map, r-1, c-1); }

						// Are we on an edge
						else if (r == 0) { increment(map, r, c+1); increment(map, r, c-1); increment(map, r+1, c+1); increment(map, r+1, c); increment(map, r+1, c-1); }
						else if (c == 0) { increment(map, r+1, c); increment(map, r-1, c); increment(map, r+1, c+1); increment(map, r, c+1); increment(map, r-1, c+1); } 
						else if (r == 9) { increment(map, r, c+1); increment(map, r, c-1); increment(map, r-1, c+1); increment(map, r-1, c); increment(map, r-1, c-1); }
						else if (c == 9) { increment(map, r+1, c); increment(map, r-1, c); increment(map, r+1, c-1); increment(map, r, c-1); increment(map, r-1, c-1); }

						// We're in the field
						else {
							increment(map, r-1, c-1);	increment(map, r-1, c);	increment(map, r-1, c+1);
							increment(map, r, c-1);								increment(map, r, c+1);
							increment(map, r+1, c-1);	increment(map, r+1, c);	increment(map, r+1, c+1);
						}
					}
		}
		
		// Now zero out everything which has flashed. These will be -1
		// First, increment everything
		for (int r = 0; r < 10; r++)
			for (int c = 0; c < 10; c++)
				if (map[r][c] == -1) map[r][c] = 0;
		
		return flashCount;
	}

	public static int part1(int[][] map) {
		int flashCount = 0;

		for (int step = 0; step < 100; step++) {
			flashCount += flash(map);

		}
		return flashCount;
	}
	
	public static boolean allZero(int[][] map) {
		for (int r=0; r<10; r++)
			for (int c=0; c<10; c++)
				if (map[r][c] != 0) return false;
		
		return true;
	}

	public static int part2(int[][] map) {
		boolean allFlashed = false;
		int step = 0;
		
		while (!allFlashed) {
			flash(map);
			allFlashed = allZero(map);
			step++;
		}
		
		return step;
	}

	public static void main(String[] argv) {
		BufferedReader input = null;
//		String filename="src/day11/input";
//		String filename = "src/day11/sample";
		String filename = "src/day11/sample2";

		ArrayList<String> lines = new ArrayList<>();

		try {
			// Open the input file
			input = new BufferedReader(new FileReader(filename));

			// Read every line
			String line;
			while ((line = input.readLine()) != null)
				if (line.length() > 0)
					lines.add(line);
		} catch (Exception e) {
			System.out.println("Problem reading input");
			System.exit(1);
		}

		int[][] map = new int[10][10];

		for (int r = 0; r < lines.size(); r++) {
			String line = lines.get(r).strip();
			for (int c = 0; c < line.length(); c++) {
				map[r][c] = line.charAt(c) - '0';
			}
		}

		System.out.println("Part 1: " + part1(map));
		System.out.println("Part 2: " + part2(map));

	}
}
