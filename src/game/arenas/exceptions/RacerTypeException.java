package game.arenas.exceptions;

@SuppressWarnings("serial")
public class RacerTypeException extends Exception {
	public RacerTypeException(String racerType, String arenaType) {
		super(arenaType.compareTo("Land") == 0?
				("Invalid Racer of type \"" + racerType + "\" for " + arenaType + " arena. (Or there isn\'t wheels in this racer)") :
					("Invalid Racer of type \"" + racerType + "\" for " + arenaType + " arena."));
	}

}
