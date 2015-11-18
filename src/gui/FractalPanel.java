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
		double x_center = (x_min + x_max) / 2;
		double y_center = (y_min + y_max) / 2;
		x_min = (x_min + x_center) / 2;
		x_max = (x_max + x_center) / 2;
		y_min = (y_min + y_center) / 2;
		y_max = (y_max + y_center) / 2;
	}

	public void zoom_out() {
		double x_center = (x_min + x_max) / 2;
		double y_center = (y_min + y_max) / 2;
		x_min = 2 * x_min - x_center;
		x_max = 2 * x_max - x_center;
		y_min = 2 * y_min - y_center;
		y_max = 2 * y_max - y_center;
		if (x_min <= -2) {
			x_min = -2;
		}
		if (x_max >= 2) {
			x_max = 2;
		}
		if (y_min <= -2) {
			y_min = -2;
		}
		if (y_max >= 2) {
			y_max = 2;
		}
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
		double frame_aspect_ratio = (double) (width) / ((double) (height));
		double area_aspect_ratio = (x_max - x_min) / (y_max - y_min);
		double top = y_max;
		double bottom = y_min;
		double left = x_min;
		double right = x_max;
		if (frame_aspect_ratio > area_aspect_ratio) {
			double scale = (y_max - y_min) * frame_aspect_ratio * 0.5;
			double x_center = (x_min + x_max) / 2;
			left = x_center - scale;
			right = x_center + scale;
		} else {
			double scale = (x_max - x_min) / frame_aspect_ratio * 0.5;
			double y_center = (y_min + y_max) / 2;
			bottom = y_center - scale;
			top = y_center + scale;
		}
		double[][] buffer = new double[m_image.getWidth()][m_image.getHeight()];
		double min_color = 1000000;
		double max_color = 0;
		int max_iterations = 20;
		for (int x_idx = 0; x_idx < width; x_idx++) {
			for (int y_idx = 0; y_idx < height; y_idx++) {
				// The x-coordinate in the complex plane
				double x = (right - left) * x_idx / (width - 1) + left;
				// The y-coordinate in the complex plane
				double y = (top - bottom) * (height - y_idx - 1) / (height - 1) + bottom;
				Complex coord = new Complex(x, y);

				double result;
				if (display_mode == 1) {
					result = escape_time_julia(coord, max_iterations);
				} else {
					result = escape_time(coord, max_iterations);
				}

				if (result < min_color) {
					min_color = result;
				}
				if (result > max_color) {
					max_color = result;
				}
				buffer[x_idx][y_idx] = result;
			}
		}
		for (int x = 0; x < m_image.getWidth(); x++) {
			for (int y = 0; y < m_image.getHeight(); y++) {
				m_image.setRGB(x, y, getColor(min_color, max_color, buffer[x][y], max_iterations).getRGB());
			}
		}
		if (display_mode == 1) {
			j_image = m_image;
		}
	}

	public Color getColor(double min, double max, double n, int threshold) {
		if (n < min) {
			n = min;
		}
		if (n > max) {
			n = max;
		}
		double value = (n - min) / (max - min);
		Color result;
		switch (color_mode) {
		case 0:
			result = Color.getHSBColor((float)value, (float)1, (float)1);
			break;
		case 1:
			result = Color.getHSBColor(0, 1, (float)value);
			break;
		default:
			result = Color.white;
			break;
		}
		if(n >= threshold){
			result = Color.black;
		}
		return result;
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
