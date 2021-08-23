package flappyBird;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

import java.awt.Toolkit;


public class FlappyBird implements ActionListener,MouseListener {

	private Repository repo;
	
	private JButton pause_button;
	
	public static FlappyBird flappyBird;
	
	private final int WIDTH=Settings.WINDOW_WIDTH,HEIGHT=Settings.WINDOW_HEIGHT,HEIGHT_GROUND=Settings.GROUND_HEIGHT,HEIGHT_GRASS=Settings.GRASS_HEIGHT;
	
	private final int WIDTH_COLUMN=Settings.COLUMN_WIDTH,MIN_HEIGHT_COLUMN=Settings.MIN_HEIGHT_COLUMN,SPACE=Settings.FREE_SPACE,BETWEEN_COL=Settings.DBC,SPEED=Settings.WVX;
	
	private final int UP_VELOCITY=Settings.BVU,DOWN_VELOCITY=Settings.BVD;
	
	boolean started,paintpause=true;
	
	private Renderer renderer;
	
	private JFrame jframe;
	
	private Rectangle bird;
	
	private ArrayList<Rectangle> columns;
	
	private Random random;
	
	private int yMotion=0,ticks=0,score=0,clockdown=0;
	
	boolean gameOver,between_poles;
	
	private UI_pausebutton jpanelpause;
	
	public FlappyBird(Repository repo){
		
		this.repo=repo;
		
		jframe=new JFrame();
		Timer timer=new Timer(20,this);
		
		Icon icon = new ImageIcon(Settings.IMGPAUSE_PATH);
		Image img = ((ImageIcon) icon).getImage() ;  
		Image newimg = img.getScaledInstance( 70, 70, java.awt.Image.SCALE_AREA_AVERAGING ) ;  
		icon = new ImageIcon( newimg );
		pause_button=new JButton(icon);
		pause_button.setBounds(700,HEIGHT-HEIGHT_GROUND+2,80,80);
		pause_button.addMouseListener(this);
		
		renderer=new Renderer(this);
		
		jframe.add(renderer);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.addMouseListener(this);
		jframe.setSize(WIDTH,HEIGHT);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		jframe.setLocation(dim.width/2-jframe.getWidth()/2, dim.height/2-jframe.getHeight()/2);
		jframe.setTitle("Flappy Bird");
		jframe.setVisible(true);
		
		random=new Random();
		bird=new Rectangle(WIDTH/2-10,HEIGHT/2-10,20,20);
		columns=new ArrayList<Rectangle>();
		
		addcolumn(true);
		addcolumn(true);
		addcolumn(true);
		addcolumn(true);
		
		started=true;  //the game is started
		jpanelpause=new UI_pausebutton(this);
		
		timer.start();
	}

	public JButton get_pause_button() {
		return pause_button;
	}
	
	public JFrame get_jframe() {
		return this.jframe;
	}
	
	/*
	 * The method add a column to the array of columns in different state of the game
	 * If the game is not playing(status=true) then we will add columns in relation to the right end of the window
	 * Otherwise we will add a column in relation to the location of the last column which we added in our array 
	 * Input:status-(boolean type)show us if the game is playing or not
	 * Output:a new column was added
	 * The method doesn't throw any exceptions
	 */
	private void addcolumn(boolean status) {
		int space=SPACE;
		int dist_between_columns=BETWEEN_COL;
		int width=WIDTH_COLUMN;
		int height=MIN_HEIGHT_COLUMN+random.nextInt(HEIGHT/2);
		
		if(status)
		{			
			columns.add(new Rectangle(WIDTH+columns.size()*(dist_between_columns+width),HEIGHT-HEIGHT_GROUND-height,width,height));
			columns.add(new Rectangle(WIDTH+(columns.size()-1)*(dist_between_columns+width),0,width,HEIGHT-HEIGHT_GROUND-height-space));
		}
		else 
		{
			Rectangle lastrect=columns.get(columns.size()-1);
			//I multiply with two, the addition between width of a column and distance between them, because at every step i added 2 columns,not just one
			columns.add(new Rectangle(lastrect.x + 2*(lastrect.width + dist_between_columns),HEIGHT-HEIGHT_GROUND-height,width,height));
			columns.add(new Rectangle(lastrect.x + 2*(lastrect.width + dist_between_columns),0,width,HEIGHT-HEIGHT_GROUND-height-space));
		}
	}
	
	
	/*
	 * The method color a column
	 * Input:g-a Graphic object
	 * 		 column-a column of Rectangle type
	 * Output:the specified column is colored
	 * The method doesn't throw any exceptions
	 */
	private void paintColumn(Graphics g,Rectangle column)
	{
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
	}
	
	/*
	 * The method is responsible for the motion of the bird on the Y-axis
	 * It update the movement of the bird once at two repaints and the velocity of the bird cannot be greater than 16
	 * Input:--
	 * Output:It update Y-velocity of the bird
	 * The method doesn't throw any exceptions
	 */
	private void motionBird()
	{
		ticks++;
		
		if(ticks%2==0 && yMotion<16)
		{
			yMotion+=DOWN_VELOCITY;
			ticks=0;
		}
		
		bird.y+=yMotion;
	}
	
	/*
	 * The method moves the columns on the screen.If one of the column gets out of the game screen
	 * it is deleted and is added a new one
	 * Input:-
	 * Output:the movement of the columns and update of them 
	 * The method doesn't throw any exceptions
	 */
	private void motionPoles()
	{
		boolean find=false;
		int position=-1;
		for(int i=0;i<columns.size();i++)
		{
		
			Rectangle column=columns.get(i);
			column.x-=SPEED;
			
			//Checking if the column gets out completely of the game screen
			//Only one column for each draw can be deleted
			if(column.x + column.width < 0 && !find)
			{
				position=i;
				find=true;
			}
		}
		
		if(find==true)
		{
			//it add a new column in relation to the last added column
			addcolumn(false);
			columns.remove(position);
			columns.remove(position); //after deleting the above element,element from position + 1 will be on position 'position'
		}
	}
	
	/*
	 *The method checks if the bird has a collision with one of the columns 
	 *Input:-
	 *Output:if there is a collision,then the flag gameOver is set and the bird gets a specific behavior 
	 *		 Otherwise the game will go on
	 *The method doesn't throw any exception
	 */
	private void colision()
	{
		for(Rectangle column:columns)
			if(bird.intersects(column))
			{
				gameOver=true;
			
				if(bird.x>column.x)
				{
					//the bird is in 'free space' between the two columns
					//then the bird will fall in the right of column
					bird.x=column.x+column.width;
					yMotion=0; //the bird will up when it touch the bottom of the top column.So yMotion=0
				}
				else
				{
					//the bird will fall in the left of the column
					bird.x=column.x-bird.width;
				}
		
				break;
			}
			
		    //Checking if the bird touch the ground or the top of sky
			if(bird.y+bird.height>=HEIGHT-HEIGHT_GROUND || bird.y<=0)
				gameOver=true;
			
			if(bird.y<=0) //what will happen if the bird touch the up border.It will fall->yMotion=0.After that, yMotion will only increase cause the game will restart if we click
				yMotion=0;
			
			if( bird.y+bird.height>=HEIGHT-HEIGHT_GROUND ) 
				bird.y=HEIGHT-HEIGHT_GROUND-bird.height;
	}
	
	/*
	 * The method checking if there's a score situation
	 * Input:-
	 * Output:The score will increase if the bird pass the obstacle
	 * The method doesn't throw any exception
	 */
	private void score_bird()
	{
		//betwwen_poles variable marks that the bird was between columns
		//ok variable marks that the bird is between columns now
		boolean ok=false;
		
		for(Rectangle column:columns)
		if(bird.x>=column.x && bird.x<=column.x+column.width )
		{
			//we set the flags if the bird is between two columns
			between_poles=true;
			ok=true;
			break;
		}
		
		if(between_poles==true && ok==false )//the bird was between poles,but isn't longer there
		{
			score++;
			between_poles=false;
		}
	}
	
	/*
	 * The method draws the frames of the game
	 * Input:e-ActionEvent
	 * Output:frames of the game
	 * The method doesn'y throw any kind of exceptions
	 */
	@Override
    public void actionPerformed(ActionEvent e) {
		
		if(started) {//the game is started
			
			//paintpause variable shows if the clockdown has been painted and we can start playing
			if(paintpause==false && clockdown==3) 
			{
				jframe.remove(jpanelpause);
				jframe.add(renderer);
				jframe.addMouseListener(this);
			}

			if(paintpause==true)
			{
				motionBird();
				motionPoles();
				colision();
	
				if(gameOver==false)
					score_bird();
							
				if(gameOver==true && score>repo.get_bestscore())
					try {
						repo.updatescore(score);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
			}
			
			renderer.repaint();
			
			if(clockdown<3 && clockdown>=0 && paintpause==false)
			{
				try {
					Thread.sleep(700);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				if(clockdown==0)
				{
					yMotion=yMotion/2;
					paintpause=true;
				}
			}
		
				
		}
		else
		{
			if(paintpause==true)
			{
				jframe.remove(renderer); //it removes the jpanel of the game
				jframe.removeMouseListener(this); //it remove the mouselistener.I don t want to treat the mouse events of the frame in pause menu
				jframe.add(jpanelpause); //it adds a new jpanel and the game is repainted
				jpanelpause.repaint();
			}
			clockdown=3;
			paintpause=false;
			
		}
    	
    }

	/*
	 * The method draw the frame
	 * Input:g-Graphics object
	 * Output:draw the components
	 * The method doesn't throw any exception
	 */
	public void repaint(Graphics g)  {
	
		g.setColor(Color.cyan);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.orange);
		g.fillRect(0, HEIGHT-HEIGHT_GROUND, WIDTH, HEIGHT_GROUND);
		
		g.setColor(Color.green);
		g.fillRect(0, HEIGHT-HEIGHT_GROUND, WIDTH, HEIGHT_GRASS);
		
		g.setColor(Color.red);
		g.fillRect(bird.x, bird.y, bird.width, bird.height);
		
		for(Rectangle column:columns)
    		paintColumn(g, column);
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial",10,30));
		g.drawString("Best Score: " + String.valueOf(repo.get_bestscore()),0,22);
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial",10,100));
		g.drawString(String.valueOf(score),350, 70);
	
		
		if(gameOver)//the game is over
		{
			g.setColor(Color.white);
			g.setFont(new Font("Arial",10,100));
			g.drawString("Game Over!",WIDTH/6,HEIGHT/2-50);
		}
	
		if(paintpause==false)//the painting of clockdown is running
		{
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman",Font.CENTER_BASELINE,40));
			g.drawString("The Game start in: " +String.valueOf(clockdown)+" seconds ",WIDTH/6,HEIGHT/2-50);
			clockdown--;
		}

	}
	
	/*
	 * This mehtod implements the logic part of game when the mouse is pressed on the frame
	 * Input:-
	 * Output:A certain event will be generated
	 * This method doesn't rise any exception
	 */
	private void jump() {
		
		if(gameOver) {//if the game is over,we reboot the game
			
			bird=new Rectangle(WIDTH/2-10,HEIGHT/2-10,20,20);
			columns.clear();
			score=0;
			between_poles=false;
	
			addcolumn(true);
			addcolumn(true);
			addcolumn(true);
			addcolumn(true);
			
			gameOver=false;
			started=true;
		}
		
		if(!started)
		{
			started=true;
		}
		else if(!gameOver) {//the game is running.When I press mouse then the bird rise up
			
			if(yMotion>0)
				yMotion=0;
			yMotion+=UP_VELOCITY;
		}
	}
	
	/*
	 * The method is invoked when a mouse button has been pressed on a component
	 * Input:e-MouseEvent
	 * Output:the event will be dealt with
	 * The method doesn't throw any exceptions
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		Object sourse=e.getSource();
		if(sourse==pause_button && gameOver==false)
			started=false;
		else if(sourse!=pause_button)
			jump();
	}
	
	

	@Override
	public void mouseClicked(MouseEvent e) {

	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
}
