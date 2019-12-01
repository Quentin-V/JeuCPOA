import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;

import javax.vecmath.Vector3d;
import java.util.ArrayList;

class Environnement extends EnvironmentDescription {
	private ArrayList<Tir> tirs;
	private int numTir;

	Environnement(){
		this.light1SetPosition(-20,8,0);
		add(new Wall(new Vector3d(0,0,10), 20, 5, this));
		add(new Wall(new Vector3d(0,0,-10), 20, 5, this));
		Wall mur = new Wall(new Vector3d(10,0,0), 20, 5, this);
		mur.rotate90(1);
		add(mur);
		mur = new Wall(new Vector3d(-10,0,0), 20, 5, this);
		mur.rotate90(1);
		add(mur);
		RobotJoueur rbtJoueur = new RobotJoueur(new Vector3d(0, 0.25, 0), "Joueur", this);
		add(rbtJoueur);
		for(int i = 0; i < 15; i++) {
			addEnnemi(i);
		}
		tirs = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			tirs.add(new Tir(new Vector3d(1000, -5, 1000), "tir"));
		}
		for(Tir tir : tirs) this.add(tir);
	}

	private void addEnnemi(int n) {
		int randX = (int) (Math.random() * 4);
		randX = Math.random() < 0.5 ? -(randX+5) : (randX+5);
		int randZ = (int) (Math.random() * 4);
		randZ = Math.random() < 0.5 ? -(randZ+5) : (randZ+5);
		this.add(new RobotEnnemi(new Vector3d(randX, 0.25, randZ), "e" + n));
	}

	void tirer(double angleDirection) {
		if(numTir != tirs.size()) {
			Tir leBonTir = tirs.get(numTir);
			leBonTir.resetPos();
			leBonTir.rotateY(angleDirection);
			leBonTir.tirer();
			++numTir;
		}else {
			numTir = 0;
			Tir leBonTir = tirs.get(numTir);
			leBonTir.resetPos();
			leBonTir.rotateY(angleDirection);
			leBonTir.tirer();
		}
	}
}