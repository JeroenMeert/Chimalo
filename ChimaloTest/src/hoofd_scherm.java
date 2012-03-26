import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class hoofd_scherm extends JFrame implements ChangeListener{
	private ImagePanel contentPane;
	private JTextField activeTitel;
	private JTextField activeAuteur;
	private JTextField activeDatum;
	private JTextField zoekVeld;
	private JTextArea activeBeschrijving;
	//private ArrayList<Item> items;
	private JPanel panel_1;
	private Model model;
	private JRadioButton rdbtnAfgekeurde;
	private JScrollPane scrollPane;
	private JFrame parent;
	private JRadioButton rdbtnGoedgekeurd;
	private JRadioButton rdbtnKeurlijst;
	private ItemPanel ip;
	private JButton saveWijziging;
	private String state;
	private JButton btnWijzigen;
	private JButton btnAfkeuren;
	private JButton btnGoedkeuren;
	private JComboBox zoekBox ;
	private JLabel lblTeller;
	private ImagePanel activePhoto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Model m = new Model("Anthony", "Administrator");
					hoofd_scherm frame = new hoofd_scherm(m);
					frame.setVisible(true);
					Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
				    
				    // bereken het midden van je scherm
				    int w = frame.getSize().width;
				    int h = frame.getSize().height;
				    int x = (size.width-w)/2;
				    int y = (size.height-h)/2;
				    
				    // verplaats de GUI
				    frame.setLocation(x, y);  
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public hoofd_scherm(Model m) {
		super("Dashboard");
		parent=this;
		model = m;
		state="Keuzelijst";
		
		m.subscribe(this);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAccountManagement = new JMenu("Opties");
		menuBar.add(mnAccountManagement);
		
		JMenuItem mntmBeheerdersBeheren = new JMenuItem("Account beheer");
		if(model.getActiveType().equals("Beheerder"))
		{
			mntmBeheerdersBeheren.setEnabled(false);
		}
		mntmBeheerdersBeheren.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
						parent.dispose();
						Accountbeheer frame = new Accountbeheer(new Model(model.getUser()));
						frame.setVisible(true);
						Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
					    
					    // bereken het midden van je scherm
					    int w = frame.getSize().width;
					    int h = frame.getSize().height;
					    int x = (size.width-w)/2;
					    int y = (size.height-h)/2;
					    
					    // verplaats de GUI
					    frame.setLocation(x, y);
			}
						
		});
		mnAccountManagement.add(mntmBeheerdersBeheren);
		
		JMenuItem mntmNieuw = new JMenuItem("Nieuw item maken");
		mntmNieuw.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
			
		});
		mnAccountManagement.add(mntmNieuw);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		separator.setBackground(Color.WHITE);
		menuBar.add(separator);
		
		JLabel lblUBentAangemeld = new JLabel("U bent aangemeld als:  ");
		lblUBentAangemeld.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(lblUBentAangemeld);
		
		JMenu mnGebruiker = new JMenu(model.getUser());
		menuBar.add(mnGebruiker);
		
		JMenuItem mntmUitloggen = new JMenuItem("Uitloggen");
		mntmUitloggen.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.dispose();
				Chimalo frame = new Chimalo(model.getUser());
				frame.setVisible(true);
				Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
			    
			    // bereken het midden van je scherm
			    int w = frame.getSize().width;
			    int h = frame.getSize().height;
			    int x = (size.width-w)/2;
			    int y = (size.height-h)/2;
			    
			    // verplaats de GUI
			    frame.setLocation(x, y);  
			}


			
		});
		mnGebruiker.add(mntmUitloggen);
		
		
		contentPane = new ImagePanel("background.jpg");
		contentPane.setPreferredSize(new Dimension(1000,539));
		contentPane.setBackground(UIManager.getColor("Button.disabledShadow"));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		activeTitel = new JTextField();
		activeTitel.setEditable(false);
		activeTitel.setBounds(405, 163, 188, 20);
		contentPane.add(activeTitel);
		activeTitel.setColumns(10);
		
		JLabel lblTitel = new JLabel("Titel");
		lblTitel.setBounds(330, 166, 71, 14);
		contentPane.add(lblTitel);
		
		JLabel lblAuteur = new JLabel("Auteur");
		lblAuteur.setBounds(330, 191, 71, 14);
		contentPane.add(lblAuteur);
		
		activeBeschrijving = new JTextArea();
		activeBeschrijving.setWrapStyleWord(true);
		activeBeschrijving.setLineWrap(true);
		activeBeschrijving.setEditable(false);
		activeBeschrijving.setBackground(Color.WHITE);
		activeBeschrijving.setBounds(405, 241, 188, 237);
		activeBeschrijving.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		contentPane.add(activeBeschrijving);
		
		
		
		
		
		//items = model.getItems();
		panel_1 = new JPanel();
		
		panel_1.setSize(279, model.getItems().size()*120);
		/*panel_1.setLayout(new GridBagLayout());
		GridBagConstraints c= new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;*/
		//contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(100, 0));
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(621, 152, 320, 373);
		scrollPane.setViewportView(panel_1);
		scrollPane.getVerticalScrollBar().setUnitIncrement(150);
		scrollPane.getVerticalScrollBar().setBlockIncrement(150);
		contentPane.add(scrollPane);
		
		
		activeAuteur = new JTextField();
		activeAuteur.setEditable(false);
		activeAuteur.setBounds(405, 188, 188, 20);
		contentPane.add(activeAuteur);
		activeAuteur.setColumns(10);
		
		JLabel lblDatum = new JLabel("Datum");
		lblDatum.setBounds(330, 216, 71, 14);
		contentPane.add(lblDatum);
		
		activeDatum = new JTextField();
		activeDatum.setForeground(Color.BLACK);
		activeDatum.setEditable(false);
		activeDatum.setBounds(405, 213, 188, 20);
		contentPane.add(activeDatum);
		activeDatum.setColumns(10);
		
		JLabel lblBeschrijving = new JLabel("Beschrijving");
		lblBeschrijving.setBounds(330, 241, 71, 14);
		contentPane.add(lblBeschrijving);
		

		ImagePanel panel = new ImagePanel("logochimalo.jpg");
		panel.setBounds(0, 0, 1000, 92);
		contentPane.add(panel);
		
		 btnWijzigen = new JButton("Wijzigen");
		btnWijzigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activeTitel.setEditable(true);
				activeBeschrijving.setEditable(true);
				saveWijziging.setVisible(true);
			}
		});
		btnWijzigen.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnWijzigen.setBounds(67, 489, 123, 23);
		contentPane.add(btnWijzigen);
		
		btnAfkeuren = new JButton("Afkeuren");
		btnAfkeuren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.wijzigStatus("Afgekeurd");
				model.leesOpStatus(state);
				refreshItems();
				clearActive();
				refreshForState();
				refreshTeller();
				activeTitel.setEditable(false);
				activeBeschrijving.setEditable(false);
				
			}
		});
		btnAfkeuren.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnAfkeuren.setBounds(330, 489, 134, 23);
		contentPane.add(btnAfkeuren);
		
		btnGoedkeuren = new JButton("Goedkeuren");
		btnGoedkeuren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//model.getActiveItem().setAuteur(activeAuteur.getText());
				model.getActiveItem().setTitel(activeTitel.getText());
				model.getActiveItem().setText(activeBeschrijving.getText());
				model.wijzigStatus("Goedgekeurd");
				model.leesOpStatus(state);
				refreshItems();
				clearActive();
				refreshForState();
				refreshTeller();
				activeTitel.setEditable(false);
				activeBeschrijving.setEditable(false);
			}
		});
		
		activePhoto = new ImagePanel();
		activePhoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		activePhoto.setBounds(67, 163, 253, 315);
		contentPane.add(activePhoto);
		
		JLabel lblBewerken = new JLabel("Bewerken");
		lblBewerken.setBounds(67, 132, 87, 14);
		contentPane.add(lblBewerken);
		
		rdbtnKeurlijst = new JRadioButton("Keurlijst");
		rdbtnKeurlijst.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnKeurlijst.setOpaque(true);
		rdbtnKeurlijst.setBounds(619, 94, 79, 23);
		rdbtnKeurlijst.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rdbtnKeurlijst.isSelected()){
					model.leesOpStatus("Keurlijst");
					scrollPane.setViewportView(panel_1);
					clearActive();
					state="Keurlijst";
					refreshForState();
					activeTitel.setEditable(false);
					activeBeschrijving.setEditable(false);
					repaint();
				}				
			}
		});
		contentPane.add(rdbtnKeurlijst);
		
		 rdbtnGoedgekeurd = new JRadioButton("Goedgekeurde items");
		rdbtnGoedgekeurd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnGoedgekeurd.setBounds(698, 93, 134, 24);
		rdbtnGoedgekeurd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (rdbtnGoedgekeurd.isSelected()){
					model.leesOpStatus("Goedgekeurd");
					scrollPane.setViewportView(panel_1);
					clearActive();
					state="Goedgekeurd";
					refreshForState();
					activeTitel.setEditable(false);
					activeBeschrijving.setEditable(false);
					repaint();
				}				
			}
			
		});
		contentPane.add(rdbtnGoedgekeurd);
		
		rdbtnAfgekeurde = new JRadioButton("Afgekeurde Items");
		rdbtnAfgekeurde.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnAfgekeurde.setBounds(830, 94, 170, 23);
		rdbtnAfgekeurde.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnAfgekeurde.isSelected()){
					model.leesOpStatus("Afgekeurd");
					scrollPane.setViewportView(panel_1);
					clearActive();
					state="Afgekeurd";
					refreshForState();
					activeTitel.setEditable(false);
					activeBeschrijving.setEditable(false);
					repaint();
				}
				
			}
			
		});
		contentPane.add(rdbtnAfgekeurde);
		

		
		ButtonGroup groep = new ButtonGroup();
		groep.add(rdbtnKeurlijst);
		groep.add(rdbtnGoedgekeurd);
		groep.add(rdbtnAfgekeurde);
		
		
		
		JLabel lblZoeken = new JLabel("Zoeken:");
		lblZoeken.setBounds(631, 132, 45, 14);
		contentPane.add(lblZoeken);
		
		zoekVeld = new JTextField();
		zoekVeld.setBounds(686, 129, 157, 20);
		contentPane.add(zoekVeld);
		zoekVeld.setColumns(10);
		
		zoekBox = new JComboBox();
		zoekBox.setModel(new DefaultComboBoxModel(new String[] {"Titel", "Auteur", "Datum"}));
		zoekBox.setSelectedIndex(0);
		zoekBox.setBounds(853, 129, 79, 20);
		contentPane.add(zoekBox);
		

		btnGoedkeuren.setBounds(200, 489, 120, 23);
		contentPane.add(btnGoedkeuren);
		
		lblTeller = new JLabel("Keurlijst: 0  Goedgekeurde items: 0   Afgekeurde Items: 0");
		lblTeller.setVerticalAlignment(SwingConstants.TOP);
		lblTeller.setBounds(10, 97, 583, 24);
		contentPane.add(lblTeller);
		
		saveWijziging = new JButton("Bewaren");
		saveWijziging.setVisible(false);
		saveWijziging.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.getActiveItem().setText(activeBeschrijving.getText());
				model.getActiveItem().setTitel(activeTitel.getText());
				model.wijzigStatus("Keurlijst");
				
					model.leesOpStatus(state);
					refreshItems();
					clearActive();
					refreshForState();
					refreshTeller();
				saveWijziging.setVisible(false);
			}
		});
		saveWijziging.setBounds(475, 489, 118, 23);
		contentPane.add(saveWijziging);
		zoekVeld.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshItems();
				
			}
		});
		refreshForState();
		refreshTeller();
		repaint();
		pack();
	}
	private void refreshItems(){
			panel_1.removeAll();
		for (Item i : model.getItems()){
			ip =new ItemPanel(i);
			final String titel= i.getTitel();
			final String tekst = i.getText();
			final String auteur = i.getAuteur();
			final String datum = String.valueOf(i.getInzendDatum());
			ip.addMouseListener(new MouseListener(){
			
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					activeTitel.setText(titel);
					activeAuteur.setText(auteur);
					activeDatum.setText(datum);
					activeBeschrijving.setText(tekst);
					activeTitel.setEditable(false);
					activeBeschrijving.setEditable(false);
					model.setActiveItem(activeAuteur.getText(),activeBeschrijving.getText(),activeDatum.getText(),activeTitel.getText());
					activePhoto.setNewFoto(model.getActiveItem().getFoto());
					refreshForState();
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					return;
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					return;
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					return;					
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					return;					
				}
				
			});
			
			if (!zoekVeld.getText().isEmpty()){
				if (String.valueOf(zoekBox.getSelectedItem())=="Titel"){
				
					if (i.getTitel().toLowerCase().contains(zoekVeld.getText().toLowerCase()))
						panel_1.add(ip);
				}
				if (String.valueOf(zoekBox.getSelectedItem())=="Auteur"){

					if (i.getAuteur().toLowerCase().contains(zoekVeld.getText().toLowerCase()))
						panel_1.add(ip);
				}
				if (String.valueOf(zoekBox.getSelectedItem())=="Datum"){

					if (String.valueOf(i.getInzendDatum()).toLowerCase().contains(zoekVeld.getText().toLowerCase()))
						panel_1.add(ip);
					
				}
			}else
			panel_1.add(ip);
		}

		panel_1.repaint();
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		refreshItems();
		if(model.getItems().size() == 0)
			panel_1.setLayout(new GridLayout(1, 0));
		else
			panel_1.setLayout(new GridLayout(model.getItems().size(), 0));
		refreshForState();
		repaint();
	}
	private void clearActive(){
		activeAuteur.setText("");
		activeBeschrijving.setText("");
		activeDatum.setText("");
		activeTitel.setText("");
		activePhoto.setNewFoto(null);
		model.clearActive();
		
	}
	
	public void refreshForState(){
		if (model.getActiveItem() == null){
			btnGoedkeuren.setEnabled(false);
			btnAfkeuren.setEnabled(false);
			btnWijzigen.setEnabled(false);
			saveWijziging.setVisible(false);
			}
		else{
		if (state == "Goedgekeurd"){
			btnGoedkeuren.setEnabled(false);
			btnAfkeuren.setEnabled(true);
			btnWijzigen.setEnabled(true);
			saveWijziging.setVisible(false);
		}
		if (state=="Afgekeurd"){
			btnGoedkeuren.setEnabled(true);
			btnAfkeuren.setEnabled(false);
			btnWijzigen.setEnabled(true);
			saveWijziging.setVisible(false);
		}
		if (state =="Keurlijst"){
			btnGoedkeuren.setEnabled(true);
			btnAfkeuren.setEnabled(true);
			btnWijzigen.setEnabled(true);
			saveWijziging.setVisible(false);
		}
		}
	
	}
	public void refreshTeller(){
		int goed=0;
		int slecht=0;
		int keur=0;
		for (Item i : model.alleItems()){
			if (i.getStatus().equals("Keurlijst"))
				keur++;
			if (i.getStatus().equals("Goedgekeurd"))
				goed++;
			if (i.getStatus().equals("Afgekeurd"))
				slecht++;
		}
		lblTeller.setText("Keurlijst : "+keur+" Goedgekeurd : "+goed+" Afgekeurd : "+slecht);
	}
}
