package modell.animation.animations;

import java.awt.image.BufferedImage;

import modell.animation.Animation;
import modell.animation.AnimationFrameLoader;

public class AnimationManagerEnemy {

	Animation walk_right = new Animation(AnimationFrameLoader.enemy_walking_right);
	Animation walk_up = new Animation(AnimationFrameLoader.enemy_walking_up);
	Animation walk_left = new Animation(AnimationFrameLoader.enemy_walking_left);
	Animation walk_down = new Animation(AnimationFrameLoader.enemy_walking_down);

	public AnimationManagerEnemy(){}

	int lastX = 0;
	Animation currentAni = walk_right;
	BufferedImage img;
	
	public  void update(double tickTime, int x){
		
		if (x == lastX || x == 0){
			
		} else if (x == 1){
			currentAni = walk_right;
		} else if (x == -1){
			currentAni = walk_left;
		}
		currentAni.update(tickTime);
		img = currentAni.getImage();
		lastX = x;
	}
	
	public BufferedImage getImage() {
		return img;
	}
	public int getImgHalfWidth() {
		return currentAni.getImgHalfWidth();
	}
	public int getImgHalfHeight() {
		return currentAni.getImgHalfHeight();
	}
}
