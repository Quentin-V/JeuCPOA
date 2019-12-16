import simbad.gui.Simbad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Frame pour créer une partir personnalisée
 */
public class FrameCustom extends JFrame implements ActionListener, WindowListener {

	private IHM ihm;

	private JTextField tfNbMun, tfNbRobots, tfVitesse;

	/**
	 * Constructeur qui crée la frame
	 * @param ihm l'IHM de la partie
	 */
	FrameCustom(IHM ihm) {
		this.ihm = ihm;

		this.setTitle("Custom");
		this.setSize(500,170);
		this.setLocation(250, 400);
		this.setResizable(false);

		JPanel pnlCentre = new JPanel(new GridLayout(3,2));

		JLabel lblNbMun, lblNbRobots, lblVitesse;
		JButton btnConfirmer;

		lblNbMun    = new JLabel("Munitions (entier)");
		lblNbRobots = new JLabel("Robots ennemis (entier)");
		lblVitesse  = new JLabel("Vitesse des robots (nb décimal)");


		tfNbMun    = new JTextField(5);
		tfNbRobots = new JTextField(5);
		tfVitesse  = new JTextField(8);

		btnConfirmer = new JButton("Lancer la partie");
		btnConfirmer.addActionListener(this);

		pnlCentre.add(lblNbMun);
		pnlCentre.add(tfNbMun);
		pnlCentre.add(lblNbRobots);
		pnlCentre.add(tfNbRobots);
		pnlCentre.add(lblVitesse);
		pnlCentre.add(tfVitesse);

		this.add(pnlCentre);
		this.add(btnConfirmer, "South");

		this.setVisible(true);
	}

	/**
	 * Méthode qui crée la partie personnalisée si tous les paramètres sont bons
	 * @param actionEvent l'évènement déclenché
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		try {
			int nbMun      = Integer.parseInt(tfNbMun.getText().replaceAll(" ", ""));
			int nbRobots   = Integer.parseInt(tfNbRobots.getText().replaceAll(" ", ""));
			double vitesse = Double.parseDouble(tfVitesse.getText().replaceAll(" ", ""));

			Simbad s = new Simbad(new Environnement(nbMun, nbRobots, vitesse, ihm.options, "classic"), false);
			s.setSize(1280, 720);
			s.setTitle("Simbad Shooter Custom");

			this.ihm.dispose();
			this.dispose();
		}catch(NumberFormatException e) {
			new FrameErreur("Un des paramètres n'est pas correct");
		}
	}

	public void windowClosing(WindowEvent e)
	{
		new IHM(ihm.options);
	}

	public void windowClosed(WindowEvent e){}
	public void windowDeactivated(WindowEvent e){}
	public void windowActivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}
}
