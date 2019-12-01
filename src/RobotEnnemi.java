import simbad.sim.*;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class RobotEnnemi extends Agent {

	RobotEnnemi(Vector3d position, String name) {
		super(position, name);
		this.setColor(new Color3f(255, 0, 0));
		//RangeSensorBelt sonar = RobotFactory.addSonarBeltSensor(this);
	}
	public void performBehavior() {
		this.setTranslationalVelocity(1);
		if(this.getCounter() == 1) {
			rotateToJoueur();
		}
		/*
		if(this.sonar.hasHit(0)) {
			this.moveToPosition(new Vector3d(1000, 1000, 1000));
		}
		if(this.collisionDetected()) {
			this.moveToPosition(new Vector3d(1000, 1000, 1000));
		}
		*/
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