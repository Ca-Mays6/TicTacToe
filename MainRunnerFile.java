import java.awt.*;
import javax.swing.*;

public class Player {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("Tic-Tac-Toe");
		frame.setSize(1000,1000);
		int num = 5;
		int[][] board = new int[num][num];
		GameViewer panel = new GameViewer(Color.black, board);
		Container pane = frame.getContentPane();
		pane.add(panel);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		panel.setInfo("" + num);
		for(int row = 0; row<board.length; row++) {
			for(int col = 0; col<board[row].length; col++) {
				if(col%5==0) board[row][col] = 0;
				else if(col%4==0) board[row][col] = 4;
				else if(col%3==0) board[row][col] = 3;
				else if(col%2==0) board[row][col] = 2;
				else board[row][col] = 1;
			}
		}
	}

}
