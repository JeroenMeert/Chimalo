import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.Button;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.sun.corba.se.spi.legacy.connection.GetEndPointInfoAgainException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;


public class ErfgoedLijstPanel extends JPanel implements ChangeListener{

	/**
	 * Create the panel.
	 */
	private JPanel lijst;
	private JScrollPane scrollPane;
	private Model model;
	private hoofd_scherm parentFrame;
	private Erfgoed activeErfgoed;
	private ErfgoedItemPanel erfg;
	
	public ErfgoedLijstPanel(Model m, hoofd_scherm par) {
		setLayout(null);
		model = m;
		parentFrame=par;
		model.subscribe(this);
		lijst = new JPanel();
		lijst.setLayout(new GridLayout(100,0));
		
		
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 590, 469);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(lijst);
		scrollPane.getVerticalScrollBar().setUnitIncrement(150);
		scrollPane.getVerticalScrollBar().setBlockIncrement(150);
		add(scrollPane);
		
		JButton btnWissen = new JButton("Wissen");
		btnWissen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (activeErfgoed!=null & model.magErfgoedVerwijderdWorden(activeErfgoed)){
					model.removeErfgoed(activeErfgoed);
					refresh();
				}
				else{
					JOptionPane.showMessageDialog(null, "Dit erfgoed kan niet verwijderd worden omdat er nog inzendingen aan dit erfgoed gelinkt zijn");
				}
			}
		});
		refresh();
		btnWissen.setBounds(618, 60, 179, 34);
		add(btnWissen);
		
		JButton btnNieuwErfgoedMaken = new JButton("Nieuw erfgoed maken");
		btnNieuwErfgoedMaken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NieuwErfgoedFrame nif = new NieuwErfgoedFrame(model);
				nif.setVisible(true);
			}
		});
		btnNieuwErfgoedMaken.setBounds(618, 10, 179, 34);
		add(btnNieuwErfgoedMaken);
				
		setPreferredSize(new Dimension(840,540));
		
		JLabel lblTerugNaarHoofdmenu = new JLabel("<< Terug naar hoofdmenu");
		lblTerugNaarHoofdmenu.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblTerugNaarHoofdmenu.setBounds(610, 433, 220, 45);
		lblTerugNaarHoofdmenu.addMouseListener(new MouseListener() {
			
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
				parentFrame.newPanel(new DashbordPanel(model, parentFrame));
				
			}
		});
		add(lblTerugNaarHoofdmenu);

	}
	
	private void refresh(){
		lijst.removeAll();
		//lijst.setLayout(new GridLayout(model.getErfgoeden().size(),0));
		for (Erfgoed e : model.getErfgoeden()){
			if( model.getActiveErfgoed()!=null && e.getNaam().equals(model.getActiveErfgoed().getNaam()) ){
				erfg =new ErfgoedItemPanel(e,parentFrame,model);
				erfg.setBackground(Color.gray);
				System.out.println("found");
			}
			else
			erfg =new ErfgoedItemPanel(e,parentFrame,model);
			
			lijst.add(erfg);
			
			
			
		}
		lijst.repaint();
		scrollPane.setViewportView(lijst);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		refresh();		
	}

}
