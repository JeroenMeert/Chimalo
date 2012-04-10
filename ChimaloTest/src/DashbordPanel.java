import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JTextPane;


public class DashbordPanel extends ImagePanel implements ChangeListener{

	/**
	 * Create the panel.
	 */
	private JTextField activeTitel;
	private JTextField zoekVeld;
	private JTextPane activeBeschrijving;
	//private ArrayList<Item> items;
	private JPanel panel_1;
	private Model model;
	private JRadioButton rdbtnAfgekeurde;
	private JScrollPane scrollPane;
	private hoofd_scherm parentPanel;
	private DashbordPanel parent;
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
	private JTextField activeLink;
	private JLabel lblErfgoed;
	private JComboBox<String> activeErfgoed;
	private JLabel lblLocatie;
	private JTextField activeLocatie;
	private JLabel lblBewerken;
	private JTextPane activeHistoriek;
	private JComboBox activeGemeente;
	
	/**
	 * @wbp.parser.constructor
	 */
	public DashbordPanel(Model m, Parameter para) {
		super("background1.jpg");
		parent=this;
		model = m;
		parentPanel=model.getHoofdframe();
		m.subscribe(this);
		
		this.setPreferredSize(new Dimension(1000, 500));
		this.setBackground(UIManager.getColor("Button.disabledShadow"));
		
		this.setLayout(null);
		
		activeTitel = new JTextField();
		activeTitel.setBounds(405, 76, 188, 20);
		this.add(activeTitel);
		activeTitel.setColumns(10);
		
		JLabel lblTitel = new JLabel("Titel");
		lblTitel.setBounds(330, 80, 71, 14);
		this.add(lblTitel);
		
		
		activeBeschrijving = new JTextPane();
		activeBeschrijving.setBackground(Color.WHITE);
		JScrollPane beschrijvingScroll = new JScrollPane(activeBeschrijving);
		beschrijvingScroll.setBounds(405, 335, 188, 100);
		this.add(beschrijvingScroll);
		
		
		
		
		
		//items = model.getItems();
		panel_1 = new JPanel();
		
		panel_1.setSize(279, model.getItems().size()*120);
		/*panel_1.setLayout(new GridBagLayout());
		GridBagConstraints c= new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;*/
		//this.add(panel_1);
		panel_1.setLayout(new GridLayout(100, 0));
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(622, 63, 316, 420);
		scrollPane.setViewportView(panel_1);
		scrollPane.getVerticalScrollBar().setUnitIncrement(150);
		scrollPane.getVerticalScrollBar().setBlockIncrement(150);
		this.add(scrollPane);
		
		JLabel lblBeschrijving = new JLabel("Beschrijving");
		lblBeschrijving.setBounds(330, 335, 71, 14);
		this.add(lblBeschrijving);
		
		btnWijzigen = new JButton("Wijzigen");
		btnWijzigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activePanel(true);
				saveWijziging.setVisible(true);
				btnGoedkeuren.setEnabled(false);
				btnAfkeuren.setEnabled(false);
				btnWijzigen.setVisible(false);
			}
		});
		btnWijzigen.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnWijzigen.setBounds(67, 441, 123, 23);
		this.add(btnWijzigen);
		
		btnAfkeuren = new JButton("Afkeuren");
		btnAfkeuren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.wijzigStatus("Afgekeurd");
				model.leesOpStatus(state);
				try {
					MailFrame frame = new MailFrame(model, false);
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
				refreshItems();
				clearActive();
				refreshForState();
				refreshTeller();
				
			}
		});
		btnAfkeuren.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnAfkeuren.setBounds(330, 441, 134, 23);
		this.add(btnAfkeuren);
		
		btnGoedkeuren = new JButton("Goedkeuren");
		btnGoedkeuren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.getActiveItem().setStatus("Goedgekeurd");
				model.overschrijfActive();
				model.leesOpStatus(state);
				try {
					MailFrame frame = new MailFrame(model, false);
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
				refreshItems();
				clearActive();
				refreshForState();
				refreshTeller();
			}
		});
		
		activePhoto = new ImagePanel();
		activePhoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		activePhoto.setBounds(67, 75, 253, 360);
		activePhoto.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(activeTitel.isEnabled())
				{
				if(model.getActiveItem()!=null){
					if(model.getActiveItem().getId() != -1)
					{
						EditFrame frame = new EditFrame(activePhoto.getFoto(),model);
						frame.setVisible(true);
					}
					else
					{
						BufferedImage image;
						try {
							image = ImageIO.read(getClass().getResource("NoFoto.png"));
							EditFrame frame = new EditFrame(image,model);
							frame.setVisible(true);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		this.add(activePhoto);
		
		lblBewerken = new JLabel("Geen item geselecteerd");
		lblBewerken.setBounds(67, 40, 379, 14);
		this.add(lblBewerken);
		
		rdbtnKeurlijst = new JRadioButton("Keurlijst");
		rdbtnKeurlijst.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnKeurlijst.setOpaque(true);
		rdbtnKeurlijst.setBounds(619, 1, 79, 25);
		rdbtnKeurlijst.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rdbtnKeurlijst.isSelected()){
					model.leesOpStatus("Keurlijst");
					//scrollPane.setViewportView(panel_1);
				}				
			}
		});
		this.add(rdbtnKeurlijst);
		
		 rdbtnGoedgekeurd = new JRadioButton("Goedgekeurde items");
		rdbtnGoedgekeurd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnGoedgekeurd.setBounds(694, 2, 128, 24);
		rdbtnGoedgekeurd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (rdbtnGoedgekeurd.isSelected()){
					model.leesOpStatus("Goedgekeurd");
					//scrollPane.setViewportView(panel_1);
				}				
			}
			
		});
		this.add(rdbtnGoedgekeurd);
		
		rdbtnAfgekeurde = new JRadioButton("Afgekeurde Items");
		rdbtnAfgekeurde.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnAfgekeurde.setBounds(819, 2, 181, 24);
		rdbtnAfgekeurde.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnAfgekeurde.isSelected()){
					model.leesOpStatus("Afgekeurd");
					//scrollPane.setViewportView(panel_1);
				}
				
			}
			
		});
		this.add(rdbtnAfgekeurde);
		

		
		ButtonGroup groep = new ButtonGroup();
		groep.add(rdbtnKeurlijst);
		groep.add(rdbtnGoedgekeurd);
		groep.add(rdbtnAfgekeurde);
		
		
		
		JLabel lblZoeken = new JLabel("Zoeken:");
		lblZoeken.setBounds(629, 40, 45, 14);
		this.add(lblZoeken);
		
		zoekVeld = new JTextField();
		zoekVeld.setBounds(678, 37, 161, 20);
		this.add(zoekVeld);
		zoekVeld.setColumns(10);
		
		zoekBox = new JComboBox();
		zoekBox.setModel(new DefaultComboBoxModel(new String[] {"Titel", "Auteur", "Datum","Erfgoed"}));
		zoekBox.setSelectedIndex(0);
		zoekBox.setBounds(849, 37, 79, 20);
		this.add(zoekBox);
		

		btnGoedkeuren.setBounds(200, 441, 120, 23);
		this.add(btnGoedkeuren);
		
		lblTeller = new JLabel("Keurlijst: 0  Goedgekeurde items: 0   Afgekeurde Items: 0");
		lblTeller.setVerticalAlignment(SwingConstants.TOP);
		lblTeller.setBounds(10, 5, 583, 15);
		this.add(lblTeller);
		
		saveWijziging = new JButton("Bewaren");
		saveWijziging.setVisible(false);
		saveWijziging.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!((activeTitel.getText().equals("")) || (activeBeschrijving.getText().equals("")) || (activeGemeente.getSelectedItem().equals(null)) || (activeErfgoed.getSelectedItem().equals(null)) || (activeLocatie.getText().equals("")) || (!activePhoto.isUsed())))
						{
						model.getActiveItem().setText(activeBeschrijving.getText());
						model.getActiveItem().setTitel(activeTitel.getText());
						model.getActiveItem().setHistoriek(activeHistoriek.getText());
						model.getActiveItem().setLocatie(activeLocatie.getText());
						model.getActiveItem().setLink(activeLink.getText());
						model.getActiveItem().setGemeente(activeGemeente.getSelectedItem().toString());
						model.getActiveItem().setErfgoed(activeErfgoed.getSelectedItem().toString());
						model.getActiveItem().setStatus("Goedgekeurd");
						if(!(model.getActiveItem().getId() == -1))
						{
							try {
							model.overschrijfActive();
							}
							finally {
								MailFrame frame = new MailFrame(model, true);
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
						}
						else
						{
						model.getActiveItem().setAuteur(model.getUser());
						Calendar cal = new GregorianCalendar() ;
						java.sql.Date date = new java.sql.Date( cal.getTime().getTime());
						model.getActiveItem().setInzendDatum(date);
						model.schrijfNieuwItem(model.getActiveItem());
						}
						activePanel(false);
						model.leesOpStatus(state);
						refreshForState();
						refreshTeller();
						refreshItems();
						btnWijzigen.setVisible(true);
						saveWijziging.setVisible(false);
						clearActive();
						}
				else {
					JOptionPane.showMessageDialog(model.getHoofdframe(), "Gelieve alle velden in te vullen en een foto te selecteren");	
					}
			}
		});
		saveWijziging.setBounds(475, 441, 118, 23);
		this.add(saveWijziging);
		
		JLabel lblNaarErfgoed = new JLabel("Naar Erfgoed >>");
		lblNaarErfgoed.setBounds(495, 40, 112, 14);
		lblNaarErfgoed.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if (model.getActiveItem()!=null){
					model.unsubscribe(parent);
					int i=0;
					while (  i<model.getErfgoeden().size()){
						if (model.getErfgoeden().get(i).getNaam().equals(model.getActiveItem().getErfgoed())){
							break;
						}
						i++;
					}
					parentPanel.newPanel(new ErfgoedPanel(model,parentPanel,model.getErfgoeden().get(i)));
					}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		add(lblNaarErfgoed);
		
		activeLink = new JTextField();
		activeLink.setBounds(405, 107, 188, 20);
		add(activeLink);
		activeLink.setColumns(10);
		
		JLabel lblLink = new JLabel("Link");
		lblLink.setBounds(330, 109, 46, 14);
		add(lblLink);
		
		lblErfgoed = new JLabel("Erfgoed");
		lblErfgoed.setBounds(330, 140, 46, 14);
		add(lblErfgoed);
		
		activeErfgoed = new JComboBox<String>();
		ArrayList<Erfgoed> erfgoeden = model.getErfgoeden();
		activeErfgoed.removeAllItems();
		for(Erfgoed e : erfgoeden)
		{
			activeErfgoed.addItem(e.getNaam());
		}
		activeErfgoed.setBounds(405, 138, 188, 20);
		add(activeErfgoed);
		
		lblLocatie = new JLabel("Locatie");
		lblLocatie.setBounds(330, 169, 46, 14);
		add(lblLocatie);
		
		activeLocatie = new JTextField();
		activeLocatie.setBounds(405, 166, 188, 20);
		add(activeLocatie);
		activeLocatie.setColumns(10);
		
		JLabel lblGemeente = new JLabel("Gemeente");
		lblGemeente.setBounds(330, 197, 71, 14);
		add(lblGemeente);
		
		JLabel lblHistoriek = new JLabel("Historiek");
		lblHistoriek.setBounds(330, 222, 71, 14);
		add(lblHistoriek);
		
		activeGemeente = new JComboBox(model.getGemeenten().toArray());
		activeGemeente.setBounds(405, 194, 188, 20);
		add(activeGemeente);
		
		activeHistoriek = new JTextPane();
		JScrollPane historiekScroll = new JScrollPane(activeHistoriek);
		historiekScroll.setBounds(405, 222, 188, 102);
		add(historiekScroll);
		zoekVeld.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshItems();
				
			}
		});
		model.nieuwItem();
		activePanel(false);
		refreshForState();
		refreshTeller();
		repaint();
		btnWijzigen.setEnabled(false);
		processParameter(para);
	}
	
	private void refreshItems(){
			panel_1.setVisible(false);
			panel_1.removeAll();
		for (final Item i : model.getItems()){
			ip =new ItemPanel(i);
			final String titel= i.getTitel();
			final String tekst = i.getText();
			final String auteur = i.getAuteur();
			final String datum = String.valueOf(i.getInzendDatum());
			final String erfgoed = i.getErfgoed();
			final String historiek = i.getHistoriek();
			final String link = i.getLink();
			final String gemeente = i.getGemeente();
			final String locatie = i.getLocatie();
			ip.addMouseListener(new MouseListener(){
			
				@Override
				public void mouseClicked(MouseEvent arg0) {
					activeTitel.setText(titel);
					activeBeschrijving.setText(tekst);
					activeHistoriek.setText(historiek);
					activeLink.setText(link);
					activeLocatie.setText(locatie);
					activeGemeente.setSelectedItem(gemeente);
					lblBewerken.setText(titel.toUpperCase() + " door " + auteur + " op " + datum);
					activeErfgoed.setSelectedItem(erfgoed);
					model.setActiveItem(i);
					activePhoto.setNewFoto(model.getActiveItem().getFoto());
					activePanel(false);
					btnWijzigen.setVisible(true);
					state= i.getStatus();
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
				if (String.valueOf(zoekBox.getSelectedItem())=="Erfgoed"){

					if (String.valueOf(i.getErfgoed()).toLowerCase().contains(zoekVeld.getText().toLowerCase()))
						panel_1.add(ip);
					
				}
			}else
			panel_1.add(ip);
		}
		panel_1.repaint();
		panel_1.setVisible(true);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		try {
		refreshItems();
		}
		finally {
			if(model.getItems().size() == 0)
				panel_1.setLayout(new GridLayout(1, 0));
			else
				panel_1.setLayout(new GridLayout(model.getItems().size(), 0));
		}
		ArrayList<Erfgoed> erfgoeden = null;
		try {
		erfgoeden = model.getErfgoeden();
		}
		finally {
		activeErfgoed.removeAllItems();
		for(Erfgoed e : erfgoeden)
		{
			activeErfgoed.addItem(e.getNaam());
		}
		}
		if(model.getActiveItem() != null)
		{
			activePhoto.setNewFoto(model.getActiveItem().getFoto());
		}
			
		refreshForState();
		repaint();
	}
	private void clearActive(){
		model.nieuwItem();
		lblBewerken.setText("");
		activeBeschrijving.setText("");
		activeTitel.setText("");
		activeLocatie.setText("");
		activeGemeente.setSelectedIndex(0);
		activeErfgoed.setSelectedIndex(0);
		activeLink.setText("");
		activeHistoriek.setText("");
		activePhoto.setNewFoto(null);
	}
	
	public void refreshForState(){
		if (model.getActiveItem().getId() == -1){
			btnGoedkeuren.setEnabled(false);
			btnAfkeuren.setEnabled(false);
			saveWijziging.setVisible(false);
			activePanel(false);
			if(model.getActiveItem().getFoto() != null)
			{
				activePanel(true);
				btnWijzigen.setVisible(false);
				saveWijziging.setVisible(true);
			}
			}
		else{
		if (state.equals("Goedgekeurd")){
			btnGoedkeuren.setEnabled(false);
			btnAfkeuren.setEnabled(true);
			btnWijzigen.setEnabled(true);
			btnWijzigen.setVisible(true);
			saveWijziging.setVisible(false);
			activePanel(false);
		}
		if (state.equals("Afgekeurd")){
			btnGoedkeuren.setEnabled(true);
			btnAfkeuren.setEnabled(false);
			btnWijzigen.setEnabled(true);
			btnWijzigen.setVisible(true);
			saveWijziging.setVisible(false);
			activePanel(false);
		}
		if (state.equals("Keurlijst")){
			btnGoedkeuren.setEnabled(true);
			btnAfkeuren.setEnabled(true);
			btnWijzigen.setEnabled(true);
			btnWijzigen.setVisible(true);
			saveWijziging.setVisible(false);
			activePanel(false);
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
	
	private void processParameter(Parameter para)
	{
		if(para.getSoort() != null)
		{
			if(para.getSoort().equals("nieuw"))
			{
				lblBewerken.setText("Nieuw Item");
				btnWijzigen.setEnabled(true);
				clearActive();
			}
		}
	}
	
	public void activePanel(boolean b)
	{
		activeBeschrijving.setEnabled(b);
		activeErfgoed.setEnabled(b);
		activeGemeente.setEnabled(b);
		activeHistoriek.setEnabled(b);
		activeLink.setEnabled(b);
		activeLocatie.setEnabled(b);
		activeTitel.setEnabled(b);
	}
	}


