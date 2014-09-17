package modell.entity.items;

import modell.entity.geometry.Point;

public abstract class Weapon {
	
	int coolDown;
	double tickCounter = 0;
	
	
	Weapon(int cool){
		coolDown = cool;
	}
	
	// @OVERRIDE
	public void fire(Point p, double tick, int x, int y){
		tickCounter = tickCounter+tick;
		if (tickCounter >= this.coolDown){

			shoot(p, x, y);
			
			tickCounter = tickCounter-coolDown;
		}
	}
	
	public abstract  void shoot(Point p, int x, int y);

}
