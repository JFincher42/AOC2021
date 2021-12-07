package day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.LongStream;

public class Day06 {
	
	public static long countFish(ArrayList<String> lines, int daysToRun) {
		String [] counts = lines.get(0).split(",");
		long [] fishDays = new long[9];
		
		// Get all the counts
		for (String count: counts) 
			fishDays[Integer.parseInt(count)]+= 1;
		
		// Run this for however many days we need
		for (int i=0; i<daysToRun; i++) {
			long newFish = fishDays[0];
			for (int x=0; x<8; x++)
				fishDays[x] = fishDays[x+1];
			
			// Fish respawn at 6 days
			fishDays[6] += newFish;
			// They have new fish with 8 day lives
			fishDays[8] = newFish;
		}
		
		// Sum it all up using a stream
        LongStream stream = Arrays.stream(fishDays);
        return stream.sum();
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day06/input";
//		String filename="src/day06/sample";
		
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

		System.out.println("Part 1: " + countFish(lines, 80));
		System.out.println("Part 2: " + countFish(lines, 256));

	}
}
