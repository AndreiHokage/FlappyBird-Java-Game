package flappyBird;

import java.io.FileWriter;
import java.io.IOException;

public class FlappyBirdGame {

	public static void main(String[] args) throws IOException {
		Tests tests=new Tests("./src/flappy_test");
		tests.run_tests();
		Repository repo=new Repository("./src/bestScore");
		UI console=new UI(repo);
	}
	
}
