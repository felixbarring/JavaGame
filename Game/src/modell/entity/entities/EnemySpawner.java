package modell.entity.entities;

import java.util.ArrayList;
import java.util.List;

import modell.entity.Entity;
import modell.entity.geometry.Polygon;

public class EnemySpawner extends Entity{

	// OBS !!!
	// THE POLYGON IS STOOPID!!!
	// Do all entities really need a polygon? consider redesign the class hierarchy
	
	double tickCounter = 0;
	int cooldown = 200;
	
	int createdEnemys = 0; 
	int totalEnemys = 20;
	
	int x;
	int y;
	
	double stTime;
	
	public EnemySpawner(int x, int y, int cooldown, int totalEnemys, int startTime ) {
		super();
		this.x = x;
		this.y = y;
		this.cooldown = cooldown;
		this.totalEnemys = totalEnemys;
		stTime = startTime;
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);  list.add(0);
		list.add(35); list.add(0);
		list.add(35); list.add(45);
		list.add(0);  list.add(45);
		polygon = new Polygon(list);
	}

	@Override
	public void update(double tickCompensate) {
		tickCounter = tickCounter+tickCompensate;
		stTime = stTime-tickCompensate;
		if (createdEnemys >= totalEnemys){
			return;
		}
		
		if (tickCounter >= cooldown && stTime < 0){
			Enemy.getAnInstance(x, y);
			tickCounter = tickCounter-cooldown;
			createdEnemys++;
		}
	}

	@Override
	public void collidedWith(Entity e) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getImageX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getImageY() {
		// TODO Auto-generated method stub
		return 0;
	}

}
