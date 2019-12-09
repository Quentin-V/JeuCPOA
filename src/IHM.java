import simbad.gui.Simbad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe qui doit être lancée par l'utilisateur pour démarrer ses parties
 */
public class IHM extends JFrame implements ActionListener {

	Options options;

	/**
	 * Constructeur qui lance le launcher
	 */
	private IHM() {

		this.options = new Options();

		this.setTitle("CPOA");
		this.setLocation(100,200);
		this.setSize(400, 200);

		this.setResizable(false);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setLayout(new GridLayout(4, 1));

		JPanel pnlTitre = new JPanel(new BorderLayout());
		JLabel titre = new JLabel("SIMBAD SHOOTER !");
		pnlTitre.add(titre);

		JPanel pnlGame = new JPanel();
		JButton btnCampagne = new JButton("Campagne");
		pnlGame.add(btnCampagne);
		JButton btnTime = new JButton("Time");
		pnlGame.add(btnTime);
		JButton btnCustom = new JButton("Custom");
		pnlGame.add(btnCustom);

		btnCampagne.addActionListener(this);
		btnCampagne.setActionCommand("campagne");

		btnCustom.addActionListener(this);
		btnCustom.setActionCommand("custom");

		btnTime.addActionListener(this);
		btnTime.setActionCommand("time");

		JPanel pnlRegles = new JPanel();
		pnlRegles.add(new JButton("Regles"));

		JPanel pnlOptions = new JPanel();
		JButton btnOptions = new JButton("Options");
		btnOptions.addActionListener(this);
		btnOptions.setActionCommand("options");
		pnlOptions.add(btnOptions);

		this.add(pnlTitre);
		this.add(pnlGame);
		this.add(pnlRegles);
		this.add(pnlOptions);

		this.setVisible(true);
	}

	/**
	 * Crée une nouvelle instance de IHM
	 */
	public static void main(String[] args) {
		new IHM();
	}

	/**
	 * Lis le bouton sur lequel l'utilisateur a appuyé et fait le nécessaire
	 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		switch (actionEvent.getActionCommand()) {
			case "options":
				new FrameOptions(this.options);
				break;
			case "campagne":
				Simbad s = new Simbad(new Environnement(10, 10, 1, options, "classic"), false);
				s.setSize(1280, 720);
				s.setTitle("Simbad Shooter Classic");
				break;
			case "time":
				Simbad s1 = new Simbad(new Environnement(20, 2, 0.5, options, "time"), false);
				s1.setSize(1280, 720);
				s1.setTitle("Simbad Shooter Time");
				break;
			case "custom":
				new FrameCustom(this);
				break;
		}
		this.dispose();
	}
}
