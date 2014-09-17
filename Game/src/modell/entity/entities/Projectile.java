package modell.entity.entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import modell.animation.animations.AnimationManagerBrick;
import modell.entity.Entity;
import modell.entity.EntityType;
import modell.entity.WorldManager;
import modell.entity.geometry.CollisionManager;
import modell.entity.geometry.Point;
import modell.entity.geometry.Polygon;

public class Projectile extends Entity{

	private static Queue<Projectile> entitiesNotInUse = new LinkedList<Projectile>();
	boolean isInUse = true;

	double xDirection, yDirection;
	boolean isAlive = true;
	double aliveTime = 0;
	double lifeTime = 100;
	int speed = 5;

	AnimationManagerBrick aniM = new AnimationManagerBrick();

	public static Projectile getAnInstance(){
		if (entitiesNotInUse.size() > 0){
			entitiesNotInUse.peek().isInUse = true;
			entitiesNotInUse.peek().isAlive = true;
			return entitiesNotInUse.poll();
		}
		return new Projectile();
	}

	private Projectile(){
		super();
		type = EntityType.PROJECTILE;
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);  list.add(0);
		list.add(15); list.add(0);
		list.add(15); list.add(15);
		list.add(0);  list.add(15);
		polygon = new Polygon(list);
		WorldManager.currentRoom.addNewEntity(this);	}

	public void setDirectionAndShit(double x, double y, Point p){
		xDirection = x;
		yDirection = y;
		polygon.setLocation(p);
		polygon.movePolygon((xDirection)*speed*1.5, (yDirection)*speed*1.5, 0);
	}

	@Override
	public void update(double tickTime){
		if (!isInUse){
			image = null;
			return;
		}
		if (isAlive){
			aliveTime = aliveTime+tickTime;
			if(aliveTime >= lifeTime){
				explode();
			}
			polygon.movePolygonTowards(xDirection, yDirection, speed*tickTime);
			CollisionManager.getInstance().registerForCollisionHandeling(this);
		} else{
			clear();
		}
		aniM.update(tickTime);
		image = aniM.getImage();
	}

	private void explode(){
		isAlive = false;
	}

	private void clear(){
		image = null;
		aliveTime = 0;
		entitiesNotInUse.add(this);
		isInUse = false;
	}

	@Override
	public void collidedWith(Entity e) {
		if(e.getType() == EntityType.ENEMY || e.getType() == EntityType.TERRAIN){
			explode();
		}
	}

	@Override
	public int getImageX() {
		return (int)polygon.getCentroid().getX()-aniM.getImgHalfWidth();
	}

	@Override
	public int getImageY() {
		return (int)polygon.getCentroid().getY()-aniM.getImgHalfHeight();
	}

}