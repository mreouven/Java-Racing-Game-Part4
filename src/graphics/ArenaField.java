package graphics;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.racers.air.IAerialRacer;
import utilities.API;
import utilities.EnumContainer;

public class ArenaField extends JPanel  {

	private static API api = API.getInstance();
	/**
	 * Create the panel.
	 */
	public ArenaField(){
		
		setPreferredSize(new Dimension(5050, 1500));
		setBackgr(EnumContainer.Arena.NULL);
		
		
	}

	
	private static final long serialVersionUID = 1L;

	
	
	
	private BufferedImage backroun;
	private boolean bgr;
	IAerialRacer tia;
	
	
	
	

	
	public synchronized void paintComponent(Graphics g){
		
		   	super.paintComponent(g);
		   	if(bgr && (backroun!=null))
	            g.drawImage(backroun, 0, 0, getWidth(), getHeight(), this);
		   		if(api.getArena()!=null)
		   			{
		   			api.getArena().drawObject(g, this);}
		   
		   	
	   }
	   
	
	
	public void setBackgr(EnumContainer.Arena type) {
		   switch(type) {
		   case NAVAL:
			   try { backroun = ImageIO.read(new File(IDrawable.PICTURE_PATH+"NavalArena.jpg")); } 
				catch (IOException e) { System.out.println("Cannot load background"); }
			   bgr = true; 
			   break;
		   case AERA:
			   try { backroun = ImageIO.read(new File(IDrawable.PICTURE_PATH+"AerialArena.jpg")); } 
				catch (IOException e) { System.out.println("Cannot load background"); }
			   bgr = true; 
			   break;
		   case LAND:
			   try { backroun = ImageIO.read(new File(IDrawable.PICTURE_PATH+"LandArena.jpg")); } 
				catch (IOException e) { System.out.println("Cannot load background"); }
			   bgr = true; 
			   break;
		   default:
			   try { backroun = ImageIO.read(new File(IDrawable.PICTURE_PATH+"default.jpg")); } 
				catch (IOException e) { System.out.println("Cannot load background"); }
			   bgr = true;
			     
		   }
		   repaint();
	}
	
	
	
	
	
	
	


	
	

}
