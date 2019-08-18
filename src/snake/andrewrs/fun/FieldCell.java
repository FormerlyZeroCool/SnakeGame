package snake.andrewrs.fun;

import java.awt.Color;

import mazesolvers.andrewrs.fun.Node;

public class FieldCell extends Node {

	private boolean isSnakeHere,isFoodHere,isObstructionHere = false;
	private static PlayingScreen screen;
	
	
	public FieldCell(PlayingScreen screen,int x, int y) 
	{
		super(x,y);
		FieldCell.screen = screen;
	}
	
	public void setSnakeHere(boolean value)
	{
		isSnakeHere = value;
	}
	private static final Color darkBlue = new Color(10,10,80);
	public Color getColor()
	{
		if(isObstructionHere && !isSnakeHere)
			return Color.ORANGE;
		else if(isSnakeHere)
			return Color.WHITE;
		else if(!isFoodHere)
			return darkBlue;
		else 
			return Color.RED;
	}

	public Point getCellLocation() {
		return this;
	}

	public int getPixelX() {
		return this.getX() * this.getWidth();
	}

	public int getPixelY() {
		return this.getY() * this.getHeight();
	}

	public int getWidth() {
		return screen.getWidth() / screen.getSquareDim();
	}

	public int getHeight() {
		return screen.getHeight() / screen.getSquareDim();
	}

	public boolean isFoodHere() {
		return isFoodHere;
	}

	public void setFoodHere(boolean isFoodHere) {
		this.isFoodHere = isFoodHere;
	}

	public boolean isSnakeHere() {
		return isSnakeHere;
	}

	public boolean isObstructionHere() {
		return isObstructionHere || isSnakeHere;
	}

	public void setObstructionHere(boolean isObstructionHere) {
		this.isObstructionHere = isObstructionHere;
	}
}
