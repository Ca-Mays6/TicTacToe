import javax.swing.*;
import java.awt.*;

public class GameViewer extends JPanel {
	
	private int[][] board;
	private int gridWidth;
	private int gridHeight;
	private String info;
	private int widthRemainder;
	private int heightRemainder;
	private int widthOffset;
	private int heightOffset;
	
	public GameViewer(Color backColor, int[][] board) {
		this.setBackground(backColor);
		this.board = board;
		this.gridWidth = 0;
		this.gridHeight = 0;
		this.widthRemainder = 0;
		this.heightRemainder = 0;
		this.widthOffset = 0;
		this.heightOffset = 0;
		this.info = "";
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.green);
		this.updateGridSize();
		this.drawGrid(g);
		this.drawInfoPanel(g);
		this.fillGrid(g);
	}
	public void drawGrid(Graphics g) {
		for(int w = widthRemainder; w<this.getWidth()-(1); w+=this.gridWidth) g.drawLine(w,heightRemainder,w,(int)Math.round(this.getHeight()*.9)-(1+this.heightRemainder));
		for(int h = heightRemainder; h<Math.round(this.getHeight()*.9)-(1); h+=this.gridHeight) g.drawLine(widthRemainder,h,this.getWidth()-(1+this.widthRemainder),h);
		g.drawLine(this.widthRemainder, this.heightRemainder,this.getWidth() - this.widthRemainder, this.heightRemainder);
		g.drawLine(this.widthRemainder, this.heightRemainder,this.widthRemainder, this.getHeight() - this.heightRemainder);
		g.drawLine(this.getWidth()-(this.widthRemainder), this.getHeight()-(this.heightRemainder),this.widthRemainder, this.getHeight()-(this.heightRemainder));
		g.drawLine(this.getWidth()-(this.widthRemainder), this.getHeight()-(this.heightRemainder),this.getWidth() - (this.widthRemainder), this.heightRemainder);
		g.drawLine(this.widthRemainder, (int)Math.round(this.getHeight()*.9)-(1+this.heightRemainder), this.getWidth() - (1+this.widthRemainder), (int)Math.round(this.getHeight()*.9)-(1+this.heightRemainder));
	}
	public void fillGrid(Graphics g) {
		for(int row = 0; row<this.board.length; row++) {
			for(int col = 0; col<this.board[row].length; col++) {
				int shapeWidth = row*this.gridWidth+this.widthRemainder;
				int shapeHeight = col*this.gridHeight+this.heightRemainder;
				switch(board[row][col]) {
				case 1: this.drawX(g, shapeWidth, shapeHeight);
				break;
				case 2: this.drawO(g, shapeWidth, shapeHeight);
				break;
				case 3: this.drawP(g, shapeWidth, shapeHeight);
				break;
				case 4: this.drawS(g, shapeWidth, shapeHeight);
				break;
				default: break;
				}
			}
		}
	}
	public void drawX(Graphics g, int x1, int y1) {
		g.drawLine(x1+widthOffset, y1+heightOffset, this.gridWidth+x1-widthOffset, this.gridHeight+y1-heightOffset);
		g.drawLine(this.gridWidth+x1-widthOffset, y1+heightOffset, x1+widthOffset, this.gridHeight+y1-heightOffset);
	}
	public void drawO(Graphics g,int x1 ,int y1) {
		g.drawOval(x1+widthOffset,y1+heightOffset,this.gridWidth-widthOffset*2,this.gridHeight-heightOffset*2);
	}
	public void drawP(Graphics g,int x1 ,int y1) {
		g.drawLine(x1+widthOffset, (y1+(this.gridHeight+y1))/2, this.gridWidth+x1-widthOffset, (y1+(this.gridHeight+y1))/2);
		g.drawLine((x1+(this.gridWidth+x1))/2, y1+heightOffset, (x1+(this.gridWidth+x1))/2, this.gridHeight+y1-heightOffset);
	}
	public void drawS(Graphics g,int x1 ,int y1) {
		g.drawRect(x1+widthOffset,y1+heightOffset,this.gridWidth-widthOffset*2,this.gridHeight-heightOffset*2);
	}
	public void drawInfoPanel(Graphics g) {
		Font font = new Font("Courier", Font.PLAIN, 16);
		g.setFont(font);
		g.drawString(("     " + this.info),widthRemainder ,(int)(this.getHeight()*.96));
	}
	public void updateGridSize() {
		this.gridWidth = this.getWidth()/board.length;
		this.gridHeight = (int)(this.getHeight()*.9)/board.length;
		this.widthRemainder = (this.getWidth()%board.length)/2;
		this.heightRemainder = ((int)(this.getHeight()*.9)%board.length)/2;
		this.widthOffset = (int)(this.gridWidth*.2);
		this.heightOffset = (int)(this.gridHeight*.15);
	}
	public void setBoard(int[][] newBoard) {this.board = newBoard;}
	public void setInfo(String newInfo) {this.info = newInfo;}
}
