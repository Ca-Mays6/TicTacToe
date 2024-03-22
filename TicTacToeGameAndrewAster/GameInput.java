import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class GameInput implements MouseListener, KeyListener {
	
	private Container pane;
	private int clickX;
	private int clickY;
	private int keyInput;
	private String leaderboardInput;
	
	public GameInput(Container pane) {
		this.pane = pane;
		JTextField JF = new JTextField();
		this.pane.addMouseListener(this);
		JF.addKeyListener(this);
		this.pane.add(JF);
		this.clickX = -1;
		this.clickY = -1;
		this.keyInput = -1;
		this.leaderboardInput = "";
	}	
	public void mouseClicked(MouseEvent m) {
		this.clickX = m.getX();
		this.clickY = m.getY();
	}
	public void mouseExited(MouseEvent m) {}
	public void mouseReleased(MouseEvent m) {}
	public void mouseEntered(MouseEvent m) {}
	public void mousePressed(MouseEvent m) {}
	public void keyPressed(KeyEvent k) {}
	public void keyReleased(KeyEvent k) {
		this.keyInput = k.getKeyCode();
		this.leaderboardInput = (k.getKeyChar())+"";
	}
	public void keyTyped(KeyEvent k) {}
	public int getClickX() {return this.clickX;}
	public int getClickY() {return this.clickY;}
	public int getKeyInput() {return this.keyInput;}
	public String getKeyCharInput() {return this.leaderboardInput;}
	public boolean isWriteable() {
		return (this.getKeyInput()>=32&&this.getKeyInput()<=126);
	}
	public void resetMouse() {
		this.clickX = -1;
		this.clickY = -1;
	}
	public void resetKey() {
		this.keyInput = -1;
		this.leaderboardInput = "";
	}
	
}
