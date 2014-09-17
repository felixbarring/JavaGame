package modell.entity.entities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import modell.entity.Entity;
import modell.entity.EntityType;
import modell.entity.geometry.CollisionManager;
import modell.entity.geometry.Polygon;

public class Wall extends Entity{
	
	int imageHalfWidth;
	int imageHalfHeight;
	
	public Wall(int x, int y){
		type = EntityType.TERRAIN;
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);  list.add(0);
		list.add(40); list.add(0);
		list.add(40); list.add(40);
		list.add(0);  list.add(40);
		polygon = new Polygon(list);
		polygon.movePolygon(x, y, 0);
		
		try {
			image = ImageIO.read(new File("resources/images/wall.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.mass = -1;
		imageHalfWidth = image.getWidth()/2;
		imageHalfHeight = image.getHeight()/2;
	}

	@Override
	public void update(double tickCompensate) {
		CollisionManager.getInstance().registerForCollisionHandeling(this);
	}

	@Override
	public void collidedWith(Entity e) {
		
	}

	@Override
	public int getImageX() {
		return (int)polygon.getCentroid().getX()-imageHalfWidth;
	}

	@Override
	public int getImageY() {
		return (int)polygon.getCentroid().getY()-imageHalfHeight;
	}

}
