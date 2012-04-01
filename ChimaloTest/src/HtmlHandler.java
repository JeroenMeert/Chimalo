import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;


public class HtmlHandler {

	private InputStream aFile;
	private String contents;
	private Item item;
	private Model m;
	private String texthtml;

	public HtmlHandler(InputStream aFile, Item item, Model m) {
		this.aFile = aFile;
		this.item = item;
		this.m = m;
		
	}
	
	public String getHtml() {
	    StringBuilder contents = new StringBuilder();
	    
	    try {
	      BufferedReader input =  new BufferedReader(new InputStreamReader(aFile));
	      try {
	        String line = null;
	        while (( line = input.readLine()) != null){
	          contents.append(line);
	          contents.append(System.getProperty("line.separator"));
	        }
	      }
	      finally {
	        input.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    
	    return contents.toString();
	  }
	
//	public void write(String aContents)
//            throws FileNotFoundException, IOException {
//		if (aFile == null) {
//			throw new IllegalArgumentException("File should not be null.");
//		}
//		if (!aFile.exists()) {
//			throw new FileNotFoundException ("File does not exist: " + aFile);
//		}
//		if (!aFile.isFile()) {
//			throw new IllegalArgumentException("Should not be a directory: " + aFile);
//		}
//		if (!aFile.canWrite()) {
//			throw new IllegalArgumentException("File cannot be written: " + aFile);
//		}
//
//		Writer output = new BufferedWriter(new OutputStreamWriter(aFile));
//		try {
//			output.write( aContents );
//		}
//		finally {
//			output.close();
//		}
//	}
	
	public String getText()
	{
		contents = getHtml();
		contents = contents.replaceAll("&title", "Uw inzending van " + item.getInzendDatum().toString() + " met als titel "+ item.getTitel() + " werd " + item.getStatus().toLowerCase());
		contents = contents.replaceAll("&text", m.getMailText(item.getStatus()) + "<br/>");
		texthtml = contents;
		return contents;
	}
	
	public void setText(String text)
	{
		m.setMailText(item.getStatus(), text);
	}

	public String getTexthtml() {
		return texthtml;
	}
	
}
