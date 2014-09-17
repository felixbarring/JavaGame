
import modell.GameLoop;
import view.ViewManager;

public class Main{

  public static void main(String[] pootis){

    // Daemon thread. Intended to improve Clock accuracy
    Thread thread = new Thread(){
      public void run(){
        try{
          Thread.sleep(Long.MAX_VALUE);
        }
        catch(Exception exc){}
      }
    };

    thread.setDaemon(true);
    thread.start();

    //new Thread(new ViewManager()).start();
    new Thread(new GameLoop()).start();
  }
}