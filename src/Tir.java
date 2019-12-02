import com.sun.j3d.utils.geometry.Cylinder;
import simbad.sim.Agent;
import simbad.sim.CherryAgent;

import javax.media.j3d.Appearance;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class Tir extends Agent {

	private boolean actif;

	Tir(Vector3d position, String name) {
		super(position, name);
		this.setColor(new Color3f(255,255,0));
		this.actif = false;
		Appearance appearance = new Appearance();
		this.body = new Cylinder(100, 100, appearance);
		this.body.setCollidable(true);
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

	void resetPos() {
		this.moveToPosition(new Vector3d(0, 0.25, 0));
	}
	void tirer() {
		this.actif = true;
	}

}
