
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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class DataBankConnection {
	private BufferedImage image;
	private Model m;
	private String type = "Administrator";
	
	public DataBankConnection(Model m) {
		this.m = m;
	}
	public DataBankConnection() {
		
	}


	public boolean checkAccount (String name, String pass) {

		Connection conn = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");

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
            		type = status;
            		return true; 
            	}
            	else
            		return false;
            }
            else   
            	return false;
            
           

        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
      
		return false;
       
    }
	public boolean hasDuplicates(String Gebruikersnaam) {
		Connection conn= null;
		try {
			conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");
    		int count = 0;
    		PreparedStatement haalItemsOp = conn.prepareStatement("SELECT COUNT(*) AS rowcount FROM Gebruiker WHERE Gebruikersnaam =? ");
    		haalItemsOp.setString(1,Gebruikersnaam);
    		ResultSet r = haalItemsOp.executeQuery();
    		r.next();
    		count = r.getInt("rowcount") ;
    		r.close() ;
    		if(count > 0)
    			return true;
    		else
    			return false;
    		
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return false;
	}
	
	
	public void voegToe(String Gebruikersnaam, String Naam, String pass, String type){
		Connection conn = null;
		if(!hasDuplicates(Gebruikersnaam)) {
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");
        	PreparedStatement voegBeheerderToe = conn.prepareStatement("INSERT INTO Gebruiker (Gebruikersnaam, Naam, Wachtwoord, Type, Actief) VALUES (?,?,?,?,?)");
        	voegBeheerderToe.setString(1, Gebruikersnaam);
        	voegBeheerderToe.setString(2, Naam);
        	voegBeheerderToe.setString(3, pass);
        	voegBeheerderToe.setString(4, type);
        	voegBeheerderToe.setBoolean(5, true);

           voegBeheerderToe.executeUpdate();
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
        }
		}
		else {
			JOptionPane.showMessageDialog(null, "Een gebruiker met deze Gebruikersnaam bestaat al, kies een andere!");
		}
	}
	public void updateGebruiker(int nr, String Gebruikersnaam, String Naam, String pass, String type){
		Connection conn = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");
        	if(!pass.equals(""))
        	{
        		PreparedStatement voegBeheerderToe = conn.prepareStatement("UPDATE Gebruiker SET Gebruikersnaam = ? ,Naam = ?,Wachtwoord = ?, Type = ?, Actief = ? WHERE gebruikerNr = ?");
        		voegBeheerderToe.setString(1, Gebruikersnaam);
        		voegBeheerderToe.setString(2, Naam);
        		voegBeheerderToe.setString(3, pass);
        		voegBeheerderToe.setString(4, type);
        		voegBeheerderToe.setBoolean(5, true);
        		voegBeheerderToe.setInt(6, nr);
        		voegBeheerderToe.executeUpdate();
        	}
        	else {
        		PreparedStatement voegBeheerderToe = conn.prepareStatement("UPDATE Gebruiker SET Gebruikersnaam = ? ,Naam = ?, Type = ?, Actief = ? WHERE gebruikerNr = ?");
            	voegBeheerderToe.setString(1, Gebruikersnaam);
            	voegBeheerderToe.setString(2, Naam);
            	voegBeheerderToe.setString(3, type);
            	voegBeheerderToe.setBoolean(4, true);
            	voegBeheerderToe.setInt(5, nr);
            	voegBeheerderToe.executeUpdate();
        	}

           
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	public void overschrijfItem(Item i){
		

		Connection conn = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");

        	BufferedImage im = i.getFoto(); 
        	ByteArrayOutputStream os = new ByteArrayOutputStream();
        	ImageIO.write(im, "gif", os);
        	InputStream is = new ByteArrayInputStream(os.toByteArray());
        	if (is == null)
        		System.out.println("kak");
        	PreparedStatement overschrijf = conn.prepareStatement("UPDATE Object SET Naam = ? , Tijdstip = ?, Tekst = ?, Foto = ?, GebruikerNr = ?, Status = ? WHERE ObjectNr = ?");
        	overschrijf.setString(1, i.getTitel());
        	overschrijf.setDate(2,i.getInzendDatum());
        	overschrijf.setString(3,i.getText());
        	overschrijf.setBinaryStream(4,is,is.available());
        	overschrijf.setInt(5,zoekGebruikerNr(i.getAuteur()));
        	overschrijf.setString(6,"Keurlijst");
        	overschrijf.setInt(7, i.getId());
        	overschrijf.executeUpdate();

        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	public ArrayList<Item> leesItems(){
		
		
		ArrayList<Item> terugTeGevenItems= new ArrayList<Item>();
		Connection conn = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");

            Statement stat = conn.createStatement();
        	PreparedStatement haalItemsOp = conn.prepareStatement("SELECT GebruikerNr, Naam, Tijdstip , Tekst, Status, ObjectNr, Erfgoed , Geschiedenis, Link FROM Object ");
        	ResultSet rs = haalItemsOp.executeQuery();
        	
        	while (rs.next()){
        		int in = rs.getInt("ObjectNr");
        		Item i = new Item(rs.getString("Naam"), haalGebruikerOp(rs.getInt("GebruikerNr")),rs.getDate("Tijdstip"),rs.getString("Tekst"),rs.getString("Status"),in,haalFotoOp(in),rs.getString("Erfgoed"),rs.getString("Geschiedenis"),rs.getString("Link"));
        		terugTeGevenItems.add(i);
        	}
        	


        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return terugTeGevenItems;
	}
	private String haalGebruikerOp(int userNr){
		String result="";
		Connection conn = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");
			
            Statement stat = conn.createStatement();
        	PreparedStatement haalGebruikerOp = conn.prepareStatement("SELECT Gebruikersnaam, Naam FROM Gebruiker WHERE GebruikerNr =? ");
        	haalGebruikerOp.setInt(1, userNr);
        	ResultSet rs = haalGebruikerOp.executeQuery();
        	if (rs.next()){
        		 result = rs.getString("Naam")+" "+ rs.getString("Gebruikersnaam");
        	}
        	
        	


        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return result;
	}
	public BufferedImage getFoto() {
		BufferedImage image= new BufferedImage(1,1,1);
		Connection conn = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");

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
	        
	                ex.printStackTrace();
	            
	        }
	        finally {
	        	try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		 return image;
	}
	public ArrayList<Item> leesItemsOpDatum(){
		
		
		ArrayList<Item> terugTeGevenItems= new ArrayList<Item>();
		Connection conn = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");
        	PreparedStatement haalItemsOp = conn.prepareStatement("SELECT Naam, GebruikerNr, Tijdstip , Tekst, Status, ObjectNr, ErfGoed , Geschiedenis, Link FROM Object ORDER BY Tijdstip DESC");
        	ResultSet rs = haalItemsOp.executeQuery();
        	
        	while (rs.next()){
        		
        		int in = rs.getInt("ObjectNr");
        		Item i = new Item(rs.getString("Naam"), haalGebruikerOp(rs.getInt("GebruikerNr")),rs.getDate("Tijdstip"),rs.getString("Tekst"),rs.getString("Status"),in,haalFotoOp(in),rs.getString("Erfgoed"),rs.getString("Geschiedenis"),rs.getString("Link"));
        		terugTeGevenItems.add(i);
        	}
        	


        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return terugTeGevenItems;
	}

	public ArrayList<Item> leesItemsOpStatus(String status){
		
		
		ArrayList<Item> terugTeGevenItems= new ArrayList<Item>();
		Connection conn = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");

            Statement stat = conn.createStatement();
        	PreparedStatement haalItemsOp = conn.prepareStatement("SELECT * FROM Object WHERE Status = ?");
        	haalItemsOp.setString(1, status);
        	ResultSet rs = haalItemsOp.executeQuery();
        	
        	while (rs.next()){
        		int in = rs.getInt("ObjectNr");
        		Item i = new Item(rs.getString("Naam"), haalGebruikerOp(rs.getInt("GebruikerNr")),rs.getDate("Tijdstip"),rs.getString("Tekst"),rs.getString("Status"),in,haalFotoOp(in),rs.getString("Erfgoed"),rs.getString("Geschiedenis"),rs.getString("Link"));
        		terugTeGevenItems.add(i);
        	}
        	


        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return terugTeGevenItems;
	}
	public ArrayList<Item> leesItemsOpAuteur(int auteurNr){
		
		
		ArrayList<Item> terugTeGevenItems= new ArrayList<Item>();
		Connection conn = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");

            Statement stat = conn.createStatement();
        	PreparedStatement haalItemsOp = conn.prepareStatement("SELECT Naam, GebruikerNr, Tijdstip , Tekst, Status, ObjectNr,Erfgoed,Geschiedenis,Link FROM Object WHERE GebruikerNr =? ");
        	haalItemsOp.setInt(1,auteurNr);
        	ResultSet rs = haalItemsOp.executeQuery();
        	
        	while (rs.next()){
        		
        		int in = rs.getInt("ObjectNr");
        		Item i = new Item(rs.getString("Naam"), haalGebruikerOp(rs.getInt("GebruikerNr")),rs.getDate("Tijdstip"),rs.getString("Tekst"),rs.getString("Status"),in,haalFotoOp(in),rs.getString("Erfgoed"),rs.getString("Geschiedenis"),rs.getString("Link"));
        		terugTeGevenItems.add(i);
        	}
        	


        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return terugTeGevenItems;
	}
	public void wijzigStatus(String status,int i, String titel, String beschrijving){
		Connection conn = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");

            //Statement stat = conn.createStatement();
        	PreparedStatement wijzigStatus = conn.prepareStatement("UPDATE Object SET Naam = ? ,Tekst = ?,Status = ?  WHERE ObjectNr = ?");
        	wijzigStatus.setString(1 ,titel);
        	wijzigStatus.setString(2,beschrijving);
        	wijzigStatus.setString(3,status);
        	wijzigStatus.setInt(4, i);
        	wijzigStatus.executeUpdate();
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}

	
	public BufferedImage haalFotoOp(int userID){
		Connection conn = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");
		       
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
		    }catch(Exception e ){
		    	
		    }
		    finally {
	        	try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		   
		return image;
	}
	
	public ArrayList<Gebruiker> getGebruikers() {
		Connection conn = null;
		ArrayList<Gebruiker> gebruikers= new ArrayList<Gebruiker>();
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");
        	PreparedStatement geb = conn.prepareStatement("SELECT * FROM Gebruiker");
        	ResultSet rs = geb.executeQuery();
        	
        	while (rs.next()){
        		String type = rs.getString("Type");
        		if(type.equals("Beheerder") || type.equals("Administrator"))
        		{
        		Gebruiker i = new Gebruiker(rs.getString("Gebruikersnaam"), rs.getString("Naam"),rs.getString("Wachtwoord"),type, rs.getInt("GebruikerNr"), rs.getBoolean("Actief"));
        		gebruikers.add(i);
        		}
        	}
        	


        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return gebruikers;
	}
	
	public boolean verwijderGebruiker(int nr)
	{
		Connection conn = null;
		ResultSet rs = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");
            
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
        	for (Throwable t : ex) {
                t.printStackTrace();
            }
        	return false;
        }
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return true;
	}
	
	public boolean controleOpVerwijderen(int nr)
	{
		Connection conn = null;
		ResultSet rs = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");
            
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
        	for (Throwable t : ex) 
                t.printStackTrace();
        	return false;
            }
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// 0TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return true;
	}
	
	public void gebruikersBerichtenVerwijderen(int nr)
	{
		Connection conn = null;
		ResultSet rs = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");
            
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
        	for (Throwable t : ex) 
                t.printStackTrace();
            }
        finally {
        	try {
				conn.close();
				verwijderGebruiker(nr);
			} catch (SQLException e) {
				// 0TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	public void maakGebruikerInactief(int nr)
	{
		Connection conn = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");

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
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	public void maakGebruikerActief(int nr)
	{
		Connection conn = null;
        try {
        	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");

            //Statement stat = conn.createStatement();
        	PreparedStatement wijzigStatus = conn.prepareStatement("UPDATE Gebruiker SET Actief = ? WHERE GebruikerNr = ?");
        	wijzigStatus.setBoolean(1, true);
        	wijzigStatus.setInt(2 ,nr);
        	wijzigStatus.executeUpdate();
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
 private int zoekGebruikerNr(String naam){
	 Connection conn = null;
     try {
     	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");

         //Statement stat = conn.createStatement();
     	PreparedStatement findNr = conn.prepareStatement("SELECT GebruikerNr FROM Gebruiker WHERE Gebruikersnaam =?");
     	findNr.setString(1,naam);
     	ResultSet rs= findNr.executeQuery();
     	if (rs.next())     	
     	return rs.getInt(1);
     } catch (SQLException ex) {
         for (Throwable t : ex) {
             t.printStackTrace();
         }
     }
     finally {
     	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     }
     return 1;
	}
public ArrayList<Erfgoed> getErfGoeden() {
	 Connection conn = null;
     try {
     	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");
     	ArrayList<Erfgoed> result=new ArrayList<Erfgoed>();
         
     	PreparedStatement erfgoed = conn.prepareStatement("SELECT * FROM Erfgoed");
     	ResultSet rs= erfgoed.executeQuery();
     	while (rs.next()){
     		Erfgoed e = new Erfgoed( rs.getString("Naam"), rs.getString("Locatie"),rs.getString("Type"));
     		result.add(e);
     		
     	}
     	return result;
     } catch (SQLException ex) {
         for (Throwable t : ex) {
             t.printStackTrace();
         }
     }
     finally {
     	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     }
     return new ArrayList<Erfgoed>();
}
public void schrijfNieuwItem(Item i) {
	
	 Connection conn = null;
     try {
     	conn = DriverManager.getConnection("jdbc:odbc:JdbcVb");
     	    
     	PreparedStatement newItem = conn.prepareStatement("INSERT INTO Object (Naam, Tijdstip, Tekst, Foto,GebruikerNr,Status,Erfgoed,Geschiedenis,Link) VALUES (?,?,?,?,?,?,?,?,?)");
     	
    	BufferedImage im = i.getFoto(); 
    	ByteArrayOutputStream os = new ByteArrayOutputStream();
    	ImageIO.write(im, "gif", os);
    	InputStream is = new ByteArrayInputStream(os.toByteArray());     	
     	newItem.setString(1,i.getTitel());
     	newItem.setDate(2,i.getInzendDatum());
     	newItem.setString(3,i.getText());
     	newItem.setBinaryStream(4, is,is.available());
     	newItem.setInt(5, zoekGebruikerNr(i.getAuteur()));
     	newItem.setString(6,"Keurlijst");
     	newItem.setString(7, i.getErfgoed());
     	newItem.setString(8, i.getGeschiedenis());
     	newItem.setString(9, i.getLink());
     	
     	newItem.executeUpdate();
     	
 
     } catch (SQLException ex) {
         for (Throwable t : ex) {
             t.printStackTrace();
         }
     } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     finally {
     	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     }
    
}

}
	