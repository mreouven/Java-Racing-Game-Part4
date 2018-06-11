package Builder;

import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.arenas.land.LandArena;
import game.racers.Prototype;
import game.racers.Racer;

import java.util.ArrayList;

public class CarRaceBuilder {
	private ArrayList<Racer> cars;
	private Arena land;
	public CarRaceBuilder(int N){
		cars = new ArrayList<Racer>(N);
		for (int i = 0; i < N; i++) {
			Racer r = Prototype.getRacer("Car");
			r.setSerialNumber(i+1);
			cars.add(r);
		}
		
		land = new LandArena();
		for (Racer car : cars) {
			try {
				land.addRacer(car);
			} catch (RacerLimitException | RacerTypeException e) {}
		}
		
		land.initRace();
	}
	
	public ArrayList<Racer> getCars(){
		return cars;
	}
	public Arena getArena(){
		return land;
	}
	
	public void startRace(){
		try {
			land.startRace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
