import java.awt.Color;

public class Cat {
	private int x;
	private int y;
	private Color color;
	private int foodCount = 0; // Initially, our cat never eats before, counter equals to zero

	Cat(int x, int y, Color color) { // Creating constructor
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public void draw(String direction) {

		if (direction != "") {
			StdDraw.setPenColor(StdDraw.WHITE);
			switch (direction) {
			case "Down":
				// StdDraw.filledCircle(x + 0.5, y + 0.5, 0.5); 0.5 draws residue where the cat passes. So I reduced this value a bit.
				StdDraw.filledCircle(x + 0.5, y + 0.5, 0.4);
				y--; // If the cat goes down, it's y coordinate decreases
				break;

			case "Up":
				StdDraw.filledCircle(x + 0.5, y + 0.5, 0.4);
				y++; // If the cat goes up, it's y coordinate increases
				break;

			case "Left":
				StdDraw.filledCircle(x + 0.5, y + 0.5, 0.4);
				x--; // If the cat goes left, it's x coordinate decreases
				break;

			case "Right":
				StdDraw.filledCircle(x + 0.5, y + 0.5, 0.4);
				x++; // If the cat goes right, it's x coordinate increases
				break;

			default:
				break;
			}
		} else {
			StdDraw.setPenColor(color);
			StdDraw.filledCircle(x + 0.5, y + 0.5, 0.3); // It places our cat in the middle of the squares with a suitable size.

		}
	}
	//Setter and getter methods if needed
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getFoodCount() {
		return foodCount;
	}

	public void setFoodCount(int foodCount) {
		this.foodCount += foodCount;
	}
}
