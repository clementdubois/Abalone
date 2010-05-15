import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class FinFrame extends JFrame{
	
	private JPanel container = new JPanel();
	
	public FinFrame(int gagnant) {
		super();
		this.setTitle("Fin de partie");
		setSize(540,200);
		setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		container.setLayout(new BorderLayout());
		FinPanel panel=new FinPanel(gagnant);
		show();
		container.add(panel, BorderLayout.CENTER);
		this.getContentPane().add(container);
		this.setVisible(true);   
	}
}
