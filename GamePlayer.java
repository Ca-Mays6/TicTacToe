import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePlayer {

	public static void main(String[] args) {
		
		//Creates window
		JFrame gameWindow = new JFrame();
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setTitle("Tic-Tac-Toe");
		gameWindow.setSize(1920,1080);
		gameWindow.setUndecorated(true);
		GameViewer viewer = new GameViewer();
		
		//Set saved information to viewer
		GameSave save = new GameSave();
		save.readFileContents();
		viewer.setInfo(save.getSavedInfo());
		
		//Pane used to display graphics and get input
		Container pane = gameWindow.getContentPane();
		GameInput input = new GameInput(pane);
		pane.add(viewer);
		
		
		gameWindow.setVisible(true);
		
		//Checker used to verify the game is valid
		GameChecker checker = new GameChecker(viewer.getBoard());
		
		int count = 0;
		int inputPower = 1;
		int previousGameStatus = 0;
		int[] startMenuOptions = {1,2,3,4,-1};
		int turn = 0;
		
		while(viewer.getGameStatus()>=0) {
			
			//Short pause to prevent double or accidental clicks.
			try {Thread.sleep(25);}
			catch(Exception e) {System.out.println(e);}
			
			//Switch of the current game status of the viewer class
			switch(viewer.getGameStatus()) {
			
			//Start Menu
			case 0: 
				if(input.getClickX()+input.getClickY()>=-1)viewer.clickMenu(input.getClickX(), input.getClickY(), 1, startMenuOptions);
				checker.setBoard(viewer.getBoard());
				count = 0;
				inputPower = 1;
				viewer.setGameResult("Playing");
				turn = 0; 
				previousGameStatus = 0;
				break;
				
			//Active Game
			case 1: 
				if(input.getKeyInput()==KeyEvent.VK_ESCAPE) {
					previousGameStatus = 1;
					viewer.setGameStatus(7);
				}
				if(count==0&&viewer.getGamemode()==1&&checker.checkClassic()==0) {
					if(input.getKeyInput()==KeyEvent.VK_SPACE)viewer.setGameStatus(5);
					if(input.getClickX()>=0||input.getClickY()>=0)viewer.setPos(input.getClickX(), input.getClickY(), 1);
				}
				else if(count==0&&viewer.getGamemode()==2&&(checker.checkConquest(viewer.getNumPlayers())==0||turn<=viewer.getNumPlayers())) {
					if(turn>viewer.getNumPlayers()) viewer.setRates(checker.getRates(viewer.getNumPlayers()));
					if(input.getKeyInput()==KeyEvent.VK_SPACE)viewer.setGameStatus(5);
					if(input.getKeyInput()>=48&&input.getKeyInput()<=51)inputPower = input.getKeyInput()-48;
					if(viewer.setPos(input.getClickX(), input.getClickY(), inputPower)) {
						turn++;
					}
				}
				else {
					if(input.getKeyInput()==KeyEvent.VK_SPACE)viewer.setGameStatus(6);
					if(count<=0) {
						try {Thread.sleep(25);}
						catch(Exception e) {System.out.println(e);}
						viewer.setGameStatus(6);
					}
					viewer.setGameResult(checker.getWinner(viewer.getNumPlayers(), viewer.getGamemode()));
					count=1;
					turn=0;
				}
				checker.setBoard(viewer.getBoard());
				break;
			
			//Game Settings
			case 2: 
				if(input.getClickX()+input.getClickY()>=-1)viewer.clickMenu(input.getClickX(), input.getClickY(), 1, startMenuOptions);
				if(input.getKeyInput()==KeyEvent.VK_SPACE) {
					viewer.setGameStatus(9);
					previousGameStatus = 2;
				}
				viewer.sliderMenu(input.getClickX(), input.getClickY());
				break;
			
			//Color Settings
			case 3: 
				if(input.getClickX()+input.getClickY()>=-1)viewer.clickMenu(input.getClickX(), input.getClickY(), 1, startMenuOptions);
				if(input.getKeyInput()==KeyEvent.VK_SPACE) {
					viewer.setGameStatus(9);
					previousGameStatus = 3;
				}
				viewer.sliderMenu(input.getClickX(), input.getClickY());
				break;
			
			//Leaderboard
			case 4: 
				if(input.getKeyInput()==KeyEvent.VK_ESCAPE) viewer.setGameStatus(previousGameStatus);
				if(input.getKeyInput()==KeyEvent.VK_UP) viewer.scrollLeaderboard(1);
				if(input.getKeyInput()==KeyEvent.VK_DOWN) viewer.scrollLeaderboard(-1);
				break;
			
			//Info Panel
			case 5: 
				if(input.getKeyInput()==KeyEvent.VK_SPACE) viewer.setGameStatus(1);
				break;
			
			//End Report
			case 6: 
				if(input.getKeyInput()==KeyEvent.VK_SPACE) viewer.setGameStatus(1);
				int[] endMenuOptions = {8,0,-1};
				viewer.clickMenu(input.getClickX(), input.getClickY(), 3,endMenuOptions);
				break;
			
			//Pause Menu
			case 7: 
				if(input.getKeyInput()==KeyEvent.VK_ESCAPE) viewer.setGameStatus(1);
				int[] pauseMenuOptions = {1,4,0,-1};
				viewer.clickMenu(input.getClickX(), input.getClickY(), 1, pauseMenuOptions);
				break;
			
			//Save Score Menu
			case 8: 
				if(turn>=viewer.getNumPlayers()) {
					viewer.setGameStatus(0);
					viewer.enterNamesToLeaderboard(checker.getWinnerInt(viewer.getNumPlayers(), viewer.getGamemode()));
					save.setSavedInfo(viewer.getInfo());
					save.writeFileContents();
				}
				if(input.getKeyInput()==KeyEvent.VK_ENTER) turn++;
				if(input.isWriteable())viewer.addToNames(turn, input.getKeyCharInput());
				if(input.getKeyInput()==KeyEvent.VK_BACK_SPACE) viewer.subtractToNames(turn);
				break;
			
			//Demo Panel
			case 9: 
				if(input.getKeyInput()==KeyEvent.VK_SPACE)viewer.setGameStatus(previousGameStatus);
				break;
			
			//Exit Game
			default: 
				viewer.setGameStatus(-1);
				break;
			}
			
			if((viewer.getGameStatus()==2||viewer.getGameStatus()==3)&&input.getKeyInput()==KeyEvent.VK_ENTER) {
				save.setSavedInfo(viewer.getInfo());
				save.writeFileContents();
				viewer.setGameStatus(0);
			}
			
			//Resets mouse or keyboard input. Prevents unecessary calculations.
			if(input.getClickX()>=0||input.getClickY()>=0)input.resetMouse();
			if(input.getKeyInput()>-1) input.resetKey();
			
			//Refreshed the windows graphics
			gameWindow.repaint();
			
		}
		
		//Closes program
		System.exit(0);
	}
}