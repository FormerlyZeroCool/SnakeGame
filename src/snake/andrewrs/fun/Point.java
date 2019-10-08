package snake.andrewrs.fun;

public class Point extends Object{

	private int x,y;
	private Point lastPointPointer;

	public Point(int x,int y) 
	{
		this.x  = x;
		this.y = y;
	}
	public Point(Point p) 
	{
		this.x  = p.getX();
		this.y = p.getY();
	}
	public Point(Point lastPoint,int x,int y) 
	{
		this.lastPointPointer = lastPoint;
		this.x  = x;
		this.y = y;
	}
	public String toString()
	{
		return Integer.toString(x)+" "+Integer.toString(y);
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public Point getLastPointPointer() {
		return lastPointPointer;
	}
	public void setLastPointPointer(Point lastPoint)
	{
		this.lastPointPointer = lastPoint;
	}
	@Override
	public int hashCode()
	{
		return (x+1000000) * (y+1);
	}
	@Override
	public boolean equals(Object p)
	{
		return this.hashCode() == (p!=null?p.hashCode():-1);
	}

}
