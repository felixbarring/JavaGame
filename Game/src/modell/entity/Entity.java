package modell.entity;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import view.IFPaintable;
import modell.entity.geometry.Polygon;

public abstract  class Entity implements IFEntity, IFPaintable {

	public Polygon polygon;
	protected BufferedImage image;
	protected int layerId = 0; // should be final
	protected EntityType type;
	public int mass = 3;

	protected Entity(){}

	// Entity
	@Override
	public abstract void update(double tickCompensate);
	
	// Paintable
	@Override
	public List<Integer> getPolygonData() {
		return polygon.getLocationData();
	}

	@Override
	public Image getImage() {
		return image;
	}

	public abstract int getImageX();
	
	public abstract int getImageY();
	
	@Override
	public int getLayerId(){
		return layerId;
	}
	
	public abstract void collidedWith(Entity e);
	
	public boolean interSects(Entity c) {
		return polygon.intersects(c.polygon);
	}
	
	public EntityType getType(){
		return type;
	}

}
