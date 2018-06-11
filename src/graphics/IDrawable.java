package graphics;

import java.awt.Graphics;

import utilities.EnumContainer.Color;

public interface IDrawable {
	public final static String PICTURE_PATH = "src/icons/";
	 public void loadImages(String nm);
	 public void drawObject(Graphics g,ArenaField panel);
	 public Color getColor();	
}
