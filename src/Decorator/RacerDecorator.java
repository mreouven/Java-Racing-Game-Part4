package Decorator;

import game.racers.IRacer;

public abstract class RacerDecorator implements IRacer{
	protected IRacer decoratedRacer;
	
	public RacerDecorator(IRacer decoratedRacer){
		this.decoratedRacer = decoratedRacer;
	}
	
	@Override
	public Object getAttribut(String type) {
		return this.decoratedRacer.getAttribut(type);
	}
	@Override
	public void introduce() {
		decoratedRacer.introduce();
	}
	
}
