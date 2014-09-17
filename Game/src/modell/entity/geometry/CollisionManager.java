package modell.entity.geometry;

import java.util.LinkedList;
import java.util.Queue;
import modell.entity.Entity;
import modell.entity.EntityType;

public class CollisionManager {
	
	// should use a quadtree so that the collision algorithm gets O(n log n) instead of O(n^2) complexity

	private Queue<Entity> collisionPol = new LinkedList<Entity>();

	private static CollisionManager instance;
	public static CollisionManager getInstance(){
		if (instance == null){
			instance = new CollisionManager();
		}
		return instance;
	}

	private CollisionManager(){}

	public void resolveCollisions(){
		while (true){
			Entity curr = collisionPol.poll();
			if (curr == null){ return;}
			for(Entity e : collisionPol){
				if(curr.interSects(e)){
					handleCollision(curr, e);
				}
			}
		}
	}
	
	private void handleCollision(Entity e, Entity e2){
		// try all different type combinations
		
		if(e.getType() == EntityType.ENEMY && e2.getType() == EntityType.ENEMY){
			e.polygon.separatePolygons(e2.polygon, e.mass, e2.mass, 0.2);
			e.collidedWith(e2);
			e2.collidedWith(e);
			return;
		}
		
		if(e.getType() == EntityType.PLAYER && e2.getType() == EntityType.ENEMY || 
				e.getType() == EntityType.ENEMY && e2.getType() == EntityType.PLAYER){
			e.polygon.separatePolygons(e2.polygon, e.mass, e2.mass, 1);
			e.collidedWith(e2);
			e2.collidedWith(e);
			return;
		}
		
		if(e.getType() == EntityType.TERRAIN && (e2.getType() == EntityType.ENEMY || e2.getType() == EntityType.PLAYER)||
				(e.getType() == EntityType.ENEMY || e.getType() == EntityType.PLAYER) && e2.getType() == EntityType.TERRAIN){
 			e.polygon.separatePolygons(e2.polygon, e.mass, e2.mass, 1);
			e.collidedWith(e2);
			e2.collidedWith(e);
			return;
		}
		
		if (e.getType() == EntityType.PROJECTILE && 
				(e2.getType() == EntityType.ENEMY || e2.getType() == EntityType.PLAYER || e2.getType() == EntityType.TERRAIN) ||
			(e.getType() == EntityType.ENEMY || e.getType() == EntityType.PLAYER || e.getType() == EntityType.TERRAIN) &&
				e2.getType() == EntityType.PROJECTILE){
			e.collidedWith(e2);
			e2.collidedWith(e);
		}
	}
	
	public void registerForCollisionHandeling(Entity e){
		collisionPol.add(e);
	}

}
