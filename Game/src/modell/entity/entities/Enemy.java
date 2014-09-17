package modell.entity.entities;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import modell.animation.animations.AnimationManagerEnemy;
import modell.entity.Entity;
import modell.entity.EntityType;
import modell.entity.WorldManager;
import modell.entity.geometry.CollisionManager;
import modell.entity.geometry.Polygon;
import modell.entity.geometry.SimpleVector;

public class Enemy extends Entity{

	private static Queue<Enemy> entitiesNotInUse = new LinkedList<Enemy>();
	boolean isInUse = true;
	private double speed = 0.5;
	protected int health = 5;
	protected int damadge = 1;

	AnimationManagerEnemy aniM = new AnimationManagerEnemy();

	public static Enemy getAnInstance(double x, double y){
		Enemy enti;
		if (entitiesNotInUse.size() > 0){
			entitiesNotInUse.peek().isInUse = true;

			enti = entitiesNotInUse.poll();
		}else{
			enti = new Enemy();
		}
		enti.health = 5;
		enti.polygon.setLocation(x, y);
		return enti;
	}

	private Enemy(){
		super();
		speed = speed+(Math.random()/2);
		type = EntityType.ENEMY;
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);  list.add(0);
		list.add(35); list.add(0);
		list.add(35); list.add(45);
		list.add(0);  list.add(45);
		polygon = new Polygon(list);
		WorldManager.currentRoom.addNewEntity(this);
	}

	@Override
	public void update(double tickTime){
		if (health <= 0){
			image = null;
			return;
		}
		
		int x = 0;
		
		SimpleVector v = polygon.moveTowards(Player.getInstance().getPolygon(), speed);
		
		if (v.getX() >= 0){
			x = 1;
		} else{
			x = -1;
		}
		CollisionManager.getInstance().registerForCollisionHandeling(this);
		aniM.update(tickTime, x);
		image = aniM.getImage();
	}

	@Override
	public void collidedWith(Entity e){
		if(e.getType() == EntityType.PROJECTILE){
			health--;
		}
		if(e.getType() == EntityType.PLAYER){
			health--;
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