package modell;

import view.ViewManager;
import modell.entity.WorldManager;
import modell.menus.MainMenu;
import input.InputCore;

public class GameLoop implements Runnable{

  public ViewManager viewManager = new ViewManager();
  public IFGameState worldManager  = new WorldManager(this);
  public IFGameState mainMenu = new MainMenu(this);

  private IFGameState current = mainMenu;

  private static GameLoop instance;
  public static synchronized GameLoop getInstance(){
    if (instance == null){
      instance = new GameLoop();
    }
    return instance;
  }

  public void event(IFGameState gs){
    current = gs;
  }

  // a millisecond is equal to 1/1000 second 
  private final long DESIRED_TIME_PER_TICK = 10; 

  // fancy loop that sleeps if the game is running fast 
  // (which it should always do unless its running on a very weak machine), 
  // and sends a tick compensate if it is running slow
  @Override
  public void run(){

    int tickSleepTime = 0;
    float tickCompensate = 1;
    int tickCounter = 0;
    int tickIntervall = 10;

    long elapsedTime = 0;
    while (true){

      tickCounter++;
      long poot = System.nanoTime(); 

      InputCore.getInstance().updateInput();
      current.update(tickCompensate);
      viewManager.updatePicture(current.getDrawingImage());

      long swosh = System.nanoTime()-poot; 
      elapsedTime = elapsedTime+swosh;

      if (tickCounter == tickIntervall){
        tickSleepTime = (int)(DESIRED_TIME_PER_TICK-(elapsedTime/1000000)/(tickIntervall));
        tickCounter = 0;
        elapsedTime = 0;
        //System.out.println(tickSleepTime);
      }

      try {
        if (tickSleepTime > 0){
          Thread.sleep(tickSleepTime);
          tickCompensate = 1;
        }else{
          tickCompensate = 1+(Math.abs(tickSleepTime)/DESIRED_TIME_PER_TICK);
        }
      }catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


}
