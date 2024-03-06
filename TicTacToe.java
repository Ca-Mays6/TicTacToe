import javax.swing.*;
import java.awt.*;

public class GameViewer extends JPanel {
	
	private int[][] board;
	private int gridWidth;
	private int gridHeight;
	
	public GameViewer(Color backColor, int[][] board) {
		this.setBackground(backColor);
		this.board = board;
		this.gridWidth = this.getWidth()/board.length;
		this.gridHeight = (int)(this.getHeight()*.9)/board.length;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		this.drawGrid(g);
		g.drawString("Info Panel",(int)(this.getWidth()*.05)-1 ,(int)(this.getHeight()*.95)-1);
		this.updateGridSize();
		this.drawX(g, 0,0,this.gridWidth,this.gridHeight);
	}
	public void drawGrid(Graphics g) {
		this.updateGridSize();
		g.drawRect(0,0,this.getWidth()-1, this.getHeight()-1);
		g.drawRect(0,0,(int)(this.getWidth()*1)-1, (int)(this.getHeight()*.9)-1);
		for(int i =0; i<this.getWidth()-(1+this.gridWidth); i+=this.gridWidth) g.drawLine(i,0,i, (int)(this.getHeight()*.9)-1);
		for(int l =0; l<(int)(this.getHeight()*.9)-(1+this.gridHeight); l+=this.gridHeight) g.drawLine(0,l,this.getWidth()-1, l);
	}
	public void drawX(Graphics g, int x1, int y1, int x2, int y2) {
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(x2, y1, x1, y2);
	}
	public void updateGridSize() {
		this.gridWidth = this.getWidth()/board.length;
		this.gridHeight = (int)(this.getHeight()*.9)/board.length;
	}
}
