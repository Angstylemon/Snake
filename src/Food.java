import processing.core.PApplet;

public class Food extends PApplet {
	private PApplet parent;
	private int posX;
	private int posY;
	private int size = Sketch.step;
	
	public Food(int x, int y, PApplet p) {
		parent = p;
		posX = x;
		posY = y;
	}
	
	public int x() {
		return posX;
	}
	
	public int y() {
		return posY;
	}
	
	public void display() {
		parent.fill(255, 255, 0);
		parent.noStroke();
		parent.rect(posX, posY, size, size);
	}
}
