package state;

import game.racers.Racer;

public class Broken implements State {

	@Override
	public synchronized void doAction(Racer racer) {
		racer.setState(this);
	}

	@Override
	public String toString() {
		return "Broken";
	}
}
