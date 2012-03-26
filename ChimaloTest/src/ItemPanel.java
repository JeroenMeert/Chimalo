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


public class ItemPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private Item item;
	public ItemPanel(Item i) {
		item=i;
		setLayout(null);

		this.setBackground(Color.white);

		
		this.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY,Color.lightGray ));
		setPreferredSize(new Dimension(280,120));
		JLabel lblTitel = new JLabel("Titel :");
		lblTitel.setBounds(83, 11, 46, 14);
		add(lblTitel);
		
		JLabel lblAuteur = new JLabel("Auteur:");
		lblAuteur.setBounds(83, 24, 46, 14);
		add(lblAuteur);
		
		JLabel lblIngezondenOp = new JLabel("Datum :");
		lblIngezondenOp.setBounds(83, 36, 84, 14);
		add(lblIngezondenOp);
		
		JLabel lblBeschrijving = new JLabel("Beschrijving :");
		lblBeschrijving.setBounds(83, 49, 84, 14);
		add(lblBeschrijving);
		
		ImagePanel imagePanel = new ImagePanel(item.getFoto());
		imagePanel.setBounds(10, 11, 63, 58);
		add(imagePanel);
		
		JLabel titel = new JLabel(i.getTitel());
		titel.setBounds(175, 11, 105, 14);
		add(titel);
		
		JLabel auteur = new JLabel(i.getAuteur());
		auteur.setBounds(175, 24, 105, 14);
		add(auteur);
		
		JLabel datum = new JLabel(String.valueOf(i.getInzendDatum()));
		datum.setBounds(175, 36, 95, 14);
		add(datum);
		
		JTextArea text = new JTextArea(i.getText());
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.setEditable(false);
		text.setBackground(Color.WHITE);
		text.setBounds(83, 61, 187, 46);
		add(text);
		
		
	}
	public Item getItem(){
		return item;
	}
	/**
	 * 
	 * @wbp.parser.constructor 
	 */
	public ItemPanel(Gebruiker g) {
		setLayout(null);
		this.setBackground(Color.white);
		this.setBorder(BorderFactory.createEtchedBorder(Color.DARK_GRAY,Color.lightGray ));
		setPreferredSize(new Dimension(280, 79));
		JLabel lblNaam = new JLabel("Gebruikersnaam:");
		lblNaam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNaam.setBounds(10, 11, 120, 14);
		add(lblNaam);
		
		JLabel lbTypeGebruiker = new JLabel("Type Gebruiker");
		lbTypeGebruiker.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbTypeGebruiker.setBounds(10, 42, 84, 14);
		add(lbTypeGebruiker);
		
		JLabel naam = new JLabel(g.getNaam());
		naam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		naam.setBounds(140, 11, 130, 14);
		add(naam);
		
		JLabel type = new JLabel(g.getType());
		type.setFont(new Font("Tahoma", Font.PLAIN, 11));
		type.setBounds(140, 42, 95, 14);
		add(type);
		
		JLabel lblVoornaam = new JLabel("Naam:");
		lblVoornaam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVoornaam.setBounds(10, 27, 107, 14);
		add(lblVoornaam);
		
		JLabel voornaam = new JLabel(g.getVoornaam());
		voornaam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		voornaam.setBounds(140, 27, 130, 14);
		add(voornaam);
		
		JLabel lblUitgeschakeld = new JLabel("Uitgeschakeld");
		lblUitgeschakeld.setVisible(false);
		if(!g.isActief())
		{
			lblUitgeschakeld.setVisible(true);
		}
		lblUitgeschakeld.setBounds(90, 60, 84, 14);
		add(lblUitgeschakeld);
		
		
	}
}
