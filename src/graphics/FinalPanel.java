package graphics;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FinalPanel extends JPanel implements ActionListener{
	//private static API api = API.getInstance();
	public ArenaField field;
	public ControlPane control;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FinalPanel() {
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		field = new ArenaField();
		add(field,BorderLayout.EAST);
		control = new ControlPane(field);
		add(control,BorderLayout.AFTER_LAST_LINE);

	}
	public ControlPane getcontrol() {
		return control;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}

}
