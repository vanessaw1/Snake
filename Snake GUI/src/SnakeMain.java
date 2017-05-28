import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

public class SnakeMain extends JFrame {
	
	private Board board;
	
	public SnakeMain() {
		setTitle("Snake");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

		setLayout(new BorderLayout());
		board = new Board();
		
		add(board);
		//frame.pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	

	
	public static void main(String[] args) {
		new SnakeMain();
	}
}
