package day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day13 {
	
	public static int countDots(boolean[][] paper) {
		int dots = 0;
		for (boolean[] row: paper)
			for (boolean dot: row)
				if (dot) dots++;
		return dots;
	}
	
	public static void printPaper(boolean[][] paper) {
		for (boolean[] row: paper) {
			for (boolean dot: row) {
				if (dot) System.out.print("#");
				else	 System.out.print(".");
			}
			System.out.println();
		}		
	}
	
	public static int part1(boolean[][] paper, ArrayList<String> foldLines) {
		
		// Figure out the fold
		String[] foldParts = foldLines.get(0).strip().split(" ");
		String[] fold = foldParts[2].strip().split("=");
		
		// Get the row/col number
		int lineNumber = Integer.parseInt(fold[1]);
				
		// Which axis
		if (fold[0].equals("y")) {
			int newRow = lineNumber-1;
			for (int y=lineNumber+1; y<paper.length; y++, newRow--)
				for (int x=0; x<paper[y].length; x++) {
					paper[newRow][x] = paper[newRow][x] | paper[y][x];
					paper[y][x] = false;
				}
			
		} else {
			for (int y=0; y<paper.length; y++) {
				int newCol = lineNumber-1;
				for (int x=lineNumber+1; x<paper[y].length; x++, newCol--) {
					
					paper[y][newCol] = paper[y][newCol] | paper[y][x];
					paper[y][x] = false;
				}
			}
			
		}
		
		// Count the dots
		return countDots(paper);
	}
	
	public static int part2(boolean[][] paper, ArrayList<String> foldLines) {
		
		
		for (String foldInstructions: foldLines) {
			// Figure out the fold
			String[] foldParts = foldInstructions.strip().split(" ");
			String[] fold = foldParts[2].strip().split("=");
			
			// Get the row/col number
			int lineNumber = Integer.parseInt(fold[1]);
			
			// Setup our new paper, which is trimmed from the original
			boolean[][] newPaper;
					
			// Which axis
			if (fold[0].equals("y")) {
				int newRow = lineNumber-1;
				newPaper = new boolean[newRow+1][paper[0].length];
				
				for (int y=lineNumber+1; y<paper.length; y++, newRow--)
					for (int x=0; x<paper[y].length; x++)
						newPaper[newRow][x] = paper[newRow][x] | paper[y][x];
					
			} else {
				newPaper = new boolean[paper.length][lineNumber];
				for (int y=0; y<paper.length; y++) {
					int newCol = lineNumber-1;
					for (int x=lineNumber+1; x<paper[y].length; x++, newCol--)						
						newPaper[y][newCol] = paper[y][newCol] | paper[y][x];
				}
			}
			
			// Trim the paper down
			paper = newPaper;
		}
		
		// Print the paper so we can see the letters
		printPaper(paper);
		
		// Just return 0 -- printing the paper is what we needed here
		return 0;
	}
	
	public static boolean[][] makePaper(ArrayList<String> lines, int x, int y) {
		boolean[][] paper = new boolean[y][x];
		
		// Parse each line
		for (String line: lines) {
			String[] coords = line.strip().split(",");
			paper[Integer.parseInt(coords[1])][Integer.parseInt(coords[0])] = true;
 		}
		
		return paper;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day13/input";
//		String filename="src/day13/sample";
		
		ArrayList<String> dotLines = new ArrayList<>();
		ArrayList<String> foldLines = new ArrayList<>();
		
		try {
			// Open the input file
			input = new BufferedReader(new FileReader(filename));
			
			// Read every line
			String line;
			// First read the dots, then the fold instructions
			boolean readDots = true;
			while ((line = input.readLine()) != null)
				if (line.length()==0)
					readDots = false;
				else if (readDots)	dotLines.add(line);
				else				foldLines.add(line);
		} catch (Exception e) {
			System.out.println("Problem reading input");
			System.exit(1);
		}
		
		boolean[][] paper = makePaper(dotLines, 1311, 895);	// My input
//		boolean[][] paper = makePaper(dotLines, 11,15);		// Sample

		System.out.println("Part 1: " + part1(paper, foldLines));
		System.out.println("Part 2: " + part2(paper, foldLines));

	}
}
