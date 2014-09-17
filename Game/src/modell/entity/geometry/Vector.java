
package modell.entity.geometry;

public class Vector{

	private double x;
	private double y;

	private double xU; // Unit vector
	private double yU;
	
	private double xN; // Normalized unit vector
	private double yN;
	
	private Point p1;
	private Point p2;
	
	// This is a vector from point p1 to p2
	
	Vector(Point p11, Point p22){
		p1 = p11;
		p2 = p22;
		update();
	}

	void update(){ 	// Heavy computation? Perhaps, but i can't bother a.t.m. :3
		x = p1.getX()-p2.getX();
		y = p1.getY()-p2.getY(); 

		xU = x/(Math.sqrt(x*x+y*y));
		yU = y/(Math.sqrt(x*x+y*y));

		xN = yU;
		yN = -xU;
	}
	
	void copyStateTo(Vector v){
		v.x = this.x;
		v.y = this.y;
	}

	// ehm?
	double getMagnitude(){
		return Math.sqrt((x*x)+(y*y));
	}

	double findAngleDegreeBetween(Vector v){
		return (Math.atan2(v.y, v.x) - Math.atan2(y, x));
	}

	boolean isParalell(Vector v){
		// use cross product!
		//u*v = |u|*|v|*cos(v)  | parallel if u*v = 0 
		// this is wrong !?! should be == not !=
		if (((int)x*v.getX()+y*v.getY()) != 0){
			return true;
		}
		return false;
	}

	double getX(){ return x;}
	double getY(){ return y;}
	
	double getXN(){ return xN;}
	double getYN(){ return yN;}
	
	double getXU(){	return xU;}
	double getYU(){	return yU;}

	@Override
	public String toString(){
		return " x = "+x+". y = "+y;
	}

}
