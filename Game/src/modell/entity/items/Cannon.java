package modell.entity.items;

import modell.entity.entities.Projectile;
import modell.entity.geometry.Point;

public class Cannon extends Weapon{

	public Cannon(){
		super(1);
	}
	
	@Override
	public void shoot(Point p, int x, int y){
			Projectile pro = Projectile.getAnInstance();
			pro.setDirectionAndShit(x, y, p);
	}

}
