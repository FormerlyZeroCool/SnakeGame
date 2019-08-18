package mazesolvers.andrewrs.fun;


import java.util.HashMap;


public class DirectionMap extends HashMap<Direction,Direction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	public DirectionMap() 
	{
		super(500,0.65f);
	}

	public void put(Direction p)
	{
		super.put(p, p);
	}
}
