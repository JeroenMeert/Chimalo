import java.awt.image.BufferedImage;
import java.sql.Date;
import java.sql.Time;


public class Item {

	private BufferedImage foto;
	private int id = -1;
	private String titel;
	private String text;
	private Gebruiker auteur; 
	private Date inzendDatum;
	private Erfgoed erfgoed;
	private String link;
	private String status;
	private String extentie;
	private String type;
	
	public Item(BufferedImage foto, int id, String titel, String text,
			Gebruiker auteur, Date inzendDatum, Erfgoed erfgoed, String link,
			String status,
			String extentie, String type) {
		super();
		this.foto = foto;
		this.id = id;
		this.titel = titel;
		this.text = text;
		this.auteur = auteur;
		this.inzendDatum = inzendDatum;
		this.erfgoed = erfgoed;
		this.link = link;
		this.status = status;
		this.extentie = extentie;
		this.type = type;
	}

	public Item(BufferedImage foto, String titel, String text, Gebruiker auteur,
			Date inzendDatum, Erfgoed erfgoed, String link, String status, String extentie, String type) {
		super();
		this.foto = foto;
		this.titel = titel;
		this.text = text;
		this.auteur = auteur;
		this.inzendDatum = inzendDatum;
		this.erfgoed = erfgoed;
		this.link = link;
		this.status = status;
		this.extentie = extentie;
		this.type = type;
	}

	public Item()
	{
		
	}

	public String getExtentie() {
		return extentie;
	}


	public void setExtentie(String extentie) {
		this.extentie = extentie;
	}

	public Erfgoed getErfgoed() {
		return erfgoed;
	}

	public void setErfgoed(Erfgoed erfgoed) {
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

	

	public Gebruiker getAuteur() {
		return auteur;
	}

	public void setAuteur(Gebruiker auteur) {
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

	@Override
	public String toString() {
		return "Item [foto=" + foto + ", id=" + id + ", titel=" + titel
				+ ", text=" + text + ", auteur=" + auteur + ", inzendDatum="
				+ inzendDatum + ", erfgoed=" + erfgoed + ", link=" + link
				+ ", status=" + status + ", extentie=" + extentie + "]";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
