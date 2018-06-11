/* reouven mimoun 341094993 emet genah 336530878 */
package Decorator;

import game.racers.IRacer;
import utilities.EnumContainer.Color;

public class ColoredRacer extends RacerDecorator {
	public static final String ATTRIBUTENAME = "color";
	private Color color;
	
	public ColoredRacer(IRacer decoratedRacer, Color color) {
		super(decoratedRacer);
		this.color = null;
		addAttribute(ATTRIBUTENAME, color);
	}
	
	@Override
	public void addAttribute(String type, Object attr) {
		this.color = (Color)attr;
		//decoratedRacer.addAttribute(type, this.color);
	}

	@Override
	public void introduce() {
		decoratedRacer.introduce() ; System.out.println("Color: "+color);
		
	}
}
