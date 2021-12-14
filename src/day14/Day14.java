package day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class Day14 {
	static HashMap<String, Character> patterns = new HashMap<>();
	static HashMap<Character, Long> frequency = new HashMap<>();
	static HashMap<String, Long> pairs = new HashMap<>();
	
	// Add a character to the frequency map
	public static void addFreq(char ch, long count) {
		// If the character exists, increment the count
		if (frequency.containsKey(ch))	frequency.replace(ch, frequency.get(ch)+count);
		else							frequency.put(ch, count);		
	}

	// Adds a new pair to a given pairs map
	public static void addPair(String pair, HashMap<String, Long> pairsMap, long count) {
		// If the pair exists, increment the count
		if (pairsMap.containsKey(pair))	pairsMap.replace(pair, pairsMap.get(pair)+count);
		else							pairsMap.put(pair, count);
	}
	
	public static void buildNewPairs(String pair, char rep, HashMap<String, Long> pairs, long count) {
		// Pair is a two char string
		// We need to build two strings, each two chars long, consisting of:
		// pair[0]+rep and rep+pair[1]rep
		char[] newCh = new char[2];
		
		// First pair
		newCh[0] = pair.charAt(0);
		newCh[1] = rep;
		addPair(new String(newCh), pairs, count);
		
		// Second pair
		newCh[0] = rep;
		newCh[1] = pair.charAt(1);
		addPair(new String(newCh), pairs, count);
		
	}

	@SuppressWarnings("unchecked")
	public static long substitute(String template, int iterations) {
		// First, let's get the frequency of the template chars entered
		for (char ch:template.toCharArray()) addFreq(ch, 1l);
		
		// Build the pairs table from the template
		for (int start=0; start < template.length()-1; start++)
			addPair(template.substring(start, start+2), pairs, 1);		

		// We need a place to put the new pairs for a while
		HashMap<String, Long> newPairs = new HashMap<>();
		
		// Now let's do 10 iterations of replacements
		for (int i=0; i<iterations; i++) {
			// Look at each character pair			
			for (String pair: pairs.keySet()) {
				// Each pair may be in there more than once
				long c = pairs.get(pair);
								
				// Find the replacement char
				char rep = patterns.get(pair);
				
				// Add the character to the frequency table
				addFreq(rep, c);
				
				// Build and add the new pairs
				buildNewPairs(pair, rep, newPairs, c);
				
			}
			// Replace the master pairs maps with the temp
			pairs = (HashMap<String, Long>) newPairs.clone();
			
			// Clear the newPairs map
			newPairs.clear();
		}
		
		// Find the most and least common elements
		long most=0, least=frequency.get('N');
		
		for (char element:frequency.keySet()) {
			long  currentFrequency = frequency.get(element); 
			if (currentFrequency > most) most = currentFrequency; 
			if (currentFrequency < least) least = currentFrequency; 
		}
		return most - least;
	}
	
	public static long part1(String template) {
		return substitute(template, 10);
	}
	
	public static long part2(String template) {
		return substitute(template, 40);
	}
	
	public static void parsePatterns(ArrayList<String> lines) {
		for (String line: lines) {
			String[] pattern = line.strip().split(" -> ");
			patterns.put(pattern[0], pattern[1].charAt(0));
		}
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day14/input";
//		String filename="src/day14/sample";
		
		String template="";
		ArrayList<String> lines = new ArrayList<>();
		
		try {
			// Open the input file
			input = new BufferedReader(new FileReader(filename));
			
			// Read the first line as our starting point
			template = input.readLine().strip();
			
			// Read the rest
			String line;
			while ((line = input.readLine()) != null)
				if (line.length()>0)
					lines.add(line);
		} catch (Exception e) {
			System.out.println("Problem reading input");
			System.exit(1);
		}
		
		// Make the patterns map 
		parsePatterns(lines);

		// Only run these one at a time.
//		System.out.println("Part 1: " + part1(template));
		System.out.println("Part 2: " + part2(template));

	}
}
