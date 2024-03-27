import java.util.ArrayList;
import java.util.List;

public class GameChecker {
	
	private int[][] board;
	
	public GameChecker(int boardSize) {
		this.board = new int[boardSize][boardSize];
	}
	
	public GameChecker(int[][] board) {
		this.board = board;
	}
	
	//Gets the current board as a 2D int array.
	public int[][] getBoard() {return this.board;}
	
	//Sets the board to a new 2D int array.
	public void setBoard(int[][] newBoard) {this.board = newBoard;}
	
	//Outputs and array of ints containing all the column lines. These lines have been checked using checkList method.
	public int[] checkColumn() {
		int[] result = new int[board.length];
		List<Integer> colContents = new ArrayList<Integer>();
		for(int row = 0; row<board.length; row++) {
			for(int col = 0; col<board[row].length; col++) {
				if(!colContents.contains(board[row][col]))colContents.add(board[row][col]);
			}
			result[row] = this.checkList(colContents);
			colContents = new ArrayList<Integer>();
		}
		return result;
	}
	
	//Outputs and array of ints containing all the row lines. These lines have been checked using checkList method.
	public int[] checkRow() {
		int[] result = new int[board.length];
		List<Integer> rowContents = new ArrayList<Integer>();
		for(int col = 0; col<board[0].length; col++) {
			for(int row = 0; row<board.length; row++) {
				if(!rowContents.contains(board[row][col]))rowContents.add(board[row][col]);
			}
			result[col] = this.checkList(rowContents);
			rowContents = new ArrayList<Integer>();
		}
		return result;
	}
	
	//Outputs and array of ints containing all the diagonal lines. These lines have been checked using checkList method.
	public int[] checkDiagonal() {
		int[] result = new int[2];
		List<Integer> diagonalContents = new ArrayList<Integer>();
		for(int left = 0; left<board.length; left++) if(!diagonalContents.contains(board[left][left])) diagonalContents.add(board[left][left]);
		result[0] = this.checkList(diagonalContents);
		diagonalContents = new ArrayList<Integer>();
		for(int right = board.length-1; right>=0; right--) if(!diagonalContents.contains(board[right][board.length-1-right])) diagonalContents.add(board[right][board.length-1-right]);
		result[1] = this.checkList(diagonalContents);
		return result;
	}
	
	//Checks line for validity. If a line is empty or contains one piece the line is valid. If the line contains one piece for the entire line the line is a winner. If the line contains multiple pieces the line is a draw.
	public int checkList(List<Integer> list) {
		if((list.size()>2&&list.contains(0))||(list.size()>1&&!list.contains(0))) return -1;
		if(list.size()==1) {
			switch(list.get(0)) {
			case 0: return 0;
			case 1: return 1;
			case 2: return 2;
			case 3: return 3;
			case 4: return 4;
			}
		}
		return 0;
	}
	
	//Checks the board for number of pieces each player has. Used in conquest to determine if a player is dead.
	public int[] checkBoard(int players){
		int[] result = new int[players];
		for(int row = 0; row<board.length; row++) {
			for(int col = 0; col<board[row].length; col++) {
				if(board[row][col]>0) result[board[row][col]-1]++;
			}
		}
		return result;
	}
	
	//Checks if classic tic-tac-toe is valid. valid = 0, draw = -1, winner = 1-4
	public int checkClassic() {
		int draw = 0; 
		for(int x = 0; x<this.board.length; x++) {
			if(this.checkRow()[x]<0)draw++;
			else if(this.checkRow()[x]!=0) return this.checkRow()[x];
			if(this.checkColumn()[x]<0) draw++;
			else if(this.checkColumn()[x]!=0) return this.checkColumn()[x];
		}
		for(int  y = 0; y<2; y++) {
			if(this.checkDiagonal()[y]<0) draw++;
			else if(this.checkDiagonal()[y]!=0) return this.checkDiagonal()[y];
		}
		if(draw>=this.board.length*2+2) return -1;
		return 0;
	}
	
	//Checks if conquest is valid. valid = 0, winner = 1-4.
	public int checkConquest(int players) {
		int index = 0;
		int count = 0; 
		for(int i = 0; i<this.checkBoard(players).length; i++) {
			if(this.checkBoard(players)[i]>0) {
				count++;
				index = i;
			}
		}
		if(count==1) return index+1;
		else return 0;
	}
	
	//Gets the winner of a game as a string.
	public String getWinner(int players, int mode) {
		int num = this.checkClassic();
		if(mode==2) num=this.checkConquest(players);
		switch(num) {
		case -1: return "Draw";
		case 1: return "X Wins";
		case 2: return "O Wins";
		case 3: return "Plus Wins";
		case 4: return "Square Wins";
		}
		return "Playing";
	}
	
	//Gets the points that each player receives after a game. Winner = 1, Losers = -1, Draw = 0.
	public int[] getWinnerInt(int players, int mode) {
		int[] result = new int[players];
		int num = this.checkClassic();
		if(mode==2) num=this.checkConquest(players);
		if(num==-1) return result;
		for(int i = 0; i<result.length; i++) {
			if(i==num-1) result[i] = 1;
			else result[i] = -1;
		}
		return result;
	}
	
	//Sets the resource rates for the conquest gamemode.
	public int[] getRates(int players) {
		int[] result = new int[players];
		for(int a = 0; a<this.checkBoard(players).length; a++) if(this.checkBoard(players)[a]==0) result[a]=-1;
		for(int b = 0; b<board.length; b++) {
			if(this.checkRow()[b]>0) result[this.checkRow()[b]-1]++;
			if(this.checkColumn()[b]>0) result[this.checkColumn()[b]-1]++;
		}
		for(int c = 0; c<2; c++) if(this.checkDiagonal()[c]>0) result[this.checkDiagonal()[c]-1]++;
		for(int x = 0; x<players; x++) result[x]++;
		return result;
	}
	
}
