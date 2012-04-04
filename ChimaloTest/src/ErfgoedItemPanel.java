import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ErfgoedItemPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private hoofd_scherm parentFrame;
	private Model model;
	private Erfgoed erfgoed;
	
	public ErfgoedItemPanel(Erfgoed e,hoofd_scherm parentframe, Model m) {
		erfgoed = e; 
		this.parentFrame=parentframe;
		model=m;
		
		setLayout(null);
		setPreferredSize(new Dimension(590, 75));
		JLabel lblNaam = new JLabel("Naam :");
		lblNaam.setBounds(10, 11, 46, 14);
		add(lblNaam);
		
		JLabel lblLocatie = new JLabel("Locatie :");
		lblLocatie.setBounds(10, 45, 59, 14);
		add(lblLocatie);
		
		JLabel lblN = new JLabel(e.getNaam());
		lblN.setBounds(79, 11, 167, 14);
		add(lblN);
		
		JLabel lblL = new JLabel(e.getLocatie());
		lblL.setBounds(79, 45, 167, 14);
		add(lblL);
		
		JLabel lblType = new JLabel("Type :");
		lblType.setBounds(256, 11, 46, 14);
		add(lblType);
		
		JLabel lblT = new JLabel(e.getType());
		lblT.setBounds(302, 11, 262, 14);
		add(lblT);
		
		JButton btnDetails = new JButton("Details");
		btnDetails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.newPanel(new ErfgoedPanel(model, parentFrame ,erfgoed));
			}
		});
		btnDetails.setBounds(475, 41, 89, 23);
		add(btnDetails);
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				model.setActiveErfgoed(erfgoed);
				System.out.println(model.getActiveErfgoed().getNaam());
				
			}
		});

	}

	public Erfgoed getErfgoed(){
		return erfgoed;
	}
}
