
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class DataBankConnection {
	private BufferedImage image;
	private Model m;
	private String type = "Administrator";
	private Connection conn;
	
	public DataBankConnection(Model m) {
		try {
			conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
		}
		this.m = m;
	}
	public DataBankConnection() {
		try {
			conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
			for (Throwable t : ex) {
                t.printStackTrace();
            }
		}
	}


	public Gebruiker checkAccount (String name, String pass) {
		Gebruiker result = null;
        try {
        	
            Statement stat = conn.createStatement();
        	PreparedStatement zoekLogin = conn.prepareStatement("SELECT * FROM Gebruiker WHERE Gebruikersnaam = ? AND Wachtwoord = ? AND Actief = ?");

        	zoekLogin.setString(1, name);
        	zoekLogin.setString(2, pass);
        	zoekLogin.setBoolean(3, true);
        	
        	ResultSet rs = zoekLogin.executeQuery();
            if (rs.next())
            {
            	String status = rs.getString("Type");
            	if(status.equals("Beheerder") || status.equals("Administrator"))
            	{
            		result = new Gebruiker(rs.getString("Gebruikersnaam"), rs.getString("Naam"),rs.getString("Wachtwoord"),type, rs.getInt("GebruikerNr"), rs.getBoolean("Actief"), rs.getString("Email"));
            		type = status;
            	}
            }
            
            
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
      
        return result;
    }
	public boolean hasDuplicates(String Gebruikersnaam) {
		try {
    		int count = 0;
    		PreparedStatement haalItemsOp = conn.prepareStatement("SELECT COUNT(*) AS rowcount FROM Gebruiker WHERE Gebruikersnaam =? ");
    		haalItemsOp.setString(1,Gebruikersnaam);
    		ResultSet r = haalItemsOp.executeQuery();
    		r.next();
    		count = r.getInt("rowcount") ;
    		if(count > 0)
    			return true;
    		else
    			return false;
    		
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
		return false;
	}
	
	
	public void voegToe(String Gebruikersnaam, String Naam, String pass, String type, String email){
		if(!hasDuplicates(Gebruikersnaam)) {
        try {
        	PreparedStatement voegBeheerderToe = conn.prepareStatement("INSERT INTO Gebruiker (Gebruikersnaam, Naam, Wachtwoord, Type, Actief, Email) VALUES (?,?,?,?,?,?)");
        	voegBeheerderToe.setString(1, Gebruikersnaam);
        	voegBeheerderToe.setString(2, Naam);
        	voegBeheerderToe.setString(3, pass);
        	voegBeheerderToe.setString(4, type);
        	voegBeheerderToe.setBoolean(5, true);
        	voegBeheerderToe.setString(6, email);

           voegBeheerderToe.executeUpdate();
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
		}
		else {
			JOptionPane.showMessageDialog(null, "Een gebruiker met deze Gebruikersnaam bestaat al, kies een andere!");
		}
	}
	public void updateGebruiker(int nr, String Gebruikersnaam, String Naam, String pass, String type, String email){
        try {
        	if(!pass.equals(""))
        	{
        		PreparedStatement voegBeheerderToe = conn.prepareStatement("UPDATE Gebruiker SET Gebruikersnaam = ? ,Naam = ?,Wachtwoord = ?, Type = ?, Actief = ?, Email = ? WHERE gebruikerNr = ?");
        		voegBeheerderToe.setString(1, Gebruikersnaam);
        		voegBeheerderToe.setString(2, Naam);
        		voegBeheerderToe.setString(3, pass);
        		voegBeheerderToe.setString(4, type);
        		voegBeheerderToe.setBoolean(5, true);
        		voegBeheerderToe.setString(6, email);
        		voegBeheerderToe.setInt(7, nr);
        		voegBeheerderToe.executeUpdate();
        	}
        	else {
        		PreparedStatement voegBeheerderToe = conn.prepareStatement("UPDATE Gebruiker SET Gebruikersnaam = ? ,Naam = ?, Type = ?, Actief = ?, Email= ? WHERE gebruikerNr = ?");
            	voegBeheerderToe.setString(1, Gebruikersnaam);
            	voegBeheerderToe.setString(2, Naam);
            	voegBeheerderToe.setString(3, type);
            	voegBeheerderToe.setBoolean(4, true);
            	voegBeheerderToe.setString(5, email);
            	voegBeheerderToe.setInt(6, nr);
            	voegBeheerderToe.executeUpdate();
        	}

           
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
        }
	}
	public void overschrijfItem(Item i){
        try {
        	BufferedImage im = i.getFoto();
        	ByteArrayOutputStream os = new ByteArrayOutputStream();
        	ImageIO.write(im, i.getExtentie(), os);
        	InputStream is = new ByteArrayInputStream(os.toByteArray());
        	PreparedStatement overschrijf = conn.prepareStatement("UPDATE Object SET Naam = ? , Tijdstip = ?, Tekst = ?, Foto = ?, GebruikerNr = ?, Status = ?, Erfgoed = ?, Link = ?, Extentie = ? WHERE ObjectNr = ?");
        	overschrijf.setString(1, i.getTitel());
        	overschrijf.setDate(2,i.getInzendDatum());
        	overschrijf.setString(3,i.getText());
        	overschrijf.setBinaryStream(4,is,is.available());
        	overschrijf.setInt(5,i.getAuteur().getGebruikersnummer());
        	overschrijf.setString(6,i.getStatus());
        	overschrijf.setString(7, i.getErfgoed());
        	overschrijf.setString(8, i.getLink());
        	overschrijf.setString(9, i.getExtentie());
        	overschrijf.setInt(10, i.getId());
        	overschrijf.executeUpdate();

        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        } catch (IOException ex) {
			// TODO Auto-generated catch block
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
		}
        finally {
        }
	}
	public void overschrijfItemZonderAfbeelding(Item i){
        try {
        	PreparedStatement overschrijf = conn.prepareStatement("UPDATE Object SET Naam = ? , Tijdstip = ?, Tekst = ?, GebruikerNr = ?, Status = ?, Erfgoed = ?, Link = ?, Extentie = ? WHERE ObjectNr = ?");
        	overschrijf.setString(1, i.getTitel());
        	overschrijf.setDate(2,i.getInzendDatum());
        	overschrijf.setString(3,i.getText());
        	overschrijf.setInt(4,i.getAuteur().getGebruikersnummer());
        	overschrijf.setString(5,i.getStatus());
        	overschrijf.setString(6, i.getErfgoed());
        	overschrijf.setString(7, i.getLink());
        	overschrijf.setString(8, i.getExtentie());
        	overschrijf.setInt(9, i.getId());
        	overschrijf.executeUpdate();

        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        }
	}
	public ArrayList<Item> leesItems(){
		
		
		ArrayList<Item> terugTeGevenItems= new ArrayList<Item>();
		ResultSet rs = null;
        try {
        	PreparedStatement haalItemsOp = conn.prepareStatement("SELECT GebruikerNr, Naam, Tijdstip , Tekst, Status, ObjectNr, Erfgoed, Link, Extentie FROM Object");
        	rs = haalItemsOp.executeQuery();
        	while (rs.next()){
        		int in = rs.getInt("ObjectNr");
        		String titel = rs.getString(2);
        		String tekst = rs.getString("Tekst");
        		Gebruiker gebruiker = haalGebruikerOp(rs.getInt("GebruikerNr"));
        		String erfgoed = rs.getString("Erfgoed");
        		String link = rs.getString("Link");
        		String status = rs.getString("Status");
        		BufferedImage foto = haalFotoOp(in);
        		String extentie = rs.getString("Extentie");
        		Item i = new Item(foto,in,titel,tekst,gebruiker,rs.getDate("Tijdstip"),erfgoed,link, status, extentie);
        		terugTeGevenItems.add(i);
        	}
        	


        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
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
	private Gebruiker haalGebruikerOp(int userNr){
		Gebruiker i = null;
        try {
        	PreparedStatement haalGebruikerOp = conn.prepareStatement("SELECT * FROM Gebruiker WHERE GebruikerNr =? ");
        	haalGebruikerOp.setInt(1, userNr);
        	ResultSet rs = haalGebruikerOp.executeQuery();
        	if (rs.next()){
        		i = new Gebruiker(rs.getString("Gebruikersnaam"), rs.getString("Naam"),rs.getString("Wachtwoord"),type, rs.getInt("GebruikerNr"), rs.getBoolean("Actief"), rs.getString("Email"));
        	}
        	else
        	{
        		JOptionPane.showMessageDialog(null, "Geen gebruiker gevonden!");
        	}
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
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
            Statement stat = conn.createStatement();
        	PreparedStatement haalGebruikerOp = conn.prepareStatement("SELECT * FROM Gebruiker WHERE Gebruikersnaam =? ");
        	haalGebruikerOp.setString(1, gebruikersnaam);
        	ResultSet rs = haalGebruikerOp.executeQuery();
        	if (rs.next()){
        		 result = new Gebruiker(rs.getString("Gebruikersnaam"), rs.getString("Naam"), rs.getString("Wachtwoord"), rs.getString("Type"), rs.getInt("GebruikerNr"), rs.getBoolean("Actief"), rs.getString("Email"));
        	}
        	
        	


        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
		return result;
	}
	public BufferedImage getFoto() {
		BufferedImage image= new BufferedImage(1,1,1);
        try {
	            Statement stat = conn.createStatement();
	        	PreparedStatement getFoto = conn.prepareStatement("SELECT foto FROM Object WHERE ObjectNr = 1");
	        	
	           ResultSet rs = getFoto.executeQuery();
	          // byte aByteArray[];
	           //Blob b = rs.getBlob(1);
	           if (rs.next()){
	        	   System.out.println("gevonden");
	           //aByteArray = new byte[b.getBinaryStream()];
	           //aByteArray = rs.getBytes(1);
	           image = ImageIO.read(rs.getBinaryStream(1)); 
	           //ImageIcon ii = new ImageIcon(aByteArray);
	           
	           
	           }
	           
	        } catch (Exception ex) {
	        
	        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
	        }
		 return image;
	}
	public ArrayList<Item> leesItemsOpDatum(){
		
		
		ArrayList<Item> terugTeGevenItems= new ArrayList<Item>();
        try {
        	PreparedStatement haalItemsOp = conn.prepareStatement("SELECT Naam, GebruikerNr, Tijdstip , Tekst, Status, ObjectNr, ErfGoed , Geschiedenis, Link, Locatie, Gemeente, Extentie FROM Object ORDER BY Tijdstip DESC");
        	ResultSet rs = haalItemsOp.executeQuery();
        	
        	while (rs.next()){
        		
        		int in = rs.getInt("ObjectNr");
        		String titel = rs.getString("Naam");
        		String tekst = rs.getString("Tekst");
        		Gebruiker gebruiker = haalGebruikerOp(rs.getInt("GebruikerNr"));
        		String erfgoed = rs.getString("Erfgoed");
        		String link = rs.getString("Link");
        		String status = rs.getString("Status");
        		BufferedImage foto = haalFotoOp(in);
        		String extentie = rs.getString("Extentie");
        		Item i = new Item(foto,in,titel,tekst,gebruiker,rs.getDate("Tijdstip"),erfgoed,link, status, extentie);
        		terugTeGevenItems.add(i);
        	}
        	


        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        return terugTeGevenItems;
	}

	public ArrayList<Item> leesItemsOpStatus(String status){
		ArrayList<Item> terugTeGevenItems= new ArrayList<Item>();
		PreparedStatement haalItemsOp =null;
        try {
        	haalItemsOp = conn.prepareStatement("SELECT * FROM Object WHERE Status = ?");
        	haalItemsOp.setString(1, status);
        	ResultSet rs = haalItemsOp.executeQuery();
        	
        	while (rs.next()){
        		int in = rs.getInt("ObjectNr");
        		String titel = rs.getString(2);
        		String tekst = rs.getString("Tekst");
        		Gebruiker gebruiker = haalGebruikerOp(rs.getInt("GebruikerNr"));
        		String erfgoed = rs.getString("Erfgoed");
        		String link = rs.getString("Link");
        		String status1 = rs.getString("Status");
        		BufferedImage foto = haalFotoOp(in);
        		String extentie = rs.getString("Extentie");
        		Item i = new Item(foto,in,titel,tekst,gebruiker,rs.getDate("Tijdstip"),erfgoed,link, status1, extentie);
        		terugTeGevenItems.add(i);
        	}


        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        	if(haalItemsOp != null)
				try {
					haalItemsOp.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        }
        return terugTeGevenItems;
	}
	public ArrayList<Item> leesItemsOpAuteur(int auteurNr){
		
		
		ArrayList<Item> terugTeGevenItems= new ArrayList<Item>();
        try {
        	PreparedStatement haalItemsOp = conn.prepareStatement("SELECT Naam, GebruikerNr, Tijdstip , Tekst, Status, ObjectNr,Erfgoed,Geschiedenis,Link, Locatie, Gemeente, Extentie FROM Object WHERE GebruikerNr =? ");
        	haalItemsOp.setInt(1,auteurNr);
        	ResultSet rs = haalItemsOp.executeQuery();
        	
        	while (rs.next()){
        		
        		int in = rs.getInt("ObjectNr");
        		String titel = rs.getString("Naam");
        		String tekst = rs.getString("Tekst");
        		Gebruiker gebruiker = haalGebruikerOp(rs.getInt("GebruikerNr"));
        		String erfgoed = rs.getString("Erfgoed");
        		String link = rs.getString("Link");
        		String status = rs.getString("Status");
        		BufferedImage foto = haalFotoOp(in);
        		String extentie = rs.getString("Extentie");
        		Item i = new Item(foto,in,titel,tekst,gebruiker,rs.getDate("Tijdstip"),erfgoed,link, status, extentie);
        		terugTeGevenItems.add(i);
        	}
        	


        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        return terugTeGevenItems;
	}
	public void wijzigStatus(String status,int i, String titel, String beschrijving){
        try {
            //Statement stat = conn.createStatement();
        	PreparedStatement wijzigStatus = conn.prepareStatement("UPDATE Object SET Naam = ? ,Tekst = ?,Status = ?  WHERE ObjectNr = ?");
        	wijzigStatus.setString(1 ,titel);
        	wijzigStatus.setString(2,beschrijving);
        	wijzigStatus.setString(3,status);
        	wijzigStatus.setInt(4, i);
        	wijzigStatus.executeUpdate();
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
        }
	}

	
	public BufferedImage haalFotoOp(int userID){
        try {     
		    	PreparedStatement readImage = conn.prepareStatement("SELECT Foto FROM Object WHERE ObjectNr=?");
		    	readImage.setInt(1, userID);
		    	ResultSet rs = readImage.executeQuery();
		    	
             if (rs.next()) {
                 
                 // Om de afbeelding terug uit te lezen, volgen we een omgekeerde werkwijze.
                 // We beginnen met het opvragen van een InputStream die de bytes uit het OLE Object veld opnieuw kan uitlezen.
                 InputStream dbStream = rs.getBinaryStream("Foto");
                 
                 // We gebruiken de methode ImageIO.read() om de bytes uit de stream te lezen en deze om te zetten naar een BufferedImage.
                 image = ImageIO.read(dbStream);
             } else {
                 System.out.println("Afbeelding niet gevonden in databank."+userID);
             }
		    }catch(Exception ex ){
		    	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
	                ex.printStackTrace();
		    }
		return image;
	}
	
	public ArrayList<Gebruiker> getGebruikers() {
		ArrayList<Gebruiker> gebruikers= new ArrayList<Gebruiker>();
        try {
        	PreparedStatement geb = conn.prepareStatement("SELECT * FROM Gebruiker");
        	ResultSet rs = geb.executeQuery();
        	
        	while (rs.next()){
        		String type = rs.getString("Type");
        		if(type.equals("Beheerder") || type.equals("Administrator"))
        		{
        		Gebruiker i = new Gebruiker(rs.getString("Gebruikersnaam"), rs.getString("Naam"),rs.getString("Wachtwoord"),type, rs.getInt("GebruikerNr"), rs.getBoolean("Actief"), rs.getString("Email"));
        		gebruikers.add(i);
        		}
        	}
        	


        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
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
        	Statement stmt = conn.createStatement(
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
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
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
        	Statement stmt = conn.createStatement(
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
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
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
        	Statement stmt = conn.createStatement(
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
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
	}
	
	public void maakGebruikerInactief(int nr)
	{
        try {

            //Statement stat = conn.createStatement();
        	PreparedStatement wijzigStatus = conn.prepareStatement("UPDATE Gebruiker SET Actief = ? WHERE GebruikerNr = ?");
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
            //Statement stat = conn.createStatement();
        	PreparedStatement wijzigStatus = conn.prepareStatement("UPDATE Gebruiker SET Actief = ? WHERE GebruikerNr = ?");
        	wijzigStatus.setBoolean(1, true);
        	wijzigStatus.setInt(2 ,nr);
        	wijzigStatus.executeUpdate();
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
	}
	public String getType() {
		return type;
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
	
	
	public void setMailText(String soort, String txt)
	{
        try {
            //Statement stat = conn.createStatement();
        	PreparedStatement wijzig = conn.prepareStatement("UPDATE Standaardtekst SET Tekst = ? WHERE Soort = ?");
        	
        	wijzig.setString(1, txt);
        	wijzig.setString(2, soort);
        	wijzig.executeUpdate();
        } catch (SQLException ex) {
        	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
	}
	
	public String getMailText(String soort)
	{
		String result = null;
        try {
		    	PreparedStatement read = conn.prepareStatement("SELECT Tekst FROM Standaardtekst WHERE Soort=?");
		    	read.setString(1, soort);
		    	ResultSet rs = read.executeQuery();
		    	
             if (rs.next()) {
                 result = rs.getString("Tekst");
             } else {
                 System.out.println("Standaardtekst niet gevonden in databank.");
             }
		    }catch(Exception ex ){
		    	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
		    }
        return result;
	}
public ArrayList<Erfgoed> getErfGoeden() {
     try { 	
     	ArrayList<Erfgoed> result=new ArrayList<Erfgoed>();
     	PreparedStatement erfgoed = conn.prepareStatement("SELECT * FROM Erfgoed");
     	ResultSet rs= erfgoed.executeQuery();
     	while (rs.next()){
     		Erfgoed e = new Erfgoed( rs.getString("Naam"), rs.getString("Locatie"),rs.getString("Type"),rs.getString("Link"),rs.getString("Geschiedenis"),rs.getString("Info"));
     		result.add(e);
     		
     	}
     	return result;
     } catch (SQLException ex) {
    	 JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
    	 for (Throwable t : ex) {
             t.printStackTrace();
         }
     }
     return new ArrayList<Erfgoed>();
}
public void schrijfNieuwItem(Item i) {
     try {	    
     	PreparedStatement newItem = conn.prepareStatement("INSERT INTO Object (Naam, Tijdstip, Tekst, Foto,GebruikerNr,Status,Erfgoed,Link, Extentie) VALUES (?,?,?,?,?,?,?,?,?)");
     	
    	BufferedImage im = i.getFoto(); 
    	ByteArrayOutputStream os = new ByteArrayOutputStream();
    	ImageIO.write(im, i.getExtentie(), os);
    	InputStream is = new ByteArrayInputStream(os.toByteArray());     	
     	newItem.setString(1,i.getTitel());
     	newItem.setDate(2,i.getInzendDatum());
     	newItem.setString(3,i.getText());
     	newItem.setBinaryStream(4, is,is.available());
     	newItem.setInt(5, i.getAuteur().getGebruikersnummer());
     	newItem.setString(6,"Goedgekeurd");
     	newItem.setString(7, i.getErfgoed());
     	newItem.setString(8, i.getLink());
     	newItem.setString(9, i.getExtentie());
     	
     	newItem.executeUpdate();
     	
 
     } catch (SQLException ex) {
    	 JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
    	 for (Throwable t : ex) {
             t.printStackTrace();
         }
     } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
}

public void schrijfNieuwItemZonderAfbeelding(Item i) {
    try {	    
    	PreparedStatement newItem = conn.prepareStatement("INSERT INTO Object (Naam, Tijdstip, Tekst, GebruikerNr,Status,Erfgoed,Link, Extentie) VALUES (?,?,?,?,?,?,?,?)");
    	newItem.setString(1,i.getTitel());
    	newItem.setDate(2,i.getInzendDatum());
    	newItem.setString(3,i.getText());
    	newItem.setInt(4, i.getAuteur().getGebruikersnummer());
    	newItem.setString(5,"Goedgekeurd");
    	newItem.setString(6, i.getErfgoed());
    	newItem.setString(7, i.getLink());
    	newItem.setString(8, i.getExtentie());
    	
    	newItem.executeUpdate();
    	

    } catch (SQLException ex) {
   	 JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
   	 for (Throwable t : ex) {
            t.printStackTrace();
        }
    }
   
}

public boolean magErfgoedVerwijderdWorden(Erfgoed er) {
	 Boolean b = true;
     try { 	    
     	PreparedStatement magweg = conn.prepareStatement("SELECT * FROM Object WHERE Erfgoed = ?");
     	
     	magweg.setString(1,er.getNaam());
     	
     	ResultSet rs =magweg.executeQuery();
     	if (rs.next()){
     		b=false;
     	}
 
     } catch (SQLException ex) {
    	 JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
    	 for (Throwable t : ex) {
             t.printStackTrace();
         }
     }
     return b;
}
public void removeErfgoed(Erfgoed er) {
	// TODO Auto-generated method stub
    try {
    	PreparedStatement weg = conn.prepareStatement("DELETE FROM Erfgoed WHERE Naam =?");
    	
    	weg.setString(1,er.getNaam());
    	weg.executeUpdate();

    } catch (SQLException ex) {
    	JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
    	for (Throwable t : ex) {
            t.printStackTrace();
        }
    }
}
public void schrijfNieuwerfgoed(Erfgoed er) {
     try {   
     	PreparedStatement newE = conn.prepareStatement("INSERT INTO Erfgoed (Naam, Locatie, Type, Link ,Geschiedenis ,Info) VALUES (?,?,?,?,?,?)");
     	
    	   	
     	newE.setString(1,er.getNaam());
     	newE.setString(2,er.getLocatie());
     	newE.setString(3,er.getType());
     	newE.setString(4,er.getLink());
     	newE.setString(5,er.getGeschiedenis());
     	newE.setString(6,er.getInfo());

     	
     	
     	newE.executeUpdate();
     	
 
     } catch (SQLException ex) {
    	 JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
    	 for (Throwable t : ex) {
             t.printStackTrace();
         }
     }
}

public ArrayList<String> getGemeenten() {
	ArrayList<String> gemeenten = new ArrayList<String>();
	try {
     	PreparedStatement magweg = conn.prepareStatement("SELECT * FROM Gemeente");
     	
     	ResultSet rs =magweg.executeQuery();
     	while (rs.next()){
     		gemeenten.add(rs.getString("Gemeente"));
     	}
     	
     } catch (SQLException ex) {
    	 JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
    	 for (Throwable t : ex) {
             t.printStackTrace();
         }
     }
	finally {
		return gemeenten;
	}
}

public void sluitConnectie()
{
	try {
		if(conn != null)
		conn.close();
	} catch (SQLException ex) {
		// TODO Auto-generated catch block
		JOptionPane.showMessageDialog(null, "Er is een database fout opgetreden tengevolge van een Timeout, overbelasting op de huidige connectie.\nDe connectie wordt automatisch herstart\n Probeer het opnieuw.\nFoutmelding: " + ex.toString());
		for (Throwable t : ex) {
            t.printStackTrace();
        }
	}
}

public void herstartConnectie()
{
	if(conn != null)
	{
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
}
	
