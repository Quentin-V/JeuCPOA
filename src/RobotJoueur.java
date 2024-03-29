import simbad.sim.Agent;

import javax.vecmath.Vector3d;
import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * Classe du robot du joueur
 */
public class RobotJoueur extends Agent {

	private double angleDirection;// Direction du robot
	private Environnement env; // Environnement Simbad
	Options options; // Options (touches)

	boolean fin = false; // Si la partie est finie ou non

	/**
	 * Constructeur du robot du joueur
	 * @param position position initiale du robot
	 * @param name nom du robot
	 * @param env envronnement du robot
	 * @param options options du jeu
	 */
	RobotJoueur(Vector3d position, String name, Environnement env, Options options) {
		super(position, name);
		this.env = env;
		this.options = options;
		Thread t = new Thread(new ClavierCheck(this));
		t.start();
	}

	/**
	 * Action que le robot effectuera pendant la simulation
	 */
	public void performBehavior() {
		if(anOtherAgentIsVeryNear() && getVeryNearAgent() instanceof RobotEnnemi && !fin) {
			fin = true;
			new IHM(options);
		}
		if(getCounter() % 10 == 0 && env.partieFinie() && !fin) {
			System.out.println("Bravo, vous avez gagné");
			fin = true;
			new IHM(options);
		}
	}

	/**
	 * Tourne d'un degré sur la gauche
	 */
	private void rotationGauche() {
		this.rotateY(1.0/360 * 2 * Math.PI);
		if(this.angleDirection + 1.0/360 * 2 * Math.PI >= 2 * Math.PI + 0.01 || this.angleDirection + 1.0/360 * 2 * Math.PI >= 2 * Math.PI - 0.01) { // Pour la marge d'erreur double
			this.angleDirection = 0;
		}else {
			this.angleDirection += 1.0/360 * 2 * Math.PI;
		}
	}

	/**
	 * Tourne d'un degré sur la droite
	 */
	private void rotationDroite() {
		this.rotateY(-1.0/360 * 2 * Math.PI);
		if(this.angleDirection - 1.0/360 * 2 * Math.PI <= 0) { // Pour la marge d'erreur double
			this.angleDirection = 2 * Math.PI;
		}else {
			this.angleDirection -= 1.0/360 * 2 * Math.PI;
		}
	}

	/**
	 * Classe qui vérifie l'entrée clavier de l'utilisateur pour effectuer les actions du robot
	 */
	private static class ClavierCheck implements Runnable {

		RobotJoueur rbtJoueur;
		boolean GPressed; // Si la touche pour tourner à gauche est pressée
		boolean DPressed; // Si la touche pour tourner à droite est pressée
		boolean aTire;    // Si le joueur a tiré

		boolean playingSound; // Si un son est en cours

		/**
		 * Constructeur qui crée le KeyboardFocusManager nécessaire pour connaitre les touches appuyées par l'utilisateur
		 * @param rbtJoueur le robot du joueur
		 */
		ClavierCheck(RobotJoueur rbtJoueur) {
			this.rbtJoueur = rbtJoueur;
			GPressed = false;
			DPressed = false;
			this.aTire = false;


			int tG = rbtJoueur.options.toucheGauche; // Touche pour tourner à gauche
			int tD = rbtJoueur.options.toucheDroite; // Touche pour tourner à droite
			int tT = rbtJoueur.options.toucheTir;    // Touche de tir

			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(ke -> {
				synchronized (ClavierCheck.class) {
					switch (ke.getID()) {
						case KeyEvent.KEY_PRESSED: // Lorsque l'on appuie sur une touche
							if (ke.getKeyCode() == tG) {
								GPressed = true;
								if(!this.playingSound)
									SoundEffects.sounds.get("tourner").play();
								this.playingSound = true;
							}else if (ke.getKeyCode() == tD) {
								DPressed = true;
								if(!this.playingSound)
									SoundEffects.sounds.get("tourner").play();
								this.playingSound = true;
							}else if (ke.getKeyCode() == tT && !aTire) {
								aTire = true; // Pour éviter de tirer à l'infini en restant appuyé
								rbtJoueur.env.tirer(rbtJoueur.angleDirection);
							}
							break;

						case KeyEvent.KEY_RELEASED:
							if (ke.getKeyCode() == tG) {
								GPressed = false;
								SoundEffects.sounds.get("tourner").stop();
								this.playingSound = false;
							}else if(ke.getKeyCode() == tD) {
								DPressed = false;
								SoundEffects.sounds.get("tourner").stop();
								this.playingSound = false;
							}else if(ke.getKeyCode() == tT) {
								aTire = false;
							}
							break;
					}
					return false;
				}
			});
		}

		/**
		 * Méthode qui vérifie si une touche est appuyée et fait tourner le robot si besoin
		 */
		@Override
		public void run() {
			//noinspection InfiniteLoopStatement
			while (true) {
				if (DPressed) {
					rbtJoueur.rotationDroite();
				} else if (GPressed) {
					rbtJoueur.rotationGauche();
				}
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
