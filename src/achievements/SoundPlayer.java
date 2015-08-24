package achievements;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {
	
	private Logger logger = Logger.getAnonymousLogger();
	
	public void play(String file) {
		try {
			// get sound file and play it
			URL location = SoundPlayer.class.getResource("/sound/" + file);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(location);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			logger.log(Level.SEVERE, "[SoundPlayer] Error", e1);
		}	
	}
}
