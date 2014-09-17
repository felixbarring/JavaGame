package modell.menus;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import view.IFPaintable;

public class BackGround implements IFPaintable{

	private BufferedImage image;
	private int x, y;

	public BackGround(String fileName, int xp, int yp)	{
		try {
			image = ImageIO.read(new File(fileName));

		} catch(IOException e){e.printStackTrace();}
		x = xp;
		y = yp;
	}

	@Override
	public List<Integer> getPolygonData() {
		return null;
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public int getImageX() {
		return x;
	}

	@Override
	public int getImageY() {
		return y;
	}

	@Override
	public int getLayerId() {
		return 0;
	}

}
