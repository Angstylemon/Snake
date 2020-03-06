import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Snake {
	private PApplet parent;
	private int posX;
	private int posY;
	private int velX = 0;
	private int velY = 0;
	private int step = Sketch.step;
	private List<Block> body;
	private boolean alive = true;
	private int[] colours;
	private int lives = 3;
	
	private class Block {
		private int posX;
		private int posY;
		
		private int r;
		private int g;
		private int b;
		
		private Block(int x, int y) {
			posX = x;
			posY = y;
			
			r = (int) (Math.random()*255);
			g = (int) (Math.random()*255);
			b = (int) (Math.random()*255);
		}
	}
	
	public Snake(PApplet p) {
		parent = p;
		posX = Sketch.width/2*step;
		posY = Sketch.height/2*step;
		body = new ArrayList<Block>();
		grow(1);
		grow(1);
	}
	
	public Snake(PApplet p, int[] c) {
		parent = p;
		posX = Sketch.width/2*step;
		posY = Sketch.height/2*step;
		body = new ArrayList<Block>();
		grow(1);
		grow(1);
		
		if (c != null) {
			colours = c;
		}
	}
	
	public void grow(int n) {
		for (int i = 0; i < n; i++) {
			body.add(new Block(posX, posY));
		}
	}
	
	public void vel(int x, int y) {
		boolean valid = true;
		if (body.size() > 1) {
			if (posX + x*step == body.get(1).posX && posY + y*step == body.get(1).posY) {
				valid = false;
			}
		}
		if (valid) {
			if (velX != -1*x) {
				velX = x;
			}
			if (velY != -1*y) {
				velY = y;
			}
		}
	}
	
	public void move() {
		inBounds();
		
		if (alive) {
			posX += velX * step;
			posY += velY * step;
			
			for (int i = body.size()-1; i > 0; i--) {
				body.get(i).posX = body.get(i-1).posX;
				body.get(i).posY = body.get(i-1).posY;
				body.get(i).r = body.get(i-1).r;
				body.get(i).g = body.get(i-1).g;
				body.get(i).b = body.get(i-1).b;
			}
			
			body.get(0).posX = posX;
			body.get(0).posY = posY;
			
			body.get(0).r = (int) (Math.random()*255);
			body.get(0).g = (int) (Math.random()*255);
			body.get(0).b = (int) (Math.random()*255);
			
		}
		
		overlap();
	}
	
	private void inBounds() {
		int nextXPos = posX + velX*step;
		int nextYPos = posY + velY*step;
		
		boolean xBound = true;
		if (nextXPos < 0 || nextXPos == Sketch.width*step) {
			xBound = false;
		}
		
		boolean yBound = true;
		if (nextYPos < 0 || nextYPos == Sketch.height*step) {
			yBound = false;
		}
		
		alive = xBound && yBound;
	}
	
	private void overlap() {
		for (int i = 2; i < body.size(); i++) {
			if (posX == body.get(i).posX && posY == body.get(i).posY) {
				alive = false;
			}
		}
	}
	
	public boolean eats(Food f) {
		if (posX == f.x() && posY == f.y()) {
			return true;
		}
		
		return false;
	}
	
	public boolean alive() {
		return alive;
	}
	
	public void display() {
		if (alive) {
			parent.fill(255, 255, 255);
			parent.noStroke();
			
			for (int i = 0; i < body.size(); i++) {
				if (colours == null) {
					parent.fill(body.get(i).posX * 255/(Sketch.width*step), body.get(i).posY * 255/(Sketch.height*step), 255 - body.get(i).posX * 255/(Sketch.width*step));
				} else {
					parent.fill(colours[0], colours[1], colours[2]);
				}
				//parent.fill(body.get(i).r, body.get(i).g, body.get(i).b);
				parent.rect(body.get(i).posX, body.get(i).posY, step, step);
			}
		}
	}
}
