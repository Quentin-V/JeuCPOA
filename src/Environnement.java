import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;

import javax.vecmath.Vector3d;
import java.util.ArrayList;

/**
 * Environnement simbad
 */
class Environnement extends EnvironmentDescription {
	private ArrayList<Tir> tirs;
	private int numTir;

	private double vitesseEnnemi;

	private ArrayList<RobotEnnemi> ennemis;
	private RobotJoueur rbtJoueur;

	String mode;


	/**
	 * Constructeur de l'environnement, crée tous les éléments nécessaires au lancement d'une partie
	 * @param nbMunitions nombre de munitions du robot joueur
	 * @param nbEnnemis nombre d'ennemis
	 * @param vitesseEnnemi vitesse des ennemis
	 * @param options options de la partie --> Touches de jeu
	 */
	Environnement(int nbMunitions, int nbEnnemis, double vitesseEnnemi, Options options, String mode) {

		this.mode = mode;

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
		rbtJoueur = new RobotJoueur(new Vector3d(0, 0.25, 0), "Joueur", this, options);
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

	/**
	 * Ajoute un ennemi
	 * @param n numéro du robot
	 */
	private void addEnnemi(int n) {
		int[] randPos = randXZ();
		RobotEnnemi ennemi = new RobotEnnemi(this.vitesseEnnemi, new Vector3d(randPos[0], 0.25, randPos[1]), "e" + n, this);
		ennemis.add(ennemi);
		this.add(ennemi);
	}

	int[] randXZ() {
		int randX = (int) (Math.random() * 4);
		randX = Math.random() < 0.5 ? -(randX+5) : (randX+5);
		int randZ = (int) (Math.random() * 4);
		randZ = Math.random() < 0.5 ? -(randZ+5) : (randZ+5);
		return new int[] {randX, randZ};
	}

	/**
	 * Envoie un tir sur le terrain
	 * @param angleDirection direction du tir
	 */
	void tirer(double angleDirection) {
		SoundEffects.sounds.get("tir").play();
		if(numTir != tirs.size()) {
			Tir leBonTir = tirs.get(numTir);
			leBonTir.resetPos();
			leBonTir.rotateY(angleDirection);
			leBonTir.tirer();
			++numTir;
		}else if(this.mode.equals("time")) {
			numTir = 0;
			Tir leBonTir = tirs.get(numTir);
			leBonTir.resetPos();
			leBonTir.rotateY(angleDirection);
			leBonTir.tirer();
		}
	}

	/**
	 * Renvoie true si tous les ennemis ne sont plus actifs
	 * @return l'état de la partie
	 */
	boolean partieFinie() {
		for(RobotEnnemi ennemi : ennemis) {
			if(ennemi.actif) return false;
		}
		return true;
	}

	public boolean getFin(){
		return rbtJoueur.fin;
	}

	@Override
	public void accueil() {
		new IHM(rbtJoueur.options);
	}

}