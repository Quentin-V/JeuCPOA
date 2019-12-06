import simbad.sim.Agent;

import javax.vecmath.Vector3d;
import java.awt.*;
import java.awt.event.KeyEvent;

public class RobotJoueur extends Agent {

	private double angleDirection;
	private Environnement env;
	private Options options;

	RobotJoueur(Vector3d position, String name, Environnement env, Options options) {
		super(position, name);
		this.env = env;
		this.options = options;
		this.setCanBeTraversed(true);
		Thread t = new Thread(new ClavierCheck(this));
		t.start();
	}

	public void performBehavior() {
		if(anOtherAgentIsVeryNear() && getVeryNearAgent() instanceof RobotEnnemi) {
			System.exit(0);
		}
	}

	private void rotationGauche() {
		this.rotateY(1.0/360 * 2 * Math.PI);
		if(this.angleDirection + 1.0/360 * 2 * Math.PI >= 2 * Math.PI + 0.01 || this.angleDirection + 1.0/360 * 2 * Math.PI >= 2 * Math.PI - 0.01) { // Pour la marge d'erreur double
			this.angleDirection = 0;
		}else {
			this.angleDirection += 1.0/360 * 2 * Math.PI;
		}
	}
	private void rotationDroite() {
		this.rotateY(-1.0/360 * 2 * Math.PI);
		if(this.angleDirection - 1.0/360 * 2 * Math.PI <= 0) { // Pour la marge d'erreur double
			this.angleDirection = 2 * Math.PI;
		}else {
			this.angleDirection -= 1.0/360 * 2 * Math.PI;
		}
	}

	private static class ClavierCheck implements Runnable {

		RobotJoueur rbtJoueur;
		boolean GPressed;
		boolean DPressed;
		boolean aTire;

		ClavierCheck(RobotJoueur rbtJoueur) {
			this.rbtJoueur = rbtJoueur;
			GPressed = false;
			DPressed = false;
			this.aTire = false;

			int tG = rbtJoueur.options.toucheGauche;
			int tD = rbtJoueur.options.toucheDroite;
			int tT = rbtJoueur.options.toucheTir;

			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(ke -> {
				synchronized (ClavierCheck.class) {
					switch (ke.getID()) {
						case KeyEvent.KEY_PRESSED:
							if (ke.getKeyCode() == tG) {
								GPressed = true;
							}else if (ke.getKeyCode() == tD) {
								DPressed = true;
							}else if (ke.getKeyCode() == tT && !aTire) {
								aTire = true;
								rbtJoueur.env.tirer(rbtJoueur.angleDirection);
							}
							break;

						case KeyEvent.KEY_RELEASED:
							if (ke.getKeyCode() == tG) {
								GPressed = false;
							}else if(ke.getKeyCode() == tD) {
								DPressed = false;
							}else if(ke.getKeyCode() == tT) {
								aTire = false;
							}
							break;
					}
					return false;
				}
			});
		}

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
