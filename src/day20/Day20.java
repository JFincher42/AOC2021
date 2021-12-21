package day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day20 {
	
	public static int convert(boolean val) {
		if (val) return 1;
		else return 0;
	}
	
	public static void printImage(boolean[][] image) {
		for (int r=0; r<image.length; r++) {
			for (int c=0; c<image[r].length; c++) {
				if (image[r][c]) System.out.print("#");
				else 			 System.out.print(".");
			}
			System.out.println();
		}
	}
	
	public static int getIndex(boolean[][] image, int row, int col) {
		int index = 0;
		for (int r=row-1; r < row + 2; r++)
			for (int c=col-1; c < col + 2; c++) {
				int val = 0;
				if (r<0 || r >= image.length) 			val = convert(image[row][col]);
				else if (c<0 || c >= image[0].length) 	val = convert(image[row][col]);
				else 			 						val = convert(image[r][c]);

				index = (index << 1) + val;
			}
		return index;
	}

	
	public static int part1(boolean[][] image, String algorithm) {
		boolean[][] newImage = new boolean[image.length][image[0].length];
		
		// Do this twice
		for (int iterations = 0; iterations < 2; iterations++) {
			for (int row = 0; row < image.length; row++) {
				for (int col = 0; col < image.length; col++) {
					// If it's an even index, we're checking image and moving to newImage
					if (iterations % 2 == 0) {
						int index = getIndex(image, row, col);
						newImage[row][col] = (algorithm.charAt(index) == '#');
					} else {
						int index = getIndex(newImage, row, col);
						image[row][col] = (algorithm.charAt(index) == '#');
					}
				}
			}			
		}
		
		// Count the hash marks
		int count = 0;
		for (int row = 1; row < image.length-1; row++) {
			for (int col = 1; col < image[row].length-1; col++) {
				if (image[row][col]) count++;
			}
		}
		return count;
	}
	
	public static int part2(boolean[][] image, String algorithm) {
		// We need to swap back and forth between two arrays
		boolean[][] newImage = new boolean[image.length][image[0].length];
		
		// Do this 48 more times
		// Total of 505 swaps, but Part 1 modified image already twice
		for (int iterations = 0; iterations < 48; iterations++) {			
			for (int row = 0; row < image.length; row++) {
				for (int col = 0; col < image.length; col++) {
					// If it's an even index, we're checking image and moving to newImage
					if (iterations % 2 == 0) {
						int index = getIndex(image, row, col);
						newImage[row][col] = (algorithm.charAt(index) == '#');
					} else {
						int index = getIndex(newImage, row, col);
						image[row][col] = (algorithm.charAt(index) == '#');
					}
				}
			}
			System.out.print("."); // Just to see progress...
		}
		System.out.println();
		
		// Count the hash marks
		int count = 0;
		for (int row = 1; row < image.length-1; row++) {
			for (int col = 1; col < image[row].length-1; col++) {
				if (image[row][col]) count++;
			}
		}
		return count;
	}
	
	public static boolean[][] buildBaseImage(ArrayList<String> lines){
		// Why so bloody big? Part 2 is why...
		boolean[][] baseImage = new boolean[lines.size()*110][lines.get(0).length()*110];
		
		// Put the image in the middle of this huge array.
		int row = lines.size()*55;
		
		for (String line:lines) {
			int col = lines.get(0).length()*55;
			for (char ch:line.toCharArray()) {
				baseImage[row][col++] = (ch == '#');
			}
			row += 1;
		}
		return baseImage;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
		String filename="src/day20/input";
//		String filename="src/day20/sample";
		
		ArrayList<String> lines = new ArrayList<>();
		String algorithm= "";
		
		try {
			// Open the input file
			input = new BufferedReader(new FileReader(filename));
			
			// Read the first line as the lookup
			algorithm = input.readLine().strip();
			
			// Read the rest of the lines as the image
			String line;
			while ((line = input.readLine()) != null)
				if (line.length()>0)
					lines.add(line);
		} catch (Exception e) {
			System.out.println("Problem reading input");
			System.exit(1);
		}
		
		// Build the base image bigger than the original so we have some room to grow...
		boolean[][] base_image = buildBaseImage(lines);

		System.out.println("Part 1: " + part1(base_image, algorithm));
		System.out.println("Part 2: " + part2(base_image, algorithm));

	}
}
