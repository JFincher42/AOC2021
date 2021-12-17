package day17;

public class Day17 {
	
	static int lowX = 269, highX = 292, lowY = -68, highY = -44;	// My input
//	static int lowX = 20,  highX = 30,  lowY = -10, highY = -5;		// Sample
	
	public static boolean hitTarget(int x, int y) {
		return ((x >= lowX) && (x <=highX) && (y >= lowY) && (y <= highY));
	}
	
	public static boolean pastTarget(int x, int y, int vy) {
		// If we're falling and have passed the bottom of the target
		// We'll never hit the bottom of the target
		return ((vy < 0) && (y < lowY)) || 
		// If we've passed the target horizontally, we'll never come back
				(x > highX);		
	}
	
	public static int calcPos(int vx, int vy) {
		int curX = 0, curY = 0;
		int top = 0;
		
//		System.out.println("Trying vel: (" + vx + ", " + vy + ")");  // Debugging
		
		while (!pastTarget(curX, curY, vy)) {
			// Change position per velocity
			curX += vx;		curY += vy;
			
			// Change velocity - horizontal slows to 0
			vx -= 1;
			if (vx < 0) vx = 0;
			
			// Vertical keeps decreasing due to gravity
			vy -= 1;
			
			// Check if we've hit the apogee
			if (curY > top) top = curY;

			// Did we hit the target?
			if (hitTarget(curX, curY)) {
//				System.out.println("Hit target with top at " +  top);  // Debugging
				return top;
			}
		}
		// Missed the target, so we quit
		return -1;
	}
	
	public static int part1() {
		// Starting velocity X is a stab so we don't have to loop too much
		// Starting velocity Y just increases until we're at some sort of maximum
		int startVX = (int) Math.sqrt((double)lowX);
		int startVY = 0;
		
		// Track the highest point on this trip.
		int highY = 0;
		
		// Check every velocity in our chosen range
		for (int vx = startVX; vx < startVX * 2; vx++) {
			for (int vy = startVY; vy < Math.abs(lowY); vy++) {
				// Get the top of the curve and store it if it's higher
				int top = calcPos(vx, vy);
				if (top > highY) highY = top;
			}
		}
		
		return highY;
	}
	
	public static int part2() {
		// Here, we need every trajectory that hits the target
		// So we start at the minimum it could be, and go from there3
		int startVX = 0;
		int startVY = lowY;
		
		// How many have hit the target
		int count = 0;
		
		for (int vx = startVX; vx <= highX; vx++) {
			for (int vy = startVY; vy <= Math.abs(lowY); vy++) {
				// If we hit the target, calcPos() will return a non-negative number
				if (calcPos(vx, vy) >= 0) count++;
			}
		}
		
		return count;
	}
	
	public static void main(String[] argv) {
		// No need for input reading today -- my input is given above
		
		System.out.println("Part 1: " + part1());
		System.out.println("Part 2: " + part2());
	}
}
