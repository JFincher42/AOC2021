package day21;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

record State(int p1, int p2, long s1, long s2) {	
}

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
	
	public static HashMap<State, long[]> savedPositions;
	
	public static long[] countWins(int p1, int p2, long s1, long s2) {
		// Per https://www.youtube.com/watch?v=a6ZdJEntKkk
		// Recursion with memoization
		
		// Did someone already win?
		if (s1 >= 21) return new long[] {1,0};
		if (s2 >= 21) return new long[] {0,1};
		
		// Have we seen this state before
		if (savedPositions.containsKey(new State(p1, p2, s1, s2)))
			return savedPositions.get(new State(p1, p2, s1, s2));
		
		// Let's do this thing
		long[] ans = new long[] {0, 0};
		
 		for (int d1=1; d1 <= 3; d1++) {
			for (int d2=1; d2 <= 3; d2++) {
				for (int d3=1; d3 <= 3; d3++) {
					int newp1 = (p1 + d1 + d2 + d3);
					if (newp1 > 10) {
						newp1 = newp1 % 10;
						// If hit 10, then the mod is 0, so restore it
						if (newp1 == 0) newp1 = 10;
					}
					long news1 = s1 + newp1;
		
					long[] newans = countWins(p2, newp1, s2, news1);
					ans[0] += newans[1]; ans[1] += newans[0];
				}
			}
		}
 		savedPositions.put(new State(p1, p2, s1, s2), ans);
 		return ans;

	}
	
	public static int part2(int p1, int p2) {
		// Initialize the map
		savedPositions = new HashMap<>(10*10*21*21);
		long[] wins = countWins(p1, p2, 0, 0);
		
		System.out.println("" + wins[0] + ", " + wins[1]);
		
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
