package day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Day15 {
	
	public static int findPath(int[][] floor) {
		// Get the length of the floor
		int endR = floor.length, endC = floor[0].length;
		
		// To hold the shortest path to every node
		int[][] shortest = new int[endR][endC];
		// Initialize it to all -1
		for (int r=0; r<shortest.length; r++)
			for (int c=0; c<shortest[r].length; c++)
				shortest[r][c] = -1;
		
		// To hold the next nodes to visit
		// We use an int[] to represent a tuple of three values
		// r and c are the coordinates of the node to visit
		// length is the length of the path before we entered.
		ArrayDeque<Integer[]> nodes = new ArrayDeque<>();
		
		// Start at 0
		shortest[0][0] = 0;
		
		// Push the next two nodes onto the queue
		nodes.add(new Integer[] {0, 1, 0});
		nodes.add(new Integer[] {1, 0, 0});
		shortest[0][1] = Integer.MAX_VALUE;
		shortest[1][0] = Integer.MAX_VALUE;
		
		// While we still have nodes to visit
		while (!nodes.isEmpty()) {
			// Get the next node from the queue, decode the contents
			Integer[] node = nodes.remove();
			int r = node[0]; int c = node[1]; int l = node[2];
			
			// Where are we?
//			System.out.println("Visiting node["+ r + "]["+ c + "], length=" + l + ", nodes remaining=" + nodes.size());

			// Is this the first time visiting this node?
			if (shortest[r][c] == Integer.MAX_VALUE)
				// Set the shortest path
				shortest[r][c] = l + floor[r][c];
			else
				// It's not, check if this path is shorter
				if (shortest[r][c] > l + floor[r][c])
					// It is, so reset it
					shortest[r][c] = l + floor[r][c];
			
			// Check the adjacent nodes
			if (c-1 >= 0) {
				// If the top node hasn't been visited, visit it
				if (shortest[r][c-1] == -1) {
					nodes.add(new Integer[] {r, c-1, shortest[r][c]});
					shortest[r][c-1] = Integer.MAX_VALUE;
				}
				// Else if we have a shorter path, set that path
				else
					if (shortest[r][c] + floor[r][c-1] < shortest[r][c-1]) {
					shortest[r][c-1] = shortest[r][c] + floor[r][c-1];
					nodes.add(new Integer[] {r, c-1, shortest[r][c]});
				}
			}
			
			if (c+1 < endC) {
				// Node below us
				if (shortest[r][c+1] == -1) { 
					nodes.add(new Integer[] {r, c+1, shortest[r][c]});
					shortest[r][c+1] = Integer.MAX_VALUE;
				}
				// Else if we have a shorter path, set that path
				else 
					if (shortest[r][c] + floor[r][c+1] < shortest[r][c+1]) {
					shortest[r][c+1] = shortest[r][c] + floor[r][c+1];
					nodes.add(new Integer[] {r, c+1, shortest[r][c]});
				}
			}
			
			if (r-1 >= 0) {
				// If the left node hasn't been visited, or has a longer path
				if (shortest[r-1][c] == -1) {
					nodes.add(new Integer[] {r-1, c, shortest[r][c]});
					shortest[r-1][c] = Integer.MAX_VALUE;
				}
				// Else if we have a shorter path, set that path
				else 
					if (shortest[r][c] + floor[r-1][c] < shortest[r-1][c]) {
					shortest[r-1][c] = shortest[r][c] + floor[r-1][c];
					nodes.add(new Integer[] {r-1, c, shortest[r][c]});
				}
			}
			
			if (r+1 < endR) {
				// Node to the right of us
				if (shortest[r+1][c] == -1) {
					nodes.add(new Integer[] {r+1, c, shortest[r][c]});
					shortest[r+1][c] = Integer.MAX_VALUE;
				}
				// Else if we have a shorter path, set that path
				else 
					if (shortest[r][c] + floor[r+1][c] < shortest[r+1][c]) {
					shortest[r+1][c] = shortest[r][c] + floor[r+1][c];
					nodes.add(new Integer[] {r+1, c, shortest[r][c]});
				}
			}
		}

		return shortest[endR-1][endC-1];
		
	}
	
	public static int part1(int[][] floor) {
				
		return findPath(floor);
	}
	
	public static int part2(int[][] floor) {

		// Make the new larger floor
		int rMax = floor.length;
		int cMax = floor[0].length;
		int[][] newFloor=new int[rMax*5][cMax*5];
		
		for (int r=0; r<floor.length; r++) {
			for (int c=0; c<floor[0].length; c++) {
				for (int rmul=0; rmul<5; rmul++) {
					for (int cmul=0; cmul<5; cmul++) {
						int newRisk = floor[r][c] + rmul + cmul;
						if (newRisk > 9) newRisk -= 9;
						newFloor[r+rMax*rmul][c+cMax*cmul] = newRisk;
					}	
				}
			}
		}
		
		return findPath(newFloor);
	}
	
	public static int[][] getCaveFloor(ArrayList<String> lines){
		int[][] floor = new int[lines.size()][lines.get(0).length()];
		
		for (int r=0; r<lines.size(); r++) {
			for (int c=0; c<lines.get(r).length(); c++) {
				floor[r][c] = lines.get(r).charAt(c) - '0';
			}
		}
		
		return floor;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day15/input";
//		String filename="src/day15/sample";
		
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
		
		int[][] floor = getCaveFloor(lines);

		System.out.println("Part 1: " + part1(floor));
		System.out.println("Part 2: " + part2(floor));

	}
}
