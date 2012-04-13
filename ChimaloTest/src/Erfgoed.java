
public class Erfgoed {

	private int erfgoedNr;
	private String naam;
	private String locatie;
	private String type;
	private String link;
	private String geschiedenis;
	private String info;
	private String kenmerken;
	private String statuut;
	private String gemeente;
	
	public int getErfgoedNr() {
		return erfgoedNr;
	}
	public void setErfgoedNr(int erfgoedNr) {
		this.erfgoedNr = erfgoedNr;
	}
	public String getGemeente() {
		return gemeente;
	}
	public void setGemeente(String gemeente) {
		this.gemeente = gemeente;
	}
	public String getStatuut() {
		return statuut;
	}
	public void setStatuut(String statuut) {
		this.statuut = statuut;
	}
	public String getKenmerken() {
		return kenmerken;
	}
	public void setKenmerken(String kenmerken) {
		this.kenmerken = kenmerken;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getGeschiedenis() {
		return geschiedenis;
	}
	public void setGeschiedenis(String geschiedenis) {
		this.geschiedenis = geschiedenis;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Erfgoed(int e, String n , String l, String t,String link, String h, String i, String k, String s, String g){
		setErfgoedNr(e);
		setNaam(n);
		setLocatie(l);
		setType(t);
		setLink(link);
		setGeschiedenis(h);
		setInfo(i);
		setKenmerken(k);
		setStatuut(s);
		setGemeente(g);
	}
	
	public Erfgoed(String n , String l, String t,String link, String h, String i, String k, String s, String g){
		setNaam(n);
		setLocatie(l);
		setType(t);
		setLink(link);
		setGeschiedenis(h);
		setInfo(i);
		setKenmerken(k);
		setStatuut(s);
		setGemeente(g);
	}
	
	public Erfgoed() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return naam;
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
