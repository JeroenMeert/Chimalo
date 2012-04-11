import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Accountbeheer extends JFrame implements ChangeListener {
	private ImagePanel contentPane;
	private ArrayList<Gebruiker> gebruikers;
	private JPanel panel_1 = new JPanel();
	private JPanel panel_1_1;
	private JRadioButton rdbtnAfgekeurde;
	private JRadioButton rdbtnGeenFilter;
	private JTextField naamVeld;
	private JTextField wachtwoordVeld;
	private JTextField voornaamVeld;
	private JComboBox typeBox;
	private int activeGebruiker = -1;
	private Model m;
	private JFrame parent;
	private JButton activate;
	private ChangeListener accountbeheer;
	private JTextField emailveld;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Model m = new Model(new Gebruiker("Van Dooren", "Anthony", "", "Administrator", 8, true, "anthonyvandooren@gmail.com"));
					Accountbeheer frame = new Accountbeheer(m);
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
	public Accountbeheer(Model mod) {
		super("Account Beheer");
		parent = this;
		m = mod;
		m.subscribe(this);
		accountbeheer = this;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAccountManagement = new JMenu("Opties");
		menuBar.add(mnAccountManagement);
		
		JMenuItem mntmBeheerdersBeheren = new JMenuItem("Account beheer");
		mntmBeheerdersBeheren.setEnabled(false);
		mnAccountManagement.add(mntmBeheerdersBeheren);
		
		JMenuItem mntmDashboard = new JMenuItem("Dashboard");
		mntmDashboard.setEnabled(true);
		mntmDashboard.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				parent.dispose();
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
				
			}
		});
		mnAccountManagement.add(mntmDashboard);
		
		JMenuItem mntmNieuw = new JMenuItem("Nieuw");
		mnAccountManagement.add(mntmNieuw);
		mntmNieuw.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				nieuw();
				
			}
		});
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		separator.setBackground(Color.WHITE);
		menuBar.add(separator);
		
		JLabel lblUBentAangemeld = new JLabel("U bent aangemeld als:  ");
		lblUBentAangemeld.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(lblUBentAangemeld);
		
		JMenu mnGebruiker = new JMenu(m.getAdmin().getNaam());
		menuBar.add(mnGebruiker);
		
		JMenuItem mntmUitloggen = new JMenuItem("Uitloggen");
		mnGebruiker.add(mntmUitloggen);
		mntmUitloggen.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parent.dispose();
				m.unsubscribe(accountbeheer);
				Chimalo frame = new Chimalo(m.getAdmin().getGebruikersnaam());
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
		contentPane = new ImagePanel("accbackground.jpg");
		contentPane.setPreferredSize(new Dimension(725,539));
		contentPane.setBackground(UIManager.getColor("Button.disabledShadow"));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		gebruikers = m.getGebruikers();
		
		ImagePanel panel = new ImagePanel("banner2.jpg");
		panel.setBounds(0, 0, 725, 92);
		contentPane.add(panel);
		
		JLabel lblBewerken = new JLabel("Bewerken");
		lblBewerken.setBounds(67, 132, 87, 14);
		contentPane.add(lblBewerken);
		
		ButtonGroup groep = new ButtonGroup();
		groep.add(rdbtnAfgekeurde);
		groep.add(rdbtnGeenFilter);
		
		JLabel lblGebruikersnaam = new JLabel("Gebruikersnaam:");
		lblGebruikersnaam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGebruikersnaam.setBounds(67, 166, 123, 14);
		contentPane.add(lblGebruikersnaam);
		
		naamVeld = new JTextField();
		naamVeld.setFont(new Font("Tahoma", Font.PLAIN, 11));
		naamVeld.setBounds(181, 163, 142, 20);
		contentPane.add(naamVeld);
		naamVeld.setColumns(10);
		
		JLabel lblWachtwoord = new JLabel("Wachtwoord:");
		lblWachtwoord.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblWachtwoord.setBounds(67, 208, 113, 14);
		contentPane.add(lblWachtwoord);
		
		wachtwoordVeld = new JPasswordField();
		wachtwoordVeld.setFont(new Font("Tahoma", Font.PLAIN, 11));
		wachtwoordVeld.setBounds(181, 205, 142, 20);
		contentPane.add(wachtwoordVeld);
		wachtwoordVeld.setColumns(10);
		panel_1_1 = GetGebruikerLijst(gebruikers);
		JScrollPane panel_1 = new JScrollPane(panel_1_1);
		contentPane.add(panel_1);
		panel_1.setBounds(363, 154, 304, 371);
		
		//contentPane.add(panel_1);
		
		//GridLayout gl_panel_1 = new GridLayout(dbconn.getCount("Gebruikers"), 0);
		GridLayout gl_panel_1;
		if(gebruikers.size() == 0)
			gl_panel_1 = new GridLayout(1, 0);
		else
			gl_panel_1 = new GridLayout(gebruikers.size(), 0);
		panel_1_1.setLayout(gl_panel_1);
		
		JLabel lblType = new JLabel("Type:");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblType.setBounds(67, 230, 46, 14);
		contentPane.add(lblType);
		
		typeBox = new JComboBox();
		typeBox.setFont(new Font("Tahoma", Font.PLAIN, 11));
		typeBox.setModel(new DefaultComboBoxModel(new String[] {"Beheerder", "Administrator"}));
		typeBox.setBounds(181, 227, 142, 20);
		contentPane.add(typeBox);
		
		JLabel lblVoornaam = new JLabel("Naam:");
		lblVoornaam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVoornaam.setBounds(67, 187, 123, 14);
		contentPane.add(lblVoornaam);
		
		voornaamVeld = new JTextField();
		voornaamVeld.setFont(new Font("Tahoma", Font.PLAIN, 11));
		voornaamVeld.setBounds(181, 184, 142, 20);
		contentPane.add(voornaamVeld);
		voornaamVeld.setColumns(10);
		
		activate = new JButton("Activeer");
		activate.setVisible(false);
		activate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		activate.setBounds(245, 281, 78, 23);
		contentPane.add(activate);
		activate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				m.maakGebruikerActief(activeGebruiker);
				nieuw();
			}
		});
		
		JButton btnOpslaan = new JButton("Opslaan");
		btnOpslaan.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnOpslaan.setBounds(67, 281, 78, 23);
		contentPane.add(btnOpslaan);
		btnOpslaan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(activeGebruiker == -1) {
					m.voegToe(naamVeld.getText(), voornaamVeld.getText(), m.getMd5(wachtwoordVeld.getText()), typeBox.getSelectedItem().toString(), emailveld.getText());
				}
				else
					m.updateGebruiker(activeGebruiker,naamVeld.getText(), voornaamVeld.getText(), m.getMd5(wachtwoordVeld.getText()), typeBox.getSelectedItem().toString(), emailveld.getText());
				nieuw();
			}
		});
		
		JButton btnVerwijderen = new JButton("Verwijderen");
		btnVerwijderen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnVerwijderen.setBounds(147, 281, 95, 23);
		contentPane.add(btnVerwijderen);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEmail.setBounds(67, 253, 113, 14);
		contentPane.add(lblEmail);
		
		emailveld = new JTextField();
		emailveld.setBounds(181, 250, 142, 20);
		contentPane.add(emailveld);
		emailveld.setColumns(10);
		btnVerwijderen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(activeGebruiker >=0)
				{
				int result = JOptionPane.showConfirmDialog(null, "Ben je zeker dat je " + typeBox.getSelectedItem().toString().toLowerCase() + " " + naamVeld.getText() + " " + voornaamVeld.getText() + " wilt verwijderen?" );
				if(result == JOptionPane.OK_OPTION)
				{
					boolean ok = m.controleOpVerwijderen(activeGebruiker);
					if(ok) {
						nieuw();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Er is een fout opgetreden bij het verwijderen van de " + typeBox.getSelectedItem().toString().toLowerCase());
					}
						
				}
				}
			}
		});
		pack();
	}
	
	private JPanel GetGebruikerLijst(ArrayList<Gebruiker> gebruikers)
	{
		/*panel_1.setLayout(new GridBagLayout());
		GridBagConstraints c= new GridBagConstraints();
		c.gridx=0;
		c.gridy=0;*/
		

		for (final Gebruiker i :gebruikers){
			final ItemPanel ip =new ItemPanel(i);
			final String naam= i.getGebruikersnaam();
			final String voornaam = i.getNaam();
			final String type = i.getType();
			final String email = i.getEmail();
			ip.addMouseListener(new MouseListener(){
			
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					naamVeld.setText(naam);
					voornaamVeld.setText(voornaam);
					typeBox.setSelectedItem(type);
					activeGebruiker = i.getGebruikersnummer();
					emailveld.setText(email);
					if(i.isActief()) {
						activate.setVisible(false);
					}
					else
						activate.setVisible(true);
						
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
			panel_1.add(ip);
			
		}
		return panel_1;
	}
	public void nieuw() {
		naamVeld.setText("");
		wachtwoordVeld.setText("");
		voornaamVeld.setText("");
		typeBox.setSelectedIndex(0);
		emailveld.setText("");
		activeGebruiker = -1;
		activate.setVisible(false);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		panel_1_1.setVisible(false);
		panel_1_1.removeAll();
		gebruikers = m.getGebruikers();
		panel_1_1 = GetGebruikerLijst(gebruikers);
		panel_1_1.setVisible(true);
		if(gebruikers.size() == 0)
			panel_1.setLayout(new GridLayout(1, 0));
		else
			panel_1.setLayout(new GridLayout(gebruikers.size(), 0));
	}
}
