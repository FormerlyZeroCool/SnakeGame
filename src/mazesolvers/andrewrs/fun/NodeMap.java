package mazesolvers.andrewrs.fun;


import java.util.HashMap;


public class NodeMap extends HashMap<Node,Node> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	public NodeMap() 
	{
		super(3600,0.85f);
	}

	public void put(Node p)
	{
		super.put(p, p);
	}
}
