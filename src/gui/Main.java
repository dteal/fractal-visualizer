package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Main extends JFrame implements ComponentListener, ActionListener{

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
		
		file_exit.addActionListener(this);
		file.add(file_exit);
		view_zoom.addActionListener(this);
		view.add(view_zoom);
		view_full.addActionListener(this);
		view.add(view_full);
		help_about.addActionListener(this);
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
		repaint();
	}
	
	public static void main(String[] args) {
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, fall back to cross-platform
		    try {
		        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		    } catch (Exception ex) {
		        // not worth my time
		    }
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


	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == file_exit){
			System.exit(0);
		}
		
	}

}
