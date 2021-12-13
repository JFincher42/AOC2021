package day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Stack;

public class Day12 {
	// The names of all the caves, used for printing and finding the start/end caves
	static ArrayList<String> caves = new ArrayList<>();
	
	// Adjacency list of the cave system
	static ArrayList<ArrayList<Integer>> caveSystem = new ArrayList<ArrayList<Integer>>();
	
	// How many times have we visited every cave?
    static int[] visited;

	// The current path we're building
    public static Stack<Integer> path = new Stack<>();

    // How many items are in the current path
    public static int pathCount = 0;
    
    // Which part are we doing?
    public static int part;
	
    // Gets the index of the named cave
    // If the cave isn't in the system, add it first
	public static int getCaveIndex(String caveName) {
		// Do we know about this cave?
		if (!caves.contains(caveName)) {
			// Add it both the caves list and the cave system
			caves.add(caveName);
			caveSystem.add(new ArrayList<Integer>());
		}
		return caves.indexOf(caveName);
	}
	
	// Adds an edge between two caves in the system
	// This is a non-directed graph, so the edge goes both ways
	// So we add both edges.
	public static void addEdge(int from, int to) {
		caveSystem.get(from).add(to);
		caveSystem.get(to).add(from);
	}
	
	// Build the whole cave system from the input lines we were given
	public static void buildCaveSystem(ArrayList<String> lines) {
		// Create the cave system
		for (String line: lines) {
			// Split each line at the dash 
			String[] nodes = line.strip().split("-");
			// Get the index for each cave and add the edge between them
			int u = getCaveIndex(nodes[0]);
			int v = getCaveIndex(nodes[1]);
			addEdge(u,v);
		}
	}
	
	// Print the path we created
    public static void printPath() {
    	// Increment the path count
    	pathCount++;
    	
    	// Step through everything in the path and print the cave name
    	for (int i=0; i< path.size(); i++) {
    		int cave = path.get(i);
    		System.out.print(caves.get(cave) + ",");
    	}
    	System.out.println();
    }
    
    // Is this a small cave?
    // It is iff the name is lowercase
    // SO compare the name to the lowercase version 
    public static boolean smallCave(int cave) {
    	return (caves.get(cave).compareTo(caves.get(cave).toLowerCase())==0);
    }
    
    // Check if we've visited any small cave 2 times already
    public static boolean caveCount(int cave) {
    	for (int i=0; i<visited.length; i++) {
    		if (smallCave(i) && visited[i]==part)
    			return false;
    	}
    	return true;
    }
    
    // Check if we can visit this cave at all
    public static boolean checkCave(int cave) {
    	// Is this a large cave? We're OK
    	if (!smallCave(cave)) return true;
    	
    	// Is this the start cave? Don't go there again
    	if (cave == getCaveIndex("start")) return false;
    	
    	// Is this the end cave? We can go there iff we've never been there
    	if (cave == getCaveIndex("end")) return visited[cave]==0;
    	
    	// It's a small cave, not the start or end, so...
    	// If we haven't visited it yet, we're OK
    	if (visited[cave]==0) return true;
    	
    	// Otherwise, we've not visited any small cave more than two times, we're OK
    	return (caveCount(cave));
    	
    }

    // Depth first search, recursive 
	public static void DFS(int from, int to) {
		// If we're at the end, then we're done - print the path (or just increment the count)
		// Print the path if we're debugging
//		if (from == to) printPath();
		if (from == to) pathCount++;
		
		else {			
			// Check out the caves we can get to from here
			for (int adj: caveSystem.get(from)) {
				// Have we visited this cave already?
				if (checkCave(adj)){				// Part 2
					// Push this node to the path, and indicate it's been visited
					path.push(adj);  visited[adj]++;
					// Find the path from the node to the end
					DFS(adj, to);
					// Pop the node off and indicate it's no longer visited
					path.pop();      visited[adj]--;
				}
			}
		}
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day12/input";
//		String filename="src/day12/sample1";
//		String filename="src/day12/sample2";
//		String filename="src/day12/sample3";
		
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

		buildCaveSystem(lines);
		
		// Initialize the visited list
		visited =  new int[caves.size()];
		
		// Push the start node to the path, and mark it as visited
		path.push(getCaveIndex("start")); visited[getCaveIndex("start")]=1;

		// OK, search for every path we can find
		part = 2;
		DFS(getCaveIndex("start"), getCaveIndex("end"));
		
		// And print the number of them we find
		System.out.println("Part " + part + ": " + pathCount);

	}
}
