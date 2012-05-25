import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.border.Border;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;


public class ItemPanel extends JPanel {

	/**
	 * Create the panel.
	 */

	private Item item;

	public ItemPanel(Item i) {
		item=i;

		this.setBackground(Color.white);

		
		this.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY,Color.lightGray ));
		setPreferredSize(new Dimension(280,120));
		setLayout(new GridLayout(0, 1, 0, 0));
		
		ImagePanel panel = new ImagePanel("itempanel.jpg");
		add(panel);
		panel.setLayout(null);
		
		ImagePanel imagePanel = new ImagePanel(item.getFoto());
		imagePanel.setBounds(10, 26, 79, 79);
		panel.add(imagePanel);
		if(i.getStatus().equals("Keurlijst"))
		{
			imagePanel.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 0, Color.yellow));
		}
		else
		{
			if(i.getStatus().equals("Goedgekeurd"))
			{
				imagePanel.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 0, Color.green));
			}
			else
			{
				imagePanel.setBorder(BorderFactory.createMatteBorder(0, 10, 0, 0, Color.red));
			}
		}
		
		JLabel lblErfgoed = new JLabel("Auteur:");
		lblErfgoed.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErfgoed.setBounds(107, 26, 46, 14);
		panel.add(lblErfgoed);
		
		JLabel lblErfgoed_1 = new JLabel("Erfgoed:");
		lblErfgoed_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErfgoed_1.setBounds(107, 42, 46, 14);
		panel.add(lblErfgoed_1);
		
		JLabel lblDatum = new JLabel("Datum:");
		lblDatum.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDatum.setBounds(107, 58, 46, 14);
		panel.add(lblDatum);
		
		JLabel auteur = new JLabel("New label");
		auteur.setFont(new Font("Tahoma", Font.PLAIN, 11));
		auteur.setBounds(174, 26, 102, 14);
		panel.add(auteur);
		
		JLabel erfgoed = new JLabel("New label");
		erfgoed.setFont(new Font("Tahoma", Font.PLAIN, 11));
		erfgoed.setBounds(174, 42, 104, 14);
		panel.add(erfgoed);
		
		JLabel datum = new JLabel("New label");
		datum.setFont(new Font("Tahoma", Font.PLAIN, 11));
		datum.setBounds(174, 58, 102, 14);
		panel.add(datum);
		auteur.setText(i.getAuteur().getGebruikersnaam());
		DateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		StringBuilder nu = new StringBuilder( formatter.format( i.getInzendDatum() ) );
		datum.setText(nu.toString());
		erfgoed.setText(i.getErfgoed().getNaam());
		
		ImagePanel panel_1 = new ImagePanel("itembar.jpg");
		panel_1.setBounds(0, 0, 403, 18);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblTitel = new JLabel("titel");
		lblTitel.setBounds(5, 2, 403, 14);
		panel_1.add(lblTitel);
		lblTitel.setForeground(Color.GRAY);
		lblTitel.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		//opvullen
		lblTitel.setText(i.getTitel());
		
		
	}
	public Item getItem(){
		return item;
	}
	

	public ItemPanel(Gebruiker g) {
		setLayout(null);
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY,Color.lightGray ));
		setPreferredSize(new Dimension(280, 95));
		JLabel lblNaam = new JLabel("Gebruikersnaam:");
		lblNaam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNaam.setBounds(10, 11, 120, 14);
		add(lblNaam);
		
		JLabel lbTypeGebruiker = new JLabel("Type Gebruiker");
		lbTypeGebruiker.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbTypeGebruiker.setBounds(10, 42, 84, 14);
		add(lbTypeGebruiker);
		
		JLabel naam = new JLabel(g.getGebruikersnaam());
		naam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		naam.setBounds(112, 11, 168, 14);
		add(naam);
		
		JLabel type = new JLabel(g.getType());
		type.setFont(new Font("Tahoma", Font.PLAIN, 11));
		type.setBounds(112, 42, 168, 14);
		add(type);
		
		JLabel lblVoornaam = new JLabel("Naam:");
		lblVoornaam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVoornaam.setBounds(10, 27, 107, 14);
		add(lblVoornaam);
		
		JLabel voornaam = new JLabel(g.getNaam());
		voornaam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		voornaam.setBounds(112, 27, 168, 14);
		add(voornaam);
		
		JLabel lblUitgeschakeld = new JLabel("Uitgeschakeld");
		lblUitgeschakeld.setVisible(false);
		if(!g.isActief())
		{
			lblUitgeschakeld.setVisible(true);
		}
		lblUitgeschakeld.setBounds(91, 75, 84, 14);
		add(lblUitgeschakeld);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEmail.setBounds(10, 57, 84, 14);
		add(lblEmail);
		
		JLabel email = new JLabel(g.getEmail());
		email.setFont(new Font("Tahoma", Font.PLAIN, 11));
		email.setBounds(112, 57, 168, 14);
		add(email);
		
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ItemPanel(Erfgoed e, ArrayList<Item> items) {
		
		BufferedImage img = null;
		int count = 0;
		
		for(Item i : items) {
			
			if(e.getErfgoedNr() == i.getErfgoed().getErfgoedNr() && i.getStatus().equals("Goedgekeurd"))
			{
				if(i.getFoto() != null && e.getErfgoedNr() == i.getErfgoed().getErfgoedNr())
				{
					img = i.getFoto();
				}
				count++;
			}
		}
		this.setBackground(Color.white);

		
		this.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY,Color.lightGray ));
		setPreferredSize(new Dimension(280,120));
		setLayout(new GridLayout(0, 1, 0, 0));
		
		ImagePanel panel = new ImagePanel("itempanel.jpg");
		add(panel);
		panel.setLayout(null);
		
		ImagePanel imagePanel = new ImagePanel(img);
		imagePanel.setBounds(10, 26, 79, 79);
		panel.add(imagePanel);
		
		JLabel lblErfgoed = new JLabel("Type: " + e.getType());
		lblErfgoed.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErfgoed.setBounds(107, 26, 159, 14);
		panel.add(lblErfgoed);
		
		JLabel lblErfgoed_1 = new JLabel("Locatie: " + e.getLocatie());
		lblErfgoed_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblErfgoed_1.setBounds(107, 42, 159, 14);
		panel.add(lblErfgoed_1);
		
		JLabel lblDatum = new JLabel("Aantal inzendingen: " + count);
		lblDatum.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDatum.setBounds(108, 78, 158, 14);
		panel.add(lblDatum);
		
		ImagePanel panel_1 = new ImagePanel("itembar.jpg");
		panel_1.setBounds(0, 0, 403, 18);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblTitel = new JLabel("titel");
		lblTitel.setBounds(5, 2, 403, 14);
		panel_1.add(lblTitel);
		lblTitel.setForeground(Color.GRAY);
		lblTitel.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		//opvullen
		lblTitel.setText(e.getNaam());
		
		JLabel lblGemeente = new JLabel("Gemeente: " +e.getGemeente());
		lblGemeente.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGemeente.setBounds(107, 60, 159, 14);
		panel.add(lblGemeente);
		
		
	}
}
