package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class FractalPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	BufferedImage image;
	
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
	
	public void renderImage(){
		image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
		double top = 2;
		double bottom = -2;
		double left = -2;
		double right = 2;
		double width = 500;
		double height = 500;
		for(int x_idx = 0; x_idx < width; x_idx++){
			for(int y_idx = 0; y_idx < height; y_idx++){
				
			}
		}
	}
	
}
