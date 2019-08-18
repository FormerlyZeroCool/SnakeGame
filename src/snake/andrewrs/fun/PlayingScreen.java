package snake.andrewrs.fun;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import com.andrewrs.jsonparser.JsonObjectification;

import mazesolvers.andrewrs.fun.Direction;
import mazesolvers.andrewrs.fun.Node;
import mazesolvers.andrewrs.fun.Tree;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PlayingScreen extends JPanel {

	/**
	 * JPanel with all logic for a snake game
	 * note that keyListeners are not implemented here for game play,
	 * only for drawing obstacles.
	 * Update method must also be called every frame of the game
	 */
	private static final long serialVersionUID = 2L;

	private final int width = 70;
	private final int height = width;
	private FieldData field;
	private Snake snake = new Snake(width, height);
	private Food food;
	private GameStatePrinter statePrinter;
	private boolean autoPlay = false,showHeatMap = false,showPredictedPath = false,liveUpdatePath = true,
			isDijkstra = true,drawingBorders = false,paused = false;
	
	
	public PlayingScreen() 
	{
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				FieldCell lastCell = field.get(field.size()-1);
				int x = (e.getX())/(lastCell.getWidth());
				int y = (e.getY())/(lastCell.getHeight());
				
				if(x + y*width <field.size())
					field.get(x + y*width).setObstructionHere(true);
				
			}
		});

		
		field = new FieldData(this);
		generateNewFood();
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				FieldCell lastCell = field.get(field.size()-1);
				int x = (e.getX()*width)/(lastCell.getWidth()*width);
				int y = (e.getY()*height)/(lastCell.getHeight()*height);
				
				if(x + y*width <field.size())
					field.get(x + y*width).setObstructionHere(!field.get(x + y*width).isObstructionHere());
				
		}});
	
	}
	public void update()
	{
		if(food == null)
		{
			generateNewFood();
		}
	if(!paused)
	{
		if(food.isEaten())
		{
			generateNewFood();
		}
		Direction newDir = treeData.getDirections().get(snake.getHead());
		if(newDir != null && autoPlay)
		{
			snake.setDirection(newDir.getDirection());
		}
		if(!snake.move())
		{
			System.out.println("Length: "+snake.getLength()+"\nGame Over!");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			resetGame();
		}
		if(food.equals(snake.getHead()))
		{
			food.setEaten(true);
			snake.feed();
		}
	}
		

		statePrinter = new GameStatePrinter(field);
		
		repaint();
		
	}
	private Tree treeData;
	private void generateNewFood()
	{

		for(FieldCell cell : field)
		{
			Point cellLocation = cell.getCellLocation();
			field.setSnakeHere(cell,snake.isOnTile(cellLocation));
		}
		treeData = new Tree(field,null,snake.getHead(),isDijkstra?'d':'r');
		Random rand = new Random();
		int nextPossibleSpace = rand.nextInt(width*width-1);
		boolean run = true;
		while(run)
		{
			nextPossibleSpace++;
			if(nextPossibleSpace == field.size()-1)
				nextPossibleSpace = 0;
			if(!field.get(nextPossibleSpace).isSnakeHere()) 
				run = false;


		}
		Point newFoodLocation = field.get(nextPossibleSpace).getCellLocation();
		food = new Food(newFoodLocation.getX(),newFoodLocation.getY());
		for(FieldCell cell : field)
		{
			if(cell.equals(food))
				cell.setFoodHere(true);
		}
		if(!liveUpdatePath)
			treeData = new Tree(field,food,snake.getHead(),isDijkstra?'d':'r');
		
		repaint();
	}
	private final Color darkBlue = new Color(10,10,80);
	/*@Override 
	public void paint(Graphics g)
	{
		if(liveUpdatePath)
		{
			treeData = new Tree(field,food,snake.getHead(),isDijkstra?'d':'r');
		}
		super.paint(g);
	}*/
	@Override
	public void paintComponent(Graphics g)
	{
		
		for(FieldCell cell : field)
		{
			Point cellLocation = cell.getCellLocation();
			field.setSnakeHere(cell,snake.isOnTile(cellLocation));
			if(cell.getCellLocation().equals(food))
			{
				field.setFoodHere(cell,true);
			}
			else 
			{
				field.setFoodHere(cell,false);
			}
			Node nodeData = cell;
			//Draw Field
			g.setColor(cell.getColor());
			if(cell.getCellLocation().equals(snake.getHead()))
				g.setColor(Color.CYAN);
			if(g.getColor().equals(darkBlue) && showHeatMap)
			{
				int colorCode = (int) (nodeData.getCost()
						==-1?250:nodeData.getCost()<255?nodeData.getCost():250);
				g.setColor(new Color(nodeData.getCost()==-1?250:50,255-colorCode,colorCode==-1?0:colorCode));
			}
			if(treeData.getDirections().containsKey(cell.getCellLocation())
					&& !cell.isSnakeHere() && showPredictedPath)
				g.setColor(Color.BLACK);
			g.fillRect(cell.getPixelX(), cell.getPixelY(), cell.getWidth(), cell.getHeight());
			if(drawingBorders)
			{
				//Draw Outline
				g.setColor(Color.BLACK);
				g.drawRect(cell.getPixelX(), cell.getPixelY(), cell.getWidth(), cell.getHeight());
			}
			


			//g.setColor(Color.WHITE);
			//g.drawString(Long.toString(nodeData.getCost()), cell.getX()+2, cell.getY()+15);

		}
		if(paused)	
		{
			g.setColor(Color.RED);
			g.drawString("Paused", this.getWidth()/2-30, this.getHeight()/2);
		}

		if(liveUpdatePath)
		{
			treeData = new Tree(field,food,snake.getHead(),isDijkstra?'d':'r');
		}
	}
	
	public void resetGame()
	{
		field = new FieldData(this);
		snake = new Snake(width, height);
		generateNewFood();
	}
	public int getSquareDim()
	{
		return width;
	}
	public void printField()
	{
		statePrinter.printState();
	}
	public void saveToFile(String fp)
	{
		try {
			statePrinter.saveToFile(fp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadFile(String fp)
	{
		StringBuilder data = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fp));
			String current=reader.readLine();
			while(current != null)
			{
				data.append(current);
				current=reader.readLine();
			}
			reader.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		DataLoader loader = new DataLoader(this,data.toString());
		field = loader.getField();
		food = loader.getFood();
	}
	public Snake getSnake() {
		return snake;
	}
	public boolean isAutoPlay() {
		return autoPlay;
	}
	public void setAutoPlay(boolean autoPlay) {
		this.autoPlay = autoPlay;
	}
	public boolean isShowHeatMap() {
		return showHeatMap;
	}
	public void setShowHeatMap(boolean showHeatMap) {
		this.showHeatMap = showHeatMap;
	}
	public boolean isShowPredictedPath() {
		return showPredictedPath;
	}
	public void setShowPredictedPath(boolean showPredictedPath) {
		this.showPredictedPath = showPredictedPath;
	}
	public boolean isLiveUpdatePath() {
		return liveUpdatePath;
	}
	public void setLiveUpdatePath(boolean liveUpdatePath) {
		this.liveUpdatePath = liveUpdatePath;
	}
	public boolean isDijkstra() {
		return isDijkstra;
	}
	public void setDijkstra(boolean isDijkstra) {
		this.isDijkstra = isDijkstra;
	}
	public boolean isDrawingBorders() {
		return drawingBorders;
	}
	public void setDrawingBorders(boolean drawingBorders) {
		this.drawingBorders = drawingBorders;
	}
	public boolean isPaused() {
		return paused;
	}
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
}
