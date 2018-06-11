
package game.racers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import Decorator.ColoredRacer;
import Decorator.WheeledRacer;
import game.arenas.Arena;
import graphics.ArenaField;
import graphics.IDrawable;
import state.*;
import utilities.API;
import utilities.EnumContainer;
import utilities.EnumContainer.Color;
import utilities.EnumContainer.Event;
import utilities.Fate;
import utilities.Mishap;
import utilities.Point;

public abstract class Racer extends Observable implements IDrawable, Runnable, Cloneable, IRacer {
	
 
	protected static int lastSerialNumber = 1;
	
	private int serialNumber; // Each racer has an unique number, assigned by arena in addRacer() method
	private String name;
	private Point currentLocation;
	private Point finish;
	private Arena arena;
	private double maxSpeed;
	private double friction;
	private double acceleration;
	private double currentSpeed;
	@SuppressWarnings("unused")
	private double failureProbability; // Chance to break down
	private final double FAILURE_DEFAULT = 0.35;
	private EnumContainer.Color color; // (RED,GREEN,BLUE,BLACK,YELLOW)
	private Hashtable<String, Object> attributes;
    private State state;
	//private static final State activeState = new Active();
	//private static final State brokenState = new Broken();
	protected BufferedImage img1;
	//private static final State disabledState = new Disabled();
	private static final State completedState = new Completed();
	private static double counter=0;
	private static void counter() {
		counter+=200;
		
	}
	public EnumContainer.Color getColor() {
		return color;
		
	}
	
	
	public void setFriction(double friction) {
		this.friction = friction;
	}
	public void drawObject(Graphics g,ArenaField panel)
    {	//TODO PAS ENLEVER 150 MAIS POURCENTAGE
		if(currentLocation!=null) {
			if(((int)currentLocation.getX()*(int)API.getInstance().arenaFrame.panel.field.getWidth())/(int)API.getInstance().getArena().getLength()<(int)API.getInstance().arenaFrame.panel.field.getWidth()-150)
	 			g.drawImage(img1,(((int)currentLocation.getX()*(int)API.getInstance().arenaFrame.panel.field.getWidth())/(int)API.getInstance().getArena().getLength()),(int)currentLocation.getY(),150,150, panel);
			else
				g.drawImage(img1,(int)API.getInstance().arenaFrame.panel.field.getWidth()-150,(int)currentLocation.getY(),150,150, panel);
	 		panel.repaint();
	 	
		}
 		
    }

	public void loadImages(String nm){
		 switch(getColor()){
			 case BLACK:
				 try { 
					 img1 = ImageIO.read(new File(PICTURE_PATH + nm + "Black.png"));
				 } 
				 catch (IOException e) { 
					 System.out.println("Cannot load picture");
					 }
				 break;
			 case RED:
				 try { 
					 img1 = ImageIO.read(new File(PICTURE_PATH + nm + "Red.png"));
				 } 
				 catch (IOException e) { 
					 System.out.println("Cannot load picture");
					 }
				 break;
			 case GREEN:
				 try { 
					 img1 = ImageIO.read(new File(PICTURE_PATH + nm + "Green.png"));
				 } 
				 catch (IOException e) { 
					 System.out.println("Cannot load picture");
					 }
				 break;
			 case BLUE:
				 try { 
					 img1 = ImageIO.read(new File(PICTURE_PATH + nm + "Blue.png"));
				 } 
				 catch (IOException e) { 
					 System.out.println("Cannot load picture");
					 }
				 break;
			 case YELLOW:
				 try { 
					 img1 = ImageIO.read(new File(PICTURE_PATH + nm + "Yellow.png"));
				 } 
				 catch (IOException e) { 
					 System.out.println("Cannot load picture");
					 }
				 break;
			 default:
				 try { img1 = ImageIO.read(new File(PICTURE_PATH + nm + "_n_1.png"));} 
				 catch (IOException e) { System.out.println("Cannot load picture"); }			 
		 }
	}
	


	public static void resetCounter() {
		counter=0;
		
	}
	public double getCurrentSpeed() {
		return currentSpeed;
	}
	public boolean isfinish() {
		return currentLocation.getX()>finish.getX();
	}
	
	
	private boolean clicked = false;
	public boolean isClicked() {
		return clicked;
	}
	public void setClick(boolean clicked) {
		this.clicked = clicked;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getAcceleration() {
		return acceleration;
	}
	
	public static Comparator<Racer> copmareDistance = new Comparator<Racer>() {
			public int compare(Racer r1, Racer r2) {

			   int rollno1 = (int)r1.getCurLocation().getX();
			   int rollno2 = (int)r2.getCurLocation().getX();

			   /*For ascending order*/
			   //return rollno1-rollno2;

			   /*For descending order*/
			   return rollno2-rollno1;
   }};
   public static Comparator<Racer> copmareDirug = new Comparator<Racer>() {
		public int compare(Racer r1, Racer r2) {

		   int rollno1 = r1.getDirug();
		   int rollno2 = r2.getDirug();

		   /*For ascending order*/
		   return rollno1-rollno2;

		   /*For descending order*/
		   //return rollno2-rollno1;
   }};

	public State getState() {
		return state;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public void setState(State state) {
		this.state = state;
		//arena.update(this, null);
	}
	
	private int dirug;
	public int getDirug() {
		return dirug;
	}
	public void setDirug(int dirug) {
		this.dirug = dirug;
	}
	
	public ArrayList<String> timeBroken = new ArrayList<String>();

	public String getFinishedTime() {
		if(timeBroken.size() > 0)
			return timeBroken.get(timeBroken.size()-1);
		return "";
	}
	
	public ArrayList<String> getTimeBrokenArray() {
		return timeBroken;
	}
	public void addTimeBroken(String timeBroken, String reason) {
		this.timeBroken.add(timeBroken);
		System.out.println("R" + this.getSerialNumber() + " : has " + reason + " at: " + timeBroken);
	}

	private Mishap mishap;

	/**
	 * @param name
	 * @param maxSpeed
	 * @param acceleration
	 * @param color
	 */
	public Racer(String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color) {
		this.serialNumber = Racer.lastSerialNumber++;
		this.name = name;
		this.maxSpeed = maxSpeed;
		this.acceleration = acceleration;
		this.color = color;
		this.failureProbability = FAILURE_DEFAULT;
		attributes = new Hashtable<String, Object>();
		attributes.put(WheeledRacer.ATTRIBUTENAME, 0);
		attributes.put(ColoredRacer.ATTRIBUTENAME, Color.BLACK);
	}

	public abstract String className();

	public String describeRacer() {
		String s = "";
		s += "name: " + this.name + ", ";
		s += "SerialNumber: " + this.serialNumber + ", ";
		s += "maxSpeed: " + this.maxSpeed + ", ";
		s += "acceleration: " + this.acceleration + ", ";
		s += "color: " + this.color + ", ";
		s = s.substring(0, s.length() - 2);
		// returns a string representation of the racer, including: general attributes
		// (color name, number) and specific ones (numberOfWheels, etc.)
		s += this.describeSpecific();
		return s;
	}

	
	public abstract String describeSpecific();

	public int getSerialNumber() {
		return serialNumber;
	}

	private boolean hasMishap() {
		if (this.mishap != null && this.mishap.getTurnsToFix() == 0)
			this.mishap = null;
		return this.mishap != null;
	
	}

	@Override
	public String toString() {
		return describeRacer();
	}
	public void initRace(Arena arena, Point start, Point finish) {
		regObserver(arena);
		this.currentLocation = new Point(start.getX(),counter);
		counter();
		this.finish = new Point(finish);
	}

	public void introduce() {
		// Prints a line, obtained from describeRacer(), with its type
		System.out.println("[" + this.className() + "] " + this.describeRacer());
	}

	/*public synchronized Point move(double friction) {
		double reductionFactor = 1;
		if (!(this.hasMishap()) && Fate.breakDown(this.FAILURE_DEFAULT)) {
			this.mishap = Fate.generateMishap();
			//System.out.println(this.name + " Has a new mishap! (" + this.mishap + ")");
		}
		if (this.hasMishap()) {
			if (this.mishap.isFixable() == false) {
				this.setChanged();
				disabledState.doAction(this);
				this.notifyObservers(EnumContainer.RacerEvent.DISABLED);
				Thread.currentThread().interrupt();
			} else {
				this.setChanged();
				brokenState.doAction(this);
				this.notifyObservers(EnumContainer.RacerEvent.BROKENDOWN);
			}
			reductionFactor = mishap.getReductionFactor();
			this.mishap.nextTurn();
			return this.currentLocation;
		}
		else {
			this.setChanged();
			activeState.doAction(this);
			this.notifyObservers(EnumContainer.RacerEvent.REPAIRED);
		}

		if (this.currentSpeed < this.maxSpeed) {
			this.currentSpeed += this.acceleration * friction;
		}
		if (this.currentSpeed > this.maxSpeed) {
			this.currentSpeed = this.maxSpeed;
		}
		if(this.currentLocation.getX()>=this.finish.getX()) {
			this.currentLocation.setX(this.finish.getX());
			
		}
		double newX = ((currentLocation.getX() + (this.currentSpeed)) * reductionFactor);
		GUI.leftPanel.setX(getSerialNumber(), newX);
		currentLocation.setX(newX);
		Gui.update(this, null);
		return this.currentLocation;
		
	}*/
	public Point move(double friction) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.setChanged();
		if(mishap!=null && mishap.isFixable() && hasMishap()) {
			mishap=null;
			this.notifyObservers(Event.REPAIRED);
		}
		
		if(mishap==null && Fate.breakDown(this.failureProbability)) {
			mishap=Fate.generateMishap();
			System.out.println(name + " Has a new mishap! " + mishap);
			if(mishap.isFixable()) {
				this.notifyObservers(Event.BROKENDOWN);
				
			}
			else
				{
				this.notifyObservers(Event.DISABLED);
				Thread.currentThread().interrupt();
				return new Point(finish);
				}
			
		}
		
		
		if(mishap!=null && mishap.getTurnsToFix()>0) {
			this.setCurrentSpeed(this.currentSpeed + mishap.getReductionFactor() * this.acceleration);
			if(this.mishap.isFixable())
				mishap.nextTurn();			
		}
		else if (this.currentSpeed < this.maxSpeed) 
			this.setCurrentSpeed(this.currentSpeed + this.acceleration );

			
		
		if (this.currentSpeed > this.maxSpeed) 
			this.setCurrentSpeed(this.maxSpeed);
		
		Point newLocation = new Point((this.currentLocation.getX() + (friction * this.currentSpeed)),
				this.currentLocation.getY());
		this.setCurrentLocation(newLocation);
		
		if (this.currentLocation.getX() >= this.finish.getX())
			this.notifyObservers(Event.FINISHED);
		return this.currentLocation;
	}

	public Racer getRacer() {
		return this;
	}

	public double getMaxSpeed() {
		return this.maxSpeed;
	}

	public double getCurSpeed() {
		return this.currentSpeed;
	}

	public Point getCurLocation() {
		return this.currentLocation;
	}

	public double getFailure() {
		return this.failureProbability;
	}

	public void setFinish(Point f) {
		finish = new Point(f);
	}

	public Point getFinish() {

		return this.finish;
	}

	public void setCurrentLocation(Point p) {

		this.currentLocation = new Point(p);
	}

	public Point getCurrentLocation() {

		return this.currentLocation;
	}
	private void setCurrentSpeed(double currectspeed) {
		this.currentSpeed=currectspeed;
		
	}
	/*@Override
	public void run() {
		while (this.currentLocation.getX() <= finish.getX() )
		{
			move(friction);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				Thread.currentThread().interrupt(); // restore interrupted status
			}
			if(this.hasMishap() && this.mishap.isFixable() == false) break;
		}
		completedState.doAction(this);
		this.notifyObservers(utilities.EnumContainer.RacerEvent.FINISHED);
	}	*/
	
	@Override
	public void run() {
		System.out.println("runing");
		while(move(friction).getX()<=finish.getX()) {
			
		};
		
	}
	public void regObserver(Observer observeR) {
		arena=(Arena) observeR;
	}
	
	public String getName() {
		return name;
	}
	
	public Object clone() {
		Object clone=null;
		try {
			clone=super.clone();
		}catch (CloneNotSupportedException e) {
			e.printStackTrace();
		} return clone;
	}
	public void setFullData(String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color) {
		this.name = name;
		this.maxSpeed = maxSpeed;
		this.acceleration = acceleration;
		this.color = color;
		this.failureProbability = FAILURE_DEFAULT;
	}
	
	
	@SuppressWarnings("unused")
	private Observer Gui;
	public void addGuiObserver(Observer arenasPANEL) {
		Gui = arenasPANEL;
	}
	public Arena getArena() {
		return arena;
	}
	
	@Override
	public void addAttribute(String type, Object attr){
		attributes.put(type, attr);
	}
	@Override
	public Object getAttribut(String type){
		return  attributes.get(type);
	}
}







