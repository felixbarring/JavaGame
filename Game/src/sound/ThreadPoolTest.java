package sound;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

class ThreadPoolTest {

	public static void main(String[] banana){
		ThreadPool pool = new ThreadPool(2);
		File sound1 = new File("resources/sound/Lisa Miskovsky - Still Alive (Alternate Version).wav");
		File sound2 = new File("resources/sound/KF_Abandon.wav");

		AudioInputStream stream;
		AudioInputStream stream2;
		
		try {
			stream = AudioSystem.getAudioInputStream(sound1);
			stream2 = AudioSystem.getAudioInputStream(sound2);

			SoundTask s1 = new SoundTask(stream);
			SoundTask s2 = new SoundTask(stream2);

			pool.addTask(s1);
			//pool.addTask(s2);

		} catch (UnsupportedAudioFileException e1){
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
