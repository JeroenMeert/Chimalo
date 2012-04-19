import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;


public class MailFrame extends JFrame {

	private JPanel contentPane;
	private JEditorPane  editorPane;
	private JScrollPane veld;
	private JTextArea textArea;
	private String text;
	private String standaardTekst;
	private Model m;
	private Item item;
	private HtmlHandler html;
	private boolean wijzigen = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calendar cal = new GregorianCalendar() ;
					java.sql.Date jsqlD = new java.sql.Date( cal.getTime().getTime());
					Model model = new Model(new Gebruiker("Van Dooren", "Anthony", "", "Administrator", 2, true, "anthonyvandooren@gmail.com"));
					model.setActiveItem(new Item(null, "testtitel", "dit is een test item", new Gebruiker("Van Dooren", "Anthony", "", "Administrator", 8, true, "anthonyvandooren@gmail.com"), jsqlD,new Erfgoed(),"", "Afgekeurd", "", "Foto"));
					MailFrame frame = new MailFrame(model, false);
					frame.setVisible(true);
					Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
				    // bereken het midden van je scherm
				    int w = frame.getSize().width;
				    int h = frame.getSize().height;
				    int x = (size.width-w)/2;
				    int y = (size.height-h)/2;
				    
				    // verplaats de GUI
				    frame.setLocation(x, y);  
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MailFrame(Model m, Boolean wijzigen) {
		super("Mailsysteem");
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	    
	    // bereken het midden van je scherm
	    int w = getSize().width;
	    int h = getSize().height;
	    int x = (size.width-w)/2;
	    int y = (size.height-h)/2;
	    
	    // verplaats de GUI
	    setLocation(x, y);
		this.m = m;
		this.wijzigen = wijzigen;
		item = m.getActiveItem();
		if(wijzigen) {
			item.setStatus("Gewijzigd");
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnOpties = new JMenu("Opties");
		menuBar.add(mnOpties);
		JMenuItem mntmVerzenden = new JMenuItem("Verzenden");
		mnOpties.add(mntmVerzenden);
		mntmVerzenden.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				verzenden();
				
			}
		});
		
		JMenuItem mntmVoorbeeld = new JMenuItem("Voorbeeld");
		mnOpties.add(mntmVoorbeeld);
		mntmVoorbeeld.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				compile();
				
			}
		});
		
		JMenuItem mntmResetTekst = new JMenuItem("Reset tekst");
		mnOpties.add(mntmResetTekst);
		mntmResetTekst.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				preloader();
				
			}
		});
		
		JMenuItem mntmStandaardTekstBewerken = new JMenuItem("Standaard tekst bewerken");
		mnOpties.add(mntmStandaardTekstBewerken);
		mntmStandaardTekstBewerken.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				standaardbewerken();
				
			}
		});
		contentPane = new ImagePanel("mail.jpg");
		contentPane.setPreferredSize(new Dimension(600,600));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		editorPane = new JEditorPane();
		editorPane.setContentType("text/html");
		
		
		
		veld = new JScrollPane(editorPane);
		veld.setBorder(null);
		veld.setPreferredSize(new Dimension(384,152));
		veld.setBounds(28, 146, 545, 431);
		contentPane.add(veld);
		
		JLabel lblUKuntDe = new JLabel("U kunt de volgende shortcodes gebruiken: &titel, &auteur, &inzenddatum, &beschrijving, &admin en alle html codes ");
		lblUKuntDe.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblUKuntDe.setBounds(10, 84, 563, 14);
		contentPane.add(lblUKuntDe);
		preloader();
		pack();
	}
	
	public void preloader()
	{
		html = new HtmlHandler(getClass().getResourceAsStream("/" + item.getStatus().toLowerCase() +".html"),item, m);
		editorPane.setText(html.getText());
	}
	
	public String compile()
	{
		//title
		String content = editorPane.getText();
		content = content.replaceAll("\"", "'");
		String patternStr = "<td(\\s\\w+?[^=]*?='[^']*?')*?\\s+?class='(\\S+?\\s)*?titlecell(\\s\\S+?)*?'.*?>(.*?)</td>";
		// Compile and use regular expression
		Pattern pattern = Pattern.compile(patternStr, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(content);
		boolean matchFound = matcher.find();
		String title ="";
		if (matchFound) {
		        title = matcher.group(4);
		}
		title = title.trim();
		
		//contents
		String patternStr1 = "<td(\\s\\w+?[^=]*?='[^']*?')*?\\s+?class='(\\S+?\\s)*?contentscell(\\s\\S+?)*?'.*?>(.*?)</td>";
		// Compile and use regular expression
		Pattern pattern1 = Pattern.compile(patternStr1, Pattern.DOTALL);
		Matcher matcher1 = pattern1.matcher(content);
		boolean matchFound1 = matcher1.find();
		String contents ="";
		if (matchFound1) {
		        contents = matcher1.group(4);
		}
		contents = contents.trim().replaceAll("\r\n          ","");
		HtmlHandler htmltext = new HtmlHandler(getClass().getResourceAsStream("/" + item.getStatus().toLowerCase()+".html"),item, m);
		content = htmltext.getHtml();
		content = content.replaceAll("&title", title);
		content = content.replaceAll("&text", contents);
		content = content.replaceAll("&amp;", "&");
		content = content.replaceAll("&lt;", "<");
		content = content.replaceAll("&gt;", ">");
		//content = content.replaceAll("border-bottom-color: #FF0000;", "font-family: Arial;border-bottom-color: #FF0000;");
		//content = content.replaceFirst("padding-bottom: 0;", "font-weight: bold; color: #FFFFFF;padding-bottom: 0;");
		content = content.replaceAll("&titel", item.getTitel());
		content = content.replaceAll("&auteur", item.getAuteur().getNaam());
		content = content.replaceAll("&inzenddatum", item.getInzendDatum().toString());
		content = content.replaceAll("&beschrijving", item.getText());
		content = content.replaceAll("&admin", m.getAdmin().getNaam());
		JEditorPane htmlcontent = new JEditorPane();
		htmlcontent.setContentType("text/html");
		htmlcontent.setEditable(false);
		htmlcontent.setText(content);
		
		if(JOptionPane.showConfirmDialog(this, htmlcontent) != JOptionPane.YES_OPTION)
			return null;
		else
		return content;
	}
	
	public void verzenden()
	{
		String content = compile();
		if(content != null)
		{
		Gebruiker gebruiker = item.getAuteur();
		Mail mail = new Mail(m, gebruiker.getEmail(), "[Erfgoedbank Herzele] uw item werd " + item.getStatus().toLowerCase(), content);
		mail.send();
		}
	}
	
	public void standaardbewerken()
	{
		Object[] possibleValues = { "Goedgekeurd", "Afgekeurd", "Gewijzigd"};
		Object selectedValue = JOptionPane.showInputDialog(this, "Kies welke standaardtekst je wilt bewerken", "Mail", JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
		if(selectedValue != null) {
			String content = editorPane.getText();
			content = content.replaceAll("\"", "'");
			
			String patternStr1 = "<td(\\s\\w+?[^=]*?='[^']*?')*?\\s+?class='(\\S+?\\s)*?contentscell(\\s\\S+?)*?'.*?>(.*?)</td>";
			// Compile and use regular expression
			Pattern pattern1 = Pattern.compile(patternStr1, Pattern.DOTALL);
			Matcher matcher1 = pattern1.matcher(content);
			boolean matchFound1 = matcher1.find();
			String contents ="";
			if (matchFound1) {
			        contents = matcher1.group(4);
			}
			contents = contents.trim().replaceAll("\r\n          ","");
		m.setMailText(selectedValue.toString().toLowerCase(), contents);
		}
	}
}
