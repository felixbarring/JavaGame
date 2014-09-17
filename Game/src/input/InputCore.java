package input;

import java.awt.Component;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InputCore implements KeyListener, MouseListener, MouseMotionListener{

	public static InputContainer input;
	public static InputCore instance;
	
	private List<Integer> keysPressed = new LinkedList<Integer>();
	private int[] mouseLocation = new int[2];

	public static InputCore createInstance(Component comp){
		if (instance == null){
			instance = new InputCore(comp);
		}
		return instance;
	}
	
	public static InputCore getInstance() {
		return instance;
	}
	
	InputCore(Component comp){
		comp.addKeyListener(this);
		comp.addMouseListener(this);
		comp.addMouseMotionListener(this);
		createMapOfInputBindings();
		instance = this;
	}
	
	private Map<Integer, InputAction> keyBindings;
	private Map<InputAction, Integer> inputActionMap = new HashMap<InputAction, Integer>(); 

	private Map<Integer, InputAction> createMapOfInputBindings(){
		String fileName = "resources/bindings/keyBindings";	 // Hard coded?
		File file = new File(fileName);
		try {
			Scanner scn = new Scanner(file);
			Map<Integer, InputAction> map = new HashMap<Integer, InputAction>();
			char[] line;

			while (scn.hasNextLine()){
				line = scn.nextLine().toCharArray();
				String action = "";
				String number = "";
				boolean foundEqualSign = false;
				for (int i = 0; i < line.length; i++){
					if (line[i]== '/'){
						continue; // Skip this row, used for comments.
					}
					if (line[i]== '='){
						foundEqualSign = true;
					}
					else if (foundEqualSign){
						number = number+line[i];
					}else{
						action = action+line[i];
					}
				}
				if (number != "" && action != ""){
					map.put(Integer.parseInt(number), InputAction.valueOf(action));
					// exception here!!!
				}
			}
			scn.close();
			return map;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//-----------------------------------------------------
	//--The interfaces--
	//-----------------------------------------------------
	
	@Override
	public synchronized void keyPressed(KeyEvent e){
		//System.out.println(e.getKeyCode());
		if (!keysPressed.contains(e.getKeyCode())){
			keysPressed.add(e.getKeyCode());
		}
	}

	@Override
	public synchronized void keyReleased(KeyEvent e){
		if (keysPressed.contains(e.getKeyCode())){
			keysPressed.remove((Integer)e.getKeyCode());
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0){
		// Not used
	}

	@Override
	public void mouseClicked(MouseEvent m) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public synchronized void mousePressed(MouseEvent m) {
		if (!keysPressed.contains(m.getButton())){
			keysPressed.add(m.getButton());
		}
	}

	@Override
	public synchronized  void mouseReleased(MouseEvent m) {
		if (keysPressed.contains(m.getButton())){
			keysPressed.remove((Integer)m.getButton());
		}
	}

	@Override
	public synchronized void mouseDragged(MouseEvent e) {
		mouseLocation[0] = e.getX();
		mouseLocation[1] = e.getY();
	}

	@Override
	public synchronized void mouseMoved(MouseEvent e) {
		mouseLocation[0] = e.getX();
		mouseLocation[1] = e.getY();
	}
	
	public synchronized void updateInput(){
		inputActionMap.clear();
		if (keyBindings == null){
			keyBindings = createMapOfInputBindings();
		}

		for (Integer i : keysPressed){
			if (keyBindings.get(i) != null){
				inputActionMap.put(keyBindings.get(i), i);
			}
		}
		input = new InputContainer(inputActionMap, mouseLocation);
	}

}



