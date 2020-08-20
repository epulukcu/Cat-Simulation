import java.io.File;
import java.util.Scanner;
import java.util.Random;
import java.io.FileNotFoundException;

public class Main {

	public static void main(String[] args) {

		String filename = "world.txt"; // Firstly, creat the world
		File worldFile = new File(filename); //We will use a file to create world
		Scanner input = null;
		Cat cat = new Cat(30, 20, StdDraw.ORANGE); //Create the cat 
		Random r = new Random();
		int empty = 0;
		int wall = 1;
		int sea = 2;
		int food = 3;
		int direction;
		int x;
		int y;
		String way = " ";
		StdDraw.setCanvasSize(1000, 1000);
		/*We set X and Y scale 40 because at the top of our txt file,
		 our dimensions are specified as 40 to 40.*/
		StdDraw.setXscale(0, 40); 
		StdDraw.setYscale(40, 0);
		StdDraw.setPenRadius(0.0005);
		//We'll change the number of steps to observe the cat's movements and how much it can eat
		int stepCount = 20000; // 5000, 10000, 20000

		cat.draw(""); 

		try {
			input = new Scanner(worldFile);
		} catch (FileNotFoundException e) {
			System.out.println(filename + " doesn't exist."); //If the file could not found there is a problem
		}

		String line = input.nextLine();
		int[][] matrix = new int[40][40]; //Create two-dimensional array to keep the data of the world

		for (int i = 0; i < 40; i++) { //To keep values in rows in the array
			line = input.nextLine();
			String[] a = new String[40];
			a = line.split(";"); //To separate values (0, 1, 2, or 3)

			for (int j = 0; j < 40; j++)  //For columns
				matrix[i][j] = Integer.parseInt(a[j]); //The parseInt() function parses a string and returns an integer
		}

		for (int i = 0; i < 40; i++) {
			for (int j = 0; j < 40; j++) {
				int val = matrix[j][i]; 
				switch (val) {
				case 1:
					StdDraw.setPenColor(StdDraw.LIGHT_GRAY); //Walls are light gray
					StdDraw.filledSquare(i + 0.5, j + 0.5, 0.5);
					break;
				case 2:
					StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE); //Seas are light blue
					StdDraw.filledSquare(i + 0.5, j + 0.5, 0.5);
					break;
				case 3:
					StdDraw.setPenColor(StdDraw.MAGENTA); //Foods are magenta
					StdDraw.filledSquare(i + 0.5, j + 0.5, 0.4);
					break;
				}
				StdDraw.setPenColor(StdDraw.BLACK); //Lines that stating each frame are black
				StdDraw.square(i + 0.5, j + 0.5, 0.5);
			}
		}

		int yminus1x;
		int yplus1x;
		int yxminus1;
		int yxplus1;
		boolean foodEaten = false; //This value will change if the cat eats

		for (int s = 1; s <= stepCount; s++) {
			StdDraw.setPenColor(StdDraw.WHITE);
			direction = r.nextInt(4); //Randomly determine which direction the cat will go
			x = cat.getX();
			y = cat.getY();
			//I assigned the elements of the matrix to variable. The value of the variable changes as the matrix element changes.
			//Available routes
			yminus1x = matrix[y - 1][x]; 
			yplus1x = matrix[y + 1][x];
			yxminus1 = matrix[y][x - 1];
			yxplus1 = matrix[y][x + 1];

			if (yminus1x != wall && yminus1x != sea) { //If there is no wall or sea in the specified cell...
				if (yminus1x == food) {
					StdDraw.filledSquare(x + 0.5, y - 1 + 0.5, 0.43); //0.43 for a clean image after the meal
					foodEaten = true; //Food was defeated
					yminus1x = empty; //Now, the specified cell is empty.
					way = "Down"; //Showing motion: down
				}
				if (direction == 0) 
					way = "Down";
			}

			if (yplus1x != wall && yplus1x != sea) {
				if (yplus1x == food) {
					StdDraw.filledSquare(x + 0.5, y + 1 + 0.5, 0.43);
					foodEaten = true;
					yplus1x = empty;
					way = "Up";
				}
				if (direction == 1) 
					way = "Up";
			}

			if (yxminus1 != wall && yxminus1 != sea) {
				if (yxminus1 == food) {
					StdDraw.filledSquare(x - 1 + 0.5, y + 0.5, 0.43);
					foodEaten = true;
					yxminus1 = empty;
					way = "Left";
				}
				if (direction == 2) 
					way = "Left";
			}

			if (yxplus1 != wall && yxplus1 != sea) {
				if (yxplus1 == food) {
					StdDraw.filledSquare(x + 1 + 0.5, y + 0.5, 0.43);
					foodEaten = true;
					yxplus1 = empty;
					way = "Right";
				}
				if (direction == 3) 
					way = "Right";
			}

			matrix[y - 1][x] = yminus1x;
			matrix[y + 1][x] = yplus1x;
			matrix[y][x - 1] = yxminus1;
			matrix[y][x + 1] = yxplus1;


			StdDraw.setPenColor(StdDraw.WHITE); /*Draws a white circle where the cat passes. 
			Thus, when the cat passes through a cell that has food, the magenta-colored cell is replaced by empty cell.*/

			switch (way) {
			case "Down":
				cat.draw("Down");
				y --; //If the cat goes down, it's y coordinate decreases
				break;

			case "Up":
				cat.draw("Up");
				y ++; //If the cat goes up, it's y coordinate increases
				break;

			case "Left":
				cat.draw("Left");
				x --; //If the cat goes left, it's x coordinate decreases
				break;

			case "Right":
				cat.draw("Right");
				x ++; //If the cat goes right, it's x coordinate increases
				break;
			}

			if (foodEaten)  //I've assigned a boolean value for eating or not eating. If the cat ate in its movement, increase the food counter 1
				cat.setFoodCount(1);

			foodEaten = false;
			way = " ";
			cat.draw("");
			StdDraw.save("output_figure.png"); // Save the photo of the output in .png or .jpg format
		}
	}
}
