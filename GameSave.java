import java.util.List; 
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class GameSave {
	
	private List<String> info;
	private File file;
	
	public GameSave() {
		this.info = new ArrayList<String>();
		this.file = new File("TicTacToeSavedInfo.txt");
	}
	
	//Reads a text file and converts it to an array
	public void readFileContents() {
		try {
			Scanner fileReader = new Scanner(this.file);
			while(fileReader.hasNextLine()) {
				this.info.add(fileReader.nextLine());
			}
			fileReader.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Writes the info to the text file
	public void writeFileContents() {
		try {
			FileWriter saver = new FileWriter(this.file);
			for(int i = 0; i<this.info.size(); i++) {
				if(i>0) saver.write("\n");
				saver.write(this.info.get(i));
			}
			saver.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//Gets the info ArrayList
	public List<String> getSavedInfo(){return this.info;}
	
	//Sets the info ArrayList
	public void setSavedInfo(List<String> newInfo) {this.info=newInfo;}

}
