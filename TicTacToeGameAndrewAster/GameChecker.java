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
	
	public int[][] getBoard() {return this.board;}
	public boolean canMove(int row, int col) {
		if(this.getBoard()[row][col]!=0) return false;
		else return true;
	}
	public void setBoard(int[][] newBoard) {
		this.board = newBoard;
	}
	public int checkColumn(int mode) {
		List<Integer> colContents = new ArrayList<Integer>();
		int result;
		if(mode==0) result = this.getBoard()[0].length;
		else result=0;
		for(int row = 0; row<this.getBoard().length; row++) {
			for(int col = 0; col<this.getBoard()[row].length; col++) {
				if(!colContents.contains(this.getBoard()[row][col])) colContents.add(this.getBoard()[row][col]);
			}
			result+=this.checkList(colContents, mode);
			colContents = new ArrayList<Integer>();
		}
		return result;
	}
	public int checkRow(int mode) {
		List<Integer> rowContents = new ArrayList<Integer>();
		int result;
		if(mode==0) result = this.getBoard().length;
		else result = 0;
		for(int col = 0; col<this.getBoard()[0].length; col++) {
			for(int row = 0; row<this.getBoard().length; row++) if(!rowContents.contains(this.getBoard()[row][col])) rowContents.add(this.getBoard()[row][col]);
			result+=this.checkList(rowContents, mode);
			rowContents = new ArrayList<Integer>();
		}
		return result;
	}
	public int checkDiagonal(int mode) {
		List<Integer> diagonalContents = new ArrayList<Integer>();
		int result;
		if(mode==0) result = 2;
		else result = 0;
		for(int left = 0; left<this.getBoard().length; left++) if(!diagonalContents.contains(this.getBoard()[left][left])) diagonalContents.add(this.getBoard()[left][left]);
		result+=this.checkList(diagonalContents, mode);
		diagonalContents = new ArrayList<Integer>();
		for(int right = this.getBoard().length-1; right>=0; right--) if(!diagonalContents.contains(this.getBoard()[this.getBoard().length-1-right][right])) diagonalContents.add(this.getBoard()[this.getBoard().length-1-right][right]);
		result+=this.checkList(diagonalContents, mode);
		return result;
	}
	public int checkList(List<Integer> list, int mode) {
		if(mode==0) if((list.size()>2&&list.contains(0))||(list.size()>1&&!list.contains(0))) return -1;
		if(list.size()==1) {
			switch(list.get(0)) {
			case 1: return 1;
			case 2: return 2;
			case 3: return 3;
			case 4: return 4;
			}
		}
		return 0;
	}
	public boolean isGameValid() {
		return this.checkColumn(0)+this.checkRow(0)+this.checkDiagonal(0)>0&&this.checkColumn(1)+this.checkRow(1)+this.checkDiagonal(1)==0;
	}
	public String checkGame() {
		if(this.checkColumn(0)+this.checkRow(0)+this.checkDiagonal(0)==0) return "Draw";
		switch(this.checkColumn(1)+this.checkRow(1)+this.checkDiagonal(1)) {
		case 1: return "X Wins";
		case 2: return "O Wins";
		case 3: return "Plus Wins";
		case 4: return "Square Wins";
		default: return "Playing";
		}
	}
	public int[] checkGameInt(int players) {
		int[] result = new int[players];
		if(this.checkColumn(0)+this.checkRow(0)+this.checkDiagonal(0)==0) return result;
		int winner = this.checkColumn(1)+this.checkRow(1)+this.checkDiagonal(1);
		if(winner>4||winner<1) return result;
		for(int i = 0; i<players; i++) {
			result[i] = -1;
			if((i+1)==winner) result[i] = 1;
		}
		return result;
	}
}
