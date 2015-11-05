package gui;

import javax.swing.JFrame;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	FractalPanel fp = new FractalPanel();
	
	public Main(){
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		add(fp);
		pack();
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Main();
	}

}
