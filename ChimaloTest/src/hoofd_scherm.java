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
import java.awt.event.WindowEvent;
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


public class hoofd_scherm extends JFrame {
	private JPanel contentPane;
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
	private hoofd_scherm parent;
	private JPanel hoofdpanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Model m = new Model(new Gebruiker("Van Dooren", "Anthony", "", "Administrator", 8, true, "anthonyvandooren@gmail.com"));
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
		addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(WindowEvent winEvt) {
		    	// om zeker te zijn dat de connectie wordt gesloten
		    	model.sluitConnectie();
		        System.exit(0); 
		    }
		});
		state="Keuzelijst";
		model.setHoofdframe(this);
		setResizable(false);
		setSize(new Dimension(1000,539));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu opties = new JMenu("Opties");
		menuBar.add(opties);
		
		JMenuItem mntmBeheerdersBeheren = new JMenuItem("Account beheer");
		if(model.getAdmin().getType().equals("Beheerder"))
		{
			mntmBeheerdersBeheren.setEnabled(false);
		}
		mntmBeheerdersBeheren.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
						model.unsubscribe(model.getActiveJPanel());
						parent.dispose();
						Accountbeheer frame = new Accountbeheer(model);
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
		
		JMenuItem mntmHerstartConnectie = new JMenuItem("Herstart connectie");
		mntmHerstartConnectie.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.herstartConnectie();
				
			}
		});
		opties.add(mntmHerstartConnectie);
		
		JSeparator separator_1 = new JSeparator();
		opties.add(separator_1);
		opties.add(mntmBeheerdersBeheren);
		
		JMenuItem mntmNieuw = new JMenuItem("Nieuw item maken");
		mntmNieuw.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				model.unsubscribe(model.getActiveJPanel());
				newPanel(new DashbordPanel(model, new Parameter("nieuw", "")));
			}
			
		});
		opties.add(mntmNieuw);
		
		
		
		
		
		
		JMenuItem mntmErfgoeden = new JMenuItem("Overzicht erfgoeden");
		mntmErfgoeden.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				model.unsubscribe(model.getActiveJPanel());
				newPanel(new ErfgoedLijstPanel(model,parent));
			}
			
		});
		opties.add(mntmErfgoeden);
		
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		separator.setBackground(Color.WHITE);
		menuBar.add(separator);
		
		JLabel lblUBentAangemeld = new JLabel("U bent aangemeld als:  ");
		lblUBentAangemeld.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(lblUBentAangemeld);
		
		JMenu mnGebruiker = new JMenu(model.getAdmin().getNaam());
		menuBar.add(mnGebruiker);
		
		JMenuItem mntmUitloggen = new JMenuItem("Uitloggen");
		mntmUitloggen.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				model.unsubscribe(model.getActiveJPanel());
				parent.dispose();
				Chimalo frame = new Chimalo(model.getAdmin().getGebruikersnaam());
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
		
		ImagePanel panel = new ImagePanel("logochimalo.jpg");
		panel.setPreferredSize(new Dimension(1000,89));
		getContentPane().add(panel, BorderLayout.NORTH);
		hoofdpanel = new DashbordPanel(model, new Parameter());
		getContentPane().add(hoofdpanel, BorderLayout.WEST);
		pack();
	}
	public void newPanel(JPanel p){
		hoofdpanel.setVisible(false);
		remove(hoofdpanel);
		hoofdpanel=p;
		getContentPane().add(hoofdpanel);
		hoofdpanel.setVisible(true);
	}
}
