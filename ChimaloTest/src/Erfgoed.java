
public class Erfgoed {

	
	private String naam;
	private String locatie;
	private String type;
	
	public Erfgoed(String n , String l, String t){
		setNaam(n);
		setLocatie(l);
		setType(t);
	}

	

	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public String getLocatie() {
		return locatie;
	}
	public void setLocatie(String locatie) {
		this.locatie = locatie;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
