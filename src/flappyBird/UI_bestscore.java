package flappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UI_bestscore extends JFrame implements MouseListener{


	private static final long serialVersionUID = 1L;
	
	private static int WIDTH=800,HEIGHT=800;
	private Repository repo;
	private JLabel label_score;
	private JButton button_return;
	
	public UI_bestscore(Repository repo) {
		this.repo=repo;
		initGUI();
	}
	
	private void initGUI() {
		
		JPanel panel=new JPanel();
		panel.setBounds(0, 0, WIDTH, HEIGHT);
		panel.setLayout(null);
		panel.setBackground(Color.cyan);
		
		int best_Score=repo.get_bestscore();
		label_score=new JLabel();
		label_score.setFont(new Font("TImesRoman",Font.CENTER_BASELINE,50));
		label_score.setText("Best Score: "+ String.valueOf(best_Score));
		label_score.setBounds(230,150,900,300);
		label_score.setForeground(Color.red);
		panel.add(label_score);
		
		Icon icon = new ImageIcon("./src/undo.png");
		Image img = ((ImageIcon) icon).getImage() ;  
		Image newimg = img.getScaledInstance( 110, 110,  java.awt.Image.SCALE_SMOOTH ) ;  
		icon = new ImageIcon( newimg );
	    button_return = new JButton(icon);
	    button_return.addMouseListener(this);
		button_return.setBounds(670,660,110,100);
		panel.add(button_return);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(WIDTH,HEIGHT);
		setVisible(true);
		add(panel);
	}
	
	/*public static void main(String[] args) throws FileNotFoundException {
		new UI_bestscore(new Repository("./src/bestScore"));
	}*/

	@Override
	public void mouseClicked(MouseEvent arg0) {
		dispose();
		new UI(repo);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
