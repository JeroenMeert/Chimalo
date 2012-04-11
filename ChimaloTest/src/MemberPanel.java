import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;


public class MemberPanel extends ImagePanel {

	/**
	 * Create the panel.
	 */
	private JLabel titel;
	private JLabel lblGebruikersnaam;
	private JLabel lblNaam;
	private JLabel lblType;
	private JLabel lblEmail;
	
	public MemberPanel(Gebruiker g) {
		super("member.jpg");
		setLayout(null);
		setPreferredSize(new Dimension(240,110));
		titel = new JLabel("Ledenfiche van lid #" + g.getGebruikersnummer());
		titel.setForeground(Color.DARK_GRAY);
		titel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		titel.setBounds(5, 3, 235, 14);
		add(titel);
		
		lblGebruikersnaam = new JLabel("Gebruikersnaam: " + g.getGebruikersnaam());
		lblGebruikersnaam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblGebruikersnaam.setBounds(62, 28, 178, 14);
		add(lblGebruikersnaam);
		
		lblNaam = new JLabel("Naam: "+g.getNaam());
		lblNaam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNaam.setBounds(62, 43, 178, 14);
		add(lblNaam);
		
		lblType = new JLabel("Type: " + g.getType());
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblType.setBounds(62, 59, 178, 14);
		add(lblType);
		
		lblEmail = new JLabel("Email: " +g.getEmail());
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEmail.setBounds(62, 74, 178, 14);
		add(lblEmail);

	}
	public void setGebruiker(Gebruiker g)
	{
		titel.setText("Ledenfiche van lid #" + g.getGebruikersnummer());
		lblGebruikersnaam.setText("Gebruikersnaam: " + g.getGebruikersnaam());
		lblNaam.setText("Naam: "+g.getNaam());
		lblType.setText("Type: " + g.getType());
		lblEmail.setText("Email: " +g.getEmail());
	}
}
