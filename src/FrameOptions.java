import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Frame utilisée pour modifier les touches de jeu par l'utilisateur
 */
public class FrameOptions extends JFrame implements ActionListener, WindowListener {

	private Options options;

	private JButton btnTG, btnTD, btnTT; // Boutons touche gauche, droite et tir
	private JLabel  lblTG, lblTD, lblTT;
	private boolean attendTouche;
	private char attend;
	private Thread t;

	/**
	 * Constructeur de la frame
	 * @param options les options reliées au jeu
	 */
	FrameOptions(Options options) {

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		this.options = options;

		this.setTitle("Options");
		this.setSize(300,200);
		this.setLocation(250,300);

		this.setLayout(new GridLayout(3,2));

		this.lblTG = new JLabel("Touche rotation gauche");
		this.lblTD = new JLabel("Touche rotation droite");
		this.lblTT = new JLabel("Touche de tir");
		this.btnTG = new JButton(KeyEvent.getKeyText(options.toucheGauche));
		this.btnTD = new JButton(KeyEvent.getKeyText(options.toucheDroite));
		this.btnTT = new JButton(KeyEvent.getKeyText(options.toucheTir));

		this.btnTG.addActionListener(this);
		this.btnTG.setActionCommand("tg");
		this.btnTD.addActionListener(this);
		this.btnTD.setActionCommand("td");
		this.btnTT.addActionListener(this);
		this.btnTT.setActionCommand("tt");

		this.btnTG.setFocusable(false);
		this.btnTD.setFocusable(false);
		this.btnTT.setFocusable(false);

		this.add(this.lblTG);
		this.add(this.btnTG);
		this.add(this.lblTD);
		this.add(this.btnTD);
		this.add(this.lblTT);
		this.add(this.btnTT);

		t = new Thread(new ClavierCheck(this));
		t.start();

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

	/**
	 * Pour savoir lorsque l'utilisateur appuie sur un e touche
	 */
	private static class ClavierCheck implements Runnable {

		FrameOptions frameOptions;

		boolean pressed;
		int touche;

		ClavierCheck(FrameOptions frameOptions) {

			this.frameOptions = frameOptions;

			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(ke -> {
				synchronized (ClavierCheck.class) {
					if (ke.getID() == KeyEvent.KEY_PRESSED) {
						this.pressed = true;
						this.touche = ke.getKeyCode();
					}
					return false;
				}
			});
		}

		@Override
		public void run() {
			//noinspection InfiniteLoopStatement
			while (true) {
				if(this.pressed) {
					if(this.frameOptions.attendTouche) {
						if(this.frameOptions.attend == 'g') {
							if(this.touche != KeyEvent.VK_ESCAPE) {
								this.frameOptions.options.setToucheGauche(this.touche);
								this.frameOptions.btnTG.setText(KeyEvent.getKeyText(touche));
							}else {
								this.frameOptions.btnTG.setText(KeyEvent.getKeyText(this.frameOptions.options.toucheGauche));
							}
						}else if(this.frameOptions.attend == 'd') {
							if(this.touche != KeyEvent.VK_ESCAPE) {
								this.frameOptions.options.setToucheDroite(this.touche);
								this.frameOptions.btnTD.setText(KeyEvent.getKeyText(touche));
							}else {
								this.frameOptions.btnTD.setText(KeyEvent.getKeyText(this.frameOptions.options.toucheDroite));
							}
						}else {
							if(this.touche != KeyEvent.VK_ESCAPE) {
								this.frameOptions.options.setToucheTir(this.touche);
								this.frameOptions.btnTT.setText(KeyEvent.getKeyText(touche));
							}else {
								this.frameOptions.btnTT.setText(KeyEvent.getKeyText(this.frameOptions.options.toucheTir));
							}
						}

						this.frameOptions.btnTG.setEnabled(true);
						this.frameOptions.btnTD.setEnabled(true);
						this.frameOptions.btnTT.setEnabled(true);
						pressed = false;
						frameOptions.attendTouche = false;
					}else {
						pressed = false;
					}
				}
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void windowClosing(WindowEvent e)
	{
		t.interrupt();
	}

	public void windowClosed(WindowEvent e){}
	public void windowDeactivated(WindowEvent e){}
	public void windowActivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}

}
