package day24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

class ALU {
	ArrayDeque<Integer> input;
	HashMap<String, Integer> registers;
	ArrayList<String> program;
	
	ALU(ArrayList<String> program, String input){
		this.registers = new HashMap<>();
		registers.put("w", 0);
		registers.put("x", 0);
		registers.put("y", 0);
		registers.put("z", 0);
		
		this.program = program;
		this.input = new ArrayDeque<>();
		for (char ch:input.toCharArray())
			this.input.add(ch - '0');
	}
	
//	public void reset(String input) {
//		registers.put("w", 0);
//		registers.put("x", 0);
//		registers.put("y", 0);
//		registers.put("z", 0);
//		this.input.clear();
//		for (char ch:input.toCharArray())
//			this.input.add(ch - '0');
//	}
	
//	public boolean isValid() {
//		return (this.registers.get("z") == 0);
//	}
	
	public static boolean isRegister(String input) {
		return ((input.startsWith("w")) || 
				(input.startsWith("x")) || 
				(input.startsWith("y")) ||
				(input.startsWith("z")));
	}
	
	public int run() {
		
		for (String line:this.program) {
			String[] inst = line.strip().split(" ");
			
			if (inst[0].compareTo("inp") == 0) {
				this.registers.put(inst[1], this.input.remove());
			} else if (inst[0].compareTo("add") == 0) {
				int val1 = this.registers.get(inst[1]);
				int val2;
				if (isRegister(inst[2])) val2 = this.registers.get(inst[2]);
				else 					 val2 = Integer.parseInt(inst[2]);
				this.registers.put(inst[1], val1 + val2);
			} else if (inst[0].compareTo("mul") == 0) {
				int val1 = this.registers.get(inst[1]);
				int val2;
				if (isRegister(inst[2])) val2 = this.registers.get(inst[2]);
				else 					 val2 = Integer.parseInt(inst[2]);
				this.registers.put(inst[1], val1 * val2);
			} else if (inst[0].compareTo("div") == 0) {
				int val1 = this.registers.get(inst[1]);
				int val2;
				if (isRegister(inst[2])) val2 = this.registers.get(inst[2]);
				else 					 val2 = Integer.parseInt(inst[2]);
				this.registers.put(inst[1], val1 / val2);
			} else if (inst[0].compareTo("mod") == 0) {
				int val1 = this.registers.get(inst[1]);
				int val2;
				if (isRegister(inst[2])) val2 = this.registers.get(inst[2]);
				else 					 val2 = Integer.parseInt(inst[2]);
				this.registers.put(inst[1], val1 % val2);
			} else if (inst[0].compareTo("eql") == 0) {
				int val1 = this.registers.get(inst[1]);
				int val2;
				if (isRegister(inst[2])) val2 = this.registers.get(inst[2]);
				else 					 val2 = Integer.parseInt(inst[2]);

				if (val1 == val2) 	this.registers.put(inst[1], 1);
				else 				this.registers.put(inst[1], 0);
			}
		}
		
		return this.registers.get("z");
	}
}

public class Day24 {
	
	public static boolean hasZero(String str) {
		return (str.indexOf("0")>=0);
	}
	
	public static boolean checkNumber(ArrayList<String> lines, String id) {
		ALU myALU = new ALU(lines, id);
		int z = myALU.run();
		System.out.println("ID " + id + " results in z=" + z);
		return (z == 0);
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day24/input";
		
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

		checkNumber(lines, "98491959997994");
		checkNumber(lines, "61191516111321");
		
//		System.out.println("Part 1: " + part1(lines));
//		System.out.println("Part 2: " + part2(lines));
//		Followed algorithm at https://github.com/dphilipson/advent-of-code-2021/blob/master/src/days/day24.rs
//		I had gone about 70% of the way there - missed the base 26 digit stack analogy
		
//		From the description, here's my understanding
//		We are using z as a stack for base 26 digits
//		Every positive x value winds up pushing a digit onto z
//		By multiplying z by 26 and adding the digit plus some offset in y

//		Every negative z removes the right most digit using %
//		If that digit matches the current w, nothing else happens
//		If not, the new w is pushed
//		Since there an equal number of pushes and pops, this creates an imbalance in z
//		And it will never == 0
		
//		So the trick is to know what the digits should be based on
//		- What offset was used when they were pushed (the y offset)
//		- What adjustment is made when they are popped (the x offset)
		
//		The x offset is given in line 6 of every input file
//		The y offset is given in line 16 of every input file
		
	}
}




