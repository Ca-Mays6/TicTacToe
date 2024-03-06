import java.awt.*;
import javax.swing.*;

public class Player {

	public static void main(String[] args) {
		JFrame test = new JFrame();
		test.setTitle("Tic-Tac-Toe");
		test.setSize(1000,1000);
		int[][] board = new int[10][10];
		GameViewer panel = new GameViewer(Color.black, board);
		Container pane = test.getContentPane();
		pane.add(panel);
		test.setVisible(true);
		test.setDefaultCloseOperation(test.EXIT_ON_CLOSE);
	}

}
