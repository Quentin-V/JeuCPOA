import java.awt.event.KeyEvent;

public class Options {
	private int toucheGauche;
	private int toucheDroite;
	private int toucheTir;
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

	int getToucheGauche() {
		return toucheGauche;
	}

	int getToucheDroite() {
		return toucheDroite;
	}

	int getToucheTir() {
		return toucheTir;
	}
}
