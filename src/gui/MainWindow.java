package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;

public class MainWindow extends JFrame
		implements ComponentListener, ActionListener, MouseListener, MouseMotionListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	// Define display mode choices
	String[] display_modes = { "Mandelbrot Set", "Julia Set", "M/J Comparison" };
	String[] render_modes = { "Escape Time", "Ext. Dist. Estimation" };
	String[] color_modes = { "Full Color", "Grayscale", "Black & White" };

	// Define fractal panel
	FractalPanel fp = new FractalPanel();

	// Declare tool bar items
	JToolBar toolbar = new JToolBar();
	JComboBox<String> display_mode_select = new JComboBox<String>(display_modes);
	JComboBox<String> render_mode_select = new JComboBox<String>(render_modes);
	JComboBox<String> color_mode_select = new JComboBox<String>(color_modes);
	JSpinner max_iter_chooser = new JSpinner();
	JTextField x_coord = new JTextField("X: 0");
	JTextField y_coord = new JTextField("Y: 0");
	JButton zoom_in_button = new JButton("Zoom In");
	JButton zoom_out_button = new JButton("Zoom Out");
	JButton save_button = new JButton("Save");

	Point prev_mouse_pos = new Point(0, 0);
	String current_directory;

	public MainWindow() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Fractal Visualizer");
		setMinimumSize(new Dimension(800, 500));

		toolbar.setFloatable(false);
		display_mode_select.setMaximumSize(new Dimension(100, 50));
		display_mode_select.addActionListener(this);
		toolbar.add(display_mode_select);
		toolbar.addSeparator();
		x_coord.setColumns(10);
		x_coord.setMaximumSize(new Dimension(100, 50));
		toolbar.add(x_coord);
		y_coord.setColumns(10);
		y_coord.setMaximumSize(new Dimension(100, 50));
		toolbar.add(y_coord);
		toolbar.addSeparator();
		render_mode_select.setMaximumSize(new Dimension(100, 50));
		render_mode_select.addActionListener(this);
		toolbar.add(render_mode_select);
		toolbar.addSeparator();
		SpinnerModel model = new SpinnerNumberModel(20, 0, 10000, 1);
		max_iter_chooser.setModel(model);
		max_iter_chooser.addChangeListener(this);
		max_iter_chooser.setMaximumSize(new Dimension(100, 50));
		max_iter_chooser.setMinimumSize(new Dimension(100, 5));
		toolbar.add(max_iter_chooser);
		toolbar.addSeparator();
		color_mode_select.setMaximumSize(new Dimension(100, 50));
		color_mode_select.addActionListener(this);
		toolbar.add(color_mode_select);
		toolbar.addSeparator();
		zoom_in_button.addActionListener(this);
		toolbar.add(zoom_in_button);
		toolbar.addSeparator();
		zoom_out_button.addActionListener(this);
		toolbar.add(zoom_out_button);
		toolbar.addSeparator();
		save_button.addActionListener(this);
		toolbar.add(save_button);
		toolbar.add(Box.createHorizontalGlue());
		add(toolbar, BorderLayout.NORTH);

		fp.addMouseListener(this);
		fp.addMouseMotionListener(this);
		fp.addComponentListener(this);
		add(fp);

		pack();
		fp.refresh();
		setLocationRelativeTo(null);
		setVisible(true);
		repaint();
	}

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		new MainWindow();
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		fp.refresh();
		repaint();
	}

	public void componentShown(ComponentEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == display_mode_select) {
			fp.display_mode = display_mode_select.getSelectedIndex();
			fp.refresh();
			repaint();
		}
		if (e.getSource() == render_mode_select) {
			fp.render_mode = render_mode_select.getSelectedIndex();
			fp.refresh();
			repaint();
		}
		if (e.getSource() == color_mode_select) {
			fp.color_mode = color_mode_select.getSelectedIndex();
			fp.refresh();
			repaint();
		}
		if (e.getSource() == zoom_in_button) {
			fp.zoom_in();
			fp.refresh();
			repaint();
		}
		if (e.getSource() == zoom_out_button) {
			fp.zoom_out();
			fp.refresh();
			repaint();
		}
		if (e.getSource() == save_button) {
			switch (fp.render_mode) {
			case 1:
				save(fp.j_image);
				break;
			default:
				save(fp.m_image);
			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		if (e.getSource() == fp) {
			Point next_mouse_pos = e.getPoint();
			double dx = next_mouse_pos.getX() - prev_mouse_pos.getX();
			double dy = prev_mouse_pos.getY() - next_mouse_pos.getY();

			double frame_aspect_ratio = (double) (fp.width) / ((double) (fp.height));
			double area_aspect_ratio = (fp.x_max - fp.x_min) / (fp.y_max - fp.y_min);
			if (frame_aspect_ratio > area_aspect_ratio) {
				dy = (fp.y_max - fp.y_min) * dy / fp.height;
				dx = (fp.y_max - fp.y_min) * dx / fp.height;
			} else {
				dy = (fp.x_max - fp.x_min) * dy / fp.width;
				dx = (fp.x_max - fp.x_min) * dx / fp.width;
			}
			fp.x_max -= dx;
			fp.x_min -= dx;
			fp.y_max -= dy;
			fp.y_min -= dy;
			prev_mouse_pos = next_mouse_pos;
			fp.refresh();
			repaint();
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (e.getSource() == fp) {
			prev_mouse_pos = e.getPoint();
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == max_iter_chooser) {
			fp.max_iterations = (int) max_iter_chooser.getValue();
			fp.refresh();
			repaint();
		}
	}

	public void save(BufferedImage img) {

		// Display file choosing dialog

		JFileChooser jfc = new JFileChooser(current_directory);
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.addChoosableFileFilter(new ImageFileFilter());
		int result = jfc.showSaveDialog(this);

		// Handle result

		if (result == JFileChooser.CANCEL_OPTION) {
			return;
		}
		File file = jfc.getSelectedFile();
		try {
			String formatname = new String(file.getName().substring(file.getName().lastIndexOf(".") + 1));

			// Remove transparency for JPG and BMP files

			if (formatname.equalsIgnoreCase("jpg") || formatname.equalsIgnoreCase("jpeg")
					|| formatname.equalsIgnoreCase("bmp")) {
				BufferedImage opaqueImage = new BufferedImage(img.getWidth(), img.getHeight(),
						BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = opaqueImage.createGraphics();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(Color.white);
				g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
				g2d.drawImage(img, 0, 0, null);
				g2d.dispose();
				img = opaqueImage;
			}

			// Write image to file

			ImageIO.write(img, formatname, new File(file.getPath()));

			current_directory = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("\\") + 1);
		} catch (Exception e) { // Handle errors
			JOptionPane.showMessageDialog(this, "Could not save file.", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	// Allows open/save dialogs to select only image files

	public class ImageFileFilter extends FileFilter {

		private final String[] okFileExtensions = new String[] { "jpg", "jpeg", "png", "gif", "bmp" };

		public boolean accept(File file) {
			for (String extension : okFileExtensions) { // Images are OK
				if (file.getName().toLowerCase().endsWith(extension)) {
					return true;
				}
			}
			if (file.isDirectory()) { // It helps the user to see folders
				return true;
			}
			return false;
		}

		public String getDescription() {
			return "*.bmp, *.gif, *.jpeg, *.png";
		}

	}

}
