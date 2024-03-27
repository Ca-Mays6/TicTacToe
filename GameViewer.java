import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class GameViewer extends JPanel {
	
	private static final long serialVersionUID = 7247732440388695869L;
	
	private Color foreground;
	private Color background;
	
	private int[][] board;
	private String currentTurn;
	private int turn;
	private int numPlayers;
	private int gameStatus;
	private String gameResult;
	private int[] settings;
	
	private int widthRemainder;
	private int heightRemainder;
	private int windowSize;
	private int gridSize;
	private int gridOffset;
	private float[] sliderHeights;
	
	private int[][] powerGrid;
	private int[] resources;
	private int[] rates;
	
	private List<String> leaderboard;
	private List<Integer> leaderboardScores;
	private String names[];
	
	public GameViewer() {
		this.background = Color.black;
		this.setBackground(this.background);
		this.foreground = Color.white;
		this.board = new int[3][3];
		this.powerGrid = new int[this.board.length][this.board.length];
		this.widthRemainder = 0;
		this.heightRemainder = 0;
		this.currentTurn = "";
		this.turn = 0;
		this.numPlayers = 0;
		this.gameStatus = 0;
		this.windowSize = 0;
		this.gridSize = 0;
		this.gridOffset = 0;
		this.gameResult = "Playing";
		this.settings = new int[9];
		this.sliderHeights = new float[this.settings.length];
		this.leaderboard = new ArrayList<String>();
		this.leaderboardScores = new ArrayList<Integer>();
		this.names = new String[this.numPlayers];
		this.resources = new int[numPlayers];
		this.rates = new int[numPlayers];
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Updates information
		this.updateInfo();
		
		//Sets the color of the game
		this.setBackground(background);
		g.setColor(foreground);
		
		//Sets the font and font size of the game
		Font font = new Font("Courier", Font.PLAIN, (int)(this.windowSize*.032));
		g.setFont(font);
		
		//Creates the text used in buttons and menus
		String[] startMenuText = {"Start", "Game Options", "Color Options", "Leaderboards", "Exit"}; 
		String[] pauseMenuText = {"Resume", "Leaderboards", "Return To Menu", "Exit"};
		String[] infoPanelText = {"Game Status: " + this.gameResult, "Gamemode: Classic", this.currentTurn, "", "", "", ""};
		String[] endMenuText = {"Game Result: "+this.gameResult, "Gamemode: Classic", "Save Scores", "Return To Menu", "Exit"};
		
		//Game Status controls what the viewer will show and do
		switch(this.getGameStatus()) {
		case 0: 
			this.turn = 0;
			this.board = new int[board.length][board.length];
			this.resources = new int[numPlayers];
			this.rates = new int[numPlayers];
			this.powerGrid = new int[board.length][board.length];
			for(int i = 0; i<numPlayers; i++) {
				resources[i] = 3;
				rates[i] = 1;
			}
			this.sortLeaderboard();
			this.drawMenu(g, startMenuText, 0);
			this.drawTitle(g, "Tic-Tac-Toe +");
			break;
		case 1: 
			this.drawGrid(g);
			this.fillGrid(g);
			break;
		case 2: 
			this.drawSliders(g);
			this.drawMenu(g, startMenuText, 0);
			this.drawTitle(g, "Game Options");
			break;
		case 3: 
			this.drawSliders(g);
			this.drawMenu(g, startMenuText, 0);
			this.drawTitle(g, "Color Options");
			break;
		case 4: 
			String[] leaderboardArray = new String[leaderboard.size()];
			for(int i = 0; i<leaderboard.size(); i++) {
				leaderboardArray[i] = leaderboard.get(i) + "  " + leaderboardScores.get(i);
			}
			this.drawMenu(g, leaderboardArray, 1);
			this.drawTitle(g, "Leaderboard");
			break;
		case 5: 
			if(this.settings[0]==2) {
				infoPanelText[1] = "Gamemode: Conquest";
				for(int i = 0; i<numPlayers; i++) if(i<names.length)infoPanelText[i+3] = this.names[i].substring(0,3) + this.resources[i];
			}
			this.drawMenu(g, infoPanelText, 1);
			this.drawTitle(g, "Info");
			break;
		case 6: 
			if(this.settings[0]==2)endMenuText[1] = "Gamemode: Conquest";
			this.drawMenu(g, endMenuText, 0);
			this.drawTitle(g, "Report: ");
			break;
		case 7: 
			this.drawMenu(g, pauseMenuText, 0);
			this.drawTitle(g, "Paused");
			break;
		case 8: 
			this.drawMenu(g, this.names, 1);
			this.drawTitle(g, "Save Score");
			break;
		case 9: 
			this.drawGrid(g);
			int count = 1;
			for(int col = 0; col<board[0].length; col++) {
				for(int row = 0; row<board.length; row++) {
					if(count<=numPlayers) {
						board[row][col] = count;
						count++;
					}
					else {
						col=board[0].length;
						row=board.length;
					}
				}
			}
			this.fillGrid(g);
			break;
		default: 
			break;
		}
	}
	
	//Draws the grid used int the game
	public void drawGrid(Graphics g) {
		g.drawRect(widthRemainder, heightRemainder, windowSize, windowSize);
		for(int grid = 0; grid<board.length; grid++) {
			g.drawLine(grid*gridSize+widthRemainder, heightRemainder, grid*gridSize+widthRemainder, windowSize+heightRemainder);
			g.drawLine(widthRemainder, grid*gridSize+heightRemainder, windowSize+widthRemainder, grid*gridSize+heightRemainder);
		}
	}
	
	//Fills the grid with the pieces on the board
	public void fillGrid(Graphics g) {
		for(int row = 0; row<board.length; row++) {
			for(int col = 0; col<board[row].length; col++) {
				if(board[row][col]>0)this.drawShape(g, row, col, board[row][col], powerGrid[row][col]);
			}
		}
	}
	
	//Draws a shape based on int. 1=x, 2=o, 3=plus, 4=square
	public void drawShape(Graphics g, int x1, int y1, int shape,  int pow) {
		for(int p = 0; p<powerGrid[x1][y1]+1; p++) {
			switch(this.board[x1][y1]) {
			case 1: this.drawX(g, x1*gridSize+widthRemainder, p+y1*gridSize+heightRemainder);
				break;
			case 2: this.drawO(g, p+x1*gridSize+widthRemainder, y1*gridSize+heightRemainder);
				break;
			case 3: this.drawP(g, p+x1*gridSize+widthRemainder, p+y1*gridSize+heightRemainder);
				break;
			case 4: this.drawS(g, p+x1*gridSize+widthRemainder, p+y1*gridSize+heightRemainder);
			}
		}
	}
	
	//Draws an X at the specified location
	public void drawX(Graphics g, int x1, int y1) {
		g.drawLine(x1+this.gridOffset, y1+this.gridOffset, this.gridSize+x1-this.gridOffset, this.gridSize+y1-this.gridOffset);
		g.drawLine(this.gridSize+x1-this.gridOffset, y1+this.gridOffset, x1+this.gridOffset, this.gridSize+y1-this.gridOffset);
	}
	
	//Draws an O at the specified location
	public void drawO(Graphics g,int x1 ,int y1) {
		g.drawOval(x1+this.gridOffset,y1+this.gridOffset,this.gridSize-this.gridOffset*2,this.gridSize-this.gridOffset*2);
	}
	
	//Draws an Plus at the specified location
	public void drawP(Graphics g,int x1 ,int y1) {
		g.drawLine(x1+this.gridOffset, (y1+(this.gridSize+y1))/2, this.gridSize+x1-this.gridOffset, (y1+(this.gridSize+y1))/2);
		g.drawLine((x1+(this.gridSize+x1))/2, y1+this.gridOffset, (x1+(this.gridSize+x1))/2, this.gridSize+y1-this.gridOffset);
	}
	
	//Draws an Square at the specified location
	public void drawS(Graphics g,int x1 ,int y1) {
		g.drawRect(x1+this.gridOffset,y1+this.gridOffset,this.gridSize-this.gridOffset*2,this.gridSize-this.gridOffset*2);
	}
	
	//Updates all information through out the game. Game status affects what values will be changed. Fewer values can be changed while there is an active game.
	public void updateInfo() {
		if(this.gameStatus==0||this.gameStatus==2||this.gameStatus==3) {
			this.board = new int[settings[2]][settings[2]];
			this.numPlayers = settings[1];
			this.names = new String[this.numPlayers];
			this.foreground = new Color(settings[3], settings[4], settings[5]);
			this.background = new Color(settings[6], settings[7], settings[8]);
			for(int i = 0; i<this.numPlayers; i++) {
				switch(i) {
				case 0: 
					names[i] = "X: ";
					break;
				case 1: 
					names[i] = "O: ";
					break;
				case 2: 
					names[i] = "P: ";
					break;
				case 3: 
					names[i] = "S: ";
					break;
				}
			}
		}
		if(this.getWidth()<=this.getHeight()) this.windowSize=this.getWidth();
		else this.windowSize = this.getHeight();
		if(windowSize%this.board.length!=0)	this.windowSize-=(windowSize%this.board.length);
		this.widthRemainder = (this.getWidth()-windowSize)/2;
		this.heightRemainder = (this.getHeight()-windowSize)/2;
		this.gridSize = windowSize/board.length;
		this.gridOffset = this.gridSize/5;
		if(this.turn==numPlayers) this.turn=0;
		if(this.gameStatus==1) {
			switch(this.turn+1) {
			case 1: this.currentTurn = "Turn: X";
				break;
			case 2: this.currentTurn = "Turn: O";
				break;
			case 3: this.currentTurn = "Turn: Plus";
				break;
			case 4: this.currentTurn = "Turn: Square";
				break;
			default: 
				break;
			}	
		}
		if((this.resources.length>0&&resources[turn]<=0)||(this.rates.length>0&&rates[turn]==0)) {
			turn++;
		}
	}
	
	//Converts mouse clicks to positions on a board, then sets the board to that piece if its valid.
	public boolean setPos(int x, int y, int power) {
		x = (int)Math.floor((x-widthRemainder)/(double)gridSize);
		y = (int)Math.floor((y-heightRemainder)/(double)gridSize);
		if((x<0||x>=board.length||y<0||y>=board.length)) return false;
		if((this.settings[0]==1&&board[x][y]!=0)) return false;
		if(this.settings[0]==2) {
			if((this.findPiece(turn+1)&&!(this.checkProxy(x, y, (int)Math.round(3.0/(power))).contains(((Integer)turn+1))))||!this.findPiece(turn+1)&&this.checkProxy(x, y, (int)(this.board.length/numPlayers)).size()>0||resources[turn]<=0) return false;
			if(turn-1==-1) resources[numPlayers-1]+=rates[numPlayers-1];
			else resources[turn-1]+=rates[turn-1];
			resources[turn]-=power;
		}
		turn++;
		if(this.powerGrid[x][y]>power) return true;
		if(this.powerGrid[x][y]==power&&Math.random()>0.75) return true;
		this.board[x][y] = turn;
		this.powerGrid[x][y] = power;
		return true;
	}
	
	//Converts mouse clicks to click-able buttons. Used in menus.
	public void clickMenu(int x, int y, int start, int[] options) {
		for(int i = start; i<options.length+start; i++) {
			if((x>this.getWidth()*(0.1)+1)&&x<this.getWidth()*(0.4)+1&&y>this.getHeight()*(i+2.0)/10+1&&y<this.getHeight()*(i+2.625)/10+1) {
				this.setGameStatus(options[i-start]);
				break;
			}
		}
	}
	
	//Converts mouse clicks to sliders. Used to adjust game settings.
	public void sliderMenu(int x, int y) {
		if(x>=this.getWidth()*0.89||x<=this.getWidth()*0.51||y>=this.getHeight()*0.89||y<=this.getHeight()*0.11) return;
		int num = 3;
		int num2 = 0;
		int num3 = 1;
		if(gameStatus==3) num = 6;
		for(int i = this.getWidth()/2; i<=this.getWidth()*9/10; i+=this.getWidth()*4/10/num) {
			num2 = (i-this.getWidth()/2)/(this.getWidth()*4/10/num);
			num3 = 1;
			if(x<=i) {
				num2--;
				switch(num2) {
				case 0: 
					if(this.gameStatus==2) num3 = 2;
					else num3 = 255;
					break;
				case 1: 
					if(this.gameStatus==2) num3 = 4;
					else num3 = 255;
					break;
				case 2: 
					if(this.gameStatus==2) num3 = 50;
					else num3 = 255;
					break;
				default: if(this.gameStatus==3)num3 = 255;
					break;
				}
				break;
			}
		}
		if(gameStatus==3) num2+=3;
		sliderHeights[num2] = y/(float)this.getHeight();
		settings[num2] = 1+(int)((((float)y-this.getHeight()*0.1)/(this.getHeight()*0.8))*num3);
	}
	
	//Draws menu used with clickMenus. Used to navigate menus throughout the game.
	public void drawMenu(Graphics g, String[] text, int mode) {
		for(int i=0; i<=text.length-1; i++) {
			if(mode==0)g.drawRect((int)(this.getWidth()*.1+1), (int)(this.getHeight()*((i+3)/10.0)+1), (int)(this.getWidth()*.3+1), (int)(this.getHeight()*.0625+1));
			g.drawString(text[i],(int)(this.getWidth()*.15+1), (int)(this.getHeight()*((i+3)/10.0+.05)));
		}
	}
	
	//Draws the sliders, used with sliderMenu. Used to adjust game settings and color settings.
	public void drawSliders(Graphics g) {
		String[] settingsText = {"Gamemode", "Players", "Grid Size", "R", "G", "B", "R", "G", "B"};
		int start = 0;
		int end = 3;
		int num2 = 6;
		if(this.gameStatus==3) {
			start = 3;
			end  = sliderHeights.length;
			num2 = 3;
		}
		int sliderWidth = (int)(this.getWidth()*0.4)/(this.sliderHeights.length-num2);
		for(int i = start; i<end; i++) {
			g.drawRect(this.getWidth()/2 + (i-start)*sliderWidth, (int)(sliderHeights[i]*this.getHeight()-(int)(this.getHeight()*0.0625)), sliderWidth, (int)(this.getHeight()*0.0625));
			g.drawString("   " + settingsText[i], this.getWidth()/2 + (i-start)*sliderWidth, (int)(sliderHeights[i]*this.getHeight()-(int)(this.getHeight()*0.0625)*3/8));
		}
	}
	
	//Draws a title in the upper left corner of a window.
	public void drawTitle(Graphics g, String title) {
		Font titleFont = new Font("Courier", Font.PLAIN, (int)(this.windowSize*.064));
		g.setFont(titleFont);
		g.drawString(title, (int)(this.getWidth()*.125+1), (int)(this.getHeight()*.1875+1));
	}
	
	//Finds if a piece exists on the board.
	public boolean findPiece(int piece) {
		for(int row = 0; row<board.length; row++) for(int col = 0; col<board[row].length; col++) if(board[row][col]==piece) return true;
		return false;
	}
	
	//Checks the pieces in the proximity of a point on the board.
	public List<Integer> checkProxy(int x, int y, int radius){
		List<Integer> proxyContents = new ArrayList<Integer>();
		for(int row = 0; row<board.length; row++) {
			for(int col = 0; col<board[row].length; col++) {
				if(x>=row-radius&&x<=row+radius&&y>=col-radius&&y<=col+radius&&board[row][col]!=0) proxyContents.add(board[row][col]);
			}
		}
		return proxyContents;
	}
	
	//Adds a char to the String at the specified index in the names[].
	public void addToNames(int index, String addition) {
		if(this.names[index].length()<17)this.names[index]+=addition;
	}
	//Removes a char to the String at the specified index in the names[].
	public void subtractToNames(int index) {
		if(this.names[index].length()>=4)this.names[index]=this.names[index].substring(0, this.names[index].length()-1);
	}
	
	//Enter names into leaderboard, if names already exist add on result of game.
	public void enterNamesToLeaderboard(int[] result) {
		for(int a = 0; a<leaderboard.size(); a++) {
			for(int b = 0; b<names.length; b++) {//FIX
				if(!names[b].substring(3).isBlank()&&result[b]!=2&&leaderboard.get(a).contains(names[b].substring(3))) {
					leaderboardScores.set(a, leaderboardScores.get(a)+result[b]);
					result[b]=2;
				}
			}
		}
		for(int c = 0; c<names.length; c++) {
			if(!names[c].substring(3).isBlank()&&result[c]!=2) {
				leaderboard.add(names[c].substring(3));
				leaderboardScores.add(result[c]);
			}
		}
	}
	
	//Sorts the leaderboard highest score is at the top.
	public void sortLeaderboard() {
		List<String> sortedLeaderboard = new ArrayList<String>();
		List<Integer> sortedLeaderboardScores = new ArrayList<Integer>();
		for(int x = 0; x<leaderboard.size(); x++) {
			int index = 0;
			for(int y = 0; y<leaderboardScores.size(); y++) {
				if(leaderboardScores.get(y)>leaderboardScores.get(index)) {
					index = y;
				}
			}
			sortedLeaderboard.add(leaderboard.get(index));
			sortedLeaderboardScores.add(leaderboardScores.get(index));
			leaderboard.remove(index);
			leaderboardScores.remove(index);
			x--;
		}
		this.leaderboard = sortedLeaderboard;
		this.leaderboardScores = sortedLeaderboardScores;
	}
	
	//Scroll through the leaderboard to view all scores.
	public void scrollLeaderboard(int mode) {
		List<String> tempLeaderboard = new ArrayList<String>();
		List<Integer> tempScores = new ArrayList<Integer>();
		switch(mode) {
		case -1: 
			for(int i = 0; i<leaderboard.size()-1; i++) {
				tempLeaderboard.add(leaderboard.get(i+1));
				tempScores.add(leaderboardScores.get(i+1));
			}
			tempLeaderboard.add(leaderboard.get(0));
			tempScores.add(leaderboardScores.get(0));
			break;
		case 1: 
			tempLeaderboard.add(leaderboard.get(leaderboard.size()-1));
			tempScores.add(leaderboardScores.get(leaderboardScores.size()-1));
			for(int i = 1; i<leaderboard.size(); i++) {
				tempLeaderboard.add(leaderboard.get(i-1));
				tempScores.add(leaderboardScores.get(i-1));
			}
			break;
		}
		this.leaderboard = tempLeaderboard;
		this.leaderboardScores = tempScores;
	}
	
	public int getTurn() {return this.turn;}
	public int[][] getBoard(){return this.board;}
	public void setBoard(int[][] newBoard) {this.board = newBoard;}
	public void setGameStatus(int newGameStatus) {this.gameStatus = newGameStatus;}
	public int getGameStatus() {return this.gameStatus;}
	public void setGameResult(String newResult) {this.gameResult = newResult;}
	public void setPlayers(int numPlayers) {this.numPlayers=numPlayers;}
	
	//Sets the info in this class to the information stored in the text file
	public void setInfo(List<String> newInfo) {
		int mode = 0;
		for(int i = 0; i<newInfo.size(); i++) {
			if(newInfo.get(i).equals("BREAK")) mode++;
			else {
				switch(mode) {
				case 0: 
					this.settings[i] = Integer.parseInt(newInfo.get(i));
					break;
				case 1: 
					this.sliderHeights[i-settings.length-1] = Float.parseFloat(newInfo.get(i));
					break;
				case 2: 
					this.leaderboard.add(i-settings.length-sliderHeights.length-2, newInfo.get(i));
					break;
				case 3:
					this.leaderboardScores.add(i-settings.length-sliderHeights.length-leaderboard.size()-3,Integer.parseInt(newInfo.get(i)));
					break;
				}
			}
		}
	}
	
	//Gets the info that will be save to a text file
	public List<String> getInfo(){
		List<String> info = new ArrayList<String>();
		int count = 0;
		while(count<leaderboardScores.size()+settings.length+sliderHeights.length+leaderboard.size())
			if(count<settings.length) {
				info.add(""+settings[count]);
				count++;
			}
			else if(count<sliderHeights.length+settings.length) {
				if(count==settings.length)info.add("BREAK");
				info.add(""+sliderHeights[count-settings.length]);
				count++;
			}
			else if(count<leaderboard.size()+settings.length+sliderHeights.length) {
				if(count==sliderHeights.length+settings.length)info.add("BREAK");
				info.add(leaderboard.get(count-settings.length-sliderHeights.length));
				count++;
			}
			else {
				if(count==leaderboard.size()+settings.length+sliderHeights.length)info.add("BREAK");
				info.add(""+leaderboardScores.get(count-settings.length-sliderHeights.length-leaderboard.size()));
				count++;
			}
		return info;
	}
	
	
	public int getNumPlayers() {return this.numPlayers;}
	
	public int getGamemode() {return this.settings[0];}
	public void setRates(int[] newRates) {this.rates=newRates;}
	
}