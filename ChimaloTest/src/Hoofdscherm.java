import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;


public class Hoofdscherm extends JFrame {

	private ImagePanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Hoofdscherm frame = new Hoofdscherm();
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
	public Hoofdscherm() {
		super("Dashboard");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAccountManagement = new JMenu("Account management");
		menuBar.add(mnAccountManagement);
		
		JMenuItem mntmBeheerdersBeheren = new JMenuItem("Beheerders beheren");
		mnAccountManagement.add(mntmBeheerdersBeheren);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		separator.setBackground(Color.WHITE);
		menuBar.add(separator);
		
		JLabel lblUBentAangemeld = new JLabel("U bent aangemeld als:  ");
		lblUBentAangemeld.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(lblUBentAangemeld);
		
		JMenu mnGebruiker = new JMenu("Anthony");
		menuBar.add(mnGebruiker);
		
		JMenuItem mntmUitloggen = new JMenuItem("Uitloggen");
		mnGebruiker.add(mntmUitloggen);
		contentPane = new ImagePanel("background.jpg");
		contentPane.setPreferredSize(new Dimension(1000,539));
		contentPane.setBackground(UIManager.getColor("Button.disabledShadow"));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(405, 163, 188, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblTitel = new JLabel("Titel");
		lblTitel.setBounds(330, 166, 71, 14);
		contentPane.add(lblTitel);
		
		JLabel lblAuteur = new JLabel("Auteur");
		lblAuteur.setBounds(330, 191, 71, 14);
		contentPane.add(lblAuteur);
		
		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setEditable(false);
		textField_1.setBounds(405, 188, 188, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblDatum = new JLabel("Datum");
		lblDatum.setBounds(330, 216, 71, 14);
		contentPane.add(lblDatum);
		
		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setEditable(false);
		textField_2.setBounds(405, 213, 188, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblBeschrijving = new JLabel("Beschrijving");
		lblBeschrijving.setBounds(330, 241, 71, 14);
		contentPane.add(lblBeschrijving);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBackground(Color.WHITE);
		textArea_1.setBounds(405, 241, 188, 237);
		textArea_1.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		contentPane.add(textArea_1);
		
		ImagePanel panel = new ImagePanel("logochimalo.jpg");
		panel.setBounds(0, 0, 99, 91);
		contentPane.add(panel);
		
		JButton btnNewButton = new JButton("Wijzigen");
		btnNewButton.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnNewButton.setBounds(67, 489, 123, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Verwijderen/Afkeuren");
		btnNewButton_1.setBackground(UIManager.getColor("Button.disabledShadow"));
		btnNewButton_1.setBounds(330, 489, 263, 23);
		contentPane.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(67, 163, 253, 315);
		contentPane.add(panel_1);
		
		JLabel lblBewerken = new JLabel("Bewerken");
		lblBewerken.setBounds(67, 132, 87, 14);
		contentPane.add(lblBewerken);
		
		JRadioButton rdbtnKeurlijst = new JRadioButton("Keurlijst");
		rdbtnKeurlijst.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnKeurlijst.setOpaque(true);
		rdbtnKeurlijst.setBounds(619, 94, 79, 23);
		contentPane.add(rdbtnKeurlijst);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Goedgekeurde items");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnNewRadioButton.setBounds(698, 93, 134, 24);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnAfgekeurdeItems = new JRadioButton("Afgekeurde Items");
		rdbtnAfgekeurdeItems.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnAfgekeurdeItems.setBounds(830, 94, 170, 23);
		contentPane.add(rdbtnAfgekeurdeItems);
		
		ButtonGroup groep = new ButtonGroup();
		groep.add(rdbtnKeurlijst);
		groep.add(rdbtnNewRadioButton);
		groep.add(rdbtnAfgekeurdeItems);
		
		JLabel lblZoeken = new JLabel("Zoeken:");
		lblZoeken.setBounds(631, 132, 45, 14);
		contentPane.add(lblZoeken);
		
		textField_3 = new JTextField();
		textField_3.setBounds(686, 129, 123, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Titel", "Auteur", "Datum"}));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(819, 129, 113, 20);
		contentPane.add(comboBox);
		
		JButton btnNewButton_2 = new JButton("Goedkeuren");
		btnNewButton_2.setBounds(200, 489, 120, 23);
		contentPane.add(btnNewButton_2);
		
		JLabel lblDeConnecieMet = new JLabel("Keurlijst: 0  Goedgekeurde items: 0   Afgekeurde Items: 0");
		lblDeConnecieMet.setVerticalAlignment(SwingConstants.TOP);
		lblDeConnecieMet.setBounds(10, 97, 583, 24);
		contentPane.add(lblDeConnecieMet);
		pack();
	}
}
