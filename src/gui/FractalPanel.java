package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class FractalPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public FractalPanel(){
		setPreferredSize(new Dimension(500, 500));
		setBackground(Color.lightGray);
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g.setColor(Color.black);
		g.setFont(new Font("Verdana", Font.PLAIN, 50)); 
		g.drawString("Hello, world!", 10, 50);
	}
	
}
