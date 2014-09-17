
package modell.entity.geometry;

public class Point {
	
	private double x;
	private double y;
	
	public Point(double nX, double nY){
		x = nX;
		y = nY;
	}
	
	void copyStateTo(Point p){
		p.x = this.x;
		p.y = this.y;
	}
	
	void setX(double x2){
		x = x2;
	}
	
	void setY(double y2){
		y = y2;
	}

	Point projectionOnto(Vector v, Point p){ // Change the state of the point we get in the parameter
		double dummy = 	 (x*v.getX()+y*v.getY())
						/(v.getX()*v.getX()+v.getY()*v.getY());
		p.setX(dummy*v.getX());
		p.setY(dummy*v.getY());
		return p;
	}
	
	Point projectionOntoX(Point p){
		p.setX(x);
		p.setY(0);
		return p;
	}
	
	Point projectionOntoY(Point p){
		p.setX(0);
		p.setY(y);
		return p;
	}
	
	double projectionValue(Vector v){
		return (x*v.getX()+y*v.getY()) / Math.sqrt((v.getX()*v.getX()+v.getY()*v.getY()));
	}
	
	double projectionValueUnitV(Vector v){
		return (x*v.getXU()+y*v.getYU()); // No division needed / Math.sqrt((v.getXU()*v.getXU()+v.getYU()*v.getY()));
	}
	
	void movePoint(double x2, double y2){
		x += x2;
		y += y2;
	}
	
	void rotatePoint(double r, Point pivotPoint) {
		double xAtOrigin = x-pivotPoint.getX();
		double yAtOrigin = y-pivotPoint.getY();
		
		x = (float) (xAtOrigin*Math.cos(r)-
				yAtOrigin*Math.sin(r)+pivotPoint.getX());
		y = (float) (xAtOrigin*Math.sin(r)+
				yAtOrigin*Math.cos(r)+pivotPoint.getY());
	}
	
	public double distanceFromPoint(Point p2){
		return distanceFrom(p2.x, p2.y);
	}
	
	public double distanceFrom(double nX, double nY){
		return Math.sqrt(Math.pow(nX-x, 2)+Math.pow(nY-y, 2));
	}
	
	double distanceFromOrigin(){
		return Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}

	@Override
	public String toString(){
		return " x = "+x+". y = "+y;
	}
	
}
