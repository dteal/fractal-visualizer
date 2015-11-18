package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class MainWindow extends JFrame implements ComponentListener, ActionListener, MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;

	// Define display mode choices
	String[] display_modes = {"Mandelbrot Set", "Julia Set", "M/J Comparison"};
	String[] render_modes = {"Escape Time", "Ext. Dist. Estimation"};
	String[] color_modes = {"Full Color", "Grayscale", "Black & White"};
	
	// Define fractal panel
	FractalPanel fp = new FractalPanel();

	// Declare tool bar items
	JToolBar toolbar = new JToolBar();
	JComboBox<String> display_mode_select = new JComboBox<String>(display_modes);
	JComboBox<String> render_mode_select = new JComboBox<String>(render_modes);
	JComboBox<String> color_mode_select = new JComboBox<String>(color_modes);
	JTextField x_coord = new JTextField("X: 0");
	JTextField y_coord = new JTextField("Y: 0");
	JButton zoom_in_button = new JButton("Zoom In");
	JButton zoom_out_button = new JButton("Zoom Out");
	JButton save_button = new JButton("Save");
	
	public MainWindow(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Fractal Visualizer");
		setMinimumSize(new Dimension(800,500));
		
		toolbar.setFloatable(false);
		display_mode_select.setMaximumSize(new Dimension(100,50));
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
		render_mode_select.setMaximumSize(new Dimension(100,50));
		render_mode_select.addActionListener(this);
		toolbar.add(render_mode_select);
		toolbar.addSeparator();
		color_mode_select.setMaximumSize(new Dimension(100,50));
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
		if(e.getSource()==display_mode_select){
			fp.display_mode=display_mode_select.getSelectedIndex();
			fp.refresh();
			repaint();
		}
		if(e.getSource()==render_mode_select){
			fp.render_mode=render_mode_select.getSelectedIndex();
			fp.refresh();
			repaint();
		}
		if(e.getSource()==color_mode_select){
			fp.color_mode=color_mode_select.getSelectedIndex();
			fp.refresh();
			repaint();
		}
		if(e.getSource()==zoom_in_button){
			fp.zoom_in();
			fp.refresh();
			repaint();
		}
		if(e.getSource()==zoom_out_button){
			fp.zoom_out();
			fp.refresh();
			repaint();
		}
		if(e.getSource()==save_button){
			
		}
	}

	public void mouseDragged(MouseEvent e) {
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
	}

	public void mouseReleased(MouseEvent e) {
	}

}
