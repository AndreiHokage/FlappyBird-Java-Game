package flappyBird;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Repository {
	
	private String filename;
	
	private int bestscore=0;
	
	/*
	 * The method loads the data from file in memoty
	 * The method throws FileNotFoundException exception
	 */
	private void loadfromfile() throws FileNotFoundException {
		
		File file=new File(filename);
		Scanner myReader=new Scanner(file);
		String data=myReader.nextLine();
		
		bestscore=Integer.parseInt(data);
		myReader.close();
	}
	
	/*
	 * The method stores the data from memory to file
	 * The method throws IOException exception
	 */
	private void storetofile() throws IOException {
		FileWriter myWriter=new FileWriter(filename);
		myWriter.write(String.valueOf(bestscore));
		myWriter.close();
	}
	
	/*
	 * The method update the score and store to file
	 * Input:score-(integer type) a new score
	 * Output:update and save the score
	 */
	public void updatescore(int score) throws IOException {
		bestscore=score;
		storetofile();
	}
	
	/*
	 * The method return the bestscore store in the file
	 */
	public int get_bestscore() {
		return bestscore;
	}
	
	public Repository(String filename) throws FileNotFoundException
	{
		this.filename=filename;
		loadfromfile();
	}
	
}
