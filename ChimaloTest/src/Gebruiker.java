
public class Gebruiker {
	private String gebruikersnaam;
	private String naam;
	private String wachtwoord;
	private String type;
	private String email;
	private int gebruikersnummer;
	private boolean actief;
	
	public Gebruiker(String gebruikersnaam, String naam, String wachtwoord,
			String type, int nummer, boolean actief, String email) {
		super();
		this.gebruikersnaam = gebruikersnaam;
		this.naam = naam;
		setWachtwoord(wachtwoord);
		this.type = type;
		this.gebruikersnummer = nummer;
		this.actief = actief;
		this.email = email;
	}
	public String getGebruikersnaam() {
		return gebruikersnaam;
	}
	public void setGebruikersnaam(String naam) {
		this.gebruikersnaam = naam;
	}
	public String getNaam() {
		return naam;
	}
	public void setNaam(String voornaam) {
		this.naam = voornaam;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Gebruiker [gebruikersnaam=" + gebruikersnaam + ", naam=" + naam
				+ ", wachtwoord=" + wachtwoord + ", type=" + type + ", email="
				+ email + ", gebruikersnummer=" + gebruikersnummer
				+ ", actief=" + actief + "]";
	}
	
	
}