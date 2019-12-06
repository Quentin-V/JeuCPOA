import java.awt.event.KeyEvent;

class Options {
	int toucheGauche;
	int toucheDroite;
	int toucheTir;
	Options() {
		toucheGauche = KeyEvent.VK_LEFT;
		toucheDroite = KeyEvent.VK_RIGHT;
		toucheTir    = KeyEvent.VK_UP;
	}

	void setToucheGauche(int toucheGauche) {
		this.toucheGauche = toucheGauche;
	}

	void setToucheDroite(int toucheDroite) {
		this.toucheDroite = toucheDroite;
	}

	void setToucheTir(int toucheTir) {
		this.toucheTir = toucheTir;
	}
}
