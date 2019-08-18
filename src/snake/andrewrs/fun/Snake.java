package snake.andrewrs.fun;


public class Snake {

	private CustomMap positions;
	private Point head,tail;
	private int length = 1;
	public static final int MOVE_UP = 0,MOVE_DOWN = 1,MOVE_LEFT = 2,MOVE_RIGHT = 3;
	private int direction = Snake.MOVE_LEFT,fieldWidth,fieldHeight;
	
	public Snake(int fieldWidth,int fieldHeight) 
	{
		this.fieldWidth = fieldWidth;
		this.fieldHeight = fieldHeight;
		positions = new CustomMap();
		head = new Point(fieldWidth/2 ,fieldHeight/2);
		positions.put(head);
		Point lastPoint = null;
		Point newPoint;
		for(int i = 0; i < 1// || 1 == (fieldWidth>10?fieldWidth/2:1)
				; i++)
		{
			if(lastPoint != null)
				newPoint = new Point(lastPoint,lastPoint.getX() + 1,lastPoint.getY());
			else
			{
				newPoint = new Point(head,head.getX() + 1,head.getY());
			}
			length++;
			positions.put(newPoint);

			lastPoint = newPoint;
		}
		tail = lastPoint;
		
	}

	public boolean isOnTile(Point tileLocation)
	{
		return positions.containsKey(tileLocation);
	}
	
	public void setDirection(int direction)
	{
		this.direction = direction;
	}
	
	public boolean move()
	{
		Point newHead = getNextHead();
		if(isSelfColliding(newHead) || 
				newHead.getX()<0 || 
				newHead.getX()>fieldWidth-1 || 
				newHead.getY()<0 || 
				newHead.getY()>fieldHeight-1)//resets game
			return false;
		positions.remove(tail);
		tail = tail.getLastPointPointer();
		
		head.setLastPointPointer(newHead);
		head = newHead;
		positions.put(head);
		return true;
	}
	
	public void feed()
	{
		Point newTail;
		Point inFrontOfTail = tail.getLastPointPointer();
		if(inFrontOfTail.getX() < tail.getX())
		{
			newTail = new Point (tail,tail.getX()+1,tail.getY());
		}
		else if(inFrontOfTail.getX() > tail.getX())
		{
			newTail = new Point (tail,tail.getX()-1,tail.getY());
		}
		else if (inFrontOfTail.getY() < tail.getY())
		{
			newTail = new Point (tail,tail.getX(),tail.getY()+1);
		}
		else //if (inFrontOfTail.getY() > tail.getY())
		{
			newTail = new Point (tail,tail.getX(),tail.getY()-1);
		}
		length++;
		positions.put(newTail);
		tail = newTail;
	}

	public Point getHead()
	{
		return head;
	}

	public boolean isSelfColliding(Point newHead) 
	{
		return positions.containsKey(newHead);
	}

	public Point getNextHead()
	{
		Point newHead = null;
		switch(this.direction)
		{
		case Snake.MOVE_UP:
			newHead = new Point(head.getX(),head.getY()-1);
			break;
		case Snake.MOVE_DOWN:
			newHead = new Point(head.getX(),head.getY()+1);
			break;
		case Snake.MOVE_LEFT:
			newHead = new Point(head.getX()-1,head.getY());
			break;
		case Snake.MOVE_RIGHT:
			newHead = new Point(head.getX()+1,head.getY());
			break;
		}
		return newHead;
	}
	public int getDirection()
	{
		return this.direction;
	}

	public int getLength() {
		return length;
	}

}
