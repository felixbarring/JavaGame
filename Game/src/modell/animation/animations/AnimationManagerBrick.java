package modell.animation.animations;

import java.awt.image.BufferedImage;

import modell.animation.Animation;
import modell.animation.AnimationFrameLoader;

public class AnimationManagerBrick {

	Animation brick = new Animation(AnimationFrameLoader.brick);

	boolean wasExploded = true;
	BufferedImage img;
	public  void update(double tickTime){
		brick.update(tickTime);
		img = brick.getImage();
		wasExploded = true;
		img = brick.getImage();
	}
	public void update(double tickTime, int x, int y) {
		brick.update(tickTime);
	}
	
	public BufferedImage getImage() {
		return brick.getImage();
	}
	public int getImgHalfWidth() {
		return brick.getImgHalfWidth();
	}
	public int getImgHalfHeight() {
		return brick.getImgHalfHeight();
	}
	
}
