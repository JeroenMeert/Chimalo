
import java.util.Properties;
 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class Mail {

	private Model m;
	private String to;
	private String subject;
	private String text;
	private String username;
	private String password;
	
	
	public Mail(Model m, String to, String subject, String text)
	{
		this.m = m;
		username = m.getMailUsername();
		password = m.getMailPassword();
		this.to = to;
		this.subject = subject;
		this.text = text;		
	}
	
	public void send()
	{
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", m.getSmtpHost());
		props.put("mail.smtp.port", m.getSmtpPort());
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(m.getMailFrom()));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
			message.setSubject(subject);
			message.setContent(text, "text/html" );
 
			Transport.send(message);
 
			JOptionPane.showMessageDialog(null, "De email is successvol verzonden aan " + to);
 
		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(null, "De mail kon niet verzonden worden aan " + to + "\n Foutmelding: " + e.getMessage());
		}
	}
}
