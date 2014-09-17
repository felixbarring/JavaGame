package modell.entity.items;

import java.util.Random;

import modell.entity.entities.Projectile;
import modell.entity.geometry.Point;


public class Shotgun extends Weapon{
	
	int pellets = 40;
	Random r = new Random();
	
	public Shotgun(){
		super(50);
	}

	@Override 
	public void shoot(Point p, int x, int y){
		for (int i = 0; i < pellets; i++){
			Projectile pro = Projectile.getAnInstance();

			double randX = (r.nextDouble()/3)-(r.nextDouble()/3);
			double randY = (r.nextDouble()/3)-(r.nextDouble()/3);
			pro.setDirectionAndShit(x+randX, y+randY, p);
		}
	}
}
