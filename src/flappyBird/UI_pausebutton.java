package flappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UI_pausebutton extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 1L;

	private final int WIDTH=Settings.WINDOW_WIDTH,HEIGHT=Settings.WINDOW_HEIGHT;
	
	private FlappyBird flappybird;
	
	JButton button_resume;
	JButton button_menu;
	
	public UI_pausebutton(FlappyBird flappybird) {
		this.flappybird=flappybird;
		initGUI();
	}
	
	private void set_up_button(JButton button) {
		button.addMouseListener(this);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFont(new Font("TimesRoman",Font.CENTER_BASELINE,40));
	}
	
	/*
	 * The method initialises the gui interface for pause menu
	 */
	private void initGUI() {
		
		//flappybird.get_jframe().addMouseListener(this);
		setLayout(null);
		setBounds(0,0,WIDTH,HEIGHT);
		setBackground(Color.cyan);
		
		button_resume=new JButton("Resume Game");
		set_up_button(button_resume);
		button_resume.setBounds(210,250,400,50);
		add(button_resume);
		
		
		button_menu=new JButton("Go to Menu");
		set_up_button(button_menu);
		button_menu.setBounds(210,350, 400, 50);
		add(button_menu);
		
	}
	
	/*
	 * This method draw the component JPanel
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {

		JButton button=(JButton)e.getSource();
		if(button==button_resume){
			flappybird.started=true;
		}
		else if(button==button_menu){
			flappybird.get_jframe().dispose();
			try {
				new UI(new Repository("./src/bestScore"));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JButton source=(JButton)e.getSource();
		source.setForeground(Color.yellow);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton source=(JButton)e.getSource();
		source.setForeground(Color.black);
	}

	@Override
	public void mousePressed(MouseEvent e) {

		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
