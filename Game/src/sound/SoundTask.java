package sound;

import java.io.IOException;
import javax.sound.sampled.*;

class SoundTask implements Runnable{

	/*
	 * Should fix so that pan and volume can be updated smoothly.
	 * Should fix so that volume can be changed.
	 */

	static ThreadPool threadPool = null;
	private AudioInputStream stream;
	private AudioFormat format;

	private boolean stopPlayback = false;
	private FloatControl volume_control;
	private BooleanControl mute_control;
	private FloatControl pan_control;
	private SourceDataLine line;

	public SoundTask(AudioInputStream s){
		stream = s;
		format = s.getFormat();
		init();
	}

	public synchronized void stop(){
		stopPlayback = true;
	}

	public void setPan(float i){
		pan_control.setValue(i);
	}
	// Wtf, dosnt work
	public void setVolume(float v){
		volume_control.setValue((float) (Math.log(v/100.0f) / Math.log(10.0f) * 20.0f));
	}

	public void init(){
		try	{
			SourceDataLine.Info info = new DataLine.Info(
					SourceDataLine.class,
					stream.getFormat(),
					(int) (stream.getFrameLength() * format.getFrameSize()));

			line = (SourceDataLine) AudioSystem.getLine(info);

			//volume_control = (FloatControl) line.getControl(FloatControl.Type.MASTER_GAIN);
			//mute_control = (BooleanControl) line.getControl(BooleanControl.Type.MUTE);
			//pan_control = (FloatControl) line.getControl(FloatControl.Type.PAN);

		}catch (Exception ex){ 
			ex.printStackTrace();
		}
	}

	public void run(){
		System.out.println("Playing!");

		try {
			line.open(stream.getFormat());
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
		line.start();

		int num_read = 0;
		byte[] buf = new byte[line.getBufferSize()];

		try {
			while ((num_read = stream.read(buf, 0, buf.length)) >= 0){
				int offset = 0;
				while (offset < num_read){
					offset += line.write(buf, offset, num_read - offset);
				}
				// Thread safe?
				synchronized (this){
					if (stopPlayback){
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		line.drain();
		line.stop();
	}
}
