import javafx.scene.media.AudioClip;

import java.nio.file.Paths;
import java.util.HashMap;

abstract class SoundEffects {

	static HashMap<String, AudioClip> sounds = new HashMap<>();

	static {
		sounds.put("launcher", new AudioClip(Paths.get(SoundEffects.class.getResource("/simbad_shooter.wav").toString().substring(6)).toUri().toString()));
		sounds.put("tourner", new AudioClip(Paths.get(SoundEffects.class.getResource("/tourner.wav").toString().substring(6)).toUri().toString()));
		sounds.put("tir", new AudioClip(Paths.get(SoundEffects.class.getResource("/tir.wav").toString().substring(6)).toUri().toString()));
	}

}
