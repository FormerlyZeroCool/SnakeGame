package snake.andrewrs.fun;

import com.andrewrs.jsonparser.JsonObject;
import com.andrewrs.jsonparser.JsonObjectification;

public class DataLoader {

	private FieldData field;
	private Food food;
	private Snake snake;
	
	public DataLoader(PlayingScreen screen,String data) 
	{
		//System.out.println(data);
		field = new FieldData(screen);
		String dataArr[] = data.split("\\},\\{");
		dataArr[0] = dataArr[0].substring(1);
		dataArr[dataArr.length-1] = dataArr[dataArr.length-1].substring(0,dataArr[dataArr.length-1].length()-2);
		int counter = 0;
		for(FieldCell cell : field)
		{
			String current = dataArr[counter];
			current = '{' + current + '}';
			JsonObject objDat = new JsonObjectification(current).jsonObject;
			cell.setSnakeHere(objDat.getChild("isSnakeHere").getData().equals("true"));
			cell.setObstructionHere(objDat.getChild("isObstructionHere").getData().equals("true"));
			if(objDat.getChild("isFoodHere").getData().equals("true"))
			{
				food = new Food(cell.getCellLocation().getX(),cell.getCellLocation().getY());
				cell.setFoodHere(true);
			}
			counter++;
		}
		food.toString();
	}
	
	public void load(PlayingScreen screen,String data) 
	{
		field = new FieldData(screen);
		String dataArr[] = data.split("},{");
		dataArr[0] = dataArr[0].substring(1);
		dataArr[dataArr.length] = dataArr[dataArr.length].substring(0,dataArr.length-2);
		int counter = 0;
		for(FieldCell cell : field)
		{
			String current = dataArr[counter];
			current = '{' + current + '}';
			JsonObject objDat = new JsonObjectification(current).jsonObject;
			cell.setFoodHere(objDat.getChild("isFoodHere").getData().equals("true"));
			cell.setSnakeHere(objDat.getChild("isSnakeHere").getData().equals("true"));
			cell.setObstructionHere(objDat.getChild("isObstructionHere").getData().equals("true"));
			counter++;
		}
	}
	public Food getFood()
	{
		return food;
	}
	public Snake getSnake()
	{
		return snake;
	}
	public FieldData getField()
	{
		return field;
	}

}
