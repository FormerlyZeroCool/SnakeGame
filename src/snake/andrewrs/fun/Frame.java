package snake.andrewrs.fun;

import javax.swing.JFrame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PlayingScreen screen;
	private long sleepTime = 20;
	
	public Frame() 
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700, 700);
		this.screen = new PlayingScreen();
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_UP && screen.getSnake().getDirection() != Snake.MOVE_DOWN)
				{
					screen.getSnake().setDirection(Snake.MOVE_UP);
				}
				else if(e.getKeyCode() == KeyEvent.VK_DOWN && screen.getSnake().getDirection() != Snake.MOVE_UP)
				{
					screen.getSnake().setDirection(Snake.MOVE_DOWN);
				}
				else if(e.getKeyCode() == KeyEvent.VK_LEFT && screen.getSnake().getDirection() != Snake.MOVE_RIGHT)
				{
					screen.getSnake().setDirection(Snake.MOVE_LEFT);
				}
				else if(e.getKeyCode() == KeyEvent.VK_RIGHT && screen.getSnake().getDirection() != Snake.MOVE_LEFT)
				{
					screen.getSnake().setDirection(Snake.MOVE_RIGHT);
				}
				else if(e.getKeyCode() == KeyEvent.VK_R)
				{
					screen.resetGame();
				}
				else if(e.getKeyCode() == KeyEvent.VK_A)
				{
					screen.setAutoPlay(!screen.isAutoPlay());
				}
				else if(e.getKeyCode() == KeyEvent.VK_H)
				{
					screen.setShowHeatMap(!screen.isShowHeatMap());
				}
				else if(e.getKeyCode() == KeyEvent.VK_P)
				{
					screen.setPaused(!screen.isPaused());
				}
				else if(e.getKeyCode() == KeyEvent.VK_J)
				{
					screen.setShowPredictedPath(!screen.isShowPredictedPath());
				}
				else if(e.getKeyCode() == KeyEvent.VK_L)
				{
					screen.setLiveUpdatePath(!screen.isLiveUpdatePath());
				}
				else if(e.getKeyCode() == KeyEvent.VK_EQUALS)
				{
						sleepTime+=10;
				}
				else if(e.getKeyCode() == KeyEvent.VK_MINUS)
				{
					if(sleepTime>20)
						sleepTime-=10;
				}
				else if(e.getKeyCode() == KeyEvent.VK_S)
				{
					screen.setDijkstra(false);
				}
				else if(e.getKeyCode() == KeyEvent.VK_D)
				{
					screen.setDijkstra(true);
				}
				else if(e.getKeyCode() == KeyEvent.VK_B)
				{
					screen.setDrawingBorders(!screen.isDrawingBorders());
				}
				else if(e.getKeyCode() == KeyEvent.VK_W)
				{
					screen.loadFile("data.txt");
				}
				else if(e.getKeyCode() == KeyEvent.VK_Q)
				{
					screen.saveToFile("data.txt");
				}
			}
		});
		this.getContentPane().add(screen);
		
		JPanel pnlControl = new JPanel();
		getContentPane().add(pnlControl, BorderLayout.SOUTH);
		pnlControl.setLayout(new GridLayout(1, 3, 0, 0));
		
		JPanel pnlControlSub1 = new JPanel();
		pnlControl.add(pnlControlSub1);

		JPanel pnlControlSub2 = new JPanel();
		pnlControl.add(pnlControlSub2);
		
		JPanel pnlControlSub3 = new JPanel();
		pnlControl.add(pnlControlSub3);

	}

	public void update() 
	{
		screen.update();
	}

	public long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}


}
