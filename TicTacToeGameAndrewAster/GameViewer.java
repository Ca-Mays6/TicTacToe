import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class GameViewer extends JPanel {
	
	private static final long serialVersionUID = 7247732440388695869L;
	private int[][] board;
	private String currentTurn;
	private int widthRemainder;
	private int heightRemainder;
	private int turn;
	private Color foreground;
	private Color background;
	private int numPlayers;
	private int gameStatus;
	private int windowSize;
	private int gridSize;
	private int gridOffset;
	private String gameResult;
	private int[] settings;
	private float[] sliderHeights;
	private List<String> leaderboard;
	private String names[];
	
	public GameViewer() {
		this.background = Color.black;
		this.setBackground(this.background);
		this.foreground = Color.white;
		this.board = new int[3][3];
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
		this.names = new String[this.numPlayers];
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(background);
		g.setColor(foreground);
		Font font = new Font("Courier", Font.PLAIN, (int)(this.windowSize*.032));
		g.setFont(font);
		String[] startMenuText = {"Start", "Game Options", "Color Options", "Leaderboards", "Exit"}; 
		String[] pauseMenuText = {"Resume", "Leaderboards", "Return To Menu", "Exit"};
		String[] infoPanelText = {"Game Status: " + this.gameResult, "Gamemode: Classic", this.currentTurn};
		String[] endMenuText = {"Game Result: "+this.gameResult, "Gamemode: Classic", "Save Scores", "Return To Menu", "Exit"};
		this.updateInfo();
		switch(this.getGameStatus()) {
		case 0: 
			this.turn = 0;
			this.board = new int[board.length][board.length];
			this.drawMenu(g, startMenuText, 0);
			this.drawTitle(g, "Tic-Tac-Toe");
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
			this.drawMenu(g, this.leaderboard.toArray(new String[leaderboard.size()]), 1);
			this.drawTitle(g, "Leaderboard");
			break;
		case 5: 
			this.drawMenu(g, infoPanelText, 1);
			this.drawTitle(g, "Info");
			break;
		case 6: 
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
			int num = 0;
			int num2 = 0;
			for(int i = 0; i<this.numPlayers; i++) {
				if(i>=this.board.length) {
					num++;
					num2=this.numPlayers-i;
				}
				switch(i+1) {
				case 1: this.drawX(g, (i-num2)*gridSize+widthRemainder, num*gridSize+heightRemainder);
					break;
				case 2: this.drawO(g, (i-num2)*gridSize+widthRemainder, num*gridSize+heightRemainder);
					break;
				case 3: this.drawP(g, (i-num2)*gridSize+widthRemainder, num*gridSize+heightRemainder);
					break;
				case 4: this.drawS(g, (i-num2)*gridSize+widthRemainder, num*gridSize+heightRemainder);
					break;
				}
			}
			break;
		default: 
			break;
		}
	}
	public void drawGrid(Graphics g) {
		g.drawRect(widthRemainder, heightRemainder, windowSize, windowSize);
		for(int grid = 0; grid<board.length; grid++) {
			g.drawLine(grid*gridSize+widthRemainder, heightRemainder, grid*gridSize+widthRemainder, windowSize+heightRemainder);
			g.drawLine(widthRemainder, grid*gridSize+heightRemainder, windowSize+widthRemainder, grid*gridSize+heightRemainder);
		}
	}
	public void fillGrid(Graphics g) {
		for(int row = 0; row<board.length; row++) {
			for(int col = 0; col<board[row].length; col++) {
				switch(this.board[row][col]) {
				case 1: this.drawX(g, row*gridSize+widthRemainder, col*gridSize+heightRemainder);
					break;
				case 2: this.drawO(g, row*gridSize+widthRemainder, col*gridSize+heightRemainder);
					break;
				case 3: this.drawP(g, row*gridSize+widthRemainder, col*gridSize+heightRemainder);
					break;
				case 4: this.drawS(g, row*gridSize+widthRemainder, col*gridSize+heightRemainder);
				}
			}
		}
	}
	public void drawX(Graphics g, int x1, int y1) {
		g.drawLine(x1+this.gridOffset, y1+this.gridOffset, this.gridSize+x1-this.gridOffset, this.gridSize+y1-this.gridOffset);
		g.drawLine(this.gridSize+x1-this.gridOffset, y1+this.gridOffset, x1+this.gridOffset, this.gridSize+y1-this.gridOffset);
	}
	public void drawO(Graphics g,int x1 ,int y1) {
		g.drawOval(x1+this.gridOffset,y1+this.gridOffset,this.gridSize-this.gridOffset*2,this.gridSize-this.gridOffset*2);
	}
	public void drawP(Graphics g,int x1 ,int y1) {
		g.drawLine(x1+this.gridOffset, (y1+(this.gridSize+y1))/2, this.gridSize+x1-this.gridOffset, (y1+(this.gridSize+y1))/2);
		g.drawLine((x1+(this.gridSize+x1))/2, y1+this.gridOffset, (x1+(this.gridSize+x1))/2, this.gridSize+y1-this.gridOffset);
	}
	public void drawS(Graphics g,int x1 ,int y1) {
		g.drawRect(x1+this.gridOffset,y1+this.gridOffset,this.gridSize-this.gridOffset*2,this.gridSize-this.gridOffset*2);
	}
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
	}
	public void setPos(int x, int y) {
		x = (int)Math.floor((x-widthRemainder)/(double)gridSize);
		y = (int)Math.floor((y-heightRemainder)/(double)gridSize);
		if(x>=0&&x<board.length&&y>=0&&y<board.length) if(board[x][y]==0) this.board[x][y] = this.turn+=1;
	}
	public void clickMenu(int x, int y, int start, int[] options, int mode) {
		for(int i = start; i<options.length+start; i++) {
			if(mode==0)if((x>this.getWidth()*(0.1)+1)&&x<this.getWidth()*(0.4)+1&&y>this.getHeight()*(i+2.0)/10+1&&y<this.getHeight()*(i+2.625)/10+1) {
				this.setGameStatus(options[i-start]);
				break;
			}
			if(mode==1)if((x>this.getWidth()*(i*1.0)/10+1)&&x<this.getWidth()*(i+0.4)/10+1&&y>this.getHeight()*5/10+1&&y<this.getHeight()*5.625/10+1) {
				this.setGameStatus(options[i-start]);
				break;
			}
		}
	}
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
					if(this.gameStatus==2) num3 = 1;
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
	public void drawSliders(Graphics g) {
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
		}
	}
	public void drawMenu(Graphics g, String[] text, int mode) {
		for(int i=0; i<=text.length-1; i++) {
			if(mode==0)g.drawRect((int)(this.getWidth()*.1+1), (int)(this.getHeight()*((i+3)/10.0)+1), (int)(this.getWidth()*.3+1), (int)(this.getHeight()*.0625+1));
			g.drawString(text[i],(int)(this.getWidth()*.15+1), (int)(this.getHeight()*((i+3)/10.0+.05)));
		}
	}
	public void drawTitle(Graphics g, String title) {
		Font titleFont = new Font("Courier", Font.PLAIN, (int)(this.windowSize*.064));
		g.setFont(titleFont);
		g.drawString(title, (int)(this.getWidth()*.125+1), (int)(this.getHeight()*.1875+1));
	}
	public int getTurn() {return this.turn;}
	public int[][] getBoard(){return this.board;}
	public void setBoard(int[][] newBoard) {this.board = newBoard;}
	public void setGameStatus(int newGameStatus) {this.gameStatus = newGameStatus;}
	public int getGameStatus() {return this.gameStatus;}
	public void setGameResult(String newResult) {this.gameResult = newResult;}
	public void setPlayers(int numPlayers) {this.numPlayers=numPlayers;}
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
				}
			}
		}
	}
	public List<String> getInfo(){
		List<String> info = new ArrayList<String>();
		int count = 0;
		while(count<settings.length) {
			info.add(""+settings[count]);
			count++;
		}
		info.add("BREAK");
		while(count<sliderHeights.length+settings.length) {
			info.add(""+sliderHeights[count-settings.length]);
			count++;
		}
		info.add("BREAK");
		while(count<leaderboard.size()+settings.length+sliderHeights.length) {
			info.add(leaderboard.get(count-settings.length-sliderHeights.length));
			count++;
		}
		return info;
	}
	public void scrollLeaderboard(int mode) {
		List<String> tempLeaderboard = new ArrayList<String>();
		switch(mode) {
		case -1: 
			for(int i = 0; i<leaderboard.size()-1; i++) tempLeaderboard.add(leaderboard.get(i+1));
			tempLeaderboard.add(leaderboard.get(0));
			break;
		case 1: 
			tempLeaderboard.add(leaderboard.get(leaderboard.size()-1));
			for(int i = 1; i<leaderboard.size(); i++) tempLeaderboard.add(leaderboard.get(i-1));
			break;
		}
		this.leaderboard = tempLeaderboard;
	}
	public void addToNames(int index, String addition) {
		if(this.names[index].length()<17)this.names[index]+=addition;
	}
	public void subtractToNames(int index) {
		if(this.names[index].length()>=4)this.names[index]=this.names[index].substring(0, this.names[index].length()-1);
	}
	public int getNumPlayers() {return this.numPlayers;}
	public void enterNamesToLeaderboard(int[] result) {
		for(int a = 0; a<leaderboard.size(); a++) {
			for(int b = 0; b<names.length; b++) {
				if(!names[b].substring(3).isBlank()&&leaderboard.get(a).contains(names[b].substring(3))) {
					int num = Integer.parseInt(leaderboard.get(a).substring(leaderboard.get(a).length()-1));
					int num2 = 1;
					if(leaderboard.get(a).contains("-")) {
						num*=-1;
						num2++;
					}
					leaderboard.set(a, leaderboard.get(a).substring(0,leaderboard.get(a).length()-num2)+(num+result[b]));
					result[b] = 2;
				}
			}
		}
		for(int c = 0; c<names.length; c++) {
			if(!names[c].substring(3).isBlank()&&result[c]!=2) {
				leaderboard.add(names[c].substring(3) + "  " + result[c]);
			}
		}
	}
}