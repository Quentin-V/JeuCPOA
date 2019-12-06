import simbad.gui.Simbad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameCustom extends JFrame implements ActionListener {

	private IHM ihm;

	private JTextField tfNbMun, tfNbRobots, tfVitesse;

	FrameCustom(IHM ihm) {
		this.ihm = ihm;

		this.setTitle("Custom");
		this.setSize(500,170);
		this.setLocation(250, 400);

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

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		try {
			int nbMun      = Integer.parseInt(tfNbMun.getText().replaceAll(" ", ""));
			int nbRobots   = Integer.parseInt(tfNbRobots.getText().replaceAll(" ", ""));
			double vitesse = Double.parseDouble(tfVitesse.getText().replaceAll(" ", ""));

			new Simbad(new Environnement(nbMun, nbRobots, vitesse, ihm.options), false);
			this.ihm.dispose();
			this.dispose();

		}catch(Exception e) {
			new FrameErreur("Un des paramètres n'est pas correct");
		}
	}
}