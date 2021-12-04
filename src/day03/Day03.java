package day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day03 {
	
	public static int part1(ArrayList<String> lines) {
//		StringBuilder gammaStr = new StringBuilder();
//		StringBuilder epsilonStr = new StringBuilder();
		
//		int[] oneCount = new int[strLen];
//		int[] zeroCount = new int[strLen];
		
//		for (String line:lines) {
//			for (int i=0; i<strLen; i++)
//				if (line.charAt(i)=='0')
//					zeroCount[i]++;
//				else
//					oneCount[i]++;
//		}
//		
//		for (int i=0; i<strLen; i++) {
//			if (zeroCount[i] > oneCount[i]) {
//				gammaStr.append('0');
//				epsilonStr.append('1');
//			} else {
//				gammaStr.append('1');
//				epsilonStr.append('0');
//			}
//		}
		
//		int gamma = Integer.parseInt(gammaStr.toString(), 2);
//		int epsilon = Integer.parseInt(epsilonStr.toString(), 2);
		
		int gamma=0, epsilon=0;
		
		for (int i=0; i<lines.get(0).length(); i++) {
			int newBit = mostCommonBit(lines, i);
			gamma = (gamma << 1) + newBit;
			epsilon = (epsilon << 1) + Math.abs(newBit - 1);
		}
		
		return gamma*epsilon;
	}
	
	public static int mostCommonBit(ArrayList<String> lines, int bit)
	{
		int oneCount=0, zeroCount=0;
		
		for (String line:lines)
			if (line.charAt(bit)=='0')	zeroCount++;
			else 						oneCount++;
		if (zeroCount > oneCount) return 0;
		return 1;
	}
	
	public int foo(int...v) {
		return v.length;
	}
	
	public static char charToFind(ArrayList<String> lines, int bit, boolean most)
	{
		int oneCount=0, zeroCount=0;
		
		for (String line:lines)
			if (line.charAt(bit)=='0')	zeroCount++;
			else						oneCount++;
		
		if (most) {
			if (zeroCount > oneCount) return '0';
			else if (oneCount > zeroCount) return '1';
		} else {
			if (zeroCount < oneCount) return '0';
			else if (oneCount < zeroCount) return '1';
		}
		return 'x';
	}
	
	public static int findNumber(ArrayList<String> lines, char criteria, boolean most)
	{
		// Which bit are we looking at
		int bit = 0;
		
		while (lines.size() > 1) {
			// Make a new list so we're not destroying the old one
			ArrayList<String> newLines = new ArrayList<String>();
			
			// Loop through the lines looking for the most common value
			char mostCommonChar = charToFind(lines, bit, most);
			// If it's equal, err on the side of criteria
			if (mostCommonChar == 'x') mostCommonChar=criteria;
			
			// Let's cull the list now
			for (String line: lines)
				if (line.charAt(bit) == mostCommonChar)
					newLines.add(line);
			
			// Change lines, and we should be done
			lines = newLines;
			bit++;
		}
	
		return Integer.parseInt(lines.get(0), 2);
	}
	
	public static int part2(ArrayList<String> lines) {
		int oxygen = findNumber(new ArrayList<String>(lines), '1', true);
		int co2 = findNumber(new ArrayList<String>(lines), '0', false);
		return oxygen * co2;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day03/input";
//		String filename="src/day03/sample";
		
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
