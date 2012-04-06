
public class Erfgoed {

	
	private String naam;
	private String locatie;
	private String type;
	private String link;
	private String geschiedenis;
	private String info;
	
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
	public Erfgoed(String n , String l, String t,String link, String h, String i){
		setNaam(n);
		setLocatie(l);
		setType(t);
		setLink(link);
		setGeschiedenis(h);
		setInfo(i);
	}
	@Override
	public String toString(){
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
