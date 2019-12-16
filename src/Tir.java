import simbad.sim.Agent;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class Tir extends Agent {

	/**
	 * Si le tir est actif (Bouge et est sur le plateau)
	 */
	private boolean actif;

	/**
	 * Crée le tir avec une position et un nom
	 * @param position la position initiale du tir
	 * @param name le nom du tir
	 */
	Tir(Vector3d position, String name) {
		super(position, name);
		this.setColor(new Color3f(255,255,0));
		this.actif = false;
		this.radius = 0.15F; // On réduit la taille du tir
	}

	public void performBehavior() {

		if(this.actif) {
			this.setTranslationalVelocity(2);
		}
		if(this.collisionDetected()) {
			this.moveToPosition(new Vector3d(1000, -5, 1000));
			this.setTranslationalVelocity(0);
			this.actif = false;
		}

	}

	/**
	 * Réinitialise la position du tir en 0, 0
	 */
	void resetPos() {
		this.moveToPosition(new Vector3d(0, 0.25, 0));
	}

	/**
	 * Modifie la valeur d'actif à true
	 */
	void tirer() {
		this.actif = true;
	}


}
