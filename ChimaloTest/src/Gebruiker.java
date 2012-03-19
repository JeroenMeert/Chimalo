
public class Gebruiker {
	private String naam;
	private String voornaam;
	private String wachtwoord;
	private String type;
	private int gebruikersnummer;
	private boolean actief;
	
	public Gebruiker(String naam, String voornaam, String wachtwoord,
			String type, int nummer, boolean actief) {
		super();
		this.naam = naam;
		this.voornaam = voornaam;
		setWachtwoord(wachtwoord);
		this.type = type;
		this.gebruikersnummer = nummer;
		this.actief = actief;
	}
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public String getVoornaam() {
		return voornaam;
	}
	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}
	public String getWachtwoord() {
		return wachtwoord;
	}
	public void setWachtwoord(String wachtwoord) {
		String code = "";
		for(int i = 0; i<wachtwoord.length(); i++)
		{
			code += "*";
		}
		this.wachtwoord = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getGebruikersnummer() {
		return gebruikersnummer;
	}
	public void setGebruikersnummer(int gebruikersnummer) {
		this.gebruikersnummer = gebruikersnummer;
	}
	public boolean isActief() {
		return actief;
	}
	public void setActief(boolean actief) {
		this.actief = actief;
	}
}