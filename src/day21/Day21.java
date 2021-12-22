package day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day21 {
	
	public static long part1(int p1, int p2) {
		// Get my die
		long die = 1;
		
		// Beginning score
		long p1Score=0, p2Score=0;
		
		// Who is playing right now
		boolean p1Plays = true;
		
		// While someone doesn't have 1000
		while (p1Score < 1000 && p2Score < 1000) {
			// Player 1
			if (p1Plays) {
				// die + die+1 + die+2 simplified.
				p1 += (die*3 + 3);
				// Did we wrap around
				if (p1 > 10) {
					p1 = p1 % 10;
					// If hit 10, then the mod is 0, so restore it
					if (p1 == 0) p1 = 10;
				}
				// Add it to our score
				p1Score += p1;
			} else {
				// Same thing as for p1
				p2 += (die*3 + 3);
				if (p2 > 10) {
					p2 = p2 % 10;
					if (p2 == 0) p2 = 10;
				}
				p2Score += p2;
			}

			// The next die roll -- no need to wrap around
			die += 3;

//			if (p1Plays) System.out.println("P1 Pos: " + p1 + ", P1 Score=" + p1Score + ", Die=" + die);
//			else		 System.out.println("P2 Pos: " + p2 + ", P2 Score=" + p2Score + ", Die=" + die);
			// Next player
			p1Plays = !p1Plays;

		}
		// We incremented die to the next starting point, so return it to the last rolled number
		die -= 1;
		
		System.out.println("Scores: P1=" + p1Score + ", P2=" + p2Score);
		System.out.println("Final Pos: P1=" + p1 + ", P2=" + p2);
		System.out.println("Final die: " + die);
		if (p1Score < 1000) return p1Score * die;
		else				return p2Score * die;
	}
	
	public static int part2(int p1, int p2) {
		return 0;
	}
	
	public static void main(String[] argv) {

		// Where does each player start
//		int p1=4, p2=8;		// Sample 
		int p1=7, p2=9;		// My input

		System.out.println("Part 1: " + part1(p1, p2));
		System.out.println("Part 2: " + part2(p1, p2));

	}
}
