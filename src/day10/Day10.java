package day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Stack;

public class Day10 {
	
	public static String startingChars = "([{<";
	public static String endingChars = ")]}>";
	
	public static long findMismatch(String line, boolean mismatch) {
		// If mismatch is true, we return a mismatch score
		// Otherwise we return a close the line score
		
		Stack<Character> openings = new Stack<>();
		long[] returns = new long[] {3, 57, 1197, 25137};
		long lineScore = 0;
		
		for (int i=0; i<line.length(); i++) {
			char ch = line.charAt(i);
			if (startingChars.indexOf(ch) >= 0)
				// We have a match
				openings.push(ch);
			else {
				// It's a close
				char prev = openings.pop();
				if (startingChars.indexOf(prev) != endingChars.indexOf(ch)) {
					// No match, so what to return
					if (mismatch) return returns[endingChars.indexOf(ch)];
					// We don't need to find mismatches, so just return 0
					else return 0;
				}
			}
		}
		
		// If we get here, we have an incomplete line
		// If we're looking for mismatches, we should bail
		if (mismatch) return 0;
		
		// Now we can pop everything off the stack and figure out the score
		while (!openings.isEmpty()) {
			char ch = openings.pop();
			int score = startingChars.indexOf(ch)+1;
			lineScore = lineScore*5 + score;
		}
	
		return lineScore;
		
	}
	
	public static long part1(ArrayList<String> lines) {
		long totalScore = 0;
		
		for (String line:lines) {
			totalScore += findMismatch(line, true);
		}
		
		return totalScore;
	}
	
	public static long part2(ArrayList<String> lines) {
		ArrayList<Long> lineScores = new ArrayList<>();
		
		for (String line:lines) {
			long score = findMismatch(line, false);
			if (score != 0)
				lineScores.add(findMismatch(line, false));
		}
		
		lineScores.sort(null);
		
		return lineScores.get((lineScores.size()/2));
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day10/input";
//		String filename="src/day10/sample";
		
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
