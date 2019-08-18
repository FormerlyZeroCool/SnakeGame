package snake.andrewrs.fun;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import mazesolvers.andrewrs.fun.NodeMap;

public class GameStatePrinter 
{
	private FieldData rawData;
	private String filePath;
	public GameStatePrinter(FieldData rawData)
	{
		this.rawData = rawData;
	}
	public GameStatePrinter(FieldData rawData,String filePath)
	{
		this.rawData = rawData;
		this.filePath = filePath;
	}

	public void saveToFile() throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		String state = getState();
		writer.write(state);
		writer.close();
		System.out.println("From GameStatePrinter.saveToFile: Save Complete!");
	}

	public void saveToFile(String fp) throws IOException
	{
		this.filePath = fp;
		saveToFile();
	}
	public void saveToFile(String fp,FieldData rawData) throws IOException
	{
		this.filePath = fp;
		this.rawData = rawData;
		saveToFile();
	}
	public void printState()
	{
		final String state = this.getState();
		System.out.println(state);
	}
	public String getState()
	{
		StringBuffer stateText = new StringBuffer();
		stateText.append('[');
		for(FieldCell cell : rawData)
		{

			stateText.append('{');
			stateText.append("\"X\":");
			stateText.append(cell.getCellLocation().getX());
			stateText.append(',');
			stateText.append("\"Y\":");
			stateText.append(cell.getCellLocation().getY());
			stateText.append(',');
			stateText.append("\"Cost\":");
			stateText.append(cell.getCost());
			stateText.append(',');
			stateText.append("\"isFoodHere\":");
			stateText.append(cell.isFoodHere()?"true":"false");
			stateText.append(',');
			stateText.append("\"isSnakeHere\":");
			stateText.append(cell.isSnakeHere()?"true":"false");
			stateText.append(',');
			stateText.append("\"isObstructionHere\":");
			stateText.append(cell.isObstructionHere()?"true":"false");
			stateText.append('}');
			stateText.append(',');
			
		}
		stateText.append(']');
		return stateText.deleteCharAt(stateText.length()-2).toString();
	}
	public String getState(FieldData field)
	{
		this.rawData = field;
		return getState();
	}
}
