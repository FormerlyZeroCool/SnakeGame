package mazesolvers.andrewrs.fun;


import snake.andrewrs.fun.Point;

public abstract class Node extends Point{

	public Node right,left,up,down;
	private Node previousNodeInPath;
	private long cost = -1;
	private boolean visited = false;

	public Node(int x, int y) 
	{
		super(x,y);	
	}
	public abstract boolean isObstructionHere();
	public abstract boolean isFoodHere();
	public String childrenToString()
	{

		StringBuilder data = new StringBuilder();
		data.append("Left Node: ");
		data.append(left!=null?left.toString():"NA");
		data.append('\n');
		data.append("Up Node: ");
		data.append(up!=null?up.toString():"NA");
		data.append('\n');
		data.append("Right Node: ");
		data.append(right!=null?right.toString():"NA");
		data.append('\n');
		data.append("Down Node: ");
		data.append(down!=null?down.toString():"NA");
		data.append('\n');
		
		return data.toString();
		
	}
	@Override
	public String toString()
	{
		StringBuilder data = new StringBuilder();
		data.append("Node: ");
		data.append(this.getX());
		data.append(',');
		data.append(this.getY());
		return data.toString();
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public long getCost() {
		return cost;
	}
	public void setCost(long cost) {
		this.cost = cost;
	}
	public void setPrevious(Node previous) 
	{
		previousNodeInPath = previous;
	}

	public Node getPrevious()
	{
		return previousNodeInPath;
	}
	
}
