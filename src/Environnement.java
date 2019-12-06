import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;

import javax.vecmath.Vector3d;
import java.util.ArrayList;

class Environnement extends EnvironmentDescription {
	private ArrayList<Tir> tirs;
	private int numTir;

	private double vitesseEnnemi;

	ArrayList<RobotEnnemi> ennemis;

	Environnement(int nbMunitions, int nbEnnemis, double vitesseEnnemi, Options options) {

		this.ennemis = new ArrayList<>();

		// Configuration de lalumière et des murs
		this.light1SetPosition(-20,8,0);
		add(new Wall(new Vector3d(0,0,10), 20, 5, this));
		add(new Wall(new Vector3d(0,0,-10), 20, 5, this));
		Wall mur = new Wall(new Vector3d(10,0,0), 20, 5, this);
		mur.rotate90(1);
		add(mur);
		mur = new Wall(new Vector3d(-10,0,0), 20, 5, this);
		mur.rotate90(1);
		add(mur);

		// Ajout des robots
		RobotJoueur rbtJoueur = new RobotJoueur(new Vector3d(0, 0.25, 0), "Joueur", this, options);
		add(rbtJoueur);
		this.vitesseEnnemi = vitesseEnnemi;
		for(int i = 0; i < nbEnnemis; i++) {
			addEnnemi(i);
		}
		tirs = new ArrayList<>();
		for(int i = 0; i < nbMunitions; i++) {
			tirs.add(new Tir(new Vector3d(1000, -5, 1000), "tir"));
		}
		for(Tir tir : tirs) this.add(tir);
	}

	private void addEnnemi(int n) {
		int randX = (int) (Math.random() * 4);
		randX = Math.random() < 0.5 ? -(randX+5) : (randX+5);
		int randZ = (int) (Math.random() * 4);
		randZ = Math.random() < 0.5 ? -(randZ+5) : (randZ+5);
		RobotEnnemi ennemi = new RobotEnnemi(this.vitesseEnnemi, new Vector3d(randX, 0.25, randZ), "e" + n);
		ennemis.add(ennemi);
		this.add(ennemi);
	}

	void tirer(double angleDirection) {
		if(numTir != tirs.size()) {
			Tir leBonTir = tirs.get(numTir);
			leBonTir.resetPos();
			leBonTir.rotateY(angleDirection);
			leBonTir.tirer();
			++numTir;
		}
	}

	boolean partieFinie() {
		for(RobotEnnemi ennemi : ennemis) {
			if(ennemi.actif) return false;
		}
		return true;
	}

}