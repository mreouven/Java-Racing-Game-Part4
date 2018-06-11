package game.racers;

import java.util.Hashtable;

import game.racers.air.Airplane;
import game.racers.air.Helicopter;
import game.racers.land.*;
import game.racers.naval.*;

public class Prototype  {
	private static Hashtable <String,Racer> RacerMap=new Hashtable <String,Racer>();
	
	public static Racer getRacer(String racerType) {
		Racer proto = RacerMap.get(racerType);
		return (Racer) proto.clone();
	}
	
	public static void load() {
		Racer r = null;
		
		r = new Airplane();
		r.setSerialNumber(-99);
		RacerMap.put("Airplane", r);
		
		r = new Helicopter();
		r.setSerialNumber(-99);
		RacerMap.put("Helicopter", r);
		
		r = new Bicycle();
		r.setSerialNumber(-99);
		RacerMap.put("Bicycle", r);
		
		r = new Car();
		r.setSerialNumber(-99);
		RacerMap.put("Car", r);
		
		r = new Horse();
		r.setSerialNumber(-99);
		RacerMap.put("Horse", r);
		
		r = new RowBoat();
		r.setSerialNumber(-99);
		RacerMap.put("RowBoat", r);
		
		r = new SpeedBoat();
		r.setSerialNumber(-99);
		RacerMap.put("SpeedBoat", r);

	}
	
}
