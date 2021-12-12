package day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Stack;

public class Day12 {
	static ArrayList<String> caves = new ArrayList<>();
	static ArrayList<ArrayList<Integer>> caveSystem = new ArrayList<ArrayList<Integer>>();
	
	
	public static int getCaveIndex(String caveName) {
		if (!caves.contains(caveName)) {
			caves.add(caveName);
			caveSystem.add(new ArrayList<Integer>());
		}
		return caves.indexOf(caveName);
	}
	
	public static void addEdge(int from, int to) {
		caveSystem.get(from).add(to);
		caveSystem.get(to).add(from);
	}
	
	public static void buildCaveSystem(ArrayList<String> lines) {
		// Create the cave system
		for (String line: lines) {
			String[] nodes = line.strip().split("-");
			int u = getCaveIndex(nodes[0]);
			int v = getCaveIndex(nodes[1]);
			addEdge(u,v);
		}
	}
	
    // A utility function to print the adjacency list
    // representation of graph
    static void printCaveSystem()
    {
        for (int i = 0; i < caveSystem.size(); i++) {
            System.out.println("\nEdges from " + caves.get(i));
            System.out.print("head");
            for (int j = 0; j < caveSystem.get(i).size(); j++) {
                System.out.print(","+caves.get(caveSystem.get(i).get(j)));
            }
            System.out.println();
        }
    }
	
    public static Stack<Integer> path = new Stack<>();
//    public static ArrayList<Integer> visited = new ArrayList<>();
    public static int pathCount = 0;
    
    public static void printPath() {
    	pathCount ++;
    	for (int i=0; i< path.size(); i++) {
    		int cave = path.get(i);
    		System.out.print(caves.get(cave) + ",");
    	}
    	System.out.println();
    }
    
    public static boolean smallCave(int cave) {
    	return (caves.get(cave).compareTo(caves.get(cave).toLowerCase())==0);
    }
    
    public static boolean caveCount(int cave) {
    	for (int i=0; i<visited.length; i++) {
    		if (smallCave(i) && visited[i]==2)
    			return false;
    	}
    	return true;
    }
    
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
    
    static int[] visited;
    
	public static void DFS(int from, int to) {
		if (from == to) printPath();
//		if (from == to) pathCount++;
		else {			
			// Check out the remaining caves
			for (int adj: caveSystem.get(from)) {
				// Have we visited this cave already?
//				if ((smallCave(adj) && !path.contains(adj)) || !smallCave(adj)){
				if (checkCave(adj)){
					path.push(adj);  visited[adj]++;
					DFS(adj, to);
					path.pop();      visited[adj]--;
				}
			}
		}
	}
	
	public static int part2(ArrayList<String> lines) {
		return 0;
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
		visited =  new int[caves.size()];
		path.push(getCaveIndex("start")); visited[getCaveIndex("start")]=1;
		DFS(getCaveIndex("start"), getCaveIndex("end"));
		System.out.println("Part 1: " + pathCount);
//		System.out.println("Part 2: " + part2(lines));

	}
}
