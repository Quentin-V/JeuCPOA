import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

class FrameErreur extends JFrame {
	FrameErreur(String s) {
		this.setLayout(new FlowLayout());
		JLabel lblWarn = new JLabel();
		try {
			lblWarn.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/warn.png"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.add(lblWarn);
		this.add(new JLabel(s));
	}
}
