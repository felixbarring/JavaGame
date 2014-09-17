package modell.entity.geometry;

public class SimpleVector {

	private double x, y;
	private double xu, yu;  // Unit vector
	
	public void setXAndY(double x2, double y2){
		x = x2;
		y = y2;
		
		double divisor = (Math.sqrt(x2*x2+y2*y2));
		if (divisor != 0){
			xu = x2/divisor;
		} else{
			xu = 0;
		}
		
		divisor = (Math.sqrt(x2*x2+y2*y2));
		if (divisor != 0){
			yu = y2/divisor;
		} else {
			yu = 0;
		}
	}
	
	public void changeDirection(double x2, double y2){
		setXAndY(x+x2, y+y2);
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getXUnit(){
		return xu;
	}
	
	public double getYUnit(){
		return yu;
	}
	
}
