package snake.andrewrs.fun;

public class Food extends Point{

	private boolean isEaten = false;
	public Food(int x,int y) 
	{
		super(x,y);
	}
	public boolean isEaten() {
		return isEaten;
	}
	public void setEaten(boolean isEaten) {
		this.isEaten = isEaten;
	}

}
