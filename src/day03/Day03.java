package day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day03 {
	
	public static int part1(ArrayList<String> lines) {
		StringBuilder gammaStr = new StringBuilder();
		StringBuilder epsilonStr = new StringBuilder();
		
		int strLen = lines.get(0).length();
		int[] oneCount = new int[strLen];
		int[] zeroCount = new int[strLen];
		
		for (String line:lines) {
			for (int i=0; i<strLen; i++)
				if (line.charAt(i)=='0')
					zeroCount[i]++;
				else
					oneCount[i]++;
		}
		
		for (int i=0; i<strLen; i++) {
			if (zeroCount[i] > oneCount[i]) {
				gammaStr.append('0');
				epsilonStr.append('1');
			} else {
				gammaStr.append('1');
				epsilonStr.append('0');
			}
		}
		
		int gamma = Integer.parseInt(gammaStr.toString(), 2);
		int epsilon = Integer.parseInt(epsilonStr.toString(), 2);
		
		return gamma*epsilon;
	}
	
	public static int part2(ArrayList<String> lines) {
		return 0;
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
