package modell.animation;

public class AnimationFrameLoader{

	static String pitch = "resources/images/spr_pitch2.png";
	static int time = 10;
	final static public AnimationFrames pitch_walking_right = new AnimationFrames(1, 80, 0, 0, 0, 7, pitch, time);
	final static public AnimationFrames pitch_walking_up_right = new AnimationFrames(1, 80, 0, 0, 8, 15, pitch, time);
	final static public AnimationFrames pitch_walking_up = new AnimationFrames(1, 80, 0, 0, 16, 23, pitch, time);
	final static public AnimationFrames pitch_walking_up_left = new AnimationFrames(1, 80, 0, 0, 24, 31, pitch, time);
	final static public AnimationFrames pitch_walking_left = new AnimationFrames(1, 80, 0, 0, 32, 39, pitch, time);
	final static public AnimationFrames pitch_walking_down_left = new AnimationFrames(1, 80, 0, 0, 40, 47, pitch, time); 
	final static public AnimationFrames pitch_walking_down = new AnimationFrames(1, 80, 0, 0, 48, 55, pitch, time);
	final static public AnimationFrames pitch_walking_down_right = new AnimationFrames(1, 80, 0, 0, 56, 63, pitch, time);
	
	static String enemy = "resources/images/spr_elf.png";
	final static public AnimationFrames enemy_walking_right = new AnimationFrames(1, 75, 0, 0, 0, 7, enemy, time);
	final static public AnimationFrames enemy_walking_up  = new AnimationFrames(1, 75, 0, 0, 8, 15, enemy, time);
	final static public AnimationFrames enemy_walking_left  = new AnimationFrames(1, 75, 0, 0, 16, 23, enemy, time);
	final static public AnimationFrames enemy_walking_down  = new AnimationFrames(1, 75, 0, 0, 24, 31, enemy, time);
	
	int time2 = 6;
	final static public  AnimationFrames  brick = new AnimationFrames(
			1, 8, 0, 0, 0, 7, "resources/images/spr_redbrick.png", time);
	final static public AnimationFrames brick_explosion = new AnimationFrames(
			1, 7, 0, 0, 0, 6   , "resources/images/spr_redexplo.png", time);
	
		
}
