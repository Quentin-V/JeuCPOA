import simbad.gui.Simbad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IHM extends JFrame implements ActionListener {

	private Options options;

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

	public static void main(String[] args) {
		new IHM();
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		switch (actionEvent.getActionCommand()) {
			case "options":
				new FrameOptions(this.options);
				break;
			case "campagne":
				new Simbad(new Environnement(10, 10, 1, options), false);
				break;
		}
	}
}
