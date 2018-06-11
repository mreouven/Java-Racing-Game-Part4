package graphics;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import utilities.API;

public class CtrlPanel extends JPanel {

	/**
	 * 
	 */
	public ArenaField field;
	private static API api = API.getInstance();
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public CtrlPanel(JPanel field) {
		this.field=(ArenaField) field;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JButton btnNewButton_1 = new JButton("Start Race");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					api.Start();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(51, 517, 150, 33);
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Show info");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_2.setBounds(51, 556, 150, 33);
		btnNewButton_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new JTableBasiqueAvecModeleDynamiqueObjet().setVisible(true);
			}
		});

	}

}
