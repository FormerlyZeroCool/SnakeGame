package snake.andrewrs.fun;

import java.util.HashMap;

public class CustomMap extends HashMap<Point,Point> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	public CustomMap() 
	{
		super(40,0.65f);
	}

	public void put(Point p)
	{
		super.put(p, p);
	}
}
