
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class DataBankConnection {
	private BufferedImage image;
	private Model m;
	private Connection conn;
	
	public DataBankConnection(Model m) {
		this.m = m;
	}
	public DataBankConnection() {
	}


	public Gebruiker checkAccount (String name, String pass) {
		Gebruiker result = null;
        try {
        	
        	PreparedStatement zoekLogin = m.getDbconnectie().prepareStatement("SELECT * FROM Gebruiker WHERE Gebruikersnaam = ? AND Wachtwoord = ? AND Actief = ?");

        	zoekLogin.setString(1, name);
        	zoekLogin.setString(2, pass);
        	zoekLogin.setBoolean(3, true);
        	
        	ResultSet rs = zoekLogin.executeQuery();
            if (rs.next())
            {
            	String status = rs.getString("Type");
            	if((status.equals("Beheerder")) || (status.equals("Administrator")))
            	{
            		result = new Gebruiker(rs.getString("Gebruikersnaam"), rs.getString("Naam"),rs.getString("Wachtwoord"),status, rs.getInt("GebruikerNr"), rs.getBoolean("Actief"), rs.getString("Emailadres"));
            	}
            }
            
            
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
      
        return result;
    }
	public boolean hasDuplicates(String Gebruikersnaam) {
		try {
    		int count = 0;
    		PreparedStatement haalItemsOp = m.getDbconnectie().prepareStatement("SELECT COUNT(*) AS [rowcount] FROM Gebruiker WHERE Gebruikersnaam =? ");
    		haalItemsOp.setString(1,Gebruikersnaam);
    		ResultSet r = haalItemsOp.executeQuery();
    		r.next();
    		count = r.getInt("rowcount") ;
    		if(count > 0)
    			return true;
    		else
    			return false;
    		
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
		return false;
	}
	
	
	public boolean voegToe(String Gebruikersnaam, String Naam, String pass, String type, String Emailadres){
		boolean success = false;
		if(!hasDuplicates(Gebruikersnaam)) {
        try {
        	PreparedStatement voegBeheerderToe = m.getDbconnectie().prepareStatement("INSERT INTO Gebruiker (Gebruikersnaam, Naam, Wachtwoord, Type, Actief, Emailadres) VALUES (?,?,?,?,?,?)");
        	voegBeheerderToe.setString(1, Gebruikersnaam);
        	voegBeheerderToe.setString(2, Naam);
        	voegBeheerderToe.setString(3, pass);
        	voegBeheerderToe.setString(4, type);
        	voegBeheerderToe.setBoolean(5, true);
        	voegBeheerderToe.setString(6, Emailadres);

           voegBeheerderToe.executeUpdate();
           success = true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
		}
		else {
			JOptionPane.showMessageDialog(null, "Een gebruiker met deze Gebruikersnaam bestaat al, kies een andere!");
		}
		return success;
	}
	public boolean updateGebruiker(int nr, String Gebruikersnaam, String Naam, String pass, String type, String Emailadres){
		boolean success = false;
        try {
        	if(!pass.equals(""))
        	{
        		PreparedStatement voegBeheerderToe = m.getDbconnectie().prepareStatement("UPDATE Gebruiker SET Gebruikersnaam = ? ,Naam = ?,Wachtwoord = ?, Type = ?, Actief = ?, Emailadres = ? WHERE gebruikerNr = ?");
        		voegBeheerderToe.setString(1, Gebruikersnaam);
        		voegBeheerderToe.setString(2, Naam);
        		voegBeheerderToe.setString(3, pass);
        		voegBeheerderToe.setString(4, type);
        		voegBeheerderToe.setBoolean(5, true);
        		voegBeheerderToe.setString(6, Emailadres);
        		voegBeheerderToe.setInt(7, nr);
        		voegBeheerderToe.executeUpdate();
        	}
        	else {
        		PreparedStatement voegBeheerderToe = m.getDbconnectie().prepareStatement("UPDATE Gebruiker SET Gebruikersnaam = ? ,Naam = ?, Type = ?, Actief = ?, Emailadres= ? WHERE gebruikerNr = ?");
            	voegBeheerderToe.setString(1, Gebruikersnaam);
            	voegBeheerderToe.setString(2, Naam);
            	voegBeheerderToe.setString(3, type);
            	voegBeheerderToe.setBoolean(4, true);
            	voegBeheerderToe.setString(5, Emailadres);
            	voegBeheerderToe.setInt(6, nr);
            	voegBeheerderToe.executeUpdate();
        	}
        	success = true;
           
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
        }
        return success;
	}
	public boolean overschrijfItem(Item i){
		boolean success = false;
        try {
        	Calendar calendar = Calendar.getInstance();
          	 
       	 	calendar.add(Calendar.HOUR, -7);// server time van somee.com is 7 uur eerder dan onze tijdzone, dit is de tijdelijke fix ervoor

       	 	java.util.Date now = calendar.getTime();
       	 
       	 	java.sql.Timestamp tijd = new java.sql.Timestamp(now.getTime());
       	 	
        	BufferedImage duke = i.getFoto();
        	// Vervolgens maken we een nieuwe blob aan die we zullen vullen met de afbeelding.
            Blob dukeBlob = m.getDbconnectie().createBlob();
            
            // We vragen aan de blob een OutputStream waarmee we bytes naar de blob kunnen schrijven.
            // Het argument 1 geeft aan dat we de blob willen schrijven vanaf de eerste byte (en niet ergens halverwege).
            OutputStream dukeBlobStream = dukeBlob.setBinaryStream(1);
            
            // We maken gebruik van de methode ImageIO.write() om de afbeelding weg te schrijven naar de OutputStream.
            // Het tweede argument van deze methode geeft aan om welk type afbeelding het gaat.
            // De mogelijke waarden voor dit argument kan je opvragen met de methode ImageIO.getWriterFormatNames().
            // Voorbeelden zijn "jpeg", "png, "gif" en "bmp".
            ImageIO.write(duke, i.getExtentie(), dukeBlobStream);
        	PreparedStatement overschrijf = m.getDbconnectie().prepareStatement("UPDATE Object SET Naam = ? , Tijdstip = ?, Tekst = ?, Foto = ?, GebruikerNr = ?, Status = ?, ErfgoedNr = ?, Link = ?, Extensie = ?, Type= ? WHERE ObjectNr = ?");
        	overschrijf.setString(1, i.getTitel());
        	overschrijf.setTimestamp(2,tijd);
        	overschrijf.setString(3,i.getText());
        	overschrijf.setBlob(4, dukeBlob);
        	overschrijf.setInt(5,i.getAuteur().getGebruikersnummer());
        	overschrijf.setString(6,i.getStatus());
        	overschrijf.setInt(7, i.getErfgoed().getErfgoedNr());
        	overschrijf.setString(8, i.getLink());
        	overschrijf.setString(9, i.getExtentie());
        	overschrijf.setInt(11, i.getId());
        	overschrijf.setString(10, i.getType());
        	overschrijf.executeUpdate();
        	success = true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        } catch (IOException ex) {
			// TODO Auto-generated catch block
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
		}
        finally {
        }
        return success;
	}
	public boolean overschrijfItemZonderAfbeelding(Item i){
		boolean success = false;
        try {
        	Calendar calendar = Calendar.getInstance();
          	 
       	 	calendar.add(Calendar.HOUR, -7);// server time van somee.com is 7 uur eerder dan onze tijdzone, dit is de tijdelijke fix ervoor

       	 	java.util.Date now = calendar.getTime();
       	 
       	 	java.sql.Timestamp tijd = new java.sql.Timestamp(now.getTime());
        	PreparedStatement overschrijf = m.getDbconnectie().prepareStatement("UPDATE Object SET Naam = ? , Tijdstip = ?, Tekst = ?, GebruikerNr = ?, Status = ?, ErfgoedNr = ?, Link = ?, Extensie = ?, Type = ? WHERE ObjectNr = ?");
        	overschrijf.setString(1, i.getTitel());
        	overschrijf.setTimestamp(2,tijd);
        	overschrijf.setString(3,i.getText());
        	overschrijf.setInt(4,i.getAuteur().getGebruikersnummer());
        	overschrijf.setString(5,i.getStatus());
        	overschrijf.setInt(6, i.getErfgoed().getErfgoedNr());
        	overschrijf.setString(7, i.getLink());
        	overschrijf.setString(8, i.getExtentie());
        	overschrijf.setInt(10, i.getId());
        	overschrijf.setString(9, i.getType());
        	overschrijf.executeUpdate();
        	success = true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        }
        return success;
	}
	public ArrayList<Item> leesItems(){
		System.out.println("---Lees items---");
		ArrayList<Item> terugTeGevenItems= new ArrayList<Item>();
		ResultSet rs = null;
        try {
        	PreparedStatement haalItemsOp = m.getDbconnectie().prepareStatement("SELECT GebruikerNr, Naam, Tijdstip , Tekst, Status, ObjectNr, ErfgoedNr, Link, Extensie, Type FROM Object");
        	rs = haalItemsOp.executeQuery();
        	while (rs.next()){
        		int in = rs.getInt("ObjectNr");
        		String titel = rs.getString(2);
        		String tekst = rs.getString("Tekst");
        		Gebruiker gebruiker = haalGebruikerOp(rs.getInt("GebruikerNr"));
        		Erfgoed erfgoed = getErfgoed(rs.getInt("ErfgoedNr"));
        		String link = rs.getString("Link");
        		String status = rs.getString("Status");
        		BufferedImage foto = haalFotoOp(in);
        		String Extensie = rs.getString("Extensie");
        		String type = rs.getString("Type");
        		Item i = new Item(foto,in,titel,tekst,gebruiker,rs.getDate("Tijdstip"),erfgoed,link, status, Extensie, type);
        		terugTeGevenItems.add(i);
        	}
        	


        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        	if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        return terugTeGevenItems;
	}
	public Gebruiker haalGebruikerOp(int userNr){
		Gebruiker i = null;
        try {
        	PreparedStatement haalGebruikerOp = m.getDbconnectie().prepareStatement("SELECT * FROM Gebruiker WHERE GebruikerNr =? ");
        	haalGebruikerOp.setInt(1, userNr);
        	ResultSet rs = haalGebruikerOp.executeQuery();
        	if (rs.next()){
        		i = new Gebruiker(rs.getString("Gebruikersnaam"), rs.getString("Naam"),rs.getString("Wachtwoord"),rs.getString("Type"), rs.getInt("GebruikerNr"), rs.getBoolean("Actief"), rs.getString("Emailadres"));
        	}
        	else
        	{
        		JOptionPane.showMessageDialog(null, "Geen gebruiker gevonden!");
        	}
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
		return i;
	}
	
	public Gebruiker getGebruiker(String gebruikersnaam)
	{
		Gebruiker result=null;
        try {
            Statement stat = m.getDbconnectie().createStatement();
        	PreparedStatement haalGebruikerOp = m.getDbconnectie().prepareStatement("SELECT * FROM Gebruiker WHERE Gebruikersnaam =? ");
        	haalGebruikerOp.setString(1, gebruikersnaam);
        	ResultSet rs = haalGebruikerOp.executeQuery();
        	if (rs.next()){
        		 result = new Gebruiker(rs.getString("Gebruikersnaam"), rs.getString("Naam"), rs.getString("Wachtwoord"), rs.getString("Type"), rs.getInt("GebruikerNr"), rs.getBoolean("Actief"), rs.getString("Emailadres"));
        	}
        	
        	


        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
		return result;
	}
	public boolean wijzigStatus(String status, Item i){
		boolean s = false;
		System.out.println("Wijzigen van status naar " + status + " Item: " + i.getTitel());
        try {
        	Calendar calendar = Calendar.getInstance();
       	 
       	 	calendar.add(Calendar.HOUR, -7);// server time van somee.com is 7 uur eerder dan onze tijdzone, dit is de tijdelijke fix ervoor

       	 	java.util.Date now = calendar.getTime();
       	 
       	 	java.sql.Timestamp tijd = new java.sql.Timestamp(now.getTime());
            //Statement stat = m.getDbconnectie().createStatement();
        	PreparedStatement wijzigStatus = m.getDbconnectie().prepareStatement("UPDATE Object SET Status=?, Tijdstip=? WHERE ObjectNr = ?");
        	wijzigStatus.setString(1 ,status);
        	wijzigStatus.setTimestamp(2, tijd);
        	wijzigStatus.setInt(3, i.getId());
        	wijzigStatus.executeUpdate();
        	s = true;
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
        }
        return s;
	}

	
	public BufferedImage haalFotoOp(int userID){
		System.out.println("Lees foto " + userID);
		JFrame f = new JFrame();
        try {     
		    	PreparedStatement readImage = m.getDbconnectie().prepareStatement("SELECT Foto FROM Object WHERE ObjectNr=?");
		    	readImage.setInt(1, userID);
		    	ResultSet rs = readImage.executeQuery();
		    	
		    	if (rs.next()) {
                    // Om een blob uit te lezen, gebruiken we de methode getBlob().
                    Blob imageBlob = rs.getBlob("Foto");
                    if(imageBlob != null)
                    {
                    // We vragen aan de blob een InputStream waarmee we de bytes opnieuw kunnen uitlezen.
                    InputStream imageBlobStream = imageBlob.getBinaryStream();
                    
                    // De methode ImageIO.read() kan deze InputStream gebruiken om de afbeelding uit te lezen.
                    // De variabele image is nu gevuld met de afbeelding uit duke.png.
                    image = ImageIO.read(imageBlobStream);
                    }
                    else {
                    	image = (BufferedImage)f.createImage(25,25);
                    }
		    	}
		    }catch(Exception ex ){
		    	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
	                ex.printStackTrace();
		    }
		return image;
	}
	
	public ArrayList<Gebruiker> getGebruikers() {
		System.out.println("Lees gebruikers");
		ArrayList<Gebruiker> gebruikers= new ArrayList<Gebruiker>();
        try {
        	PreparedStatement geb = m.getDbconnectie().prepareStatement("SELECT * FROM Gebruiker");
        	ResultSet rs = geb.executeQuery();
        	
        	while (rs.next()){
        		String type = rs.getString("Type");
        		if(type.equals("Beheerder") || type.equals("Administrator"))
        		{
        		Gebruiker i = new Gebruiker(rs.getString("Gebruikersnaam"), rs.getString("Naam"),rs.getString("Wachtwoord"),type, rs.getInt("GebruikerNr"), rs.getBoolean("Actief"), rs.getString("Emailadres"));
        		gebruikers.add(i);
        		}
        	}
        	


        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        return gebruikers;
	}
	
	public boolean verwijderGebruiker(int nr)
	{
		ResultSet rs = null;
        try {
        	Statement stmt = m.getDbconnectie().createStatement(
                    ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_UPDATABLE);
                
        	rs = stmt.executeQuery("SELECT * from Gebruiker");
        	while (rs.next()) {
                int id = rs.getInt("GebruikerNr");
                if (id == nr) {
                  rs.deleteRow();
                }
              }
            
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        	return false;
        }
        return true;
	}
	
	public boolean controleOpVerwijderen(int nr)
	{
		ResultSet rs = null;
        try {   
        	Statement stmt = m.getDbconnectie().createStatement(
                    (ResultSet.TYPE_FORWARD_ONLY),
                    ResultSet.CONCUR_UPDATABLE);
        	rs = stmt.executeQuery("SELECT * from Object");
        	boolean gevonden = false;
        	while (rs.next() && !gevonden) {
                int id = rs.getInt("GebruikerNr");
                if (id == nr) {
                  gevonden = true;
                  int result = JOptionPane.showConfirmDialog(null, "De gebruiker heeft berichten in de database. Om de gebruiker te deleten, moet je al deze berichten verwijderen. \n Er is ook een mogelijkheid om het account uit te schakelen om zo de berichten te behouden. \n\n Om alle berichten en de gebruiker te verwijderen druk op Ja, Om uit te schakelen druk op Nee");
                  if(result == JOptionPane.OK_OPTION)
                  {
                	  gebruikersBerichtenVerwijderen(nr);
                	  m.notifyChangeListeners();
                  }
                  else if(result == JOptionPane.NO_OPTION)
                  {
                	  maakGebruikerInactief(nr);
                	  m.notifyChangeListeners();
                  }
                }
              }
        	if(!gevonden) {
        		verwijderGebruiker(nr);
        	}
            
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        	return false;
            }
        return true;
	}
	
	public void gebruikersBerichtenVerwijderen(int nr)
	{
		ResultSet rs = null;
        try {     
        	Statement stmt = m.getDbconnectie().createStatement(
                    (ResultSet.TYPE_FORWARD_ONLY),
                    ResultSet.CONCUR_UPDATABLE);
        	rs = stmt.executeQuery("SELECT * from Object");
        	while (rs.next()) {
                int id = rs.getInt("GebruikerNr");
                if (id == nr) {
                 rs.deleteRow();
                }
        	}
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
	}
	
	public void maakGebruikerInactief(int nr)
	{
        try {

            //Statement stat = m.getDbconnectie().createStatement();
        	PreparedStatement wijzigStatus = m.getDbconnectie().prepareStatement("UPDATE Gebruiker SET Actief = ? WHERE GebruikerNr = ?");
        	wijzigStatus.setBoolean(1, false);
        	wijzigStatus.setInt(2 ,nr);
        	wijzigStatus.executeUpdate();
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
	}
	
	public void maakGebruikerActief(int nr)
	{
        try {
            //Statement stat = m.getDbconnectie().createStatement();
        	PreparedStatement wijzigStatus = m.getDbconnectie().prepareStatement("UPDATE Gebruiker SET Actief = ? WHERE GebruikerNr = ?");
        	wijzigStatus.setBoolean(1, true);
        	wijzigStatus.setInt(2 ,nr);
        	wijzigStatus.executeUpdate();
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
	}
	public String md5(String md5) {
	   try {
	        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
	        byte[] array = md.digest(md5.getBytes());
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < array.length; ++i) {
	        	sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	        }
	        return sb.toString();
		    } catch (java.security.NoSuchAlgorithmException e) {
	    }
		    return null;
	}
	
	
	public boolean setMailText(String soort, String txt)
	{
		boolean success = false;
        try {
            //Statement stat = m.getDbconnectie().createStatement();
        	PreparedStatement wijzig = m.getDbconnectie().prepareStatement("UPDATE Standaardtekst SET Tekst = ? WHERE Soort = ?");
        	
        	wijzig.setString(1, txt);
        	wijzig.setString(2, soort);
        	wijzig.executeUpdate();
        	success = true;
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        return success;
	}
	
	public String getMailText(String soort)
	{
		String result = null;
        try {
		    	PreparedStatement read = m.getDbconnectie().prepareStatement("SELECT Tekst FROM Standaardtekst WHERE Soort=?");
		    	read.setString(1, soort);
		    	ResultSet rs = read.executeQuery();
		    	
             if (rs.next()) {
                 result = rs.getString("Tekst");
             } else {
                 System.out.println("Standaardtekst niet gevonden in databank.");
             }
		    }catch(Exception ex ){
		    	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
		    }
        return result;
	}
public ArrayList<Erfgoed> getErfGoeden() {
	System.out.println("---Lees erfgoeden---");
     try { 	
     	ArrayList<Erfgoed> result=new ArrayList<Erfgoed>();
     	PreparedStatement erfgoed = m.getDbconnectie().prepareStatement("SELECT * FROM Erfgoed");
     	ResultSet rs= erfgoed.executeQuery();
     	while (rs.next()){
     		Erfgoed e = new Erfgoed(rs.getInt("ErfgoedNr"), rs.getString("Naam"), rs.getString("Locatie"),rs.getString("Type"),rs.getString("Link"),rs.getString("Geschiedenis"),rs.getString("Info"), rs.getString("Kenmerken"), rs.getString("Statuut"), rs.getString("Gemeente"));
     		result.add(e);
     		
     	}
     	return result;
     } catch (SQLException ex) {
    	 JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
    	 for (Throwable t : ex) {
             t.printStackTrace();
         }
     }
     return new ArrayList<Erfgoed>();
}
public boolean schrijfNieuwItem(Item i) {
	boolean s = false;
     try {
    	 
    	 Calendar calendar = Calendar.getInstance();
    	 
    	 calendar.add(Calendar.HOUR, -7);// server time van somee.com is 7 uur eerder dan onze tijdzone, dit is de tijdelijke fix ervoor

    	 java.util.Date now = calendar.getTime();
    	 
    	 java.sql.Timestamp tijd = new java.sql.Timestamp(now.getTime());
    	 
    	BufferedImage duke = i.getFoto();
     	// Vervolgens maken we een nieuwe blob aan die we zullen vullen met de afbeelding.
         Blob dukeBlob = m.getDbconnectie().createBlob();
         
         // We vragen aan de blob een OutputStream waarmee we bytes naar de blob kunnen schrijven.
         // Het argument 1 geeft aan dat we de blob willen schrijven vanaf de eerste byte (en niet ergens halverwege).
         OutputStream dukeBlobStream = dukeBlob.setBinaryStream(1);
         
         // We maken gebruik van de methode ImageIO.write() om de afbeelding weg te schrijven naar de OutputStream.
         // Het tweede argument van deze methode geeft aan om welk type afbeelding het gaat.
         // De mogelijke waarden voor dit argument kan je opvragen met de methode ImageIO.getWriterFormatNames().
         // Voorbeelden zijn "jpeg", "png, "gif" en "bmp".
         ImageIO.write(duke, i.getExtentie(), dukeBlobStream);
     	PreparedStatement newItem = m.getDbconnectie().prepareStatement("INSERT INTO Object (Naam, Tijdstip, Tekst, Foto,GebruikerNr,Status,ErfgoedNr,Link, Extensie, Type) VALUES (?,?,?,?,?,?,?,?,?,?)");   	
     	newItem.setString(1,i.getTitel());
     	newItem.setTimestamp(2,tijd);
     	newItem.setString(3,i.getText());
     	newItem.setBlob(4, dukeBlob);
     	newItem.setInt(5, i.getAuteur().getGebruikersnummer());
     	newItem.setString(6,"Goedgekeurd");
     	newItem.setInt(7, i.getErfgoed().getErfgoedNr());
     	newItem.setString(8, i.getLink());
     	newItem.setString(9, i.getExtentie());
     	newItem.setString(10, i.getType());
     	newItem.executeUpdate();
     	s = true;
 
     } catch (SQLException ex) {
    	 JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
    	 for (Throwable t : ex) {
             t.printStackTrace();
         }
     } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     return s;
    
}

public boolean schrijfNieuwItemZonderAfbeelding(Item i) {
	boolean s = false;
    try {
    	// create a java calendar instance
   	 	Calendar calendar = Calendar.getInstance();

   	 	// get a java.util.Date from the calendar instance.
   	 	// this date will represent the current instant, or "now".
   	 	java.util.Date now = calendar.getTime();

   	 	// a java current time (now) instance
   	 	java.sql.Timestamp tijd = new java.sql.Timestamp(now.getTime());
    	PreparedStatement newItem = m.getDbconnectie().prepareStatement("INSERT INTO Object (Naam, Tijdstip, Tekst, GebruikerNr,Status,ErfgoedNr,Link, Extensie, Type) VALUES (?,?,?,?,?,?,?,?,?)");
    	newItem.setString(1,i.getTitel());
    	newItem.setTimestamp(2,tijd);
    	newItem.setString(3,i.getText());
    	newItem.setInt(4, i.getAuteur().getGebruikersnummer());
    	newItem.setString(5,"Goedgekeurd");
    	newItem.setInt(6, i.getErfgoed().getErfgoedNr());
    	newItem.setString(7, i.getLink());
    	newItem.setString(8, i.getExtentie());
    	newItem.setString(9, i.getType());
    	newItem.executeUpdate();
    	s = true;

    } catch (SQLException ex) {
   	 JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
   	 for (Throwable t : ex) {
            t.printStackTrace();
        }
    }
   return s;
}

public boolean magErfgoedVerwijderdWorden(Erfgoed er) {
	 Boolean b = true;
     try { 	    
     	PreparedStatement magweg = m.getDbconnectie().prepareStatement("SELECT * FROM Object WHERE ErfgoedNr = ?");
     	
     	magweg.setInt(1,er.getErfgoedNr());
     	
     	ResultSet rs =magweg.executeQuery();
     	if (rs.next()){
     		b=false;
     	}
 
     } catch (SQLException ex) {
    	 JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
    	 for (Throwable t : ex) {
             t.printStackTrace();
         }
     }
     return b;
}
public boolean removeErfgoed(Erfgoed er) {
	// TODO Auto-generated method stub
	boolean s = false;
    try {
    	PreparedStatement weg = m.getDbconnectie().prepareStatement("DELETE FROM Erfgoed WHERE ErfgoedNr =?");
    	
    	weg.setInt(1,er.getErfgoedNr());
    	weg.executeUpdate();
    	s = true;
    } catch (SQLException ex) {
    	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden bij het verwijderen van het erfgoed\nFoutmelding: " + ex.toString());
    	for (Throwable t : ex) {
            t.printStackTrace();
        }
    }
    return s;
}
public boolean schrijfErfgoed(Erfgoed g) {
	boolean s= false;
     try {   
     	PreparedStatement newItem = m.getDbconnectie().prepareStatement("UPDATE Erfgoed SET Naam = ? , Locatie = ?, Type = ?, Link = ?, Geschiedenis = ?, Info = ?, Kenmerken = ?, Statuut = ?, Gemeente = ? WHERE ErfgoedNr = ?");
     	newItem.setString(1,g.getNaam());
    	newItem.setString(2,g.getLocatie());
    	newItem.setString(3,g.getType());
    	newItem.setString(4, g.getLink());
    	newItem.setString(5,g.getGeschiedenis());
    	newItem.setString(6, g.getInfo());
    	newItem.setString(7, g.getKenmerken());
    	newItem.setString(8, g.getStatuut());
    	newItem.setString(9, g.getGemeente());
    	newItem.setInt(10, g.getErfgoedNr());
     	
     	
     	newItem.executeUpdate();
     	JOptionPane.showMessageDialog(null, "Het erfgoed is opgeslagen");
     	s=true;
     } catch (SQLException ex) {
    	 JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden bij het schrijven van het erfgoed\nFoutmelding: " + ex.toString());
    	 for (Throwable t : ex) {
             t.printStackTrace();
         }
     }
     return s;
}

public void sluitConnectie()
{
	try {
		if(conn != null)
		m.getDbconnectie().close();
	} catch (SQLException ex) {
		// TODO Auto-generated catch block
		JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden bij het sluiten van de connectie\nFoutmelding: " + ex.toString());
		for (Throwable t : ex) {
            t.printStackTrace();
        }
	}
}

public boolean schrijfNieuwErfgoed(Erfgoed g) {
	boolean s = false;
    try {
    	PreparedStatement newItem = m.getDbconnectie().prepareStatement("INSERT INTO Erfgoed (Naam, Locatie, Type, Link,Geschiedenis,Info,Kenmerken, Statuut, Gemeente) VALUES (?,?,?,?,?,?,?,?,?)");
    	newItem.setString(1,g.getNaam());
    	newItem.setString(2,g.getLocatie());
    	newItem.setString(3,g.getType());
    	newItem.setString(4, g.getLink());
    	newItem.setString(5,g.getGeschiedenis());
    	newItem.setString(6, g.getInfo());
    	newItem.setString(7, g.getKenmerken());
    	newItem.setString(8, g.getStatuut());
    	newItem.setString(9, g.getGemeente());
    	
    	newItem.executeUpdate();
    	JOptionPane.showMessageDialog(null, "Het erfgoed is opgeslagen");
    	s=true;

    } catch (SQLException ex) {
   	 JOptionPane.showMessageDialog(null, "Er is een fout opgetreden bij het opslaan van een nieuw erfgoed. Controlleer de naam van het erfgoed en probeer opnieuw.\n Foutmelding: " +ex.toString());
   	 for (Throwable t : ex) {
            t.printStackTrace();
        }
    }
    return s;
   
}

public Erfgoed getErfgoed(int erfgoednr) {
	System.out.println("Lees erfgoednr " +erfgoednr);
	Erfgoed i = null;
    try {
    	PreparedStatement haalGebruikerOp = m.getDbconnectie().prepareStatement("SELECT * FROM Erfgoed WHERE ErfgoedNr =? ");
    	haalGebruikerOp.setInt(1, erfgoednr);
    	ResultSet rs = haalGebruikerOp.executeQuery();
    	if (rs.next()){
    		i = new Erfgoed(rs.getInt("ErfgoedNr"), rs.getString("Naam"), rs.getString("Locatie"),rs.getString("Type"),rs.getString("Link"),rs.getString("Geschiedenis"),rs.getString("Info"), rs.getString("Kenmerken"), rs.getString("Statuut"), rs.getString("Gemeente"));
    	}
    	else
    	{
    		JOptionPane.showMessageDialog(null, "Geen Erfgoed gevonden!");
    	}
    } catch (SQLException ex) {
    	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
    	for (Throwable t : ex) {
            t.printStackTrace();
        }
    }
	return i;
}

public int countItems() {
	System.out.println("Bezig met tellen van items");
	int teller = 0;
    try {
    	PreparedStatement haalGebruikerOp = m.getDbconnectie().prepareStatement("SELECT COUNT(*) AS [teller] FROM Object");
    	ResultSet rs = haalGebruikerOp.executeQuery();
    	if (rs.next()){
    		teller = rs.getInt("teller");
    	}
    } catch (SQLException ex) {
    	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
    	for (Throwable t : ex) {
            t.printStackTrace();
        }
    }
	return teller;
}

public Item aanvullenItem(Item i)
{
	System.out.println("Aanvullen van item " + i.getTitel());
    try {
    	PreparedStatement h = m.getDbconnectie().prepareStatement("SELECT * FROM Object WHERE Naam =? AND Tekst=? AND GebruikerNr=? AND ErfgoedNr= ? AND Status= ? AND Link=? AND Type=?");
    	h.setString(1, i.getTitel());
    	h.setString(2, i.getText());
    	h.setInt(3, i.getAuteur().getGebruikersnummer());
    	h.setInt(4, i.getErfgoed().getErfgoedNr());
    	h.setString(5, "Goedgekeurd");
    	h.setString(6, i.getLink());
    	h.setString(7, i.getType());
    	ResultSet rs = h.executeQuery();
    	if (rs.next()){
    		i.setId(rs.getInt("ObjectNr"));
    	}
    	else
    	{
    		JOptionPane.showMessageDialog(null, "Geen Item gevonden!");
    	}
    } catch (SQLException ex) {
    	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
    	for (Throwable t : ex) {
            t.printStackTrace();
        }
    }
	return i;
}

public Erfgoed aanvullenErfgoed(Erfgoed i)
{
	System.out.println("Aanvullen van Erfgoed " + i.getNaam());
    try {
    	PreparedStatement h = m.getDbconnectie().prepareStatement("SELECT * FROM Erfgoed WHERE Naam =? AND Locatie=? AND Kenmerken=? AND Geschiedenis=? AND Info= ? AND Link= ? AND Gemeente=? AND Type=? AND Statuut=?");
    	h.setString(1, i.getNaam());
    	h.setString(2, i.getLocatie());
    	h.setString(3, i.getKenmerken());
    	h.setString(4, i.getGeschiedenis());
    	h.setString(5, i.getInfo());
    	h.setString(6, i.getLink());
    	h.setString(7, i.getGemeente());
    	h.setString(8, i.getType());
    	h.setString(9, i.getStatuut());
    	
    	ResultSet rs = h.executeQuery();
    	if (rs.next()){
    		i.setErfgoedNr(rs.getInt("ErfgoedNr"));
    	}
    	else
    	{
    		JOptionPane.showMessageDialog(null, "Geen Erfgoed gevonden!");
    	}
    } catch (SQLException ex) {
    	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
    	for (Throwable t : ex) {
            t.printStackTrace();
        }
    }
	return i;
}

public boolean verwijder(Item i)
{
	System.out.println("Verwijderen van item met status " + i.getStatus() + " en id "+ i.getId());
	ResultSet rs = null;
    try {     
    	PreparedStatement h = m.getDbconnectie().prepareStatement("Delete FROM Object WHERE ObjectNr=?");
    	h.setInt(1, i.getId());
    	int success = h.executeUpdate();
    	if(success != 0)
    	{
    		return true;
    	}
    } catch (SQLException ex) {
    	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden\nFoutmelding: " + ex.toString());
    	for (Throwable t : ex) {
            t.printStackTrace();
        }
    }
    return false;
}
}
	
