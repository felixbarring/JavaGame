package modell.animation;

import java.awt.image.BufferedImage;

public class Animation {

	AnimationFrames aniF;
	int currFrame;
	double tick;
	
	public Animation(AnimationFrames a){
		aniF = a;
		resetAnimation();
	}
	
	public void resetAnimation(){
		currFrame = 0;
		tick = 0;
	}
	
	public void update(double t){
		tick = tick+t;
		while(tick > aniF.frames.get(currFrame).frameTime){
			tick = tick-aniF.frames.get(currFrame).frameTime;
			currFrame++;
			if (currFrame == aniF.frames.size()){
				currFrame  = 0;
			}
		}
	}
	
	public BufferedImage getImage(){
		return aniF.frames.get(currFrame).img;
	}
	
	public int getImgHalfWidth(){
		return aniF.frames.get(currFrame).halfWidth;
	}
	
	public int getImgHalfHeight(){
		return aniF.frames.get(currFrame).halfHeight;
	}
	
}
