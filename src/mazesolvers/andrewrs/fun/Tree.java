package mazesolvers.andrewrs.fun;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

import snake.andrewrs.fun.FieldCell;
import snake.andrewrs.fun.FieldData;
import snake.andrewrs.fun.Food;
import snake.andrewrs.fun.Point;
import snake.andrewrs.fun.Snake;

public class Tree {

	private Node goal,start;
	private FieldData data;
	
	public Tree(FieldData data,Food food,Point snakeHead,char dr) 
	{
		this.data = (FieldData) data.clone();
		
		//synchronized(data)
		{
			for(FieldCell cell : data)
			{
				cell.setCost(-1L);
			}	
		}
		if(food !=null)
			goal = data.get(food.getX(),food.getY());
		//System.out.println(snakeHead.getX()+" " + snakeHead.getY());
		start = data.get(snakeHead.getX(),snakeHead.getY());
		//System.out.println("Start: "+start.toString());
		//System.out.println("Goal: "+goal.toString());
		//Boolean solutionFound = depthFirstRoute(start);
		//setNodeCost();
		//NodeMap visited = new NodeMap();
		//buildShortestPath(start,visited);
		dr = Character.toLowerCase(dr);

		if(food !=null)
			buildPath(dr);
		
	}
	private DirectionMap directions = new DirectionMap();
	private int checkCounter = 0;
	public boolean depthFirstRoute(Node working)
	{
		working.setVisited(true);
		if(working.isObstructionHere() && !working.equals(start))
			return false;
		if(goal.equals(working))
		{
			return true;
		}
		boolean right,left,up,down;

		down = isPossibleDepth(working.down);
		right = isPossibleDepth(working.right);
		left = isPossibleDepth(working.left);
		up = isPossibleDepth(working.up);

		if(right)
		{
			directions.put(new Direction(working,Snake.MOVE_RIGHT));
		}
		else if(left)
		{
			directions.put(new Direction(working,Snake.MOVE_LEFT));
		}
		else if(up)
		{
			directions.put(new Direction(working,Snake.MOVE_UP));
		}
		else if(down)
		{
			directions.put(new Direction(working,Snake.MOVE_DOWN));
		}
		else
			return false;

		checkCounter++;
		if(checkCounter < data.getSqareDim()*data.getSqareDim()-1 || working.equals(goal))
		return true;
		else
			return false;
		
	}
	public void buildPath(char dr)
	{

		if(dr == 'd')
			setNodeCostDijkstra();
		else
			setNodeCost();
		directions.clear();
		if(goal!=null)
		{
			Node current = goal;
			
			int count = 1;
			while(current.getPrevious() != null && count <= data.getSqareDim()*data.getSqareDim())
			{
				if(current.getPrevious().equals(current.left!=null?current.left:""))
					directions.put(new Direction(current.left,Snake.MOVE_RIGHT));
				else if(current.getPrevious().equals(current.up!=null?current.up:""))
					directions.put(new Direction(current.up,Snake.MOVE_DOWN));
				else if(current.getPrevious().equals(current.right!=null?current.right:""))
					directions.put(new Direction(current.right,Snake.MOVE_LEFT));
				else if(current.getPrevious().equals(current.down!=null?current.down:""))
					directions.put(new Direction(current.down,Snake.MOVE_UP));
				current = current.getPrevious();
				count++;
			}
		}
	}
	public void setNodeCostDijkstra()
	{

		Comparator<Node> compareByCost = (Node o1, Node o2) ->
        (int)(o1.getCost()- o2.getCost());
		PriorityQueue<Node> queue = new PriorityQueue<Node>(compareByCost);
		NodeMap checked = new NodeMap();
		queue.add(start);
		checked.put(start);
		start.setCost(0);
		while(!queue.isEmpty())
		{
			Node working = queue.poll();
			queue.remove(working);
			calcCost(working.right,working);
			calcCost(working.down,working);
			calcCost(working.left,working);
			calcCost(working.up,working);
			pushToPriorityQueue(queue,working.right,checked);
			pushToPriorityQueue(queue,working.down,checked);
			pushToPriorityQueue(queue,working.left,checked);
			pushToPriorityQueue(queue,working.up,checked);
			
		}
		

	
		start.setPrevious(null);
		
	}
	public void setNodeCost()
	{

		Stack<Node> queue = new Stack<Node>();
		NodeMap checked = new NodeMap();
		queue.add(start);
		start.setCost(0);
		while(!queue.isEmpty())
		{
			Node working = queue.pop();
			checked.put(working);
			calcCost(working.right,working);
			calcCost(working.down,working);
			calcCost(working.left,working);
			calcCost(working.up,working);
			pushToStack(queue,working.right,checked);
			pushToStack(queue,working.down,checked);
			pushToStack(queue,working.left,checked);
			pushToStack(queue,working.up,checked);
			
		}
		checked = new NodeMap();
		queue.push(start);
		start.setCost(0);
		while(!queue.isEmpty())
		{
			Node working = queue.pop();

			checked.put(working);
			calcCost(working.up,working);
			calcCost(working.left,working);
			calcCost(working.down,working);
			calcCost(working.right,working);
			pushToStack(queue,working.left,checked);
			pushToStack(queue,working.up,checked);
			pushToStack(queue,working.right,checked);
			pushToStack(queue,working.down,checked);
			
		}
		
		start.setPrevious(null);
		
	}
	private void calcCost(Node working,Node previous)
	 {
		if(working !=null)
		if(working.getCost()<0 || working.getCost()>=previous.getCost()+1 && !working.isObstructionHere()) 
				
		{
			working.setCost(previous.getCost()+1); 
			working.setPrevious(previous);
		}
	 }
	private void pushToStack(Stack<Node> queue,Node working,NodeMap checked)
	{
		if(working !=null && checked.get(working) == null && !working.isObstructionHere())
		{
			queue.push(working);
		}
	}
	private void pushToPriorityQueue(PriorityQueue<Node> queue,Node working,NodeMap checked)
	{
		if(working !=null && checked.get(working) == null && !working.isObstructionHere())
		{
			queue.add(working);
			checked.put(working);
		}
	}
	
	
	private boolean isPossibleDepth(Node working)
	{
		if(working!=null && !working.isVisited())
			return depthFirstRoute(working);
		else
			return false;
	}
	
	public DirectionMap getDirections() {
		return directions;
	}
}
