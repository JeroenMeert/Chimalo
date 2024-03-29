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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;


public class DashbordPanel extends ImagePanel implements ChangeListener{

	/**
	 * Create the panel.
	 */
	private JTextField activeTitel;
	private JTextField zoekVeld;
	//private ArrayList<Item> items;
	private JPanel panel_1;
	private Model model;
	private JRadioButton rdbtnAfgekeurde;
	private JTextPane activeBeschrijving;
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
	private JComboBox<Erfgoed> activeErfgoed;
	private JLabel lblBewerken;
	private MemberPanel panel;
	private JLabel lblNaarErfgoed;
	private int itemCount = 0;
	private JComboBox typebox;
	private JButton verwijder;
	private String activeList = "Keurlijst";
	private int index=0;
	private JRadioButton rdbtnErfgoeden;
	/**
	 * @wbp.parser.constructor
	 */
	public DashbordPanel(Model m, Parameter para) {
		super("background1.jpg");
		try {
            // Set cross-platform Java L&F (also called "Metal")
        UIManager.setLookAndFeel(
            UIManager.getCrossPlatformLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {
       // handle exception
		}
		catch (ClassNotFoundException e) {
       // handle exception
		}
		catch (InstantiationException e) {
       // handle exception
		}
		catch (IllegalAccessException e) {
       // handle exception
		}
		parent=this;
		model = m;
		model.setActivePanel(this);
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
		lblTitel.setBounds(317, 79, 71, 14);
		this.add(lblTitel);
		activeBeschrijving = new JTextPane();
		JScrollPane beschrijvingScroll = new JScrollPane(activeBeschrijving);
		beschrijvingScroll.setBounds(317, 202, 276, 228);
		this.add(beschrijvingScroll);
		
		String[] type = {"Foto", "Postkaart", "Geluid", "Video", "Brochure", "Prenten/Tekeningen", "Andere" };
		typebox = new JComboBox(type);
		typebox.setBounds(405, 152, 188, 20);
		add(typebox);
		
		
		
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
		
		JLabel lblBeschrijving = new JLabel("Beschrijving/Tekst");
		lblBeschrijving.setBounds(317, 185, 129, 14);
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
				if(model.wijzigStatus("Afgekeurd")) {
				for(Item i : model.getItems())
				{
					if(i.getId() == model.getActiveItem().getId())
					{
						i.setStatus("Afgekeurd");
					}
				}
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
				model.notifyChangeListeners();
				}
			}
		});
		btnAfkeuren.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnAfkeuren.setBounds(330, 441, 134, 23);
		this.add(btnAfkeuren);
		
		btnGoedkeuren = new JButton("Goedkeuren");
		btnGoedkeuren.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(model.wijzigStatus("Goedgekeurd"))
				{
				for(Item i : model.getItems())
				{
					if(i.getId() == model.getActiveItem().getId())
					{
						i.setStatus("Goedgekeurd");
					}
				}
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
				verwijder.setVisible(false);
				model.notifyChangeListeners();
				}
			}
		});
		
		activePhoto = new ImagePanel("NoFoto.jpg");
		activePhoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		activePhoto.setBounds(67, 75, 240, 240);
		activePhoto.addMouseListener(new MouseListener(){
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!model.getVensterOpen()) { //hierdoor kan je maar 1 image venster opendoen
				if(activeTitel.isEnabled()) // een alternatief om te kijken of je op wijzigen hebt geklikt
				{
				if(model.getActiveItem()!=null){
					if((model.getActiveItem().getId() != -1) && (model.getActiveItem().getFoto() != null) )
					{
						EditFrame frame = new EditFrame(activePhoto.getFoto(),model, null);
						frame.setVisible(true);
					}
					else
					{
						BufferedImage image;
						try {
							image = ImageIO.read(getClass().getResource("NoFoto.jpg"));
							EditFrame frame = new EditFrame(image,model, null);
							frame.setVisible(true);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					model.setVensterOpen(true);
					index=activeErfgoed.getSelectedIndex();
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
		
		zoekBox = new JComboBox();
		zoekBox.setModel(new DefaultComboBoxModel(new String[] {"Titel", "Auteur","Type", "Datum","Erfgoed"}));
		zoekBox.setSelectedIndex(0);
		zoekBox.setBounds(849, 37, 79, 20);
		this.add(zoekBox);
		
		lblBewerken = new JLabel("");
		lblBewerken.setBounds(67, 40, 379, 14);
		this.add(lblBewerken);
		
		rdbtnKeurlijst = new JRadioButton("Keurlijst");
		rdbtnKeurlijst.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnKeurlijst.setOpaque(true);
		rdbtnKeurlijst.setBounds(619, 1, 63, 25);
		rdbtnKeurlijst.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (rdbtnKeurlijst.isSelected()){
					if(activeList.equals("erfgoeden")) {
						zoekVeld.setText("");
					}
					activeList = "Keurlijst";
					zoekBox.setModel(new DefaultComboBoxModel(new String[] {"Titel", "Auteur","Type", "Datum","Erfgoed"}));
					model.notifyChangeListeners();
					//scrollPane.setViewportView(panel_1);
				}				
			}
		});
		this.add(rdbtnKeurlijst);
		
		 rdbtnGoedgekeurd = new JRadioButton("Goedgekeurd");
		rdbtnGoedgekeurd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnGoedgekeurd.setBounds(678, 1, 95, 24);
		rdbtnGoedgekeurd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (rdbtnGoedgekeurd.isSelected()){
					if(activeList.equals("erfgoeden")) {
						zoekVeld.setText("");
					}
					activeList = "Goedgekeurd";
					zoekBox.setModel(new DefaultComboBoxModel(new String[] {"Titel", "Auteur","Type", "Datum","Erfgoed"}));
					model.notifyChangeListeners();
					//scrollPane.setViewportView(panel_1);
				}				
			}
			
		});
		this.add(rdbtnGoedgekeurd);
		
		rdbtnAfgekeurde = new JRadioButton("Afgekeurd");
		rdbtnAfgekeurde.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnAfgekeurde.setBounds(770, 1, 93, 24);
		rdbtnAfgekeurde.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (rdbtnAfgekeurde.isSelected()){
					if(activeList.equals("erfgoeden")) {
						zoekVeld.setText("");
					}
					activeList = "Afgekeurd";
					zoekBox.setModel(new DefaultComboBoxModel(new String[] {"Titel", "Auteur","Type", "Datum","Erfgoed"}));
					model.notifyChangeListeners();
					//scrollPane.setViewportView(panel_1);
				}
				
			}
			
		});
		this.add(rdbtnAfgekeurde);
		
		
		JLabel lblZoeken = new JLabel("Zoeken:");
		lblZoeken.setBounds(629, 40, 45, 14);
		this.add(lblZoeken);
		
		zoekVeld = new JTextField();
		zoekVeld.setBounds(678, 37, 161, 20);
		this.add(zoekVeld);
		zoekVeld.setColumns(10);
		
		
		

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
				
				if(!((activeTitel.getText().equals("")) || (activeBeschrijving.getText().equals("")) || (activeErfgoed.getSelectedItem().equals(null))))
						{
						if(!activePhoto.isUsed())
						{
							model.getActiveItem().setFoto(null);
						}
						model.getActiveItem().setText(activeBeschrijving.getText());
						model.getActiveItem().setTitel(activeTitel.getText());
						model.getActiveItem().setLink(activeLink.getText());
						model.getActiveItem().setErfgoed((Erfgoed) activeErfgoed.getSelectedItem());
						model.getActiveItem().setStatus("Goedgekeurd");
						model.getActiveItem().setType(typebox.getSelectedItem().toString());
						if(!(model.getActiveItem().getId() == -1))
						{
							try {
								if(!activePhoto.isUsed())
								{
									if(!model.overschrijfItemZonderAfbeelding(model.getActiveItem()))
										return;
								}
								else {
									if(!model.overschrijfActive())
										return;
								}
								for(Item i :model.getItems()) {
									if(model.getActiveItem().getId() == i.getId())
									{
										model.getItems().remove(i);
										model.getItems().add(new Item(model.getActiveItem().getFoto(), model.getActiveItem().getId(), model.getActiveItem().getTitel(), model.getActiveItem().getText(), model.getActiveItem().getAuteur(), model.getActiveItem().getInzendDatum(), model.getActiveItem().getErfgoed(),model.getActiveItem().getLink(), model.getActiveItem().getStatus(), model.getActiveItem().getExtentie(), model.getActiveItem().getType()));
										break;
									}
								}
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
						model.getActiveItem().setAuteur(model.getAdmin());
						Calendar cal = new GregorianCalendar().getInstance() ;
						Date datum = cal.getTime();
						java.sql.Date date = new java.sql.Date( datum.getTime());
						model.getActiveItem().setInzendDatum(date);
						if(!activePhoto.isUsed()) {
							if(!model.schrijfNieuwItemZonderAfbeelding(model.getActiveItem()))
								return;
						}
						else {
						if(!model.schrijfNieuwItem(model.getActiveItem()))
							return;
						
						}
						model.setActiveItem(model.aanvullenItem(model.getActiveItem()));
						model.getItems().add(new Item(model.getActiveItem().getFoto(), model.getActiveItem().getId(), model.getActiveItem().getTitel(), model.getActiveItem().getText(), model.getActiveItem().getAuteur(), model.getActiveItem().getInzendDatum(), model.getActiveItem().getErfgoed(),model.getActiveItem().getLink(), model.getActiveItem().getStatus(), model.getActiveItem().getExtentie(), model.getActiveItem().getType()));
						}
						activePanel(false);
						
						
						clearActive();
						refreshForState();
						refreshTeller();
						refreshItems();
						btnWijzigen.setVisible(false);
						saveWijziging.setVisible(false);
						
						}
				else {
					JOptionPane.showMessageDialog(model.getHoofdframe(), "Gelieve minstens titel en beschrijving in te vullen.");	
					}
			}
		});
		saveWijziging.setBounds(475, 441, 118, 23);
		verwijder = new JButton("Verwijder");
		verwijder.setVisible(false);
		this.add(saveWijziging);
		
		verwijder.setBounds(475, 441, 118, 23);
		this.add(verwijder);
		verwijder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int option = JOptionPane.showConfirmDialog(null, "Ben u zeker dat u " + model.getActiveItem().getTitel() + " wilt verwijderen?");
				if(option == JOptionPane.OK_OPTION)
				{
				boolean gelukt = model.verwijderItem();
				if(gelukt) {
					for(Item i : model.getItems())
					{
						if(i.getId() == model.getActiveItem().getId())
						{
							model.getItems().remove(i);
							break;
						}
					}
					model.notifyChangeListeners();
					clearActive();
				}
				}
			}
		});
		lblNaarErfgoed = new JLabel("Naar Erfgoed >>");
		lblNaarErfgoed.setBounds(495, 39, 112, 14);
		lblNaarErfgoed.setVisible(false);
		lblNaarErfgoed.addMouseListener(new MouseListener(){
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (model.getActiveItem().getId()!=-1){
					model.unsubscribe(parent);
					parentPanel.newPanel(new ErfgoedPanel(model,new Parameter(model.getActiveItem().getErfgoed())));
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
		
		activeLink = new JTextField("http://");
		activeLink.setBounds(405, 127, 188, 20);
		add(activeLink);
		activeLink.setColumns(10);
		
		JLabel lblLink = new JLabel("Youtube link");
		lblLink.setBounds(317, 130, 89, 14);
		add(lblLink);
		
		lblErfgoed = new JLabel("Erfgoed");
		lblErfgoed.setBounds(317, 105, 46, 14);
		add(lblErfgoed);
		
		activeErfgoed = new JComboBox<Erfgoed>();
		ArrayList<Erfgoed> erfgoeden = model.getErfgoeden();
		activeErfgoed.removeAllItems();
		for(Erfgoed e : erfgoeden)
		{
			activeErfgoed.addItem(e);
		}
		activeErfgoed.setBounds(405, 102, 188, 20);
		add(activeErfgoed);
		zoekVeld.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.notifyChangeListeners();
				
			}
		});
		model.nieuwItem();
		activePanel(false);
		model.notifyChangeListeners();
		rdbtnKeurlijst.setSelected(true);
		panel = new MemberPanel(model.getAdmin());
		panel.setBorder(new LineBorder(Color.GRAY));
		panel.setBounds(67, 320, 240, 110);
		add(panel);
		JLabel lblType = new JLabel("Type");
		lblType.setBounds(317, 155, 46, 14);
		add(lblType);
		
		rdbtnErfgoeden = new JRadioButton("Erfgoed");
		rdbtnErfgoeden.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnErfgoeden.setBounds(863, 2, 75, 23);
		rdbtnErfgoeden.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnErfgoeden.isSelected())
				{
				activeList = "Erfgoeden";
				zoekBox.setModel(new DefaultComboBoxModel(new String[] {"Naam", "Type","Gemeente"}));
				model.notifyChangeListeners();
				}
			}
		});
		
		add(rdbtnErfgoeden);
		
		ButtonGroup groep = new ButtonGroup();
		groep.add(rdbtnKeurlijst);
		groep.add(rdbtnGoedgekeurd);
		groep.add(rdbtnAfgekeurde);
		groep.add(rdbtnErfgoeden);
		processParameter(para);
	}
	
	private void refreshErfgoedlijst() {
		if(activeList.equals("Erfgoeden"))
		{
		itemCount = 0;
		panel_1.setVisible(false);
		panel_1.removeAll();
	for (final Erfgoed i : model.getErfgoeden()){
		
		ip =new ItemPanel(i, model.getItems());
		ip.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				model.unsubscribe(parent);
				model.getHoofdframe().newPanel(new ErfgoedPanel(model,new Parameter(i)));
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
		if (!zoekVeld.getText().isEmpty())
		{
			if (String.valueOf(zoekBox.getSelectedItem())=="Naam"){
			
				if (i.getNaam().toLowerCase().contains(zoekVeld.getText().toLowerCase()))
				{
					panel_1.add(ip);
					itemCount++;
				}
			}
			if (String.valueOf(zoekBox.getSelectedItem())=="Type"){

				if (i.getType().toLowerCase().contains(zoekVeld.getText().toLowerCase()))
				{
					panel_1.add(ip);
					itemCount++;
				}
			}
			if (String.valueOf(zoekBox.getSelectedItem())=="Gemeente"){

				if (i.getGemeente().toLowerCase().contains(zoekVeld.getText().toLowerCase()))
				{
					panel_1.add(ip);
					itemCount++;
				}
				
			}
		}else {
		panel_1.add(ip);
		itemCount++;
		}
	}
	}
	panel_1.repaint();
	panel_1.setVisible(true);
		
	}
	
	private void refreshItems(){
			itemCount = 0;
			panel_1.setVisible(false);
			panel_1.removeAll();
		for (final Item i : model.getItems()){
			if((i.getStatus().equals(activeList)) || (activeList.equals("Alles")))
			{
			ip =new ItemPanel(i);
			final String titel= i.getTitel();
			final String tekst = i.getText();
			final String auteur = i.getAuteur().getGebruikersnaam();
			DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
			StringBuilder nu = new StringBuilder( formatter.format( i.getInzendDatum() ) );
			final String datum = nu.toString();
			final Erfgoed erfgoed = i.getErfgoed();
			final String link = i.getLink();
			ip.addMouseListener(new MouseListener(){
			
				@Override
				public void mouseClicked(MouseEvent e) {
					activeTitel.setText(titel);
					activeBeschrijving.setText(tekst);
					activeLink.setText(link);
					typebox.setSelectedItem(i.getType());
					lblBewerken.setText(titel.toUpperCase() + " (" + datum + ")");
					for(int i = 0; i<activeErfgoed.getItemCount(); i++)
					{
						if(erfgoed.getErfgoedNr() == activeErfgoed.getItemAt(i).getErfgoedNr())
						{
							activeErfgoed.setSelectedIndex(i);
						}
					}
					model.setActiveItem(new Item(i.getFoto(), i.getId(), i.getTitel(), i.getText(), i.getAuteur(), i.getInzendDatum(), i.getErfgoed(), i.getLink(), i.getStatus(), i.getExtentie(), i.getType()));
					activePhoto.setNewFoto(i.getFoto());
					activePanel(false);
					btnWijzigen.setVisible(true);
					lblNaarErfgoed.setVisible(true);
					state= i.getStatus();
					panel.setGebruiker(i.getAuteur());
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
			
			if (!zoekVeld.getText().isEmpty())
			{
				if (String.valueOf(zoekBox.getSelectedItem())=="Titel"){
				
					if (i.getTitel().toLowerCase().contains(zoekVeld.getText().toLowerCase()))
					{
						panel_1.add(ip);
						itemCount++;
					}
				}
				if (String.valueOf(zoekBox.getSelectedItem())=="Auteur"){

					if (i.getAuteur().getGebruikersnaam().toLowerCase().contains(zoekVeld.getText().toLowerCase()))
					{
						panel_1.add(ip);
						itemCount++;
					}
				}
				if (String.valueOf(zoekBox.getSelectedItem())=="Datum"){

					if(nu.toString().toLowerCase().contains(zoekVeld.getText().toLowerCase()))
					{
						panel_1.add(ip);
						itemCount++;
					}
					
				}
				if (String.valueOf(zoekBox.getSelectedItem())=="Erfgoed"){

					if (String.valueOf(i.getErfgoed()).toLowerCase().contains(zoekVeld.getText().toLowerCase()))
					{
						panel_1.add(ip);
						itemCount++;
					}
					
				}
				if (String.valueOf(zoekBox.getSelectedItem())=="Type"){

					if (String.valueOf(i.getType()).toLowerCase().contains(zoekVeld.getText().toLowerCase()))
					{
						panel_1.add(ip);
						itemCount++;
					}
					
				}
			}else {
			panel_1.add(ip);
			itemCount++;
			}
		}
		}
		panel_1.repaint();
		panel_1.setVisible(true);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		try {
		refreshItems();
		refreshErfgoedlijst();
		}
		finally {
			if(itemCount == 0)
				panel_1.setLayout(new GridLayout(1, 0));
			else
				panel_1.setLayout(new GridLayout(itemCount, 0));
		}
		ArrayList<Erfgoed> erfgoeden = null;
		try {
		erfgoeden = model.getErfgoeden();
		}
		finally {
		activeErfgoed.removeAllItems();
		for(Erfgoed e : erfgoeden)
		{
			activeErfgoed.addItem(e);
		}
		}
		state = model.getActiveItem().getStatus();
		refreshForState();
		if(model.getActiveItem() != null)
		{
			for(int i = 0; i<activeErfgoed.getItemCount(); i++)
			{
				if(model.getActiveItem().getErfgoed().getErfgoedNr() == activeErfgoed.getItemAt(i).getErfgoedNr())
				{
					activeErfgoed.setSelectedIndex(i);
				}
			}
			if(model.getNieuweAfbeelding())
			{
			activePhoto.setNewFoto(model.getActiveItem().getFoto());
			activeErfgoed.setSelectedIndex(index);
			activePanel(true);
			btnGoedkeuren.setEnabled(false);
			btnAfkeuren.setEnabled(false);
			saveWijziging.setVisible(true);
			btnWijzigen.setVisible(false);
			model.setNieuweAfbeelding(false);
			}
		}
		refreshTeller();
		repaint();
	}
	private void clearActive(){
		model.nieuwItem();
		lblNaarErfgoed.setVisible(false);
		typebox.setSelectedIndex(0);
		lblBewerken.setText("");
		activeBeschrijving.setText("");
		activeTitel.setText("");
		activeLink.setText("http://");
		activePhoto.setNewFoto(null);
		verwijder.setVisible(false);
	}
	
	public void refreshForState(){
		if (model.getActiveItem().getId() == -1){ //nieuw item maken
			btnGoedkeuren.setEnabled(false);
			btnAfkeuren.setEnabled(false);
			saveWijziging.setVisible(false);
			btnWijzigen.setVisible(true);
			activePanel(false);
			if(model.getActiveItem().getFoto() != null) // na het selecteren van een foto moeten de velden actief blijven
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
			verwijder.setVisible(false);
			activePanel(false);
		}
		if (state.equals("Afgekeurd")){
			btnGoedkeuren.setEnabled(true);
			btnAfkeuren.setEnabled(false);
			btnWijzigen.setEnabled(true);
			btnWijzigen.setVisible(true);
			saveWijziging.setVisible(false);
			verwijder.setVisible(true);
			activePanel(false);
		}
		if (state.equals("Keurlijst")){
			btnGoedkeuren.setEnabled(true);
			btnAfkeuren.setEnabled(true);
			btnWijzigen.setEnabled(true);
			btnWijzigen.setVisible(true);
			saveWijziging.setVisible(false);
			verwijder.setVisible(false);
			activePanel(false);
		}
		}
	
	}
	public void refreshTeller(){
		int goed=0;
		int slecht=0;
		int keur=0;
		for (Item i : model.getItems()){
			if (i.getStatus().equals("Keurlijst"))
				keur++;
			if (i.getStatus().equals("Goedgekeurd"))
				goed++;
			if (i.getStatus().equals("Afgekeurd"))
				slecht++;
		}
		lblTeller.setText("Keurlijst : "+keur+" Goedgekeurd : "+goed+" Afgekeurd : "+slecht);
	}
	// om het meervoudig schrijven van constructors te voorkomen.
	private void processParameter(Parameter para)
	{
		if(para.getSoort() != null)
		{
			if(para.getSoort().equals("nieuw"))
			{
				if(activeErfgoed.getSelectedItem() == null)
				{
					JOptionPane.showMessageDialog(null, "Het lijkt erop dat er nog geen erfgoed bestaat. Gelieve eerst een erfgoed toe te voegen");
				}
				else {
				lblBewerken.setText("");
				clearActive();
				activePanel(true);
				saveWijziging.setVisible(true);
				btnGoedkeuren.setEnabled(false);
				btnAfkeuren.setEnabled(false);
				btnWijzigen.setVisible(false);
				}
			}
			else {
				if(para.getSoort().equals("erfgoeden"))
				{
					activeList = "Erfgoeden";
					rdbtnErfgoeden.setSelected(true);
					zoekBox.setModel(new DefaultComboBoxModel(new String[] {"Naam", "Type","Gemeente"}));
					model.notifyChangeListeners();
				}
			}
		}
		if(para.getObject() != null)
		{
			if(para.getObject() instanceof Erfgoed)
			{
				if(para.getSoort().equals("nieuw")) {
					lblBewerken.setText("Nieuw Item");
					btnWijzigen.setVisible(false);
					saveWijziging.setVisible(true);
					activePanel(true);
					clearActive();
					Erfgoed erfgoed = (Erfgoed) para.getObject();
					for(int i = 0; i<activeErfgoed.getItemCount(); i++)
					{
						if(erfgoed.getErfgoedNr() == activeErfgoed.getItemAt(i).getErfgoedNr())
						{
							activeErfgoed.setSelectedIndex(i);
						}
					}
				}
				else
				{
					if(para.getSoort().equals("zoeken"))
					{
						Erfgoed erfgoed = (Erfgoed) para.getObject();
						zoekVeld.setText(erfgoed.getNaam());
						zoekBox.setSelectedItem("Erfgoed");
						activeList = "Alles";
						model.notifyChangeListeners();
					}
				}
			}
		}
	}
	
	public void activePanel(boolean b)
	{
		typebox.setEnabled(b);
		activeErfgoed.setEnabled(b);
		activeLink.setEnabled(b);
		activeTitel.setEnabled(b);
		activeBeschrijving.setEnabled(b);
	}
}


