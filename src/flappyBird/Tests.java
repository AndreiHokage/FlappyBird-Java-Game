package flappyBird;


import java.io.FileWriter;
import java.io.IOException;

public class Tests {

	private String filename;
	
	/*
	 * The method tests the repo class and its methods
	 */
	private void test_repo() throws IOException {
		
		FileWriter myWriter=new FileWriter(filename);
		myWriter.write("0");
		myWriter.close();
		
		Repository repo=new Repository(filename);
		int bestscore=repo.get_bestscore();
		assert(bestscore==0);
		
		repo.updatescore(12);
		bestscore=repo.get_bestscore();
		assert(bestscore==12);
	}
	
	/*
	 * The method test the Settings class
	 */
	private void test_settings() {
		assert(Settings.SCOREPATH.equals("./src/bestScore"));
	}
	
	/*
	 * The method runs all the tests
	 */
	public void run_tests() throws IOException {
		test_repo();
		test_settings();
	}
	
	public Tests(String filename){
		this.filename=filename;
	}
	
}
