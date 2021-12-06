package day05;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

class Point{
	int x, y;
	
	Point(String pt){
		String[] pts = pt.split(",");
		this.x = Integer.parseInt(pts[0]);
		this.y = Integer.parseInt(pts[1]);		
	}
	
	Point(int x, int y){
		this.x = x; this.y = y;
	}
}

class Line{
	Point pt1, pt2;
	
	Line(String line){
		String[] ln = line.split(" -> ");
		this.pt1 = new Point(ln[0]);
		this.pt2 = new Point(ln[1]);
	}
	
	public boolean isHorizontal() {
		return (pt1.y == pt2.y);
	}

	public boolean isVertical() {
		return (pt1.x == pt2.x);
	}
}

public class Day05 {
	
	public static int part1(ArrayList<String> lines) {
		ArrayList<Line> segments = new ArrayList<>();
		int [][] field = new int[1000][1000];
		int intersections = 0;
		
		// Create our ventlines
		for (String line:lines)
			segments.add(new Line(line));
		
		// Loop through the segments, looking for horizontal and vertical lines only
		for (Line segment:segments) {
			if (segment.isHorizontal()) {
				int lowEnd = Math.min(segment.pt1.x, segment.pt2.x);
				int highEnd = Math.max(segment.pt1.x, segment.pt2.x);
				for (int x = lowEnd; x<=highEnd; x++) {
					if (field[x][segment.pt1.y] == 1)
						intersections+=1;
					field[x][segment.pt1.y] += 1;
				}						
			} else if (segment.isVertical()) {
				int lowEnd = Math.min(segment.pt1.y, segment.pt2.y);
				int highEnd = Math.max(segment.pt1.y, segment.pt2.y);
				for (int y = lowEnd; y<=highEnd; y++) {
					if (field[segment.pt1.x][y] == 1)
						intersections+=1;
					field[segment.pt1.x][y] += 1;
				}
			}
		}
				
		return intersections;
	}
	
	public static int part2(ArrayList<String> lines) {
		ArrayList<Line> segments = new ArrayList<>();
		int [][] field = new int[1000][1000];
		int intersections = 0;
		
		// Create our ventlines
		for (String line:lines)
			segments.add(new Line(line));
		
		// Loop through the segments, looking for horizontal and vertical lines only
		for (Line segment:segments) {
			if (segment.isHorizontal()) {
				int lowEnd = Math.min(segment.pt1.x, segment.pt2.x);
				int highEnd = Math.max(segment.pt1.x, segment.pt2.x);
				for (int x = lowEnd; x<=highEnd; x++) {
					if (field[x][segment.pt1.y] == 1)
						intersections+=1;
					field[x][segment.pt1.y] += 1;
				}						
			} else if (segment.isVertical()) {
				int lowEnd = Math.min(segment.pt1.y, segment.pt2.y);
				int highEnd = Math.max(segment.pt1.y, segment.pt2.y);
				for (int y = lowEnd; y<=highEnd; y++) {
					if (field[segment.pt1.x][y] == 1)
						intersections+=1;
					field[segment.pt1.x][y] += 1;
				}
			} else {
				
				// Find the left most point				
				int lowX = Math.min(segment.pt1.x, segment.pt2.x);
				Point leftMost, rightMost;
				if (lowX == segment.pt1.x) {
					leftMost = segment.pt1;
					rightMost = segment.pt2;
				} else {
					leftMost = segment.pt2;
					rightMost = segment.pt1;
				}
				
				// Now which way do we slope - up or down
				int xInc = 1, yInc;
				if (rightMost.y > leftMost.y) {
					yInc = 1;
				} else {
					yInc = -1;
				}
				
				// Now we can work
				int x = leftMost.x; int y = leftMost.y;
				while (x <= rightMost.x) {
					if (field[x][y] == 1)
						intersections+=1;
					field[x][y] += 1;
					x += xInc; y += yInc;
				}
			}
		}
				
		return intersections;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day05/input";
//		String filename="src/day05/sample";
		
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
