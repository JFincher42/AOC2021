package day09;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day09 {
	
	public static boolean isLowPoint(int[][] map, int r, int c) {
		int maxR = map.length-1;
		int maxC = map[0].length-1;
		int position = map[r][c];
		
		// Are we in one of corners
		if (r==0 && c==0)	return ((position < map[r+1][c]) && (position < map[r][c+1]));
		if (r==0 && c==maxC)	return ((position < map[r+1][c]) && (position < map[r][c-1]));
		if (r==maxR && c==0)	return ((position < map[r-1][c]) && (position < map[r][c+1]));
		if (r==maxR && c==maxC)	return ((position < map[r-1][c]) && (position < map[r][c-1]));
		
		// Are we on an edge
		if (r==0)		return ((position < map[r+1][c]) && (position < map[r][c+1]) && (position < map[r][c-1]));
		if (c==0)		return ((position < map[r+1][c]) && (position < map[r-1][c]) && (position < map[r][c+1]));
		if (r==maxR)	return ((position < map[r-1][c]) && (position < map[r][c+1]) && (position < map[r][c-1]));
		if (c==maxC)	return ((position < map[r+1][c]) && (position < map[r-1][c]) && (position < map[r][c-1]));
		
		// We're in the field
		return ((position < map[r+1][c]) && (position < map[r-1][c]) && (position < map[r][c-1]) && (position < map[r][c+1]));
	}
	
	public static int part1(ArrayList<String> lines) {
		int riskLevel = 0;
		// First, turn the lines into an array of ints
		int [][] floorMap = createFloorMap(lines);
		
		// Next, check each location for low points
		for (int row=0; row<floorMap.length; row++)
			for (int col=0; col<floorMap[row].length; col++)
				if (isLowPoint(floorMap, row, col))
					riskLevel += floorMap[row][col] + 1;
		
		return riskLevel;
	}

	private static int[][] createFloorMap(ArrayList<String> lines) {
		int [][] floorMap = new int[lines.size()][lines.get(0).length()];
		
		for (int row = 0; row < lines.size(); row ++) {
			String[] rowHeights = lines.get(row).trim().split("");
			for (int col=0; col<rowHeights.length; col++) {
				floorMap[row][col] = Integer.parseInt(rowHeights[col]);
			}
		}
		return floorMap;
	}
	
	public static int part2(ArrayList<String> lines) {
		ArrayList<Integer> basinSizes = new ArrayList<>();
		
		// First, turn the lines into an array of ints
		int [][] floorMap = createFloorMap(lines);
		
		ArrayList<Integer> lowR = new ArrayList<>();
		ArrayList<Integer> lowC = new ArrayList<>();

		// Next, find the low points
		for (int row=0; row<floorMap.length; row++)
			for (int col=0; col<floorMap[row].length; col++)
				if (isLowPoint(floorMap, row, col)) {
					lowR.add(row);
					lowC.add(col);
				}
		
		// Now we can do an area fill to find the basin
		for (int i=0; i<lowR.size(); i++) 
			basinSizes.add(findBasinSize(floorMap, lowR.get(i).intValue(), lowC.get(i).intValue()));
		
		basinSizes.sort(null);
		return basinSizes.get(basinSizes.size()-1) * basinSizes.get(basinSizes.size()-2) * basinSizes.get(basinSizes.size()-3); 

	}
	
	private static int findBasinSize(int[][] floorMap, int r, int c) {
		// Are we off the end of the map
		if (r < 0 || c < 0 || r >= floorMap.length || c >= floorMap[r].length) return 0;
			
		// Did we hit an edge?
		if (floorMap[r][c] == 9) return 0;
		
		// Have we been here before?
		if (floorMap[r][c] == -1) return 0;
		
		// This is in the basin, so mark it and check around us as well
		floorMap[r][c] = -1;
		return (1 + findBasinSize(floorMap, r-1, c) +
					findBasinSize(floorMap, r+1, c) +
					findBasinSize(floorMap, r, c-1) +
					findBasinSize(floorMap, r, c+1));
	}

	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day09/input";
//		String filename="src/day09/sample";
		
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
