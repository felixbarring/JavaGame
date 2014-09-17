package modell.menus;

import java.util.LinkedList;
import java.util.List;

import modell.GameLoop;
import modell.IFGameState;
import view.DrawingImage;
import view.IFPaintable;

public class MainMenu implements IFGameState{

  // TODO THIS SHIT OKAY?
  // lets do it hardcode style okay?
  // okay
  // good
  // yee

  DrawingImage drawingImage = new DrawingImage(800, 600);
  private List<IFPaintable> paintStuff = new LinkedList<IFPaintable>();
  private List<Button> buttons = new LinkedList<Button>();
  private GameLoop overH;

  public MainMenu(GameLoop oc){
    overH = oc;
    paintStuff.add(new BackGround("resources/images/menu.png", 0, 0));

    Button daButton = new Button("resources/images/newGameButton.png", 580, 60, 
        drawingImage.offSetX, drawingImage.offSetY, drawingImage.scale);

    paintStuff.add(daButton);
    buttons.add(daButton);

    drawingImage.updatePaintInformation(paintStuff);
  }

  @Override
  public void update(double tickCompensate){
    for (Button b : buttons){
      if (b.update(tickCompensate)){
        overH.event(overH.worldManager);
      }
    }
  }

  @Override
  public DrawingImage getDrawingImage() {
    return drawingImage;
  }

}
