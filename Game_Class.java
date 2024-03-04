import java.io.*;
import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Game{

  // Game variables
  int boardSize = 3;

  public static void Game(int size){

    // The size of the board
    boardSize = size;

    // The board of itself
    ArrayList<ArrayList<String>> board = new ArrayList<ArrayList<String>>();
  }

  // || BOARD PLACEMENT ||
  
  public static boolean UpdateBoard(int row, int col, String symbol){

    // Make sure it is a valid placement
    if(!CheckValidPlacement(row, col, symbol)) return false;

    // Place the symbol
    
    // Let the caller know the symbol was placed on the board
    return true;
  }

  public static boolean CheckValidPlacement(int row, int col, String symbol){

    return true;
  }

  // || WIN CONDITIONS ||
}
