package gui;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

public class Main extends JFrame implements ComponentListener{

	private static final long serialVersionUID = 1L;

	FractalPanel fp = new FractalPanel();

	// Declare menu bar items

	JMenuBar menubar = new JMenuBar();
	JMenu file = new JMenu("File");
	JMenuItem file_exit = new JMenuItem("Exit");
	JMenu view = new JMenu("View");
	JCheckBoxMenuItem view_zoom = new JCheckBoxMenuItem("Zoom");
	JCheckBoxMenuItem view_full = new JCheckBoxMenuItem("Full Screen");
	JMenu help = new JMenu("Help");
	JMenuItem help_about = new JMenuItem("About Fractal Visualizer");
	
	public Main(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Fractal Visualizer");
		setMinimumSize(new Dimension(200,200));
		
		file.add(file_exit);
		view.add(view_zoom);
		view.add(view_full);
		help.add(help_about);
		menubar.add(file);
		menubar.add(view);
		menubar.add(help);
		setJMenuBar(menubar);
		
		fp.addComponentListener(this);
		add(fp);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		new Main();
	}

	public void componentHidden(ComponentEvent e) {
		
	}

	public void componentMoved(ComponentEvent e) {
		
	}

	public void componentResized(ComponentEvent e) {
		fp.renderImage();
	}

	public void componentShown(ComponentEvent e) {
		
	}

}
