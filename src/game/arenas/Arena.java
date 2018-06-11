package game.arenas;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.IRacer;
import game.racers.Racer;
import graphics.ArenaField;
import state.*;
import utilities.API;
import utilities.EnumContainer;
import utilities.Point;
import utilities.EnumContainer.Event;

public abstract class Arena implements Observer {
	private final static int MIN_Y_GAP = 50;
	private ArrayList<Racer> activeRacers;
	private ArrayList<Racer> compleatedRacers;
	private ArrayList<Racer> brokenRacers;
	private ArrayList<Racer> allRacers;
	private boolean hasStart = false;
	private ArrayList<Racer> disabledRacers;
	private double length;
	private final int MAX_RACERS;
	private final double FRICTION;
	private Stopper stopper;
	
	public ArrayList<Racer> getAllRacers() {
		return allRacers;
	}
	public boolean isHasStart() {
		return hasStart;
	}
	public double getLength() {
		return length;
	}
	public synchronized void drawObject(Graphics g,ArenaField panel) {
		for (Racer racer : activeRacers) {
			racer.drawObject(g, panel);
		}
		for (Racer racer : brokenRacers) {
			racer.drawObject(g, panel);
		}
		for (Racer racer : compleatedRacers) {
			racer.drawObject(g, panel);
		}
		
	}
	/**
	 * 
	 * @param length
	 *            the x value for the finish line
	 * @param maxRacers
	 *            Maximum number of racers
	 * @param friction
	 *            Coefficient of friction
	 * 
	 */
	protected Arena(double length, int maxRacers, double friction) {
		this.length = length;
		this.MAX_RACERS = maxRacers;
		this.FRICTION = friction;
		this.activeRacers = new ArrayList<Racer>();
		this.compleatedRacers = new ArrayList<Racer>();
		this.brokenRacers = new ArrayList<Racer>();
		this.disabledRacers = new ArrayList<Racer>();
		this.allRacers = new ArrayList<Racer>();
		
	}

	public void startRace() throws InterruptedException{
		if(activeRacers.size() == 0) {
			System.out.println("There aren't data of racer for the game");
			return;
		}
		stopper = new Stopper();
		stopper.start();
		hasStart = true;
		ExecutorService game = Executors.newFixedThreadPool(this.activeRacers.size());
		if(game==null)
			throw new InterruptedException();
		for (Racer racer : this.activeRacers) 
		{
			racer.setFinish(new Point(this.length, 0));
			racer.setCurrentLocation(new Point(0, 0));
			game.execute(racer);
		}
	
		
		
	}

	

	public void addRacer(Racer newRacer) throws RacerLimitException, RacerTypeException {
		if (this.activeRacers.size() == this.MAX_RACERS) 
		{
			throw new RacerLimitException(this.MAX_RACERS, newRacer.getSerialNumber());
		}
		
		this.activeRacers.add(newRacer);
		this.allRacers.add(newRacer);
		newRacer.setSerialNumber(activeRacers.size());
		newRacer.setFriction(FRICTION);
	}
	public void addRacer(IRacer newRacer) throws RacerLimitException, RacerTypeException {
		if (this.activeRacers.size() == this.MAX_RACERS) 
		{
			throw new RacerLimitException(this.MAX_RACERS, ((Racer) newRacer).getSerialNumber());
		}
		this.activeRacers.add((Racer) newRacer);
		this.allRacers.add((Racer) newRacer);
		((Observable) newRacer).addObserver(this);
		((Racer) newRacer).setSerialNumber(activeRacers.size());
	}

	public ArrayList<Racer> getActiveRacers() {
		return activeRacers;
	}

	public ArrayList<Racer> getCompleatedRacers() {
		return compleatedRacers;
	}

	public boolean hasActiveRacers() {
		return this.activeRacers.size() > 0;
	}

	public void initRace() {
		int y = 0;
		for (Racer racer : this.activeRacers) {
			Point s = new Point(0, y);
			Point f = new Point(this.length, y);
			racer.initRace(this, s, f);
			y += Arena.MIN_Y_GAP;
		}
	}

	public void showResults() {
		for (Racer r : this.compleatedRacers) {
			String s = "#" + this.compleatedRacers.indexOf(r) + " -> ";
			s += r.describeRacer();
			System.out.println(s);
		}
	}

	public Arena getArena() {
		return this;
	}

	public static int getNewMinYGap() {
		return MIN_Y_GAP;
	}

	public int getMaxRacer() {
		return this.MAX_RACERS;
	}

	public void setCompletedRacers(Racer racer) {
		this.compleatedRacers.add(racer);
		if(activeRacers.size() == 0) stopper.stop(true);
	}
	
	/*@Override
	public synchronized void update(Observable o, Object arg) {
		
		Racer r = (Racer) o;
		synchronized(r)
		{
			State state = r.getState();
			if(state instanceof Completed || (EnumContainer.Event)arg == Event.FINISHED) {
				this.compleatedRacers.add(r);
				this.activeRacers.remove(r);
				r.setDirug(this.compleatedRacers.size());
				r.addTimeBroken(stopper.getTime() , "Completed");
				sortRacer(compleatedRacers);
			}
			else if((EnumContainer.Event)arg == Event.BROKENDOWN||state instanceof Broken  && !this.brokenRacers.contains(r) && this.activeRacers.contains(r)) {
				this.brokenRacers.add(r);
				this.activeRacers.remove(r);
				r.addTimeBroken(stopper.getTime(), "Broken");
				//System.out.println("R" + r.getSerialNumber() + "\thas brokend");
			}
			else if(state instanceof Active && this.brokenRacers.contains(r) && !this.activeRacers.contains(r)) {
				this.brokenRacers.remove(r);
				this.activeRacers.add(r);
				sortRacer(activeRacers);
				r.setDirug(this.activeRacers.indexOf(r)+1);
				//System.out.println("R" + r.getSerialNumber() + "\thas active");
			}
			else if((EnumContainer.Event)arg == Event.DISABLED||state instanceof Disabled) {
				this.disabledRacers.add(r);
				this.activeRacers.remove(r);
				r.addTimeBroken(stopper.getTime() , "Disabled");
				r.setDirug(999);
				//System.out.println("R" + r.getSerialNumber() + "\thas disabled");
			}
			if(infoTable != null) infoTable.update(r, this);
		}
		//API.getInstance().resetGUI();
	}*/
	@Override
	public synchronized void update(Observable o, Object arg) {
		switch((EnumContainer.Event)arg)
		{
		case FINISHED:
			System.out.println("FINISHED ");
			((Racer)o).introduce();
			this.activeRacers.remove((Racer)o);
			this.compleatedRacers.add((Racer)o);
			((Racer)o).introduce();;
			
			try {
				((Racer)o).wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case BROKENDOWN:
			System.out.println("BROKENDOWN ");
			((Racer)o).introduce();
			this.activeRacers.remove((Racer)o);
			this.brokenRacers.add((Racer)o);	
			
			break;
		case REPAIRED:
			System.out.println("REPAIRED ");
			((Racer)o).introduce();
			this.brokenRacers.remove((Racer)o);	
			this.activeRacers.add((Racer)o);
			
			break;
		case DISABLED:
			System.out.println("DISABLED ");
			((Racer)o).introduce();
			this.activeRacers.remove((Racer)o);
			this.disabledRacers.add((Racer)o);
			break;
		default:			
			break;
		}
		//API.getInstance().resetGUI();
	} 	

	public void setActiveRacers(Racer rac) {
		activeRacers.remove(rac);
	}

	public double getFriction() {
		return this.FRICTION;
	}
	
	public synchronized boolean isFinished(){
		boolean res = hasStart == true && activeRacers.size() == 0 && brokenRacers.size() == 0;
		return res;
	}

	private Observer infoTable = null;
	public void setTableObserver(Observer obs) {
		infoTable = obs;
	}
	
	public void updateResultTableDate(){
		sortRacer(compleatedRacers);
	}
	private synchronized void sortRacer(ArrayList<Racer> racers){
		if(racers == activeRacers)			Collections.sort(racers, Racer.copmareDistance); 
		else if(racers == compleatedRacers) Collections.sort(racers, Racer.copmareDirug);
		if(racers == compleatedRacers && isFinished()){
			for (Racer racer : compleatedRacers) {
				System.out.println("Dirug " + racer.getDirug()+ ") Racer " + racer.getSerialNumber() + " - finished at time: " + racer.getFinishedTime());
			}
		}
		Collections.sort(allRacers, Racer.copmareDirug);
	}
}
