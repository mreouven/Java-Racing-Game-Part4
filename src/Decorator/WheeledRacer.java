package Decorator;

import game.racers.IRacer;

public class WheeledRacer extends RacerDecorator {
	public static final String ATTRIBUTENAME = "numOfWheels";
	@SuppressWarnings("unused")
	private int numOfWheels;
	public WheeledRacer(IRacer decoratedRacer, int numOfWheels) {
		super(decoratedRacer);
		this.numOfWheels = 0;
		addAttribute(ATTRIBUTENAME, numOfWheels);
	}
	@Override
	public void addAttribute(String type, Object attr) {
		if(attr instanceof Integer)
			this.numOfWheels += ((Integer)attr).intValue();
		//decoratedRacer.addAttribute(type, this.numOfWheels);
	}
	@Override
	public void introduce() {
		decoratedRacer.introduce(); System.out.println( "numof whel: "+numOfWheels);
		
	}
}
