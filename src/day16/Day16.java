package day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day16 {
	static String line;
	static int currentChar;

	public static String parseTransmission(String trans) {
		// We need to convert everything from hex to binary
		StringBuilder decoded = new StringBuilder();

		for (int i = 0; i < trans.length(); i++) {
			String digits = Integer.toBinaryString(Integer.parseInt(trans.substring(i, i + 1), 16));

			// Pad it with leading zeros to four bits
			decoded.append("0000".substring(0, 4 - digits.length()));
			decoded.append(digits);
		}

		return decoded.toString();
	}

	public static int getIntValue(int bitCount) {
		int num = Integer.parseInt(line.substring(currentChar, currentChar + bitCount), 2);
		currentChar += bitCount;
		return num;
	}

	public static String getSubstring(int bitCount) {
		String sub = line.substring(currentChar, currentChar + bitCount);
		currentChar += bitCount;
		return sub;
	}

	public static boolean dataRemains() {
		for (int i = currentChar; i < line.length(); i++) {
			if (line.charAt(i) == '1')
				return true;
		}
		return false;
	}

	public static Packet parseLine() {
		Packet packet = null;

		int version = 0;
		int type = 0;

		// Get the version and type of the packet
		version = getIntValue(3);
		type = getIntValue(3);

		if (type == 4) {
			// This is a literal
			// Build the string of bits
			StringBuilder strLit = new StringBuilder();
			String next = getSubstring(5);
			// Keep adding bits while each block of five starts with a '1'
			while (next.charAt(0) == '1') {
				strLit.append(next.substring(1));
				next = getSubstring(5);
			}
			// The last set of bits started with a '0'
			strLit.append(next.substring(1));
			long literal = Long.parseLong(strLit.toString(), 2);

			// Create a literal packet with the version, type, and value
			packet = new Packet(version, type, literal);

		} else {
			// This is an operator
			// Build an operator packet
			packet = new Packet(version, type);
			// Initialize the sub-packets list
			packet.subPackets = new ArrayList<>();

			// Get length type ID
			String ltID = getSubstring(1);
			if (ltID.charAt(0) == '0') {
				// This is a length operator, so get next 15 bits
				int length = getIntValue(15);
				// Figure out where the end should be
				int end = currentChar + length;

				while (currentChar < end && dataRemains()) {
					packet.subPackets.add(parseLine());
				}

			} else {
				// This is a sub-packet count, so get the next 11 bits
				int subpackets = getIntValue(11);

				// Process all the sub-packets
				while (subpackets > 0) {
					subpackets--;
					packet.subPackets.add(parseLine());
				}
			}
		}

		return packet;
	}

	public static int countVersion(Packet root) {
		if (root.type == 4)
			return root.version;
		else {
			int total = 0;
			for (Packet subpacket : root.subPackets) {
				total += countVersion(subpacket);
			}
			return total + root.version;
		}
	}

	public static long evaluate(Packet root) {
		long val;

		switch (root.type) {
		case 0:
			if (root.subPackets.size() == 1)
				return evaluate(root.subPackets.get(0));
			val = 0l;
			for (Packet sub : root.subPackets) {
				val += evaluate(sub);
			}
			return val;

		case 1:
			if (root.subPackets.size() == 1)
				return evaluate(root.subPackets.get(0));
			val = 1l;
			for (Packet sub : root.subPackets) {
				val *= evaluate(sub);
			}
			return val;

		case 2:
			val = evaluate(root.subPackets.get(0));
			for (Packet sub : root.subPackets) {
				long temp = evaluate(sub);
				if (temp < val)
					val = temp;
			}
			return val;

		case 3:
			val = evaluate(root.subPackets.get(0));
			for (Packet sub : root.subPackets) {
				long temp = evaluate(sub);
				if (temp > val)
					val = temp;
			}
			return val;

		case 4:
			return root.value;

		case 5:
			if (evaluate(root.subPackets.get(0)) > evaluate(root.subPackets.get(1)))
				return 1l;
			else
				return 0l;

		case 6:
			if (evaluate(root.subPackets.get(0)) < evaluate(root.subPackets.get(1)))
				return 1l;
			else
				return 0l;

		case 7:
			if (evaluate(root.subPackets.get(0)) == evaluate(root.subPackets.get(1)))
				return 1l;
			else
				return 0l;

		}

		return 0l;
	}

	public static int part1(String hexLine) {
		line = parseTransmission(hexLine);
		System.out.println(hexLine + " = " + line);
		currentChar = 0;

		Packet output = parseLine();

		System.out.println("Part 1: " + countVersion(output));

		System.out.println("Part 2: " + evaluate(output));

		return 0;

	}

	public static int part2(ArrayList<String> lines) {
		return 0;
	}

	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename = "src/day16/input";
//		String filename="src/day16/sample1";
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
				if (line.length() > 0)
					lines.add(line);
		} catch (Exception e) {
			System.out.println("Problem reading input");
			System.exit(1);
		}

		part1(lines.get(0));
//		part1("C200B40A82");
//		part1("04005AC33890");
//		part1("880086C3E88112");
//		part1("CE00C43D881120");
//		part1("D8005AC2A8F0");
//		part1("F600BC2D8F");
//		part1("9C005AC2F8F0");
//		part1("9C0141080250320F1802104A08");
//		System.out.println("Part 2: " + part2(lines));

	}
}
