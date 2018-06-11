package state;

import game.racers.Racer;

public class Disabled implements State {

	@Override
	public void doAction(Racer racer) {
		racer.setState(this);
	}

	@Override
	public String toString() {
		return "Failed";
	}
}
