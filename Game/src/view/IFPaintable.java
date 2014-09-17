package view;

import java.awt.Image;
import java.util.List;

public interface IFPaintable {

	// can be null?
	public List<Integer> getPolygonData();
	
	public Image getImage();
	
	public int getImageX();
	
	public int getImageY();
	
	public int getLayerId();
	
}
