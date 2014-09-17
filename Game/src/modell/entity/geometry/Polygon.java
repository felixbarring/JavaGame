package modell.entity.geometry;

import java.util.ArrayList;
import java.util.List;

public class Polygon{
	
	// Will i need old points for the sweep test!?!
	// Todo: sweep test, rotation when colission happend?
	
	private List<Point> points= new ArrayList<Point>();
	//private List<Point> oldPoints= new ArrayList<Point>(); // sweep test?
	private List<Vector> vectors = new ArrayList<Vector>();
	private double radius = 0;
	private SimpleVector direction = new SimpleVector();
	private List<Integer> locationData = new ArrayList<Integer>(); // Used by clients
	private Point centroid = new Point(0.0, 0.0);
	private Point centroidCopy = new Point(0.0, 0.0);

	public Polygon(List<Integer> list){
		for (int i = 0; i+1 <= list.size(); i = i+2){
			points.add(new Point(list.get(i), 
					list.get(i+1)));
			locationData.add(list.get(i));
			locationData.add(list.get(i+1));
		}
		findCentroid();
		for (Point p : points){
			double distFromPoint = p.distanceFromPoint(centroid);
			if (distFromPoint > radius){
				radius = distFromPoint;
			}
		}
		for (int i = 0; i < points.size()-1; i++){
			vectors.add(new Vector(points.get(i), points.get(i+1)));
		}
		//closing vector
		vectors.add(new Vector(points.get(points.size()-1), points.get(0)));
		removeParalellVectors();
	}

	private void updateVectors(){
		for(Vector v: vectors){
			v.update();
		}
	}
	
	private void removeParalellVectors(){
		for(int i = 0; i < vectors.size(); ++i){
			for(int j = 0; j < vectors.size(); ++j){
				if (i == j){ continue;};
				if(vectors.get(i).isParalell(vectors.get(j))){
					vectors.remove(j);
					j--;
				}
			}
		}
	}

	public void setLocation(Point c){
		setLocation(c.getX(), c.getY());
	}

	public void setLocation(double xx, double yy){
		findCentroid();
		double x = xx-centroid.getX();
		double y = yy-centroid.getY();
		movePolygon(x, y, 0);
	}

	public void movePolygon(double x, double y, double r){
		if(r != 0){
			findCentroid();
			for(Point p : points){
				p.rotatePoint(r, centroid);
			}
			for(Point p : points){
				p.movePoint(x, y);
			}
			updateVectors();
		}else{
			for(Point p : points){
				p.movePoint(x, y);
			}
			centroid.movePoint(x, y);
		}
	}

	public void moveTowards(Vector v, double speed){
		movePolygon(v.getXU()*speed, v.getYU()*speed, 0);
	}
	
	public void moveTowards(SimpleVector v, double speed){
		movePolygon(v.getXUnit()*speed, v.getYUnit()*speed, 0);
	}
	
	public SimpleVector moveTowards(Polygon p, double speed){
		findCentroid();
		double d = p.getCentroid().distanceFromPoint(centroid);
		double x = ((p.getCentroid().getX()-centroid.getX())/d)*speed;
		double y = ((p.getCentroid().getY()-centroid.getY())/d)*speed;
		movePolygon(x, y, 0);
		direction.setXAndY(x, y);
		return direction;
	}
	
	public void movePolygonTowards(double a, double b, double speed) {
		// Create a simple vector !?!
		SimpleVector sv = new SimpleVector();
		sv.setXAndY(a, b);
		movePolygon(sv.getXUnit()*speed, sv.getYUnit()*speed, 0);
	}
	
	public List<Integer> getLocationData(){
		for(int i = 0; i < points.size(); i++){
			locationData.set(i*2, (int)points.get(i).getX());
			locationData.set(i*2+1, (int)points.get(i).getY());
		}
		return locationData;
	}

	private void findCentroid(){
		// Warning: Must be a polygon with at least 3 vertices! 
		double centroidX = 0;
		double centroidY = 0;
		double area = 0;

		for(int i = 0; i < points.size(); i++){
			Point pI = points.get(i);
			Point pJ = i < (points.size() - 1) ? 
					points.get(i + 1) : points.get(0);

					double component = (pI.getX()*pJ.getY())-
							(pJ.getX()*pI.getY());
					centroidX += (pI.getX()+pJ.getX())*component;
					centroidY += (pI.getY()+pJ.getY())*component;
					area += component; 
		}
		// Divide by 0. Oh no
		if(area != 0){
			area = area*3;
			centroidX = centroidX/area;
			centroidY = centroidY/area;
		}
		centroid.setX(centroidX);
		centroid.setY(centroidY);
	}

	public Point getCentroid(){
		centroid.copyStateTo(centroidCopy);
		return centroidCopy;
	}

	private double minOverlap;		// Not very nice!
	private Vector minOverlapVector;
	private int sepDir;
	
	public boolean intersects(Polygon p){
		// Circle test using the radius, should be cheaper then SAT
		// If it detects a collision, the more accurate sat will be used
		if(centroid.distanceFromPoint(p.centroid) > radius+p.radius){
			return false;
		}
		
		// SAT
		minOverlap = Double.MAX_VALUE;
		minOverlapVector = null;
		
		// Check if they intersect on this polygons vectors 
		for (Vector v : vectors){
			double x = overlapOnVector(v, p);
			if (x == 0){
				return false; 	
			}else if(x < minOverlap){
				minOverlap = x;
				minOverlapVector = v;
				sepDir = sepDirDummy;
			}
		}
		// Check if they intersect on the other polygons vectors 
		for (Vector v : p.vectors){
			double x = p.overlapOnVector(v, this);
			if (x == -1){
				return false;
			}else if(x < minOverlap){
				minOverlap = x;
				minOverlapVector = v;
				sepDir = sepDirDummy;
			}
		}
		return true;
	}

	private int sepDirDummy;
	private double overlapOnVector(Vector v, Polygon poly){
		double overlap = 0;
		double maxDistanceP1 = -Integer.MAX_VALUE;
		double maxDistanceP2 = -Integer.MAX_VALUE;
		double minDistanceP1 = Integer.MAX_VALUE;
		double minDistanceP2 = Integer.MAX_VALUE;

		for(Point p : points){
			double dist = p.projectionValueUnitV(v);
			if (dist > maxDistanceP1){
				maxDistanceP1 = dist; 
			}if (dist < minDistanceP1){
				minDistanceP1 = dist; 
			}
		}
		for(Point p : poly.points){
			double dist = p.projectionValueUnitV(v);
			if (dist > maxDistanceP2){
				maxDistanceP2 = dist; 
			}if (dist < minDistanceP2){
				minDistanceP2 = dist; 
			}
		}
		if (minDistanceP2 >= minDistanceP1 && minDistanceP2 <= maxDistanceP1){ // Detta objektet ligger till vänster
			overlap = maxDistanceP1-minDistanceP2;
			sepDirDummy = -1;
		}else if (minDistanceP1 >= minDistanceP2 && minDistanceP1 <= maxDistanceP2){ // Detta objektet ligger till höger
			overlap = maxDistanceP2-minDistanceP1;
			sepDirDummy = 1;
		}
		return overlap;
	}
	
	public void separatePolygons(Polygon p, int m1, int m2, double modi){
		// if any of the objects have mass "-1" then it cannot be moved, and then only the other object should me moved out of collision
		
		double massRelation1;
		double massRelation2;
		if(m1 == -1){
			massRelation1 = 0;
			massRelation2 = 1;
		} else if (m2 == -1){
			massRelation1 = 1;
			massRelation2 = 0;
		}else{
			double sum = m1+m2;
			massRelation1 = (m2)/(sum);
			massRelation2 = (m1)/(sum);
		}
		
		double p1 = ((minOverlap*massRelation1))*modi;
		double p2 = ((minOverlap*massRelation2))*modi;
		this.moveTowards(minOverlapVector, p1*sepDir);
		p.moveTowards(minOverlapVector, -p2*sepDir);
	}
	
}

