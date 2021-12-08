package day08;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Day08 {
	
	public static int part1(ArrayList<String> lines) {
		int uniqueValues = 0;
		for (String line:lines) {
			String [] numberParts = line.strip().split(" \\| ");
		
			String[] outputValues = numberParts[1].strip().split(" ");
			for (String outputValue:outputValues) {
				if ((outputValue.length() == 2) ||
					(outputValue.length() == 3) ||
					(outputValue.length() == 4) ||
					(outputValue.length() == 7))
					uniqueValues++;
 			}
		}
		return uniqueValues;
	}
	
	public static String findFourEl(String strOne, String strFour) {
		String[] oneChars = strOne.split("");
		String[] fourChars = strFour.split("");
		
		StringBuilder fourElChars = new StringBuilder();
		for (String fourChar:fourChars) {
			if (!Arrays.asList(oneChars).contains(fourChar))
				fourElChars.append(fourChar);
		}
		return fourElChars.toString();
	}
	
	public static int part2(ArrayList<String> lines) {
		int output = 0;
		// Get every line
		for (String line:lines) {
			String[] numbers = new String[10];
			// Split it by the middle separator
			String [] numberParts = line.strip().split(" \\| ");
		
			// Get the signal patterns and output values
			String[] signalPatterns = numberParts[0].strip().split(" ");
			String[] outputValues = numberParts[1].strip().split(" ");
			
			// Let's see what we can find 
			for (String pattern:signalPatterns) {
				if (pattern.length() == 2)
					// This is a one
					numbers[1] = pattern;
				else if (pattern.length() == 3)
					// This is a seven
					numbers[7] = pattern;
				else if (pattern.length() == 4)
					// This is a 4
					numbers[4] = pattern;
				else if	(pattern.length() == 7)
					// This is an eight
					numbers[8] = pattern;
 			}
			
			// Figure out what's unique about the four
			String fourEl = findFourEl(numbers[1], numbers[4]);
			
			// Now we can find the others
			for (String pattern:signalPatterns) {
				if (pattern.length() == 5) {
					if (Arrays.asList(pattern.split("")).containsAll(Arrays.asList(numbers[1].split(""))))
						// This is a 3
						numbers[3] = pattern;
					else if (Arrays.asList(pattern.split("")).containsAll(Arrays.asList(fourEl.split(""))))
						// This is a 5
						numbers[5] = pattern;
					else
						numbers[2] = pattern;
				} else if (pattern.length() == 6) {
					if (!Arrays.asList(pattern.split("")).containsAll(Arrays.asList(numbers[1].split(""))))
						// This is a 6
						numbers[6] = pattern;
					else if (Arrays.asList(pattern.split("")).containsAll(Arrays.asList(fourEl.split(""))))
						// This is a 9
						numbers[9] = pattern;
					else
						numbers[0] = pattern;
				}
			}
			
			// Now we can decipher the numbers
			for (int i = 0; i<outputValues.length; i++) {
				for (int digit=0; digit<numbers.length; digit++)
					if ((numbers[digit].length() == outputValues[i].length()) && (Arrays.asList(numbers[digit].split("")).containsAll(Arrays.asList(outputValues[i].split("")))))
						output += (int)(digit*Math.pow(10, 3-i)); 
			}
			
		}
		return output;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day08/input";
//		String filename="src/day08/sample";
		
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
