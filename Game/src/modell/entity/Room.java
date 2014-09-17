package modell.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import view.DrawingImage;
import view.IFPaintable;
import modell.IFGameState;
import modell.entity.entities.EnemySpawner;
import modell.entity.entities.Player;
import modell.entity.entities.Wall;
import modell.entity.geometry.CollisionManager;

public class Room implements IFGameState{

	protected List<Entity> entitys;
	protected Queue<Entity> newEntitys;
	protected List<IFPaintable> snapShots;
	protected Queue<Entity> newSnapShots;

	private DrawingImage drawingImage;

	protected List<Room> connectedRooms = new LinkedList<Room>();

	int roomWidth;
	int roomHeight;

	public Room(String initFile){
		entitys = new ArrayList<Entity>();
		newEntitys = new LinkedList<Entity>();
		snapShots = new ArrayList<IFPaintable>();
		newSnapShots = new LinkedList<Entity>();
		init(initFile);
		drawingImage = new DrawingImage(800, 600); // should read the arguments from the init text file + 1
	}

	private enum State {
		START, TYPE_RECEIVED, READING_ARGUMENTS, CREATE_ENTITY;
	}

	public void init(String initFile){

		newEntitys.add(Player.getInstance());
		State currentState = State.START;

		try {
			Scanner scn = new Scanner(new File(initFile));
			char[] line;
			String entityType = "";
			List<Integer> paraList = new LinkedList<Integer>();

			while(scn.hasNextLine()){
				line = scn.nextLine().toCharArray();
				String word = "";
				for(char c : line){
					if (c != ' '){
						if (c != '\t'){
							word = word+c;
						}
					} else{
						//System.out.println(word+" "+currentState);
						if (currentState == State.START){
							char[] compWord = word.toCharArray();
							if (compWord[compWord.length-1] == ':'){
								currentState = State.TYPE_RECEIVED;
								entityType = word;
							} else{
								word = "";
							}
						}
						else if (currentState == State.TYPE_RECEIVED){
							if (word.equals("BEGIN")){
								currentState = State.READING_ARGUMENTS;
								paraList.clear();
								word = "";
							}else if (word.length() >= "BEGIN".length()){
								word = "";
							}
						}
						else if (currentState == State.READING_ARGUMENTS){
							char[] compWord = word.toCharArray();
							if (compWord[compWord.length-1] == ','){
								paraList.add(Integer.parseInt(word.substring(
										0, compWord.length-1)));
								word = "";
							}else if (compWord[compWord.length-1] == ';'){
								currentState = State.CREATE_ENTITY;
								paraList.add(Integer.parseInt(word.substring(
										0, compWord.length-1)));
								word = "";
							}
							else if(word.equals("END")){
								currentState = State.START;
								word = "";
							}
						}
					}
				}
				if (currentState == State.CREATE_ENTITY){
					if (entityType.equals("ENEMY_SPAWNER:")){
						newEntitys.add(
								new EnemySpawner(paraList.get(0), 
										paraList.get(1), paraList.get(2), 
										paraList.get(3), paraList.get(4)));
					}
					if (entityType.equals("WALL:")){
						newEntitys.add(
								new Wall(paraList.get(0), 
										paraList.get(1)));
					}
					paraList.clear();
					currentState = State.READING_ARGUMENTS;
				}
			}
			scn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}		

	public void addNewEntity(Entity e){
		newEntitys.add(e);
	}

	@Override
	public synchronized void update(double tickCompensate){
		for (Entity o : entitys){
			o.update(tickCompensate);
		}

		CollisionManager.getInstance().resolveCollisions();

		while(newEntitys.peek() != null){
			entitys.add(newEntitys.peek());
			newSnapShots.add(newEntitys.poll());
		}
	}

	@Override
	public synchronized DrawingImage getDrawingImage() {
		while(newSnapShots.peek() != null){
			snapShots.add(new SnapShot(newSnapShots.poll()));
		}
		drawingImage.updatePaintInformation(snapShots);
		return drawingImage;
	}

}

