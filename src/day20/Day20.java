package day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Day20 {
	
	public static int getIndex()
	
	public static int part1(boolean[][] image, String algorithm) {
		for (int row = 1; row < image.length-1; row++) {
			for (int col = 1; col < image[row].length-1; col++) {
				int index = getIndex(image, row, col);
			}
		}
		return 0;
	}
	
	public static int part2(boolean[][] image, String algorithm) {
		return 0;
	}
	
	public static boolean[][] buildBaseImage(ArrayList<String> lines){
		boolean[][] baseImage = new boolean[lines.size()*3][lines.get(0).length()*3];
		
		int row = lines.size();
		
		for (String line:lines) {
			int col = lines.get(0).length();
			for (char ch:line.toCharArray()) {
				baseImage[row][col++] = (ch == '#');
			}
			row += 1;
		}
		return baseImage;
	}
	
	public static void main(String[] argv) {
		BufferedReader input = null;
//		String filename="src/day20/input";
		String filename="src/day20/sample";
		
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
		
		// Build the base image at least three times bigger than the original
		boolean[][] base_image = buildBaseImage(lines);

		System.out.println("Part 1: " + part1(base_image, algorithm));
		System.out.println("Part 2: " + part2(base_image, algorithm));

	}
}
