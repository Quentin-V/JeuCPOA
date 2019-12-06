import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrameOptions extends JFrame implements ActionListener, KeyListener {

	private Options options;

	private JButton btnTG, btnTD, btnTT; // Boutons touche gauche, droite et tir
	private JLabel  lblTG, lblTD, lblTT;
	private boolean attendTouche;
	private char attend;

	FrameOptions(Options options) {

		this.options = options;

		this.setTitle("Options");
		this.setSize(300,200);
		this.setLocation(250,300);

		this.setLayout(new GridLayout(3,2));

		this.lblTG = new JLabel("Touche rotation gauche");
		this.lblTD = new JLabel("Touche rotation droite");
		this.lblTT = new JLabel("Touche de tir");
		this.btnTG = new JButton("Fleche Gauche");
		this.btnTD = new JButton("Fleche Droite");
		this.btnTT = new JButton("Fleche Haut");

		this.btnTG.addActionListener(this);
		this.btnTG.setActionCommand("tg");
		this.btnTD.addActionListener(this);
		this.btnTD.setActionCommand("td");
		this.btnTT.addActionListener(this);
		this.btnTT.setActionCommand("tt");

		this.add(this.lblTG);
		this.add(this.btnTG);
		this.add(this.lblTD);
		this.add(this.btnTD);
		this.add(this.lblTT);
		this.add(this.btnTT);

		this.addKeyListener(this);

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		this.attendTouche = true;
		if(actionEvent.getActionCommand().equals("tg")) {
			this.btnTG.setText("Appuyez sur une touche");
			this.btnTD.setEnabled(false);
			this.btnTT.setEnabled(false);
			this.attend = 'g';
		}else if(actionEvent.getActionCommand().equals("td")) {
			this.btnTD.setText("Appuyez sur une touche");
			this.btnTG.setEnabled(false);
			this.btnTT.setEnabled(false);
			this.attend = 'd';
		}else {
			this.btnTT.setText("Appuyez sur une touche");
			this.btnTG.setEnabled(false);
			this.btnTD.setEnabled(false);
			this.attend = 't';
		}
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) { }

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		System.out.println("Press : " + keyEvent.getKeyCode());
		if(this.attendTouche) {
			if(this.attend == 'g') {
				this.options.setToucheGauche(keyEvent.getKeyCode());
			}else if(this.attend == 'd') {
				this.options.setToucheDroite(keyEvent.getKeyCode());
			}else {
				this.options.setToucheTir(keyEvent.getKeyCode());
			}
			this.btnTG.setEnabled(true);
			this.btnTD.setEnabled(true);
			this.btnTT.setEnabled(true);
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) { }


}
