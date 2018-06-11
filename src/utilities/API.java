package utilities;



import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import factory.RaceBuilder;
import game.arenas.*;
import game.arenas.exceptions.*;
import game.racers.Racer;
import graphics.ArenaFrame;

public class API {
	@SuppressWarnings("unused")
	private Arena myArena;
	public ArenaFrame arenaFrame;
	private static ArrayList<Racer> racers;
	private static RaceBuilder builder = RaceBuilder.getInstance();
	private static API instance;
	private JComboBox<String> comboBox_1;
	private EnumContainer.Arena arena;
	public static API getInstance() {
		if (instance == null) {
			instance = new API();
		}
		return instance;
	}
	
	
	public Arena getArena() {
		return myArena;
	}


	public void BuildArena(String nm, double length, int maxRacers) {
		try {
			myArena = builder.buildArena("game.arenas."+nm, length, maxRacers);
			System.out.println(nm+" has been built ");
			setRacerChoose();
			Racer.resetCounter();
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
		
			System.out.println("Unable to build arena!");
			//TODO arenaFrame.setSize(new Dimension((int)length, 700));
			
		}
		
	}
	public void setArenaType(utilities.EnumContainer.Arena arena) {
		this.arena=arena;
	}
	
	public void setComboBox_1(JComboBox<String> comboBox_1) {
		this.comboBox_1 = comboBox_1;
	}
	
	public void resetGUI() {
		arenaFrame.panel.field.repaint();
	}
	
	public void Start() throws InterruptedException {
		
		//myArena.initRace();
		myArena.startRace();
		resetGUI();
		
	}

	public ArenaFrame getArenaFrame() {
		return arenaFrame;
	}


	public void setArenaFrame(ArenaFrame arenaField) {
		this.arenaFrame = arenaField;
	}


	public void addRacer(String type,String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color) throws RacerLimitException {
		
		if(racers==null) {
			racers=new ArrayList<>();
		}
		try {
		switch (this.arena) {
		case LAND:
			try {
				if(type.compareTo("Car")==0 || type.compareTo("Bicycle")==0)
				myArena.addRacer(builder.buildWheeledRacer("game.racers.land."+type, name, maxSpeed, acceleration, color, 4));
				else {
					myArena.addRacer(builder.buildRacer("game.racers.land."+type, name, maxSpeed, acceleration, color));
				}
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
			break;
			
		case NAVAL:
			try {
				myArena.addRacer(builder.buildRacer("game.racers.naval."+type, name, maxSpeed, acceleration, color));
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
			break;
		case AERA:
			try {
				myArena.addRacer(builder.buildWheeledRacer("game.racers.air."+type, name, maxSpeed, acceleration, color, 2));
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
			break;
		default:
			break;
		}
	
		}	catch (RacerLimitException e) {
			throw e;
			
		} catch (RacerTypeException e) {
			System.out.println("[Error] " + e.getMessage());
		}
		System.out.println("racer added");
		
	
		resetGUI();
	}
	
	
	public void setRacerChoose() {

		comboBox_1.removeAllItems();
		switch (this.arena) {
		case LAND:
			comboBox_1.insertItemAt("Car", 0);
			comboBox_1.insertItemAt("Bicycle", 0);
			comboBox_1.insertItemAt("Horse", 0);
			break;
			
		case AERA:
			comboBox_1.insertItemAt("Airplane", 0);
			comboBox_1.insertItemAt("Helicopter", 0);
			break;
			
		case NAVAL:
			comboBox_1.insertItemAt("RowBoat", 0);
			comboBox_1.insertItemAt("SpeedBoat", 0);
			break;

		default:
			break;
		}
		comboBox_1.setSelectedIndex(0);
	}
	

}
