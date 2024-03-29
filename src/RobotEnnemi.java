import simbad.sim.*;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;


/**
 * Classe des robots ennemis
 */
public class RobotEnnemi extends Agent {

	/**
	 * Si le robot est en vie
	 */
	boolean actif;
	/**
	 * La vitesse du robot
	 */
	private double vitesse;
	/**
	 * L'environnement attaché au robot
	 */
	private Environnement env;

	/**
	 * Constructeur du robot ennemi
	 * @param vitesse la vitesse du robot
	 * @param position la position initiale du robot
	 * @param name le nom du robot
	 */
	RobotEnnemi(double vitesse, Vector3d position, String name, Environnement env) {
		super(position, name);
		this.setColor(new Color3f(255, 0, 0));
		this.actif = true;
		this.vitesse = vitesse;
		this.env = env;
	}

	/**
	 * Action que le robot va effectuer pendant la simulation
	 */
	public void performBehavior() {

		if(this.actif)
			this.setTranslationalVelocity(this.vitesse);
		else
			this.setTranslationalVelocity(0);

		if(this.getCounter() == 1) {
			firstRotate();
		}

		if(this.getVeryNearAgent() instanceof Tir) {
			if(this.env.mode.equals("classic")) {
				this.moveToPosition(new Vector3d(1000, 10, 1000));
				this.actif = false;
			}else if(this.env.mode.equals("time")) {
				int[] randPos = this.env.randXZ();
				this.moveToPosition(new Vector3d(4, 0.25, 4));
				this.rotateY(90.0/360 *2*Math.PI + Math.atan(this.v1.x/this.v1.z));
			}
		}
	}



	/**
	 * Méthode utilisée en début de simulation pour que les robots se dirigent vers le centre
	 */
	private void firstRotate() {
		double x  = this.v1.x;
		double z  = this.v1.z;

		double angle;

		if(z > 0) {
			angle = (((double)90/360)*Math.PI * 2) + Math.atan(x/z);
			this.rotateY(angle);
		}else {
			angle = (((double)270/360)*Math.PI * 2) + Math.atan(x/z);
			this.rotateY(angle);
		}
	}
}