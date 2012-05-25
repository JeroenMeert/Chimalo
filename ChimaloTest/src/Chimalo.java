import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.awt.FlowLayout;
import java.awt.Font;


public class Chimalo extends JFrame {

	private JPanel contentPane;
	private JTextField user;
	private JTextField pass;
	private JPanel panel_1;
	private JFrame parent;
	private Model m;
	private JLabel lblstatus;
	private JButton btnInloggen;
	private JLabel lblWachtwoord;
	private JLabel lblEenOgenblikGeduld;
	private JLabel lblGebruikersnaam;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chimalo frame = new Chimalo("");
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
	public Chimalo(String naam) {
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
		m = new Model();
		parent=this;
		m.setChimalo(this);
		m.setLoginschermIsOpen(true);
		setTitle("Erfgoed Herzele - Overpowered by Chimalo");
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("logo1.png"));
		Image image1 = imageIcon.getImage();
		setIconImage(image1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setResizable(false);
		Color aColor = new Color(0xDEE3E9);
		ImageIcon image = new ImageIcon(getClass().getResource("logo1.gif"));
		setLayout(new FlowLayout());
		ImagePanel panel = new ImagePanel("login.jpg");
		panel.setLayout(null);
		lblGebruikersnaam = new JLabel("Gebruikersnaam:");
		lblGebruikersnaam.setFont(UIManager.getFont("Button.font"));
		lblGebruikersnaam.setBounds(184, 172, 115, 14);
		panel.add(lblGebruikersnaam);
		panel.setPreferredSize(new Dimension(600,350));
		user = new JTextField(naam);
		user.setFont(UIManager.getFont("Button.font"));
		user.setBounds(285, 169, 121, 20);
		user.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyChar() == e.VK_ENTER) {
					pass.requestFocusInWindow(); 
				}
			}
		});
		panel.add(user);
		user.setColumns(10);
		
		lblWachtwoord = new JLabel("Wachtwoord:");
		lblWachtwoord.setFont(UIManager.getFont("Button.font"));
		lblWachtwoord.setBounds(184, 200, 115, 14);
		panel.add(lblWachtwoord);
		
		pass = new JPasswordField();
		pass.setFont(UIManager.getFont("Button.font"));
		pass.setBounds(285, 197, 121, 20);
		pass.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar() == e.VK_ENTER) {
						lblstatus.setText("Bezig met inloggen..");
						try {
							m.setDbconnectie(DriverManager.getConnection("jdbc:sqlserver://ProjectChimalo.mssql.somee.com;database=ProjectChimalo;user=anthonyvd;password=klokken05"));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							lblstatus.setText("Kan geen verbinding met de database maken");
						}
						Gebruiker admin = m.checkAccount(user.getText(), m.getMd5(pass.getText()));
						
					if (admin != null){
						btnInloggen.setVisible(false);
						user.setVisible(false);
						pass.setVisible(false);
						lblWachtwoord.setVisible(false);
						lblGebruikersnaam.setVisible(false);
						
						lblEenOgenblikGeduld.setVisible(true);
						m.setAdmin(admin);
						final updateItems update = new updateItems(m);
						update.execute();
						int delay = 120000; //milliseconds
						  ActionListener taskPerformer = new ActionListener() {
						      public void actionPerformed(ActionEvent evt) {
						    	  updateItems update = new updateItems(m);
						          update.execute();
						      }
						  };
						 m.setTimer(new Timer(delay, taskPerformer));
						 m.getTimer().setRepeats(true);
						 m.getTimer().start();
						}
				
					else {
						lblstatus.setText("Ongeldige gegevens of de gebruiker heeft geen toegang tot het dashboard");
						user.setText("");
						pass.setText("");
					}
				}
				}
				
		});
		panel.add(pass);
		pass.setColumns(10);
		
		btnInloggen = new JButton("Inloggen");
		btnInloggen.setFont(UIManager.getFont("Button.font"));
		btnInloggen.setBounds(285, 228, 121, 28);
		btnInloggen.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				lblstatus.setText("Bezig met inloggen..");
				try {
					m.setDbconnectie(DriverManager.getConnection("jdbc:sqlserver://ProjectChimalo.mssql.somee.com;database=ProjectChimalo;user=anthonyvd;password=klokken05"));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					lblstatus.setText("Kan geen verbinding met de database maken");
				}
				Gebruiker admin = m.checkAccount(user.getText(), m.getMd5(pass.getText()));
				
			if (admin != null){
				btnInloggen.setVisible(false);
				user.setVisible(false);
				pass.setVisible(false);
				lblWachtwoord.setVisible(false);
				lblGebruikersnaam.setVisible(false);
				
				lblEenOgenblikGeduld.setVisible(true);
				m.setAdmin(admin);
				final updateItems update = new updateItems(m);
				update.execute();
				int delay = 120000; //milliseconds
				  ActionListener taskPerformer = new ActionListener() {
				      public void actionPerformed(ActionEvent evt) {
				    	  updateItems update = new updateItems(m);
				          update.execute();
				      }
				  };
				 m.setTimer(new Timer(delay, taskPerformer));
				 m.getTimer().setRepeats(true);
				 m.getTimer().start();
				}
		
			else {
				lblstatus.setText("Ongeldige gegevens of de gebruiker heeft geen toegang tot het dashboard");
				try {
					if(!m.getDbconnectie().isClosed())
					m.getDbconnectie().close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				user.setText("");
				pass.setText("");
			}
			}
			
		});
		panel.add(btnInloggen);
		
		lblstatus = new JLabel("De inloggegevens zijn: test/test (gebruikersnaam/wachtwoord)");
		lblstatus.setFont(UIManager.getFont("Label.font"));
		lblstatus.setBounds(10, 82, 578, 14);
		panel.add(lblstatus);
		m.setStatusLabel(lblstatus);
		add(panel);
		
		lblEenOgenblikGeduld = new JLabel("Een ogenblik geduld..");
		lblEenOgenblikGeduld.setVisible(false);
		lblEenOgenblikGeduld.setBounds(239, 210, 189, 14);
		panel.add(lblEenOgenblikGeduld);
		pack();
	}
}
