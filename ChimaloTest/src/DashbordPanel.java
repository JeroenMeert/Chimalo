import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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


public class DashbordPanel extends ImagePanel implements ChangeListener{

	/**
	 * Create the panel.
	 */
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
	
	/**
	 * @wbp.parser.constructor
	 */
	public DashbordPanel(Model m,hoofd_scherm parentframe) {
		super("background.jpg");
		parentPanel=parentframe;
		parent=this;
		model = m;
		m.subscribe(this);
		
		this.setPreferredSize(new Dimension(1000, 591));
		this.setBackground(UIManager.getColor("Button.disabledShadow"));
		
		this.setLayout(null);
		
		activeTitel = new JTextField();
		activeTitel.setEditable(false);
		activeTitel.setBounds(405, 174, 188, 20);
		this.add(activeTitel);
		activeTitel.setColumns(10);
		
		JLabel lblTitel = new JLabel("Titel");
		lblTitel.setBounds(330, 177, 71, 14);
		this.add(lblTitel);
		
		JLabel lblAuteur = new JLabel("Auteur");
		lblAuteur.setBounds(330, 202, 71, 14);
		this.add(lblAuteur);
		
		activeBeschrijving = new JTextArea();
		activeBeschrijving.setWrapStyleWord(true);
		activeBeschrijving.setLineWrap(true);
		activeBeschrijving.setEditable(false);
		activeBeschrijving.setBackground(Color.WHITE);
		activeBeschrijving.setBounds(405, 252, 188, 237);
		activeBeschrijving.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		this.add(activeBeschrijving);
		
		
		
		
		
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
		scrollPane.setBounds(621, 163, 320, 412);
		scrollPane.setViewportView(panel_1);
		scrollPane.getVerticalScrollBar().setUnitIncrement(150);
		scrollPane.getVerticalScrollBar().setBlockIncrement(150);
		this.add(scrollPane);
		
		
		activeAuteur = new JTextField();
		activeAuteur.setEditable(false);
		activeAuteur.setBounds(405, 199, 188, 20);
		this.add(activeAuteur);
		activeAuteur.setColumns(10);
		
		JLabel lblDatum = new JLabel("Datum");
		lblDatum.setBounds(330, 227, 71, 14);
		this.add(lblDatum);
		
		activeDatum = new JTextField();
		activeDatum.setForeground(Color.BLACK);
		activeDatum.setEditable(false);
		activeDatum.setBounds(405, 224, 188, 20);
		this.add(activeDatum);
		activeDatum.setColumns(10);
		
		JLabel lblBeschrijving = new JLabel("Beschrijving");
		lblBeschrijving.setBounds(330, 252, 71, 14);
		this.add(lblBeschrijving);
		

		ImagePanel panel = new ImagePanel("logochimalo.jpg");
		panel.setBounds(0, 0, 1000, 92);
		this.add(panel);
		
		 btnWijzigen = new JButton("Wijzigen");
		btnWijzigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activeTitel.setEditable(true);
				activeBeschrijving.setEditable(true);
				saveWijziging.setVisible(true);
			}
		});
		btnWijzigen.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnWijzigen.setBounds(67, 500, 123, 23);
		this.add(btnWijzigen);
		
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
		btnAfkeuren.setBounds(330, 500, 134, 23);
		this.add(btnAfkeuren);
		
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
		activePhoto.setBounds(67, 174, 253, 315);
		activePhoto.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(model.getActiveItem()!=null){
				EditFrame frame = new EditFrame(activePhoto.getFoto(),model);
				frame.setVisible(true);
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
		
		JLabel lblBewerken = new JLabel("Bewerken");
		lblBewerken.setBounds(67, 149, 87, 14);
		this.add(lblBewerken);
		
		rdbtnKeurlijst = new JRadioButton("Keurlijst");
		rdbtnKeurlijst.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnKeurlijst.setOpaque(true);
		rdbtnKeurlijst.setBounds(619, 102, 79, 23);
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
		this.add(rdbtnKeurlijst);
		
		 rdbtnGoedgekeurd = new JRadioButton("Goedgekeurde items");
		rdbtnGoedgekeurd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnGoedgekeurd.setBounds(694, 101, 134, 24);
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
		this.add(rdbtnGoedgekeurd);
		
		rdbtnAfgekeurde = new JRadioButton("Afgekeurde Items");
		rdbtnAfgekeurde.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnAfgekeurde.setBounds(830, 102, 170, 23);
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
		this.add(rdbtnAfgekeurde);
		

		
		ButtonGroup groep = new ButtonGroup();
		groep.add(rdbtnKeurlijst);
		groep.add(rdbtnGoedgekeurd);
		groep.add(rdbtnAfgekeurde);
		
		
		
		JLabel lblZoeken = new JLabel("Zoeken:");
		lblZoeken.setBounds(632, 142, 45, 14);
		this.add(lblZoeken);
		
		zoekVeld = new JTextField();
		zoekVeld.setBounds(686, 139, 157, 20);
		this.add(zoekVeld);
		zoekVeld.setColumns(10);
		
		zoekBox = new JComboBox();
		zoekBox.setModel(new DefaultComboBoxModel(new String[] {"Titel", "Auteur", "Datum","Erfgoed"}));
		zoekBox.setSelectedIndex(0);
		zoekBox.setBounds(853, 139, 79, 20);
		this.add(zoekBox);
		

		btnGoedkeuren.setBounds(200, 500, 120, 23);
		this.add(btnGoedkeuren);
		
		lblTeller = new JLabel("Keurlijst: 0  Goedgekeurde items: 0   Afgekeurde Items: 0");
		lblTeller.setVerticalAlignment(SwingConstants.TOP);
		lblTeller.setBounds(10, 103, 583, 24);
		this.add(lblTeller);
		
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
		saveWijziging.setBounds(475, 500, 118, 23);
		this.add(saveWijziging);
		
		JLabel lblNaarErfgoed = new JLabel("Naar erfgoed >>");
		lblNaarErfgoed.setFont(new Font("Arial Black", Font.BOLD, 14));
		lblNaarErfgoed.setBounds(405, 534, 188, 24);
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
		zoekVeld.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshItems();
				
			}
		});
		refreshForState();
		refreshTeller();
		repaint();
		
	}

	
	
	public DashbordPanel(Model m,hoofd_scherm parentframe,String erfgoed) {
		super("background.jpg");
		parentPanel=parentframe;
		parent=this;
		model = m;
		m.subscribe(this);
		
		this.setPreferredSize(new Dimension(1000, 591));
		this.setBackground(UIManager.getColor("Button.disabledShadow"));
		
		this.setLayout(null);
		
		activeTitel = new JTextField();
		activeTitel.setEditable(false);
		activeTitel.setBounds(405, 174, 188, 20);
		this.add(activeTitel);
		activeTitel.setColumns(10);
		
		JLabel lblTitel = new JLabel("Titel");
		lblTitel.setBounds(330, 177, 71, 14);
		this.add(lblTitel);
		
		JLabel lblAuteur = new JLabel("Auteur");
		lblAuteur.setBounds(330, 202, 71, 14);
		this.add(lblAuteur);
		
		activeBeschrijving = new JTextArea();
		activeBeschrijving.setWrapStyleWord(true);
		activeBeschrijving.setLineWrap(true);
		activeBeschrijving.setEditable(false);
		activeBeschrijving.setBackground(Color.WHITE);
		activeBeschrijving.setBounds(405, 252, 188, 237);
		activeBeschrijving.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		this.add(activeBeschrijving);
		
		
		
		
		
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
		scrollPane.setBounds(621, 163, 320, 412);
		scrollPane.setViewportView(panel_1);
		scrollPane.getVerticalScrollBar().setUnitIncrement(150);
		scrollPane.getVerticalScrollBar().setBlockIncrement(150);
		this.add(scrollPane);
		
		
		activeAuteur = new JTextField();
		activeAuteur.setEditable(false);
		activeAuteur.setBounds(405, 199, 188, 20);
		this.add(activeAuteur);
		activeAuteur.setColumns(10);
		
		JLabel lblDatum = new JLabel("Datum");
		lblDatum.setBounds(330, 227, 71, 14);
		this.add(lblDatum);
		
		activeDatum = new JTextField();
		activeDatum.setForeground(Color.BLACK);
		activeDatum.setEditable(false);
		activeDatum.setBounds(405, 224, 188, 20);
		this.add(activeDatum);
		activeDatum.setColumns(10);
		
		JLabel lblBeschrijving = new JLabel("Beschrijving");
		lblBeschrijving.setBounds(330, 252, 71, 14);
		this.add(lblBeschrijving);
		

		ImagePanel panel = new ImagePanel("logochimalo.jpg");
		panel.setBounds(0, 0, 1000, 92);
		this.add(panel);
		
		 btnWijzigen = new JButton("Wijzigen");
		btnWijzigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				activeTitel.setEditable(true);
				activeBeschrijving.setEditable(true);
				saveWijziging.setVisible(true);
			}
		});
		btnWijzigen.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnWijzigen.setBounds(67, 500, 123, 23);
		this.add(btnWijzigen);
		
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
		btnAfkeuren.setBounds(330, 500, 134, 23);
		this.add(btnAfkeuren);
		
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
		activePhoto.setBounds(67, 174, 253, 315);
		activePhoto.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(model.getActiveItem()!=null){
				EditFrame frame = new EditFrame(activePhoto.getFoto(),model);
				frame.setVisible(true);
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
		
		JLabel lblBewerken = new JLabel("Bewerken");
		lblBewerken.setBounds(67, 149, 87, 14);
		this.add(lblBewerken);
		
		rdbtnKeurlijst = new JRadioButton("Keurlijst");
		rdbtnKeurlijst.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnKeurlijst.setOpaque(true);
		rdbtnKeurlijst.setBounds(619, 102, 79, 23);
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
		this.add(rdbtnKeurlijst);
		
		 rdbtnGoedgekeurd = new JRadioButton("Goedgekeurde items");
		rdbtnGoedgekeurd.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnGoedgekeurd.setBounds(694, 101, 134, 24);
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
		this.add(rdbtnGoedgekeurd);
		
		rdbtnAfgekeurde = new JRadioButton("Afgekeurde Items");
		rdbtnAfgekeurde.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnAfgekeurde.setBounds(830, 102, 170, 23);
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
		this.add(rdbtnAfgekeurde);
		

		
		ButtonGroup groep = new ButtonGroup();
		groep.add(rdbtnKeurlijst);
		groep.add(rdbtnGoedgekeurd);
		groep.add(rdbtnAfgekeurde);
		
		
		
		JLabel lblZoeken = new JLabel("Zoeken:");
		lblZoeken.setBounds(632, 142, 45, 14);
		this.add(lblZoeken);
		
		zoekVeld = new JTextField(erfgoed);
		zoekVeld.setBounds(686, 139, 157, 20);
		this.add(zoekVeld);
		zoekVeld.setColumns(10);
		
		zoekBox = new JComboBox();
		zoekBox.setModel(new DefaultComboBoxModel(new String[] {"Titel", "Auteur", "Datum","Erfgoed"}));
		zoekBox.setSelectedIndex(3);
		zoekBox.setBounds(853, 139, 79, 20);
		this.add(zoekBox);
		

		btnGoedkeuren.setBounds(200, 500, 120, 23);
		this.add(btnGoedkeuren);
		
		lblTeller = new JLabel("Keurlijst: 0  Goedgekeurde items: 0   Afgekeurde Items: 0");
		lblTeller.setVerticalAlignment(SwingConstants.TOP);
		lblTeller.setBounds(10, 103, 583, 24);
		this.add(lblTeller);
		
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
		saveWijziging.setBounds(475, 500, 118, 23);
		this.add(saveWijziging);
		
		JLabel lblNaarErfgoeden = new JLabel("Naar erfgoed >>");
		lblNaarErfgoeden.setFont(new Font("Arial Black", Font.BOLD, 14));
		lblNaarErfgoeden.setBounds(405, 534, 188, 24);
		lblNaarErfgoeden.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if (model.getActiveItem()!=null){
				model.unsubscribe(parent);
				model.clearActive();
				int i=1;
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
		add(lblNaarErfgoeden);
		zoekVeld.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshItems();
				
			}
		});
		refreshForState();
		refreshTeller();
		repaint();
		
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
				if (String.valueOf(zoekBox.getSelectedItem())=="Erfgoed"){

					if (String.valueOf(i.getErfgoed()).toLowerCase().contains(zoekVeld.getText().toLowerCase()))
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


