package flappyBird;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Settings {

	private static Properties prop=new Properties();
	
	static {
		try {
			FileInputStream ip=new FileInputStream("./src/config.properties");
			prop.load(ip);
		}catch(IOException e) {
			
		}
	}
	
	public static final int WINDOW_WIDTH=Integer.valueOf(prop.getProperty("window.width"));
	public static final int WINDOW_HEIGHT=Integer.valueOf(prop.getProperty("window.height"));
	public static final int GROUND_HEIGHT=Integer.valueOf(prop.getProperty("ground.height"));
	public static final int GRASS_HEIGHT=Integer.valueOf(prop.getProperty("grass.height"));
	public static final int COLUMN_WIDTH=Integer.valueOf(prop.getProperty("column.width"));
	public static final int MIN_HEIGHT_COLUMN=Integer.valueOf(prop.getProperty("column.min_height_column"));
	public static final int FREE_SPACE=Integer.valueOf(prop.getProperty("column.free_space"));
	public static final int DBC=Integer.valueOf(prop.getProperty("column.distance_between_columns"));
	public static final int WVX=Integer.valueOf(prop.getProperty("window.velocityX"));
	public static final int BVU=Integer.valueOf(prop.getProperty("bird.up_velocity"));
	public static final int BVD=Integer.valueOf(prop.getProperty("bird.down_velocity"));
	public static final String TESTFILEPATH=prop.getProperty("testingfile.path");
	public static final String SCOREPATH=prop.getProperty("bestscorefile.path");
	public static final String IMGUNDO_PATH=prop.getProperty("imgundo.path");
	public static final String IMGPAUSE_PATH=prop.getProperty("imgpause.path");
}
