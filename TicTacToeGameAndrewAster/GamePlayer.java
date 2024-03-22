import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePlayer {

	public static void main(String[] args) {
		JFrame gameWindow = new JFrame();
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setTitle("Tic-Tac-Toe");
		gameWindow.setSize(1920,1080);
		gameWindow.setUndecorated(true);
		GameViewer viewer = new GameViewer();
		GameSave save = new GameSave();
		save.readFileContents();
		viewer.setInfo(save.getSavedInfo());
		Container pane = gameWindow.getContentPane();
		GameInput input = new GameInput(pane);
		pane.add(viewer);
		gameWindow.setVisible(true);
		GameChecker checker = new GameChecker(viewer.getBoard());
		int count = 0;
		int previousGameStatus = 0;
		int[] startMenuOptions = {1,2,3,4,-1};
		int scoreTurn = 0;
		while(viewer.getGameStatus()>=0) {
			try {Thread.sleep(25);}
			catch(Exception e) {System.out.println(e);}
			switch(viewer.getGameStatus()) {
			case 0: 
				if(input.getClickX()+input.getClickY()>=-1)viewer.clickMenu(input.getClickX(), input.getClickY(), 1, startMenuOptions, 0);
				checker.setBoard(viewer.getBoard());
				count = 0;
				viewer.setGameResult(checker.checkGame());
				scoreTurn = 0; 
				break;
			case 1: 
				if(input.getKeyInput()==KeyEvent.VK_ESCAPE) viewer.setGameStatus(7);
				if(checker.isGameValid()) {
					if(input.getKeyInput()==KeyEvent.VK_SPACE)viewer.setGameStatus(5);
					if(input.getClickX()>=0||input.getClickY()>=0)viewer.setPos(input.getClickX(), input.getClickY());
					checker.setBoard(viewer.getBoard());
				}
				else {
					if(input.getKeyInput()==KeyEvent.VK_SPACE)viewer.setGameStatus(6);
					if(count<=0) {
						try {Thread.sleep(25);}
						catch(Exception e) {System.out.println(e);}
						viewer.setGameStatus(6);
					}
					viewer.setGameResult(checker.checkGame());
					count=1;
				}
				break;
			case 2: 
				if(input.getClickX()+input.getClickY()>=-1)viewer.clickMenu(input.getClickX(), input.getClickY(), 1, startMenuOptions, 0);
				if(input.getKeyInput()==KeyEvent.VK_SPACE) {
					viewer.setGameStatus(9);
					previousGameStatus = 2;
				}
				viewer.sliderMenu(input.getClickX(), input.getClickY());
				break;
			case 3: 
				if(input.getClickX()+input.getClickY()>=-1)viewer.clickMenu(input.getClickX(), input.getClickY(), 1, startMenuOptions, 0);
				if(input.getKeyInput()==KeyEvent.VK_SPACE) {
					viewer.setGameStatus(9);
					previousGameStatus = 3;
				}
				viewer.sliderMenu(input.getClickX(), input.getClickY());
				break;
			case 4: 
				if(input.getKeyInput()==KeyEvent.VK_ESCAPE) viewer.setGameStatus(0);
				if(input.getKeyInput()==KeyEvent.VK_UP) viewer.scrollLeaderboard(1);
				if(input.getKeyInput()==KeyEvent.VK_DOWN) viewer.scrollLeaderboard(-1);
				break;
			case 5: 
				if(input.getKeyInput()==KeyEvent.VK_SPACE) viewer.setGameStatus(1);
				break;
			case 6: 
				if(input.getKeyInput()==KeyEvent.VK_SPACE) viewer.setGameStatus(1);
				int[] endMenuOptions = {8,0,-1};
				viewer.clickMenu(input.getClickX(), input.getClickY(), 3,endMenuOptions, 0);
				break;
			case 7: 
				if(input.getKeyInput()==KeyEvent.VK_ESCAPE) viewer.setGameStatus(1);
				int[] pauseMenuOptions = {1,4,0,-1};
				viewer.clickMenu(input.getClickX(), input.getClickY(), 1, pauseMenuOptions, 0);
				break;
			case 8: 
				if(scoreTurn>=viewer.getNumPlayers()) {
					viewer.setGameStatus(0);
					viewer.enterNamesToLeaderboard(checker.checkGameInt(viewer.getNumPlayers()));
					save.setSavedInfo(viewer.getInfo());
					save.writeFileContents();
				}
				if(input.getKeyInput()==KeyEvent.VK_ENTER) scoreTurn++;
				if(input.isWriteable())viewer.addToNames(scoreTurn, input.getKeyCharInput());
				if(input.getKeyInput()==KeyEvent.VK_BACK_SPACE) viewer.subtractToNames(scoreTurn);
				break;
			case 9: if(input.getKeyInput()==KeyEvent.VK_SPACE)viewer.setGameStatus(previousGameStatus);
				break;
			default: 
				viewer.setGameStatus(-1);
				break;
			}
			if((viewer.getGameStatus()==2||viewer.getGameStatus()==3)&&input.getKeyInput()==KeyEvent.VK_ENTER) {
				save.setSavedInfo(viewer.getInfo());
				save.writeFileContents();
				viewer.setGameStatus(0);
			}
			if(input.getClickX()>=0||input.getClickY()>=0)input.resetMouse();
			if(input.getKeyInput()>-1) input.resetKey();
			gameWindow.repaint();
		}
		System.exit(0);
	}
}