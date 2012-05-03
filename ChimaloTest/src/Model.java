import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Model {

	private ArrayList<Item>items;
	private DataBankConnection dbc;
	private ArrayList<ChangeListener> listeners;
	private Item activeItem;
	private String mailUsername = "erfgoedbankherzele@gmail.com";
	private String mailPassword = "eenbankalsgeenander";
	private String smtpHost = "smtp.gmail.com";
	private int smtpPort = 587;
	private Erfgoed activeErfgoed;
	private String mailFrom = "ErfgoedHerzele";
	private hoofd_scherm hoofdframe;
	private Gebruiker admin;
	private Boolean vensterOpen = false;
	private Boolean nieuweAfbeelding = false;
	private ChangeListener activePanel;
	private ArrayList<Erfgoed> erfgoeden;
	private int itemTeller = 0;
	private JLabel statusLabel;
	private Connection dbconnectie;
	private Timer timer;
	
	public Model(Gebruiker admin){
		listeners = new ArrayList<ChangeListener>();
		dbc = new DataBankConnection(this);
		this.admin = admin;
	}
	
	public Model() {
		listeners = new ArrayList<ChangeListener>();
		dbc = new DataBankConnection(this);
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
	
	public void leesAlleItems() {
		items = dbc.leesItems();
	}
	public void leesOpStatus(String status){
		
		items=dbc.leesItemsOpStatus(status);
		notifyChangeListeners();
	}
	public void wijzigStatus(String s){
		dbc.wijzigStatus(s,activeItem.getId(),activeItem.getTitel(),activeItem.getText());
		activeItem.setStatus(s);
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
	public void voegToe(String naam, String voornaam, String pass, String type, String email){
		dbc.voegToe(naam, voornaam, pass, type,email);
		notifyChangeListeners();
	}
	public void updateGebruiker(int nr, String naam, String voornaam, String pass, String type, String email){
		dbc.updateGebruiker(nr, naam, voornaam, pass, type, email);
		notifyChangeListeners();
	}
	
	public String getMd5(String woord)
	{
		return dbc.md5(woord);
	}

	public String getMailUsername() {
		return mailUsername;
	}

	public void setMailUsername(String mailUsername) {
		this.mailUsername = mailUsername;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public int getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(int smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	
	public void setMailText(String soort, String txt)
	{
		dbc.setMailText(soort, txt);
	}
	
	public String getMailText(String soort)
	{
		return dbc.getMailText(soort);
	}
	
	public Gebruiker getGebruiker(String gebruikersnaam)
	{
		return dbc.getGebruiker(gebruikersnaam);
	}
	public void overschrijfActive(){
		dbc.overschrijfItem(activeItem);
		notifyChangeListeners();
	}
	
	public ArrayList<Erfgoed> getErfgoeden(){
		return erfgoeden;
	}
	
	public void leesAlleErfgoeden() {
		erfgoeden = dbc.getErfGoeden();
	}

	public void schrijfNieuwItem(Item i) {
		dbc.schrijfNieuwItem(i);
		
	}
	
	public void setActiveItem(Item m)
	{
		this.activeItem =m;
	}
	public boolean magErfgoedVerwijderdWorden(Erfgoed e) {
		// TODO Auto-generated method stub
		if(e!=null){
		return dbc.magErfgoedVerwijderdWorden( e);
		}
		else return false;
	}

	public void removeErfgoed(Erfgoed e) {
		dbc.removeErfgoed(e);
		
	}

	public void setActiveErfgoed(Erfgoed erfgoed) {
		this.activeErfgoed=erfgoed;
		notifyChangeListeners();
	}
	public Erfgoed getActiveErfgoed(){
		return activeErfgoed;
	}

	public void schrijfNieuwErfgoed(Erfgoed er) {
		dbc.schrijfNieuwErfgoed(er);
		notifyChangeListeners();
	}
	public void schrijfErfgoed(Erfgoed er)
	{
		dbc.schrijfErfgoed(er);
		notifyChangeListeners();
	}
	
	public void nieuwItem()
	{
		activeItem = new Item();
	}

	public hoofd_scherm getHoofdframe() {
		return hoofdframe;
	}

	public void setHoofdframe(hoofd_scherm hoofdframe) {
		this.hoofdframe = hoofdframe;
	}
	
	public void sluitConnectie()
	{
		dbc.sluitConnectie();
	}

	public Gebruiker getAdmin() {
		return admin;
	}

	public void setAdmin(Gebruiker admin) {
		this.admin = admin;
	}
	
	public void overschrijfItemZonderAfbeelding(Item i)
	{
		dbc.overschrijfItemZonderAfbeelding(i);
	}
	
	public void schrijfNieuwItemZonderAfbeelding(Item i)
	{
		dbc.schrijfNieuwItemZonderAfbeelding(i);
	}
	
	public void setActivePanel(ChangeListener p){
		activePanel = p;
	}
	
	public ChangeListener getActivePanel()
	{
		return activePanel;
	}
	public Gebruiker checkAccount(String name, String pass) {
		return dbc.checkAccount(name, pass);
	}

	public Boolean getVensterOpen() {
		return vensterOpen;
	}

	public void setVensterOpen(Boolean vensterOpen) {
		this.vensterOpen = vensterOpen;
	}

	public Boolean getNieuweAfbeelding() {
		return nieuweAfbeelding;
	}

	public void setNieuweAfbeelding(Boolean nieuweAfbeelding) {
		this.nieuweAfbeelding = nieuweAfbeelding;
	}
	
	public Item aanvullenItem(Item i)
	{
		return dbc.aanvullenItem(i);
	}
	public Erfgoed aanvullenErfgoed(Erfgoed i)
	{
		return dbc.aanvullenErfgoed(i);
	}
	
	public int countItems() {
		return dbc.countItems();
	}

	public int getItemTeller() {
		return itemTeller;
	}

	public void setItemTeller(int itemTeller) {
		this.itemTeller = itemTeller;
	}

	public JLabel getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(JLabel statusLabel) {
		this.statusLabel = statusLabel;
	}

	public Connection getDbconnectie() {
		return dbconnectie;
	}

	public void setDbconnectie(Connection dbconnectie) {
		this.dbconnectie = dbconnectie;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	public Erfgoed getErfgoed(int erfgoednr) {
		return dbc.getErfgoed(erfgoednr);
	}
	
	public BufferedImage haalFotoOp(int userID){
		return dbc.haalFotoOp(userID);
	}
	
	public Gebruiker haalGebruikerOp(int userNr){
		return dbc.haalGebruikerOp(userNr);
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	
	
}
