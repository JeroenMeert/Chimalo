import java.awt.image.BufferedImage;
import java.sql.Date;
import java.sql.Time;


public class Item {

	private BufferedImage foto;
	private int id = -1;
	private String titel;
	private String text;
	private String auteur; 
	private Date inzendDatum;
	private String erfgoed;
	private String link;
	private String historiek;
	private String gemeente;
	private String locatie;
	private String status;
	private String extentie;
	
	public Item(BufferedImage foto, int id, String titel, String text,
			String auteur, Date inzendDatum, String erfgoed, String link,
			String historiek, String gemeente, String locatie, String status,
			String extentie) {
		super();
		this.foto = foto;
		this.id = id;
		this.titel = titel;
		this.text = text;
		this.auteur = auteur;
		this.inzendDatum = inzendDatum;
		this.erfgoed = erfgoed;
		this.link = link;
		this.historiek = historiek;
		this.gemeente = gemeente;
		this.locatie = locatie;
		this.status = status;
		this.extentie = extentie;
	}
	
	public Item()
	{
		
	}
	

	public Item(BufferedImage foto, String titel, String text, String auteur,
			Date inzendDatum, String erfgoed, String link, String historiek,
			String gemeente, String locatie, String status, String extentie) {
		super();
		this.foto = foto;
		this.titel = titel;
		this.text = text;
		this.auteur = auteur;
		this.inzendDatum = inzendDatum;
		this.erfgoed = erfgoed;
		this.link = link;
		this.historiek = historiek;
		this.gemeente = gemeente;
		this.locatie = locatie;
		this.status = status;
		this.extentie = extentie;
	}



	public String getExtentie() {
		return extentie;
	}


	public void setExtentie(String extentie) {
		this.extentie = extentie;
	}



	public String getErfgoed() {
		return erfgoed;
	}



	public void setErfgoed(String erfgoed) {
		this.erfgoed = erfgoed;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitel(){
		return titel;
	}
	public Date getInzendDatum(){
		return inzendDatum;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAuteur() {
		return auteur;
	}

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public void setInzendDatum(Date inzendDatum) {
		this.inzendDatum = inzendDatum;
	}

	public int getId() {
		return id;
	}

	public BufferedImage getFoto() {
		return foto;
	}

	public void setFoto(BufferedImage foto) {
		this.foto = foto;
	}

	public String getHistoriek() {
		return historiek;
	}

	public String getGemeente() {
		return gemeente;
	}

	public String getLocatie() {
		return locatie;
	}

	public void setHistoriek(String historiek) {
		this.historiek = historiek;
	}

	public void setGemeente(String gemeente) {
		this.gemeente = gemeente;
	}

	public void setLocatie(String locatie) {
		this.locatie = locatie;
	}



	@Override
	public String toString() {
		return "Item [foto=" + foto + ", id=" + id + ", titel=" + titel
				+ ", text=" + text + ", auteur=" + auteur + ", inzendDatum="
				+ inzendDatum + ", erfgoed=" + erfgoed + ", link=" + link
				+ ", historiek=" + historiek + ", gemeente=" + gemeente
				+ ", locatie=" + locatie + ", status=" + status + ", extentie="
				+ extentie + "]";
	}
	
	
}
