package modell.entity;

import input.InputAction;
import input.InputCore;
import modell.GameLoop;
import modell.IFGameState;
import view.DrawingImage;

public class WorldManager implements  IFGameState{

  public static Room currentRoom;
  private GameLoop overH;

  public WorldManager(GameLoop oh){
    overH = oh;
    currentRoom = new Room("resources/maps/map1.txt");
  }

  @Override
  public synchronized void update(double tickCompensate){
    if (InputCore.input.inputActions.get(InputAction.ESC) != null){
      overH.event(overH.mainMenu);
    }
    currentRoom.update(tickCompensate);
  }

  @Override
  public DrawingImage getDrawingImage() {
    return currentRoom.getDrawingImage();
  }

}
