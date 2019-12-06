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
		boolean flGPressed;
		boolean flDPressed;
		boolean aTire;
		ClavierCheck(RobotJoueur rbtJoueur) {
			this.rbtJoueur = rbtJoueur;
			flGPressed = false;
			flDPressed = false;
			this.aTire = false;
			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(ke -> {
				synchronized (ClavierCheck.class) {
					switch (ke.getID()) {
						case KeyEvent.KEY_PRESSED:
							if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
								flGPressed = true;
							}else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
								flDPressed = true;
							}else if (ke.getKeyCode() == KeyEvent.VK_UP && !aTire) {
								aTire = true;
								rbtJoueur.env.tirer(rbtJoueur.angleDirection);
							}
							break;

						case KeyEvent.KEY_RELEASED:
							if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
								flGPressed = false;
							}else if(ke.getKeyCode() == KeyEvent.VK_RIGHT) {
								flDPressed = false;
							}else if(ke.getKeyCode() == KeyEvent.VK_UP) {
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
				if (flDPressed) {
					rbtJoueur.rotationDroite();
				} else if (flGPressed) {
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
