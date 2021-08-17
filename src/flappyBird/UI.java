package flappyBird;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UI extends JFrame implements MouseListener{
	
	private static final int WIDTH=800,HEIGHT=800;
	private Repository repo;
	
	JLabel title_game;
	JButton start_game;
	JButton button_score;
	JButton button_exit;
	
	public UI(Repository repo)
	{
		this.repo=repo;
		initGUI();
	}
	
	private void set_up_button(JButton button) {
		button.addMouseListener(this);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFont(new Font("TimesRoman",Font.CENTER_BASELINE,40));
	}
	
	private void initGUI() {
		
		JPanel panel=new JPanel();
		panel.setBounds(0,0,WIDTH,HEIGHT);
		panel.setBackground(Color.cyan);
		panel.setLayout(null);

		
		Font font=new Font("Arial",Font.ITALIC,100);
		title_game=new JLabel("Flappy Bird");
		title_game.setFont(font);
		title_game.setForeground(Color.red);
		title_game.setBounds(160,20,900,300);
		
		panel.add(title_game);
		
		start_game=new JButton("Start");
		set_up_button(start_game);
		start_game.setBounds(210,300,400,50);
		panel.add(start_game);
		
		button_score=new JButton("Best Score");
		set_up_button(button_score);
		button_score.setBounds(210,400, 400, 50);
		panel.add(button_score);
		
		button_exit=new JButton("Exit game");
		set_up_button(button_exit);
		button_exit.setBounds(210, 500, 400, 50);
		panel.add(button_exit);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(WIDTH,HEIGHT);
		setVisible(true);
		add(panel);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JButton sursa=(JButton)e.getSource();
		if(sursa==start_game) {
			Repository repo;
			try {
				dispose();
				repo = new Repository("./src/bestScore");
				new FlappyBird(repo);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		if(sursa==button_score)
		{
			dispose();
			new UI_bestscore(repo);
		}
		
		if(sursa==button_exit)
		{
			System.exit(0);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		JButton sursa=(JButton)e.getSource();
		sursa.setForeground(Color.yellow);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JButton sursa=(JButton)e.getSource();
		sursa.setForeground(Color.black);
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	
	}
	

	
	
}
