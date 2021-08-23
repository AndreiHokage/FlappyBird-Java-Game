package flappyBird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

//extends ====== is a =>Renderer is a JPanel
public class Renderer extends JPanel {

	private FlappyBird flappyBird;
	
	private static final long serialVersionUID = 1L;
	
	public Renderer(FlappyBird flappyBird) {
		this.flappyBird=flappyBird;
		setBounds(0,0,Settings.WINDOW_WIDTH,Settings.WINDOW_HEIGHT);
		setLayout(null);
		add(flappyBird.get_pause_button());
	}
	
	/*
	 * This method draw the component JPanel
	 */
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		flappyBird.repaint(g);
	
	}

}
