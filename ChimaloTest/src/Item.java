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
	//private Time inzendTijd;
	private String status;
	
	public Item(String titel, String auteur , Date inzendDatum, String text, String status, int id, BufferedImage img ){
		this.titel=titel;
		this.auteur= auteur;
		this.inzendDatum = inzendDatum;
		this.text=text;
		this.status = status;
		this.id=id;
		setFoto(img);
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
