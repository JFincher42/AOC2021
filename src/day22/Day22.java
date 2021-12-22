package day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Day22 {
	public static boolean valid(int hix, int lox, int hiy, int loy, int hiz, int loz) {
		return ((hix <=50 && lox >= -50) &&
				(hiy <=50 && loy >= -50) &&
				(hiz <=50 && loz >= -50));
	}
	
	public static int part1(ArrayList<String> lines) {
		HashMap<Long, Boolean> core = new HashMap<>();
		
		boolean turnOn;
		int hix, lox, hiy, loy, hiz, loz;
		
		for (String line: lines) {
			// Get the on/off command
			String[] onoff = line.strip().split(" ");
			turnOn = onoff[0].startsWith("on");
			
			// Now get the extents
			String[] extents = onoff[1].strip().split("[,=]");
			String[] xExt = extents[1].split("[..]");
			String[] yExt = extents[3].split("[..]");
			String[] zExt = extents[5].split("[..]");
			hix = Integer.parseInt(xExt[2]);  lox = Integer.parseInt(xExt[0]);
			hiy = Integer.parseInt(yExt[2]);  loy = Integer.parseInt(yExt[0]);
			hiz = Integer.parseInt(zExt[2]);  loz = Integer.parseInt(zExt[0]);
			
			// If they are valid, we can proceed
			if (valid(hix, lox, hiy, loy, hiz, loz)) {
				for (int x=lox; x<=hix; x++)
					for (int y=loy; y<=hiy; y++)
						for (int z=loz; z<=hiz; z++) {
							// Build the key
							long key = (x << 16) + (y << 8) + z;
							if (core.containsKey(key))	core.replace(key, turnOn);
							else 						core.put(key, turnOn);
						}
				
			}
		}
		// Now we can count them all
		int count=0;
		for (boolean set:core.values())
			if (set) count++;
		return count;
	}
	
	public static int part2(ArrayList<String> lines) {
		return 0;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day22/input";
//		String filename="src/day22/sample1";
//		String filename="src/day22/sample2";
		
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
