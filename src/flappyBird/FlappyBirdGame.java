package flappyBird;

import java.io.FileWriter;
import java.io.IOException;

public class FlappyBirdGame {

	public static void main(String[] args) throws IOException {
		Tests tests=new Tests(Settings.TESTFILEPATH);
		tests.run_tests();
		Repository repo=new Repository(Settings.SCOREPATH);
		UI console=new UI(repo);
	}
	
}
