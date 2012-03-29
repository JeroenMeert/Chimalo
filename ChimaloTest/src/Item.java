import java.awt.image.BufferedImage;
import java.sql.Date;
import java.sql.Time;


public class Item {

	private BufferedImage foto;
	private int id;
	private String titel;
	private String text;
	private String auteur; 
	private Date inzendDatum;
	private String erfgoed;
	private String geschiedenis;
	private String link;
	//private Time inzendTijd;
	private String status;
	
	public Item(String titel, String auteur , Date inzendDatum, String text, String status, int id, BufferedImage img, String erfgoed, String history, String link ){
		this.geschiedenis=history;
		this.erfgoed=erfgoed;
		this.link=link;
		this.titel=titel;
		this.auteur= auteur;
		this.inzendDatum = inzendDatum;
		this.text=text;
		this.status = status;
		this.id=id;
		setFoto(img);
	}
	public Item(String titel, String auteur , Date inzendDatum, String text, String status, BufferedImage img, String erfgoed, String history, String link ){
		this.geschiedenis=history;
		this.erfgoed=erfgoed;
		this.link=link;
		this.titel=titel;
		this.auteur= auteur;
		this.inzendDatum = inzendDatum;
		this.text=text;
		this.status = status;
		setFoto(img);
	}
	
	public String getErfgoed() {
		return erfgoed;
	}

	public void setErfgoed(String erfgoed) {
		this.erfgoed = erfgoed;
	}

	public String getGeschiedenis() {
		return geschiedenis;
	}

	public void setGeschiedenis(String geschiedenis) {
		this.geschiedenis = geschiedenis;
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


}
