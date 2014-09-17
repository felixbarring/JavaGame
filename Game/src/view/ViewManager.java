package view;

import input.InputCore;
import java.awt.Canvas;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class ViewManager {

  private JFrame frame;
  private Canvas canvas;
  private BufferedImage bufferedImage;

  public static volatile int WIDTH = 800;
  public static volatile int HEIGHT = 600;

  private long fpsCounter;
  private long fpsLimit = 60;

  public ViewManager(){
    canvas = new Canvas();
    canvas.setFocusable(true);
    canvas.setIgnoreRepaint(true);
    canvas.setSize(WIDTH, HEIGHT);
    InputCore.createInstance(canvas);

    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("Pootis");
    frame.setVisible(true);
    frame.setIgnoreRepaint(true);
    frame.setResizable(false);
    frame.setLayout(new BorderLayout());
    frame.add(canvas);
    frame.pack();

    bufferedImage = new BufferedImage(WIDTH,
        HEIGHT, BufferedImage.TYPE_INT_RGB);
  }

  public void shutDown() {
    frame.dispose();
  }

  public void updatePicture(DrawingImage di){

    // TODO fit Drawing image on the current canvas.
    Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
    g.setBackground(Color.gray);
    g.clearRect(0, 0, WIDTH, HEIGHT);

    g.drawImage(di.getImage(), di.getXOffset(), di.getYOffset(), null);

    Graphics2D g2 = (Graphics2D) canvas.getGraphics();
    g2.drawImage(bufferedImage, 0, 0, null);
    g2.dispose();
  }





}
