package graphics;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import game.racers.Racer;
import utilities.API;

public class ModeleDynamiqueObjet extends AbstractTableModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Ami> amis = new ArrayList<Ami>();
	
 
    private final String[] entetes = {"Racer Name", "Current Speed", "Max Speed", "Current X", "Finished"};
 
    public ModeleDynamiqueObjet() {
        super();
        
		
        update();
        
    }
    public void update() {
    	fireTableRowsDeleted(0, getRowCount()-1);
    	amis = new ArrayList<Ami>();
    	for (Racer racer : API.getInstance().getArena().getActiveRacers()) {
			amis.add(new Ami(racer));
			fireTableRowsInserted(amis.size() -1, amis.size() -1);
			}
    	for (Racer racer : API.getInstance().getArena().getCompleatedRacers()) {
			amis.add(new Ami(racer));
			fireTableRowsInserted(amis.size() -1, amis.size() -1);
			}
    }
 
	public int getRowCount() {
	    return amis.size();
	}
	
	public int getColumnCount() {
	    return entetes.length;
	}
	
	public String getColumnName(int columnIndex) {
	    return entetes[columnIndex];
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
	    switch(columnIndex){
	        case 0:
	            return amis.get(rowIndex).getRacerName();
	        case 1:
	            return amis.get(rowIndex).getCurrentSpeed();
	        case 2:
	            return amis.get(rowIndex).getMaxSpeed();
	        case 3:
	            return amis.get(rowIndex).getCurrentX();
	        case 4:
	            return amis.get(rowIndex).isFinished();
	        default:
	            return null; 
	    }
}
}