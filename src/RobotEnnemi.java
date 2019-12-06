import com.sun.j3d.utils.geometry.Cylinder;
import simbad.sim.*;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class RobotEnnemi extends Agent {

	boolean actif;
	private double vitesse;

	RobotEnnemi(double vitesse, Vector3d position, String name) {
		super(position, name);
		this.setColor(new Color3f(255, 0, 0));
		this.body = new Cylinder(3, 3);
		this.body.setPickable(true);
		this.body.setCollidable(true);
		this.actif = true;
		this.vitesse = vitesse;
	}
	public void performBehavior() {

		if(this.actif)
			this.setTranslationalVelocity(this.vitesse);
		else
			this.setTranslationalVelocity(0);

		if(this.getCounter() == 1) {
			rotateToJoueur();
		}

		if(anOtherAgentIsVeryNear() && this.getVeryNearAgent() instanceof Tir) {
			this.moveToPosition(new Vector3d(1000, 10, 1000));
			this.actif = false;
		}

	}

	private void rotateToJoueur() {
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