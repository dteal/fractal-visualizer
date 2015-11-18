package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import complex.Complex;

public class FractalPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public int display_mode = 0;
	public int render_mode = 0;
	public int color_mode = 0;

	public BufferedImage m_image;
	public BufferedImage j_image;

	int prev_width = 1;
	int prev_height = 1;
	public int width = 1;
	public int height = 1;
	public double x_min = -2;
	public double x_max = 2;
	public double y_min = -2;
	public double y_max = 2;

	public void zoom_in() {
	}

	public void zoom_out() {
	}

	public void refresh() {
		width = getWidth();
		height = getHeight();
		renderImage();
	}

	public FractalPanel() {
		setPreferredSize(new Dimension(500, 500));
	}

	public void paint(Graphics g) {
		super.paint(g);
		switch (display_mode) {
		case 0:
			g.drawImage(m_image, 0, 0, null);
			break;
		case 1:
			g.drawImage(j_image, 0, 0, null);
			break;
		case 2:
			g.drawImage(m_image, 0, 0, null);
			g.drawImage(j_image, m_image.getWidth(), 0, null);
			break;
		default:
			g.drawImage(m_image, 0, 0, null);
		}
	}

	public void renderImage() {
		if (width <= 0 || height <= 0) {
			m_image = null;
			return;
		}
		m_image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x_idx = 0; x_idx < width; x_idx++) {
			for (int y_idx = 0; y_idx < height; y_idx++) {
				// The x-coordinate in the complex plane
				double x = (x_max - x_min) * x_idx / (width - 1) + x_min;
				// The y-coordinate in the complex plane
				double y = (y_max - y_min) * (height - y_idx - 1) / (height - 1) + y_min;
				Complex coord = new Complex(x, y);
				int result = escape_time(coord, 20);
				m_image.setRGB(x_idx, y_idx, new Color(0, 0, (int) (result * 10)).getRGB());
			}
		}
	}

	// Gives the number of iterations before escaping the Mandelbrot set
	public int escape_time(Complex c, int max_iterations) {
		Complex curr = new Complex(0, 0);
		int iterations = 0;
		while (curr.modulus() < 2 && iterations < max_iterations) {
			curr = Complex.add(Complex.multiply(curr, curr), c);
			iterations++;
		}
		return iterations;
	}

	// Gives an estimate of the closest distance to the Mandelbrot set
	public double exterior_distance(Complex c, int max_iterations) {
		Complex curr = new Complex(0, 0);
		Complex curr_d = new Complex(1, 0);
		int iterations = 0;
		while (curr.modulus() < 2 && iterations < max_iterations) {
			Complex next = Complex.add(Complex.multiply(curr, curr), c);
			Complex next_d = Complex.add(Complex.multiply(Complex.multiply(curr, curr_d), new Complex(2, 0)),
					new Complex(1, 0));
			curr = next;
			curr_d = next_d;
			iterations++;
		}
		return curr.modulus() * Math.log(curr.modulus()) / curr_d.modulus();
	}

	// Gives the number of iterations before escaping a Julia set
	public int escape_time_julia(Complex c, int max_iterations) {
		Complex seed = new Complex(-0.4, 0.65);
		Complex curr = c;
		int iterations = 0;
		while (curr.modulus() < 2 && iterations < max_iterations) {
			curr = Complex.add(Complex.multiply(curr, curr), seed);
			iterations++;
		}
		return iterations;
	}

}
