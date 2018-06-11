package graphics;


import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class ControlPane extends JPanel {
	public ArenaField field;
	public ArenalPanel onep;
	public RacerPanel twop;
	public CtrlPanel threep;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ControlPane(JPanel field) {
		this.field=(ArenaField) field;
		ArenalPanel onep=new ArenalPanel(this.field);
		RacerPanel twop=new RacerPanel(this.field);
		CtrlPanel threep=new CtrlPanel(this.field);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(200, 500));
		onep.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
		twop.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
		threep.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
		
		add(onep);
		add(twop);
		add(threep);
	}

	
	
}
