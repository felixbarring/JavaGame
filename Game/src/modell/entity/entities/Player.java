package modell.entity.entities;

import input.InputAction;
import input.InputCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modell.animation.animations.AnimationManagerPlayer;
import modell.entity.Entity;
import modell.entity.EntityType;
import modell.entity.geometry.CollisionManager;
import modell.entity.geometry.Polygon;
import modell.entity.geometry.SimpleVector;
import modell.entity.items.Cannon;
import modell.entity.items.Shotgun;
import modell.entity.items.Weapon;

public class Player extends Entity{

	int speed;
	AnimationManagerPlayer aniM = new AnimationManagerPlayer();
	private int currentWeapon = 0;
	private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	public int health = 10;
	int imuneTime = 50;
	double imuneCounter = 0;
	SimpleVector direction = new SimpleVector();

	public static Player instance;
	public static Player getInstance(){
		if (instance == null){
			instance = new Player();
		}
		return instance;
	}

	private Player(){
		super();
		type = EntityType.PLAYER;
		List<Integer> list = new ArrayList<Integer>();
		list.add(0);  list.add(0);
		list.add(20); list.add(0);
		list.add(20); list.add(30);
		list.add(0);  list.add(30);
		polygon = new Polygon(list);
		speed = 2;
		polygon.setLocation(300, 300);
		this.mass = 20;
		image = aniM.getImage();
		direction.setXAndY(0, 0);

		weapons.add(new Cannon());
		weapons.add(new Shotgun());
	}

	int facingX = 1;
	int facingY = 1;
	boolean walking  = false;
	@Override
	public void update(double tickTime){
		Map<InputAction, Integer> inputActions = InputCore.input.inputActions;

		imuneCounter = imuneCounter-tickTime;
		toggleCoolDownCounter = toggleCoolDownCounter-tickTime;

		/*
		if (health <= 0){
			System.out.println("DED");
			// explode evything
			return;
		}
		*/

		int x = 0;
		int y = 0;
		boolean fired = false;

		if (inputActions.get(InputAction.MOVELEFT)		!= null){x = -1;}
		if (inputActions.get(InputAction.MOVERIGHT)		!= null){x = 1;	}
		if (inputActions.get(InputAction.MOVEUP) 		!= null){y = -1;}
		if (inputActions.get(InputAction.MOVEDOWN)		!= null){y = 1;	}
		if (inputActions.get(InputAction.FIREACTION) 	!= null){fired = true;}
		if (inputActions.get(InputAction.TOGGLEWEAPON) 	!= null){toggleWeapon();}

		if (x*x+y*y == 0){
			walking = false;
		}else{
			walking = true;
		}

		if (fired){
			fire(tickTime, facingX, facingY);
		}else if(walking){
			facingX = x;
			facingY = y;
		}

		direction.setXAndY(x, y);
		polygon.moveTowards(direction, speed);
		CollisionManager.getInstance().registerForCollisionHandeling(this);

		if (walking){
			aniM.update(tickTime, facingX, facingY);
			image = aniM.getImage();
		}else{
			aniM.update(tickTime, 0, 0);
			image = aniM.getImage();
		}
	}

	private final int  toggleCoolDown = 30;
	double toggleCoolDownCounter = toggleCoolDown;
	private void toggleWeapon(){
		if (toggleCoolDownCounter < 0) {
			toggleCoolDownCounter = toggleCoolDown;
			++currentWeapon; 
			if (currentWeapon >= weapons.size()) { currentWeapon = 0; }
		}
	}

	private void fire(double tickTime, int x, int y){
		weapons.get(currentWeapon).fire(polygon.getCentroid(), tickTime, x, y);
	}

	public Polygon getPolygon(){
		return polygon;
	}

	@Override
	public void collidedWith(Entity e) {
		if (e.getType() == EntityType.ENEMY){
			health = 0;
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