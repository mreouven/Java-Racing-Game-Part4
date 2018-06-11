package graphics;

import javax.swing.JPanel;
import javax.swing.JTextField;

import utilities.API;
import utilities.EnumContainer.Arena;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ArenalPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private final static String[] arenaList = { "LandArena", "NavalArena", "AerialArena"};
	private static final long serialVersionUID = 1L;
	private static API api = API.getInstance();
	double lenght=-1;
	int max_racer=-1;
	public ArenaField field;
	private JTextField lenght_text;
	private JTextField racer_text;
	JComboBox<String> comboBox;
	

	/**
	 * Create the panel.
	 */
	public ArenalPanel(JPanel field) {
		
		this.field=(ArenaField) field;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setSize(187,126);
		JLabel lblChooseArena = new JLabel("Choose Arena:");
		lblChooseArena.setFont(new Font("Tahoma", Font.PLAIN, 16 ));
		//lblChooseArena.setBounds(64, 0, 112, 25);
		lblChooseArena.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblChooseArena);
		add(Box.createGlue()); 
		
		comboBox = new JComboBox<String>(arenaList);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		//comboBox.setBounds(63, 28, 113, 25);
		comboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(comboBox);
		add(Box.createGlue()); 
		
		
		JLabel lblArenaLenght = new JLabel("Arena Lenght:");
		lblArenaLenght.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblArenaLenght.setBounds(63, 53, 112, 25);
		lblArenaLenght.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblArenaLenght);
		add(Box.createGlue()); 
		
		
		lenght_text = new JTextField();
		lenght_text.setFont(new Font("Tahoma", Font.PLAIN, 16));
		//textField.setBounds(63, 74, 113, 33);
		lenght_text.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lenght_text);
		lenght_text.setColumns(10);
		
		JLabel lblArenaMracer = new JLabel("Max Racer:");
		lblArenaMracer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblArenaMracer.setBounds(63, 53, 112, 25);
		lblArenaMracer.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblArenaMracer);
		add(Box.createGlue()); 
		
		
		
		
		racer_text = new JTextField();
		racer_text.setFont(new Font("Tahoma", Font.PLAIN, 16));
		racer_text.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(racer_text);
		racer_text.setColumns(10);
		
		
		
		add(Box.createGlue()); 
		JButton btnNewButton = new JButton("Build Arena");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		//btnNewButton.setBounds(51, 160, 150, 33);
		btnNewButton.addActionListener(this);
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnNewButton);
		add(Box.createGlue()); 
	}


	@Override
	public void actionPerformed(ActionEvent e) {
	if (!lenght_text.getText().equals(""))
		{
		lenght_text.setBackground(Color.WHITE);
		try {
			lenght=Integer.parseInt(lenght_text.getText());
		} catch (NumberFormatException e1){
			JOptionPane.showMessageDialog(this, "Enter numerical value","EROOR",JOptionPane.INFORMATION_MESSAGE);
			lenght_text.setBackground(Color.ORANGE);
		}
		}
		else {
			lenght_text.setBackground(Color.RED);
			JOptionPane.showMessageDialog(this, "Invalid Content please enter Information","EROOR",JOptionPane.ERROR_MESSAGE);
			
		}
	
	if (!racer_text.getText().equals(""))
	{
		racer_text.setBackground(Color.WHITE);
	try {
		max_racer=Integer.parseInt(racer_text.getText());
	} catch (NumberFormatException e1){
		JOptionPane.showMessageDialog(this, "Enter numerical value","EROOR",JOptionPane.INFORMATION_MESSAGE);
		racer_text.setBackground(Color.ORANGE);
	}
	}
	else {
		racer_text.setBackground(Color.RED);
		JOptionPane.showMessageDialog(this, "Invalid Content please enter Information","EROOR",JOptionPane.ERROR_MESSAGE);
		
	}
	
	if(lenght !=-1 && max_racer != -1) {
		switch ((String) comboBox.getSelectedItem()) {
	
			case "LandArena":
				api.setArenaType(Arena.LAND);
				api.BuildArena("land.LandArena", lenght, max_racer);
				this.field.setBackgr(Arena.LAND);
				
				break;
			case "NavalArena":
				api.setArenaType(Arena.NAVAL);
				api.BuildArena("naval.NavalArena", lenght, max_racer);
				this.field.setBackgr(Arena.NAVAL);
				
				break;
			case "AerialArena":
				api.setArenaType(Arena.AERA);
				api.BuildArena("air.AerialArena", lenght, max_racer);
				this.field.setBackgr(Arena.AERA);
				
				break;
			default:
				break;
		}
	
		
			
			
		}
	}
}
