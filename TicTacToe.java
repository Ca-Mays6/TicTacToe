import javax.swing.*;
java.awt.*;



public class TicTacToe extends JPanel
{

   public TictacToe()
    {
    	JFrame frame = new JFrame("TicTacToe");
    	JPanel panel = new JPanel();
    	JButton b1 = new JButton("");
    	JButton b2 = new JButton("");
    	JButton b3 = new JButton("");
    	JButton b4 = new JButton("");
    	JButton b5 = new JButton("");
    	JButton b6 = new JButton("");
    	JButton b7 = new JButton("");
    	JButton b8 = new JButton("");
    	JButton b9 = new JButton("");
    	panel.setBorder(BorderFactory.createEmptyBorder());
    	panel.setLayout(new GridLayout(3,3));
    	panel.add(b1);
    	panel.add(b2);
    	panel.add(b3);
    	panel.add(b4);
    	panel.add(b5);
    	panel.add(b6);
    	panel.add(b7);
    	panel.add(b8);
    	panel.add(b9);
    	frame.add(panel, BorderLayout.CENTER);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
   
    	
    	frame.pack();
    	frame.setVisible(true);
    }
    


  
  public static void main(String[] args) 
  {

    new TicTacToe();
  }
}
