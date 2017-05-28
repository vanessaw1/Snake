import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Board extends JPanel {
	
	private int cellSize;
	private int numRow;
	private int numCol;
	private int[][] board;
	private int[] foodLocation;
	private Random random;
	private Snake snake;
	private Timer t;
	private boolean firstKeyPressed;
	private final int FPS = 60; // milliseconds
	
	public Board() {
		random = new Random();
		numRow = 20; // y value
		numCol = 37; // x value
		cellSize = 50;
		
		board = new int[numCol][numRow];
		
		snake = new Snake(new int[] {1,1}, 4);
		board[snake.getHeadPos()[0]][snake.getHeadPos()[1]] = 1;
		
		foodLocation = new int[] {0,0};
		newFood();
		
		//this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), snake.move());
		
		firstKeyPressed = false;
		
		addKeyBind("UP", 0);
		addKeyBind("RIGHT", 1);
		addKeyBind("DOWN", 2);
		addKeyBind("LEFT", 3);
		
		t = new Timer(1000/FPS, new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				snake.move();
				checkFoodEaten();
				repaint();
			}
		});
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); //Erases everything
		/*g.setColor(Color.LIGHT_GRAY); // Paints the grid
		for (int i = 0; i <= numCol; i++) {
			for (int j = 0; j <= numRow; j++) {
				g.drawLine(0, j*cellSize, numCol*cellSize, j*cellSize); // Rows
				g.drawLine(i*cellSize, 0, i*cellSize, numRow*cellSize); // Columns
			}
		}*/
		updateSnake(checkCollision());
		for (int i = 0; i < numCol; i++) {
			for (int j = 0; j < numRow; j++) {
				if (board[i][j] == 0)
					paintCell(i,j,Color.WHITE,g); // Background
				else if (board[i][j] == 1)
					paintCell(i,j,Color.YELLOW,g); // Head
				else if (board[i][j] == 2)
					paintCell(i,j,Color.GREEN,g); // Body
				else if (board[i][j]==3) 
					paintCell(i,j,Color.RED,g); // Food
			}
		}
	}
	
	public void paintCell(int x, int y, Color c, Graphics g) {
		g.setColor(c);
		g.fillRect(x*cellSize, y*cellSize, cellSize, cellSize);
	}
	
	public void updateSnake(boolean collision) {
		if (!collision) {
			board[snake.getNeckPos()[0]][snake.getNeckPos()[1]] = 2;
			board[snake.getHeadPos()[0]][snake.getHeadPos()[1]] = 1;
			if (!snake.isGrowing()) board[snake.getTailPos()[0]][snake.getTailPos()[1]] = 0;
		}
	}
	
	public void checkFoodEaten() {
		if (snake.getHeadPos()[0] == foodLocation[0] && snake.getHeadPos()[1] == foodLocation[1]) {
			snake.grow();
			newFood();
		}
	}
	
	public boolean checkCollision() {
		if (snake.getHeadPos()[0] < 0 || snake.getHeadPos()[0] > board.length-1 || snake.getHeadPos()[1] < 0 || snake.getHeadPos()[1] > board[0].length-1
				|| (board[snake.getHeadPos()[0]][snake.getHeadPos()[1]] == 2)) {
			t.stop();
			System.out.println("Crashed");
			return true;
		}
		return false;
	}
	
	public void newFood() {
		boolean foodPlaced = false;
		while (!foodPlaced) {
			foodLocation[0] = random.nextInt(numCol);
			foodLocation[1] = random.nextInt(numRow);
			if (board[foodLocation[0]][foodLocation[1]] == 0) {
				board[foodLocation[0]][foodLocation[1]] = 3;
				foodPlaced = true;
			}
		}
	}
	
	public void addKeyBind(String key, int dir) {
		getInputMap().put(KeyStroke.getKeyStroke(key), key);
		getActionMap().put(key, new AbstractAction() {
			public void actionPerformed(ActionEvent arg0) {
				if (!firstKeyPressed) {
					t.start();
					firstKeyPressed = true;
				}
				snake.changeDir(dir);
			}
			
		});
	}
}
