package mazesolvers.andrewrs.fun;

import snake.andrewrs.fun.Point;
import snake.andrewrs.fun.Snake;

public class Direction extends Point{

	private int direction;
	
	public Direction(Point location,int direction) 
	{
		super(location);
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	@Override
	public String toString()
	{
		String dir="";
		switch(this.direction)
		{
		case Snake.MOVE_DOWN:
			dir = "Down";
			break;
		case Snake.MOVE_UP:
			dir = "Up";
			break;
		case Snake.MOVE_RIGHT:
			dir = "Right";
			break;
		case Snake.MOVE_LEFT:
			dir = "Left";
			break;
		}
		return Integer.toString(this.getX())+","+Integer.toString(this.getY())+" Direction: "+dir;
	}
}
