import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;


public class updateItems extends SwingWorker<Integer, Integer> {

	private Model m;
	private JLabel text;
	private ArrayList<Item> terugTeGevenItems= new ArrayList<Item>();
	private int size;
	private int keurlijstItems=0;
	
	public updateItems(Model mo)
	{
		this.m = mo;
		text = m.getStatusLabel();
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		System.out.println("---Lees items---");
		size = m.countItems();
		Connection conn;
		if(!m.isLoginschermOpen())
		{
		publish(-1);
		conn = DriverManager.getConnection("jdbc:sqlserver://ProjectChimalo.mssql.somee.com;database=ProjectChimalo;user=anthonyvd;password=klokken05");
		m.setDbconnectie(conn);
		}
		else
		{
			conn = m.getDbconnectie();
		}
		ResultSet rs = null;
		int teller = 0;
        try {
        	PreparedStatement haalItemsOp = conn.prepareStatement("SELECT GebruikerNr, Naam, Tijdstip , Tekst, Status, ObjectNr, ErfgoedNr, Link, Extensie, Type FROM Object");
        	rs = haalItemsOp.executeQuery();
        	while (rs.next()){
        		int in = rs.getInt("ObjectNr");
        		String titel = rs.getString(2);
        		String tekst = rs.getString("Tekst");
        		Gebruiker gebruiker = m.haalGebruikerOp(rs.getInt("GebruikerNr"));
        		Erfgoed erfgoed = m.getErfgoed(rs.getInt("ErfgoedNr"));
        		String link = rs.getString("Link");
        		String status = rs.getString("Status");
        		BufferedImage foto = m.haalFotoOp(in);
        		String Extensie = rs.getString("Extensie");
        		String type = rs.getString("Type");
        		Item i = new Item(foto,in,titel,tekst,gebruiker,rs.getDate("Tijdstip"),erfgoed,link, status, Extensie, type);
        		terugTeGevenItems.add(i);
        		publish(teller++);
        	}
        	int counter=0;
        	if(m.getItems() != null) {
        	for(Item i : m.getItems()){
        		if(i.getStatus().equals("Keurlijst"))
        		{
        			counter++;
        		}
        	}
        	}
        	int countere=0;
        	for(Item i: terugTeGevenItems)
        	{
        		if(i.getStatus().equals("Keurlijst"))
        		{
        			countere++;
        		}
        	}
        	System.out.println(counter + countere);
    		keurlijstItems=countere-counter;
        
        	m.setItems(terugTeGevenItems);
        	m.leesAlleErfgoeden();
        	m.notifyChangeListeners();
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
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
    protected void done() {
		Calendar now = Calendar.getInstance();
	    String tekst = "("+ now.get(Calendar.HOUR_OF_DAY) + ":" + controlMinuten(now.get(Calendar.MINUTE)) + ") "+ keurlijstItems + " nieuwe Items in de Keurlijst. Volgende synchronisatie om ";
	    now.add(Calendar.MINUTE,2);
	    tekst = tekst + now.get(Calendar.HOUR_OF_DAY) + ":" + controlMinuten(now.get(Calendar.MINUTE));
		text.setText(tekst);
		if(m.isLoginschermOpen())
		{
			hoofd_scherm scherm = new hoofd_scherm(m);
			scherm.setVisible(true);
			m.getChimalo().dispose();
			Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
			
			// bereken het midden van je scherm
		    int w = scherm.getSize().width;
		    int h = scherm.getSize().height;
		    int x = (size.width-w)/2;
		    int y = (size.height-h)/2;
		    
		    // verplaats de GUI
		    scherm.setLocation(x, y);
		    m.setLoginschermIsOpen(false);
		}
	}
	
	@Override
    protected void process(List<Integer> chunks) {
        // In deze methode plaatsen we de code die de tussentijdse updates moet verwerken.
        // De methoden publish() en process() zijn niet synchroon.
        // Het is dus mogelijk dat we een lijst van tussentijdse updates ontvangen en niet enkel de meest recente.
        // Deze methode zal worden uitgevoerd op de event dispatching thread van Swing !
		if(m.isLoginschermOpen())
		{
			text.setText("Bezig met het laden van de inzendingen (" + (chunks.get(chunks.size()-1)*100/size) + "%)");
		}
		else {
			if((chunks.get(chunks.size()-1)) == -1)
			{
				text.setText("Bezig met het herstarten van de connectie");
			}
			else
			{
				if((chunks.get(chunks.size()-1)) >= 0)
					text.setText("Bezig met het vernieuwen van de Itemlijst. Voortgang: " + (chunks.get(chunks.size()-1)*100/size) + "% voltooid");
			}
		}
    }
	
	public String controlMinuten(int minuten) {
		if(minuten<10)
		{
			return "0"+minuten;
		}
		else
		{
			return ""+minuten;
		}
	}

}
