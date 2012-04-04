import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.JButton;


public class ErfgoedPanel extends ImagePanel  {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;
	
	private Model model;
	private hoofd_scherm parentFrame;
	private ErfgoedPanel parent;
	private Erfgoed erfgoed;
	/**
	 * Create the panel.
	 */
	public ErfgoedPanel(Model m , hoofd_scherm parentframe, Erfgoed erfGoed) {
		
		erfgoed=erfGoed;
		model = m;
		parent=this;
		parentFrame=parentframe;
		setLayout(null);
		setPreferredSize(new Dimension(810, 491));
		JLabel lblNaam = new JLabel("Naam");
		lblNaam.setBounds(25, 30, 77, 14);
		add(lblNaam);
		
		
		JLabel lblLocatie = new JLabel("Locatie : ");
		lblLocatie.setBounds(25, 83, 125, 14);
		add(lblLocatie);
		
		JLabel lblStau = new JLabel("Kenmerken");
		lblStau.setBounds(25, 208, 125, 14);
		add(lblStau);
		
		JLabel lblGeschiedenis = new JLabel("Geschiedenis");
		lblGeschiedenis.setBounds(246, 30, 83, 14);
		add(lblGeschiedenis);
		
		JLabel lblNuttigeInfo = new JLabel("Nuttige info");
		lblNuttigeInfo.setBounds(519, 30, 103, 14);
		add(lblNuttigeInfo);
		
		JTextArea txtGeschiedenis = new JTextArea(erfgoed.getGeschiedenis());
		txtGeschiedenis.setWrapStyleWord(true);
		txtGeschiedenis.setLineWrap(true);
		txtGeschiedenis.setBounds(246, 50, 243, 265);
		add(txtGeschiedenis);
		
		JTextArea txtInfo = new JTextArea(erfgoed.getInfo());
		txtInfo.setBounds(519, 50, 243, 265);
		add(txtInfo);
		
		JLabel lblBekijkInzendingenVan = new JLabel("Bekijk inzendingen van dit erfgoed >>");
		lblBekijkInzendingenVan.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblBekijkInzendingenVan.setBounds(25, 350, 299, 39);
		lblBekijkInzendingenVan.addMouseListener(new MouseListener() {
			
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
				//model.unsubscribe(parent);
				parentFrame.newPanel(new DashbordPanel(model,parentFrame,textField.getText()));
			}
		});
		add(lblBekijkInzendingenVan);
		
		textField = new JTextField(erfgoed.getNaam());
		textField.setEditable(false);
		textField.setBounds(25, 52, 211, 20);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField(erfgoed.getLocatie());
		textField_1.setEditable(false);
		textField_1.setBounds(25, 114, 211, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblLink = new JLabel("Link");
		lblLink.setBounds(25, 145, 46, 14);
		add(lblLink);
		
		textField_3 = new JTextField(erfgoed.getLink());
		textField_3.setEditable(false);
		textField_3.setBounds(25, 162, 211, 20);
		add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblMaakEenNieuw = new JLabel("Maak een nieuw item voor dit erfgoed >>");
		lblMaakEenNieuw.setFont(new Font("Arial Black", Font.BOLD, 12));
		lblMaakEenNieuw.setBounds(25, 392, 341, 32);
		lblMaakEenNieuw.addMouseListener(new MouseListener() {
			
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
				NieuwItemFrame nif = new NieuwItemFrame(model,erfgoed);
				nif.setVisible(true);
			}
		});
		add(lblMaakEenNieuw);
		
		JLabel label = new JLabel("<< Naar lijst");
		label.setFont(new Font("Arial Black", Font.BOLD, 12));
		label.setBounds(25, 435, 341, 32);
		label.addMouseListener(new MouseListener() {
			
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
				parentFrame.newPanel(new ErfgoedLijstPanel(model, parentFrame));
				
			}
		});
		add(label);

	}
}
