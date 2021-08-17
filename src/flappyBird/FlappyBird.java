package flappyBird;

import java.awt.Color;
import java.awt.Container;
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

public class FlappyBird implements ActionListener,MouseListener {

	private Repository repo;
	
	public JButton pause_button;
	
	public static FlappyBird flappyBird;
	
	public static final int WIDTH=800,HEIGHT=800,HEIGHT_GROUND=120,HEIGHT_GRASS=20;
	
	public static final int WIDTH_COLUMN=100,MIN_HEIGHT_COLUMN=50,SPACE=150,BETWEEN_COL=200,SPEED=7;
	
	public static final int UP_VELOCITY=-7,DOWN_VELOCITY=1;
	
	boolean started,paintpause=true;
	
	public Renderer renderer;
	
	private JFrame jframe;
	
	public Rectangle bird;
	
	public ArrayList<Rectangle> columns;
	
	public Random random;
	
	private int yMotion=0,ticks=0,score=0,clockdown=0;
	
	boolean gameOver,between_poles;
	
	UI_pausebutton jpanelpause=new UI_pausebutton(this);
	
	public FlappyBird(Repository repo){
		
		started=true;  //the game is started
		this.repo=repo;
		
		jframe=new JFrame();
		Timer timer=new Timer(20,this);
		
		Icon icon = new ImageIcon("./src/pause.png");
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
		jframe.setTitle("Flappy Bird");
		jframe.setVisible(true);
		
		random=new Random();
		bird=new Rectangle(WIDTH/2-10,HEIGHT/2-10,20,20);
		columns=new ArrayList<Rectangle>();
		
		addcolumn(true);
		addcolumn(true);
		addcolumn(true);
		addcolumn(true);
		
		timer.start();
	}

	public JFrame get_jframe() {
		return this.jframe;
	}
	
	
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
			columns.add(new Rectangle(lastrect.x + 2*(lastrect.width + dist_between_columns),HEIGHT-HEIGHT_GROUND-height,width,height));
			columns.add(new Rectangle(lastrect.x + 2*(lastrect.width + dist_between_columns),0,width,HEIGHT-HEIGHT_GROUND-height-space));
		}
	}
	
	private void paintColumn(Graphics g,Rectangle column)
	{
		g.setColor(Color.green.darker());
		g.fillRect(column.x, column.y, column.width, column.height);
	}
	
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
	
	private void motionPoles()
	{
		boolean find=false;
		int position=-1;
		for(int i=0;i<columns.size();i++)
		{
		
			Rectangle column=columns.get(i);
			column.x-=SPEED;
			
			if(column.x + column.width < 0 && !find)
			{
				position=i;
				find=true;
			}
		}
		
		if(find==true)
		{
			addcolumn(false);
			columns.remove(position);
			columns.remove(position); //after deleting the above element,element from position + 1 will be on position 'position'
		}
	}
	
	private void colision()
	{
		for(Rectangle column:columns)
			if(bird.intersects(column))
			{
				gameOver=true;
			
				if(bird.x>column.x)
				{
					bird.x=column.x+column.width;
					yMotion=0;
				}
				else
				{
					bird.x=column.x-bird.width;
				}
		
				break;
			}
			
			if(bird.y+bird.height>=HEIGHT-HEIGHT_GROUND || bird.y<=0)
				gameOver=true;
			
			if(bird.y<=0) //what will happen if the bird touch the up border.It will fall->yMotion=0.After that, yMotion will only increase cause the game will restart if we click
				yMotion=0;
			
			if( bird.y+bird.height>=HEIGHT-HEIGHT_GROUND ) 
				bird.y=HEIGHT-HEIGHT_GROUND-bird.height;
	}
	
	private void score_bird()
	{
		boolean ok=false;
		
		for(Rectangle column:columns)
		if(bird.x>=column.x && bird.x<=column.x+column.width )
		{
			between_poles=true;
			ok=true;
			break;
		}
		
		if(between_poles==true && ok==false )//the bird was between poles,but isn't there
		{
			score++;
			between_poles=false;
		}
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		
		if(started) {
			
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
					// TODO Auto-generated catch block
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
				jframe.remove(renderer);
				jframe.removeMouseListener(this);
				jframe.add(jpanelpause);
				jpanelpause.repaint();
			}
			clockdown=3;
			paintpause=false;
			
		}
    	
    }

	
	public void repaint(Graphics g) throws InterruptedException {
	
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
	
		if(paintpause==false)
		{
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman",Font.CENTER_BASELINE,40));
			g.drawString("The Game start in: " +String.valueOf(clockdown)+" seconds ",WIDTH/6,HEIGHT/2-50);
			clockdown--;
		}

	}
	
	private void jump() {
		
		if(gameOver) {
			
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
		else if(!gameOver) {
			
			if(yMotion>0)
				yMotion=0;
			yMotion+=UP_VELOCITY;
		}
	}
	
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
