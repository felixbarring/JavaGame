package modell.animation.animations;

import java.awt.image.BufferedImage;

import modell.animation.Animation;
import modell.animation.AnimationFrameLoader;

public class AnimationManagerPlayer   {

	private Animation walk_right 				=  new Animation(AnimationFrameLoader.pitch_walking_right);
	private Animation walk_up_right	 	= new Animation(AnimationFrameLoader.pitch_walking_up_right);
	private Animation walk_up 					= new Animation(AnimationFrameLoader.pitch_walking_up);
	private Animation walk_up_left 			= new Animation(AnimationFrameLoader.pitch_walking_up_left);
	private Animation walk_left 				= new Animation(AnimationFrameLoader.pitch_walking_left);
	private Animation walk_down_left 	= new Animation(AnimationFrameLoader.pitch_walking_down_left);
	private Animation walk_down 			= new Animation(AnimationFrameLoader.pitch_walking_down);
	private Animation walk_down_right 	= new Animation(AnimationFrameLoader.pitch_walking_down_right);

	// Use the animation for the given action
	private int lastX = 1;
	private int lastY = 0;
	BufferedImage image = null;
	private Animation currentAnimation = walk_down;

	public  void update(double tickTime, int x, int y){

		if (x == lastX && y == lastY ){
			currentAnimation.update(tickTime);
			currentAnimation.getImage();
		}

		// Gray Code

		else if(x == -1 && y == -1 ){
			changeAni(walk_up_left, tickTime, x, y);
		}else if(x == -1 && y == 0 ){
			changeAni(walk_left, tickTime, x, y);
		}else if(x == -1 && y == 1 ){
			changeAni(walk_down_left, tickTime, x, y);
		}else if(x == 0 && y == -1 ){
			changeAni(walk_up, tickTime, x, y);
		}else if(x == 0 && y == 0 ){

			//TODO a dance here lol xD
			currentAnimation.resetAnimation();
			currentAnimation.getImage();

		}else if(x == 0 && y == 1 ){
			changeAni(walk_down, tickTime, x, y);
		}else if(x == 1 && y == -1 ){
			changeAni(walk_up_right, tickTime, x, y);
		}else if(x == 1 && y == 0 ){
			changeAni(walk_right, tickTime, x, y);
		}else if(x == 1 && y == 1 ){
			changeAni(walk_down_right, tickTime, x, y);
		}
	}

	private void changeAni(Animation a, double tickTime, int x, int y){
		lastX = x;
		lastY = y;
		currentAnimation = a;
		currentAnimation.resetAnimation();
		currentAnimation.update(tickTime);
	}

	public BufferedImage getImage() {
		return currentAnimation.getImage();
	}

	public int getImgHalfWidth() {
		return currentAnimation.getImgHalfWidth();
	}

	public int getImgHalfHeight() {
		return currentAnimation.getImgHalfHeight();
	}

}
