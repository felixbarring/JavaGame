package modell;

import view.DrawingImage;

public interface IFGameState {
	
	// Pretty bad name but i can't think of any better :/
	void update(double tick);
	
	DrawingImage getDrawingImage();

}
