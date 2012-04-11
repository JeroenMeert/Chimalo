import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.FlowLayout;


public class Chimalo extends JFrame {

	private JPanel contentPane;
	private JTextField user;
	private JTextField pass;
	private JPanel panel_1;
	private JFrame parent;

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
		parent=this;
		setTitle("Chimalo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 741, 420);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menu = new JMenu("");
		menu.setBackground(Color.GRAY);
		menuBar.add(menu);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		Color aColor = new Color(0xDEE3E9);
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("logo.jpg"));
		} catch (IOException e) {
		}
		ImageIcon image = new ImageIcon(getClass().getResource("logo.jpg"));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		panel_1.setBackground(aColor);
		panel.setBackground(aColor);
		panel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel lblGebruikersnaam = new JLabel("Gebruikersnaam:");
		panel_1.add(lblGebruikersnaam);
		
		user = new JTextField(naam);
		panel_1.add(user);
		user.setColumns(10);
		
		JLabel lblWachtwoord = new JLabel("Wachtwoord:");
		panel_1.add(lblWachtwoord);
		
		pass = new JPasswordField();
		panel_1.add(pass);
		pass.setColumns(10);
		
		JButton btnInloggen = new JButton("Inloggen");
		btnInloggen.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DataBankConnection dbc = new DataBankConnection();
				try {
					Gebruiker admin = dbc.checkAccount(user.getText(), dbc.md5(pass.getText()));
					
				if (admin != null){
					hoofd_scherm scherm = new hoofd_scherm(new Model(admin));
					scherm.setVisible(true);
					parent.dispose();
					Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
					
					// bereken het midden van je scherm
				    int w = scherm.getSize().width;
				    int h = scherm.getSize().height;
				    int x = (size.width-w)/2;
				    int y = (size.height-h)/2;
				    
				    // verplaats de GUI
				    scherm.setLocation(x, y);
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Ongeldige gegevens of de gebruiker heeft geen toegang tot het dashboard");
					user.setText("");
					pass.setText("");
				}
			}
			finally {dbc.sluitConnectie();}
			}
			
		});
		panel_1.add(btnInloggen);
		JPanel panel_2 = new JPanel();
		JLabel imageLabel = new JLabel(image);
		panel_2.add(imageLabel);
		panel.add(panel_2, BorderLayout.NORTH);
		panel_2.setBackground(aColor);
	}

	public Color getPanel_1Background() {
		return panel_1.getBackground();
	}
	public void setPanel_1Background(Color background) {
		panel_1.setBackground(background);
	}
}
