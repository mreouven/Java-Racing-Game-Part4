package graphics;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JTableBasiqueAvecModeleDynamiqueObjet extends JFrame  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ModeleDynamiqueObjet modele = new ModeleDynamiqueObjet();
    private JTable tableau;
    Timer timer;  
    public JTableBasiqueAvecModeleDynamiqueObjet() {
        super(); 
        setTitle("JTable avec modèle dynamique");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 
        tableau = new JTable(modele); 
        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
        JPanel boutons = new JPanel();      
        getContentPane().add(boutons, BorderLayout.SOUTH); 
        pack();
        timer = createTimer ();
        timer.start ();
    }
 
    


    private Timer createTimer()
    {

      ActionListener action = new ActionListener ()
        {
         
          public void actionPerformed (ActionEvent event)
          {
            modele.update();
          }
        };
        
      return new Timer (50, action);
    }  
    
    
    
    
 
   
}