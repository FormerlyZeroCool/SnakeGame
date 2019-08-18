package snakemain.andrewrs.com;


import snake.andrewrs.fun.Frame;

public class SnakeMain {

	public static void main(String args[]) {
	
		Frame frame = new Frame();
		frame.setVisible(true);
		while(true)
		{
			try {
				Thread.sleep(frame.getSleepTime());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			frame.update();
		}
	}

}
