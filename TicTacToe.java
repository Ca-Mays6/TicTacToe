import javax.swing.*;
java.awt.*;



public class Main 
{

  public GUI()
    {
  JFrame frame = new JFrame("Game Board");
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
  
      
      frame.pack();
      frame.setVisible(true);
  }


  
  public static void main(String[] args) 
  {
    System.out.println("Hello World");
    new GUI();
  }
}
