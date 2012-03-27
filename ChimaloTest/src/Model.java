import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Model {

	private String activeUser;
	private String activeType;
	private ArrayList<Item>items;
	private DataBankConnection dbc;
	private ArrayList<ChangeListener> listeners;
	private Item activeItem;
	public Model(String activeUser){
		listeners = new ArrayList<ChangeListener>();
		dbc = new DataBankConnection(this);
		items = dbc.leesItemsOpStatus("KeurLijst");
		this.activeUser=activeUser;
	}
	
	public Model(String activeUser, String type)
	{
		listeners = new ArrayList<ChangeListener>();
		dbc = new DataBankConnection(this);
		items = dbc.leesItemsOpStatus("KeurLijst");
		this.activeUser=activeUser;
		this.activeType = type;
	}
	
	public void subscribe(ChangeListener c){
		listeners.add(c);
	}
	public void unsubscribe(ChangeListener c){
		listeners.remove(c);
	}
	public void notifyChangeListeners(){
		for (ChangeListener c : listeners ){
			ChangeEvent e= new ChangeEvent(this);
			c.stateChanged(e);
		}
	}
	public ArrayList<Item> getItems(){
		return items;
	}
	public String getUser(){
		return activeUser;
	}
	public ArrayList<Item> alleItems(){
		return dbc.leesItems();
	}
	public void leesOpStatus(String status){
		
		items=dbc.leesItemsOpStatus(status);
		notifyChangeListeners();
	}
	public void setActiveItem(String auteur,String beschrijving,String datum, String titel){
		for (Item it : items){
			if (it.getAuteur().equals(auteur)&&it.getText().equals(beschrijving)&&it.getTitel().equals(titel)){
				activeItem=it;
				return;
			}
			 
		}
		activeItem=null;
		System.out.println("Notfound");
	}
	public void wijzigStatus(String s){
		dbc.wijzigStatus(s,activeItem.getId(),activeItem.getTitel(),activeItem.getText());
		items=dbc.leesItems();
		notifyChangeListeners();
	}
	public Item getActiveItem(){
		return activeItem;
	}

	public void clearActive() {
		activeItem=null;
	}
	
	public boolean controleOpVerwijderen(int geb)
	{
		boolean delete = dbc.controleOpVerwijderen(geb);
		notifyChangeListeners();
		return delete;
	}
	
	public ArrayList<Gebruiker> getGebruikers()
	{
		return dbc.getGebruikers();
	}
	
	public void maakGebruikerActief(int nr)
	{
		dbc.maakGebruikerActief(nr);
		notifyChangeListeners();
	}
	public void voegToe(String naam, String voornaam, String pass, String type){
		dbc.voegToe(naam, voornaam, pass, type);
		notifyChangeListeners();
	}
	public void updateGebruiker(int nr, String naam, String voornaam, String pass, String type){
		dbc.updateGebruiker(nr, naam, voornaam, pass, type);
		notifyChangeListeners();
	}

	public String getActiveType() {
		return activeType;
	}
	
	public String getMd5(String woord)
	{
		return dbc.md5(woord);
	}
	
	public void overschrijfActive(){
		dbc.overschrijfItem(activeItem);
	}
	
	
}
