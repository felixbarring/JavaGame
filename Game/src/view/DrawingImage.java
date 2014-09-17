package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;

public class DrawingImage {

	// Need layers
	private int width, height;
	private int widthScaled, heightScaled;
	public int offSetX;
	public  int offSetY;
	public double scale;

	private BufferedImage buffImage;
	private  List<IFPaintable> paintStuff;

	public DrawingImage(int x, int y){
		width = x;
		height = y;
		buffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		double widthRelation = ViewManager.WIDTH /(double) width;
		double heightRelation = ViewManager.HEIGHT /(double) height;

		// What if width or height is bigger than the window?
		if (heightRelation < widthRelation ){
			scale =  heightRelation;
		} else {
			scale =  widthRelation;
		}

		// dummy
		offSetX = (int) ((ViewManager.WIDTH - width*scale)/2);
		offSetY = (int) ((ViewManager.HEIGHT - height*scale)/2);

		widthScaled = (int) (width*scale);
		heightScaled = (int) (height*scale);

	}

	public void updatePaintInformation(List<IFPaintable> p){
		paintStuff = p;
	}

	int getXOffset(){
		return offSetX;
	}

	int getYOffset(){
		return offSetY;
	}

	boolean drawCollisionBoxes = false;
	BufferedImage getImage(){
		Graphics2D g = (Graphics2D) buffImage.getGraphics();
		g.setColor(Color.blue);
		g.fillRect(0, 0, width, height);

		for (IFPaintable p : paintStuff){
			g.drawImage(p.getImage(), p.getImageX(), p.getImageY(), null);

			// Collision boxes
			if (drawCollisionBoxes){
				g.setColor(Color.blue);
				List<Integer> polD = p.getPolygonData();
				for (int i = 3; i < polD.size(); i = i+2){
					g.drawLine(polD.get(i-3), polD.get(i-2), polD.get(i-1), polD.get(i));
				}
				//Closing line
				int s = polD.size();
				g.drawLine(polD.get(0), polD.get(1), polD.get(s-2), polD.get(s-1));
			}
		}
		// Future optimized implementation, rescale the sprite's once
		return getScaledImage(buffImage, widthScaled, heightScaled, false);
	}


	// -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
	// -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 
	// -- -- -- -- -- -- -- -- -- -- -- -- -- -- --

	public static BufferedImage getScaledImage(BufferedImage image, int targetWidth,
			int targetHeight, boolean highQuality) {

		int accWidth, accHeight;
		if (highQuality) {
			accHeight = image.getHeight();
			accWidth = image.getWidth();
		} else {
			accHeight = targetHeight;
			accWidth = targetWidth;
		}

		BufferedImage result = image;
		do {
			if (highQuality) {
				accHeight = Math.max(accHeight / 2, targetHeight);
				accWidth = Math.max(accWidth / 2, targetWidth);
			}
			BufferedImage tmp = new BufferedImage(accWidth, accHeight, image.getType());

			Graphics2D g2 = tmp.createGraphics();

			if (highQuality) {
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			} else {
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
			}
			g2.drawImage(result, 0, 0, accWidth, accHeight, null);

			result = tmp;
		} while (accHeight != targetHeight && accWidth != targetWidth);

		return result;
	}

}
