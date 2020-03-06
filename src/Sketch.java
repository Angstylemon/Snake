import processing.core.PApplet;

public class Sketch extends PApplet {
	public static int width = 40;
	public static int height = 35;
	public static int step = 25;
	
	private final int lives = 3;
	
	private int count1 = 0;
	private int count2 = 0;
	private int moverate = 6;
	private Snake snake1;
	private Snake snake2;
	private Food food;
	private int points1 = 0;
	private int points2 = 0;
	private int lives1 = lives;
	private int lives2 = lives;
	private int highscore = 0;
	private int winner = 0;
	private int[] colours1 = {169, 255, 145};
	private int[] colours2 = {20, 38, 227};
	private int gamemode = 1;
	
	public static void main(String[] args) {
		PApplet.main("Sketch");
	}
	
	public void settings() {
		size (width*step, height*step);
	}
	
	public void setup() {
		frameRate(100);
		
		reset();
		
		int x = (int) (Math.random()*width)*step;
		int y = (int) (Math.random()*height)*step;
		food = new Food(x, y, this);
	}
	
	private void reset() {
		snake1 = null;
		snake2 = null;
		points1 = 0;
		points2 = 0;
		lives1 = lives;
		lives2 = lives;
		highscore = 0;
		winner = 0;
		
		snake1 = new Snake(this, colours1);
		if (gamemode == 2) {
			snake2 = new Snake(this, colours2);
		}
	}
		
	public void draw() {
		background(20);
		
		info();
		
		snake1();
		
		if (snake2 != null) {
			snake2();
		}
		
		food.display();
	}
	
	private void info() {
		fill(255, 255, 255);
		textSize(60);
		
		//Info for snake1
		textAlign(LEFT);
		if (lives1 > 0) {
			text(points1, 10, 60);
			for (int i = 0; i < lives1; i++) {
				circle(10 + 20*i, 80, 10);
			}
		} else {
			text("DEAD", 10, 60);
		}
		
		//Info for snake2
		if (snake2 != null) {
			textAlign(RIGHT);
			if (lives2 > 0) {
				text(points2, width*step-10, 60);
				for (int i = 0; i < lives2; i++) {
					circle(width*step-10 - 20*i, 80, 10);
				}
			} else {
				text("DEAD", width*step-10, 60);
			}
		}
		
		//Winner info
		if (gamemode == 2) {
			if (points1 > highscore) {
				highscore = points1;
				winner = 1;
			} else if (points2 > highscore) {
				highscore = points2;
				winner = 2;
			}
			
			if (winner != 0) {
				textSize(60);
				textAlign(CENTER);
				text("Winner: " + winner, width*step/2, 60);
			}
		}
	}
	
	private void snake1() {
		if (snake1.alive()) {
			snake1.display();
			count1++;
			
			if (count1 >= moverate) {
				snake1.move();
				count1 = 0;
			}
			
			if (snake1.eats(food)) {
				int x = (int) (Math.random()*width)*step;
				int y = (int) (Math.random()*height)*step;
				food = new Food(x, y, this);
				snake1.grow(1);
				points1++;
			}
		} else if (lives1 > 0) {
			if (gamemode == 2) {
				lives1--;
			}
			
			if (lives1 > 0) {
				snake1 = new Snake(this, colours1);
				points1 = 0;
			}
		}
	}
	
	private void snake2() {
		if (snake2.alive()) {
			snake2.display();
			count2++;
			
			if (count2 >= moverate) {
				snake2.move();
				count2 = 0;
			}
			
			if (snake2.eats(food)) {
				int x = (int) (Math.random()*width)*step;
				int y = (int) (Math.random()*height)*step;
				food = new Food(x, y, this);
				snake2.grow(1);
				points2++;
			}
		} else if (lives2 > 0){
			lives2--;
			
			if (lives2 > 0) {
				snake2 = new Snake(this, colours2);
				points2 = 0;
			}
		}
	}
	
	public void keyPressed() {
		if (keyCode == 87) {
			snake1.vel(0, -1);
		}
		if (keyCode == 38) {
			if (snake2 != null) {
				snake2.vel(0, -1);
			} else {
				snake1.vel(0,  -1);
			}
		}
		if (keyCode == 83) {
			snake1.vel(0, 1);
		}
		if (keyCode == 40) {
			if (snake2 != null) {
				snake2.vel(0, 1);
			} else {
				snake1.vel(0,  1);
			}
		}
		if (keyCode == 65) {
			snake1.vel(-1, 0);
		}
		if (keyCode == 37) {
			if (snake2 != null) {
				snake2.vel(-1, 0);
			} else {
				snake1.vel(-1, 0);
			}
		}
		if (keyCode == 68) {
			snake1.vel(1, 0);
		}
		if (keyCode == 39) {
			if (snake2 != null) {
				snake2.vel(1, 0);
			} else {
				snake1.vel(1, 0);
			}
		}
		
		if (keyCode == 82) {
			reset();
		}
		
		if (keyCode == 49) {
			gamemode = 1;
			reset();
		}
		if (keyCode == 50) {
			gamemode = 2;
			reset();
		}
	}
}
