import simbad.gui.Simbad;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Classe qui doit être lancée par l'utilisateur pour démarrer ses parties
 */
public class IHM extends JFrame implements ActionListener {

	Options options;

	/**
	 * Constructeur qui lance le launcher
	 */
	IHM() {

		this.options = new Options();

		this.setTitle("Simbad shooter");
		this.setLocation(100,200);
		this.setSize(640, 360);

		this.setResizable(true);

		Background bg = new Background();

		this.setContentPane(bg);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.setLayout(null);

		Font font = new Font("Arial", Font.PLAIN, 36);

		JPanel pnlTitre = new JPanel(new BorderLayout());
		JLabel titre = new JLabel("SIMBAD SHOOTER !");
		titre.setFont(font);
		titre.setBounds(140, 10, 380, 40);
		//pnlTitre.add(titre);

		//JPanel pnlGame = new JPanel();
		JButton btnCampagne = new JButton("Campagne");
		btnCampagne.setBounds(140, 233, 100, 40);
		//pnlGame.add(btnCampagne);
		//JButton btnTime = new JButton("Time");
		//pnlGame.add(btnTime);
		JButton btnCustom = new JButton("Custom");
		btnCustom.setBounds(380, 233, 100, 40);
		//pnlGame.add(btnCustom);

		btnCampagne.addActionListener(this);
		btnCampagne.setActionCommand("campagne");

		btnCustom.addActionListener(this);
		btnCustom.setActionCommand("custom");

		//btnTime.addActionListener(this);
		//btnTime.setActionCommand("time");

		//JPanel pnlRegles = new JPanel();
		//pnlRegles.add(new JButton("Regles"));
		JButton btnRegles = new JButton("Regles");
		btnRegles.addActionListener(this);
		btnRegles.setActionCommand("regles");
		btnRegles.setBounds(264, 228, 90, 30);

		//JPanel pnlOptions = new JPanel();
		JButton btnOptions = new JButton("Options");
		btnOptions.addActionListener(this);
		btnOptions.setActionCommand("options");
		btnOptions.setBounds(264, 264, 90, 30);

		//pnlOptions.add(btnOptions);

		this.add(titre);
		this.add(btnCampagne);
		this.add(btnCustom);
		this.add(btnRegles);
		this.add(btnOptions);

		this.setVisible(true);

		SoundEffects.sounds.get("launcher").play();
	}

	IHM(Options options) {
		this();
		this.options = options;
	}

	private static class Background extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D)g;

			Image im = null;
			try {
				im = ImageIO.read(getClass().getResource("/bg_simbad_shooter.png"));
				im = im.getScaledInstance(640, 360, Image.SCALE_DEFAULT);
			} catch (IOException e) {
				e.printStackTrace();
			}

			g2.drawImage(im, 0, 0, null);
		}
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
				Simbad s = new Simbad(new Environnement(15, 10, 1, options, "classic"), false);
				s.setSize(1280, 720);
				s.setTitle("Simbad Shooter Classic");
				this.dispose();
				break;
			case "time":
				Simbad s1 = new Simbad(new Environnement(20, 2, 0.5, options, "time"), false);
				s1.setSize(1280, 720);
				s1.setTitle("Simbad Shooter Time");
				this.dispose();
				break;
			case "custom":
				new FrameCustom(this);
				this.dispose();
				break;
			case "regles" :
				String texte = "SimbadShooter est un jeu de tir dans lequel le joueur (robot vert) doit se defendre face a des ennemis (robots rouges) \n Pour controler le joueur, utilisez les fleches directionnelles : \n         - droite / gauche pour pivoter \n         - haut pour tirer \n Ces touches sont modifiables dans les options";
				System.out.print("debug");
				//new JOptionPane();
				JOptionPane.showMessageDialog(null, texte, "Regles", JOptionPane.PLAIN_MESSAGE);;

		}
	}
}
