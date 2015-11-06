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
		//setBackground(Color.lightGray);
		//System.out.println(inMandelbrot(5,0));
		renderImage();
	}
	
	public void paint(Graphics g){
		super.paint(g);
		//g.setColor(Color.black);
		//g.setFont(new Font("Verdana", Font.PLAIN, 50)); 
		//g.drawString("Hello, world!", 10, 50);
		g.drawImage(image, 0, 0, null);
	}
	
	public void renderImage(){
		double top = 2; // Top value in complex plane
		double bottom = -2; // Bottom value in complex plane
		double left = -2; // Left value in complex plane
		double right = 2; // Right value in complex plane
		int width = (int)getSize().getWidth(); // Width of image
		int height = (int)getSize().getHeight(); // Height of image
		if(width <= 0 || height <= 0){
			image = null;
			return;
		}
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(int x_idx = 0; x_idx < width; x_idx++){
			for(int y_idx = 0; y_idx < height; y_idx++){
				// The x-coordinate in the complex plane
				double x = (right-left)*x_idx/(width-1)+left;
				// The y-coordinate in the complex plane
				double y = (top-bottom)*(height-y_idx-1)/(height-1)+bottom;
				// System.out.println(x + " " + y);
				image.setRGB(x_idx, y_idx, new Color(0,0,inMandelbrot(x,y)*10).getRGB());
			}
		}
	}
	
	public int inMandelbrot(double x, double y){
		int MAX_ITERATIONS = 20;
		
		double px = 0;
		double py = 0;
		int iterations = 0;
		while(square(px)+square(py)<2*2 && iterations < MAX_ITERATIONS){
			double nx = square(px)-square(py)+x;
			double ny = 2*px*py + y;
			iterations ++;
			px = nx;
			py = ny;
		}
		
		// Return 0 if in Mandelbrot, 1 if one iteration away from being inside, etc.
		return MAX_ITERATIONS - iterations;
	}
	
	public double square(double n){
		return n * n;
	}
}
