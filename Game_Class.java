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

  public static String CheckBoard(ArrayList<ArrayList<String>> board){

		String returnStatement = "No winner";
		
    return returnStatement;
  }

  public static boolean CheckWin(ArrayList<ArrayList<String>> board, String selection){

	// Convert the selection to uppercase
	selection = selection.toUpperCase();

	// Make sure the selection is valid 
	if(!(selection.equals("X") || selection.equals("O"))) return false;  

	// Check for a full row
  	for(int i = 0; i < game.size(); i++){ 
    		
    	// Get the row
    	ArrayList<String> row = game.get(i);
    	int streak = 0;
    		
    	// Loop through the row
    	for(int v = 0; v < row.size(); v++){
    			
    		// Check if string equals the selection
    		if(row.get(v).equals(selection)){ 
    			streak++;
    		}
    	}
    		
    	// Check if the row is a winning pattern
    	if(streak == row.size()) return true;
		}
	}

	// Check for a full column
    for(int i = 0; i < game.get(0).size(); i++){
    		
    	int streak = 0;
    		
    	// Loop through each column
    	for(int v = 0; v < game.size(); v++){
    			
    		// Get the column
    		ArrayList<String> column = game.get(v);
    			
    		// Check if string equals the selection
    		if(column.get(i).equals(selection)){
    			streak++;
    		}
    	}
    		
    	// Check if the row is a winning pattern
    	if(streak == game.size()){
    		return true;
    	}
    }

	// Check for a right diagonal
	int streakR = 0;
	for(int i = 0; i < game.size(); i++){
    		
    	// Check if string equals the selection
    	if(game.get(i).get(i).equals(selection)){
    		streakR++;
    	}
    }
    	
    // Check if the diagonal is a winning pattern
    if(streakR == game.size()){
    	return true;
    }
    	
    // Check for a left diagonal
    int streakL = 0;
    for(int i = game.size() - 1; i >= 0; i--){
    		
    	// Check if string equals the selection
    	if(game.get(i).get(i).equals(selection)){
    		streakL++;
    	}
    }
    	
    // Check if the diagonal is a winning pattern
    if(streakL == game.size()){
    	return true;
    }
    	
    return false;
}
