package flappyBird;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Repository {
	
	private String filename;
	
	private int bestscore=0;
	
	private void loadfromfile() throws FileNotFoundException {
		
		File file=new File(filename);
		Scanner myReader=new Scanner(file);
		String data=myReader.nextLine();
		
		bestscore=Integer.parseInt(data);
		myReader.close();
	}
	
	private void storetofile() throws IOException {
		FileWriter myWriter=new FileWriter(filename);
		myWriter.write(String.valueOf(bestscore));
		myWriter.close();
	}
	
	public void updatescore(int score) throws IOException {
		bestscore=score;
		storetofile();
	}
	
	public int get_bestscore() {
		return bestscore;
	}
	
	public Repository(String filename) throws FileNotFoundException
	{
		this.filename=filename;
		loadfromfile();
	}
	
}
