package snake.andrewrs.fun;

import java.util.ArrayList;


public class FieldData extends ArrayList<FieldCell>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	private PlayingScreen screen;
	
	public FieldData(PlayingScreen screen) //dim must be a perfect square
	{
		super();
		this.screen = screen;
		int dim = this.screen.getSquareDim();
		for(int i = 0; i < dim*dim;i++)
		{
			this.add(new FieldCell(screen,i % dim,i / dim));
		}

		for(FieldCell cell : this)
			this.connectNeighbors(cell);
	}
	public void connectNeighbors(FieldCell cell)
	{
		cell.right = this.get(cell.getX()+1,cell.getY());
		cell.left = this.get(cell.getX()-1,cell.getY());
		cell.up = this.get(cell.getX(),cell.getY()-1);
		cell.down = this.get(cell.getX(),cell.getY()+1);
	}
	public PlayingScreen getScreen()
	{
		return screen;
	}
	public int getSqareDim()
	{
		return screen.getSquareDim();
	}
	
	public void setFoodHere(FieldCell cell, boolean value) 
	{
		cell.setFoodHere(value);	
	}

	public void setSnakeHere(FieldCell cell, boolean onTile) 
	{
		cell.setSnakeHere(onTile);
	}
	@Override
	public FieldCell get(int z) 
	{
		if(z>=this.size() || z < 0)
			z = -1;
		return z==-1?null:super.get(z);
	}
	
	public FieldCell get(int x, int y) 
	{
		boolean isValidCell = true;
		if(y < 0 || y >=screen.getSquareDim() || x < 0 || x >= screen.getSquareDim())
			isValidCell = false;
			
		return isValidCell?super.get(x + y * screen.getSquareDim()):null;
	}

}
