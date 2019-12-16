import javafx.scene.media.AudioClip;

import java.nio.file.Paths;
import java.util.HashMap;

abstract class SoundEffects {

	/**
	 * HashMap qui contient les sons
	 */
	static HashMap<String, AudioClip> sounds = new HashMap<>();

	// Charge et stock les effets sonores
	static {
		AudioClip clip = new AudioClip(Paths.get(SoundEffects.class.getResource("simbad_shooter.wav").toString().substring(6)).toUri().toString());
		clip.setVolume(0.2);
		sounds.put("launcher", clip);
		sounds.put("tourner", new AudioClip(Paths.get(SoundEffects.class.getResource("tourner.wav").toString().substring(6)).toUri().toString()));
		clip = new AudioClip(Paths.get(SoundEffects.class.getResource("tir.wav").toString().substring(6)).toUri().toString());
		clip.setVolume(0.15);
		sounds.put("tir", clip);
	}

}
