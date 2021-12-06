package day04;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

class BingoCard{
	int[][] card;
	public boolean won;
	
	BingoCard(String[] lines){
		this.card = new int[5][5];
		
		for (int i=0; i<lines.length; i++) {
			String[] entries = lines[i].strip().split("\\s+");
			for (int j=0; j<entries.length; j++) {
				this.card[i][j] = Integer.parseInt(entries[j]);
			}
		}
		this.won = false;
	}
	
	void markNumber(int number) {
		for (int i=0; i<card.length; i++)
			for (int j=0; j<card[i].length; j++)
				if (card[i][j] == number)
					card[i][j] = -1;
	}
	
	boolean hasBingo() {
		
		for (int i=0; i<card.length; i++) {
			// Is there a row bingo?
			if ((card[i][0]==-1) && 
				(card[i][1]==-1) &&
				(card[i][2]==-1) &&
				(card[i][3]==-1) &&
				(card[i][4]==-1))	this.won = true;
			
			// Is there a column bingo?
			if ((card[0][i]==-1) && 
				(card[1][i]==-1) &&
				(card[2][i]==-1) &&
				(card[3][i]==-1) &&
				(card[4][i]==-1))	this.won = true;
				
		}
		// No bingo anywhere
		return this.won;
	}

	int getSumRemaining() {
		int sum = 0;
		for (int i=0; i<card.length; i++)
			for (int j=0; j<card[i].length; j++)
				if (card[i][j] != -1)
					sum += card[i][j];
		return sum;
	}
		
}


public class Day04 {
	
	public static ArrayList<Integer> calledNumbers = new ArrayList<>();
	public static ArrayList<BingoCard> bingoCards = new ArrayList<>();
	
	public static void getCalledNumbers(String line) {
		String[] called = line.split(",");
		for (String num: called)
			calledNumbers.add(Integer.parseInt(num));
	}
	
	public static int part1(ArrayList<String> lines) {
		// The first line is our called numbers
		getCalledNumbers(lines.get(0));
		
		// Skip the blank, and start processing bingo cards
		int currentLine = 1;
		while (currentLine < lines.size()-1) {
			String[] cardLines = (String[])lines.subList(currentLine, currentLine+5).toArray(new String[0]);
			bingoCards.add(new BingoCard(cardLines));
			// Get past this card and the blank line
			currentLine += 5;
		}
		
		for (int call:calledNumbers) {
			for (BingoCard card: bingoCards) {
				card.markNumber(call);
				if (card.hasBingo()) {
					System.out.println("Card: " + call + ", bingo sum: " + card.getSumRemaining());
					return call*card.getSumRemaining();
				}
			}
			
		}
		return 0;
	}
	
	public static int part2(ArrayList<String> lines) {
		BingoCard lastCard=null; 

		// The first line is our called numbers
		getCalledNumbers(lines.get(0));
		
		// Skip the blank, and start processing bingo cards
		int currentLine = 1;
		while (currentLine < lines.size()-1) {
			String[] cardLines = (String[])lines.subList(currentLine, currentLine+5).toArray(new String[0]);
			bingoCards.add(new BingoCard(cardLines));
			// Get past this card and the blank line
			currentLine += 5;
		}
		
		for (int call:calledNumbers) {
			// Mark all the numbers
			for (BingoCard card: bingoCards) {
				card.markNumber(call);
				card.hasBingo();
			}
			// How many bingo cards are left?
			int bingoCount = 0;
			for (BingoCard card: bingoCards) {
				if (!card.won) {
					bingoCount += 1;
					lastCard = card;
				}
			}
			// Is there only one? Let's get out and just run that one
			if (bingoCount == 1) break;
			
		}
		// lastCard should be the final bingo card, so let's play it
		// until it wins.
		// We can restart the called numbers, since they're all
		// marked by this point anyway
		for (int call:calledNumbers) {
			lastCard.markNumber(call);
			if (lastCard.hasBingo()) {
				System.out.println("Card: " + call + ", bingo sum: " + lastCard.getSumRemaining());
				return call*lastCard.getSumRemaining();
			}		
		}
		return 0;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day04/input";
//		String filename="src/day04/sample";
		
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

//		System.out.println("Part 1: " + part1(lines));
		System.out.println("Part 2: " + part2(lines));

	}
}
