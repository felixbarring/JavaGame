package modell.animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;

public class AnimationFrames {

	final List<Frame> frames = new LinkedList<Frame>();
	private int totalFrameTime = 0;

	public AnimationFrames(int rows, int cols, int rowsStart, int rowsEnd,
			int colsStart, int colsEnd, String fileName, int frameTime){
		try {
			BufferedImage bigImg = ImageIO.read(new File(fileName));
			final int width = bigImg.getWidth()/cols;
			final int height = bigImg.getHeight()/rows;

			for (int i = rowsStart; i <= rowsEnd; i++){
				for (int j = colsStart; j <= colsEnd; j++){
					frames.add(new Frame(bigImg.getSubimage(
							j*width, i*height, width, height), frameTime));
					totalFrameTime = totalFrameTime+frameTime;
				}
			}
		} catch(IOException e){e.printStackTrace();}
	}

	int getTotalFrameTime(){
		return totalFrameTime;
	}

	class Frame{
		final BufferedImage img;
		final int frameTime;
		final int halfWidth;
		final int halfHeight;

		protected Frame(BufferedImage i, int t){
			img = i;
			frameTime = t;
			halfWidth = img.getWidth()/2;
			halfHeight = img.getHeight()/2;
		}
	}

}
