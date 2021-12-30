package day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Day16 {
	static String line;
	static int currentChar;
		
	public static String parseTransmission(String trans) {
		// We need to convert everything from hex to binary
		StringBuilder decoded = new StringBuilder();
		
		for (int i=0; i<trans.length(); i++) {
			String digits = Integer.toBinaryString(Integer.parseInt(trans.substring(i, i+1), 16));

			// Pad it with leading zeros to four bits
			decoded.append("0000".substring(0,4-digits.length()));
			decoded.append(digits);
		}
		
		return decoded.toString();
	}
	
	public static int getIntValue(int bitCount) {
		int num = Integer.parseInt(line.substring(currentChar, currentChar+bitCount), 2);
		currentChar+=bitCount;
		return num;
	}
	
	public static String getSubstring(int bitCount) {
		String sub = line.substring(currentChar, currentChar+bitCount);
		currentChar+=bitCount;
		return sub;
	}
	
	public static Packet parseLine() {
		Packet packet;
		
		int version = 0;
		int type = 0;

		while (currentChar < line.length()) {
			// Get the version and type of the packet
			version = getIntValue(3);
			type = getIntValue(3);
			
			if (type == 4) {
				// This is a literal
				StringBuilder strLit = new StringBuilder();
				String next = getSubstring(5);
				while (next.charAt(0) == '1') {
					strLit.append(next.substring(1));
					next = getSubstring(5);
				}
				strLit.append(next.substring(1));
				int literal = Integer.parseInt(strLit.toString(), 2);
				packet = new Packet(version, type, literal);

			} else {
				// This is an operator
				// Get length type ID
				String ltID = getSubstring(1);
				if (ltID.charAt(0) == '0') {
					// Length, get next 15 bits
					int length = getIntValue(15);
					
				} else {
					// Sub=packet count
					int subpackets = getIntValue(11);
					
					packet = new Packet(version, type);
					packet.subPackets = new ArrayList<>(subpackets);
					
					while (subpackets > 0) {
						subpackets--;
						
					}
				}
			}
		}
		
		return packet;
	}
	
	public static int countVersion(Packet root) {
		if (root.type == 4) return root.version;
		else {
			int total = 0;
			for (Packet subpacket:root.subPackets) {
				total += countVersion(subpacket);
			}
			return total + root.version;
		}
	}
		
	public static int part1(String hexLine) {
		line = parseTransmission(hexLine);
		System.out.println(hexLine + " = " + line);
		currentChar = 0;
		
		Packet output = parseLine();
		
		return countVersion(output);
	}
	
	public static int part2(ArrayList<String> lines) {
		return 0;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
//		String filename="src/day16/input";
		String filename="src/day16/sample1";
//		String filename="src/day16/sample2";
//		String filename="src/day16/sample3";
//		String filename="src/day16/sample4";
//		String filename="src/day16/sample5";
//		String filename="src/day16/sample6";
//		String filename="src/day16/sample7";
		
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

		System.out.println("Part 1: " + part1(lines.get(0)));
		System.out.println("Part 2: " + part2(lines));

	}
}
