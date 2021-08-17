package flappyBird;


import java.io.FileWriter;
import java.io.IOException;

public class Tests {

	private String filename;
	
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
	
	public void run_tests() throws IOException {
		test_repo();
	}
	
	public Tests(String filename){
		this.filename=filename;
	}
	
}
