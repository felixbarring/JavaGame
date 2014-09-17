package modell.menus;

import input.InputAction;
import input.InputCore;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import view.IFPaintable;

public class Button implements IFPaintable{

	// Very hacked atm
	
	int x, y;
	int imageX, imageY;
	int width, height;
	BufferedImage image1;				// should use an animation?
	BufferedImage image2;				// should use an animation?
	BufferedImage retImage;

	Button(String fileName, int xa, int ya, int offSetX, int offSetY, double scale){
		try {
			image1 = ImageIO.read(new File(fileName));
			image2 = ImageIO.read(new File("resources/images/newGameButton2.png"));

		} catch(IOException e){e.printStackTrace();}
		
		x = (int) (xa*scale)+offSetX; y = (int) (ya*scale)+offSetY;
		imageX = xa; imageY = ya;
		width = (int) (image1.getWidth()*scale); height = (int) (image1.getHeight()*scale);
		
	}
	
	// returns true id clicked, come up with better name like, wasKlicked?
	boolean update(double tick){
		int[] mouse = InputCore.input.mouseLocation;
		
		if ((mouse[0] > x && mouse[0] < x+width) &&
				(mouse[1] > y && mouse[1] < y+height)){
			retImage = image2;
			if ( InputCore.input.inputActions.get(InputAction.MOUSECLICKED) != null){
				return true;
			}
		} else {
			retImage = image1;
		}
		return false;
	}

	@Override
	public List<Integer> getPolygonData() {
		return null;
	}

	@Override
	public Image getImage() {
		return retImage;
	}

	@Override
	public int getImageX() {
		return imageX;
	}

	@Override
	public int getImageY() {
		return imageY;
	}

	@Override
	public int getLayerId() {
		return 0;
	}


}
