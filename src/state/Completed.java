package state;

import game.racers.Racer;

public class Completed implements State {

	@Override
	public void doAction(Racer racer) {
		racer.setState(this);
	}

	@Override
	public String toString() {
		return "Completed";
	}
}
