import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.io.File;
import java.io.IOException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class EditFrame extends JFrame{
	
	//wijziging
	 public final static String jpeg = "jpeg";
	 public final static String jpg = "jpg";
	 public final static String gif = "gif";
	 public final static String png = "png";
	 private String [] acceptableExtensions= new String[]{"jpeg","jpg","gif","png"};
	 private JFileChooser kiesFile;
	 private Model model;
	 private JPanel btnPanel; 
	 private ImagePanel imagePanel;
	 private JButton btnOpslaan; 
	 private JButton btnSave;
	 private JPanel hoofdPanel;
	 private EditFrame parent;
	 private String extentie;
	 private BufferedImage img;
	 
	 public EditFrame(final Image bi, Model m, String ext){
		super("Afbeelding");
		ImageIcon imageIcon = new ImageIcon(getClass().getResource("logo1.png"));
		Image image = imageIcon.getImage();
		setIconImage(image);
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				model.setVensterOpen(false);
			}
			
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		btnPanel = new JPanel();
		imagePanel=new ImagePanel((BufferedImage)bi);
		btnOpslaan = new JButton("Opslaan");
		extentie= m.getActiveItem().getExtentie();
		btnOpslaan.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser c = new JFileChooser();
		        int x = c.showSaveDialog(parent);
		        if (x == JFileChooser.APPROVE_OPTION) {
		        	File f = c.getSelectedFile();
		        	String filePath = f.getPath();
		        	BufferedImage b = (BufferedImage) bi;
		            File outputfile = new File(filePath + "." + extentie);
		            try {
						ImageIO.write(b, extentie, outputfile);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
				
			}
		});
		parent=this;
		model=m;
		imagePanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser fc = new JFileChooser();
				int returnVal=fc.showOpenDialog(null);
				
				if (returnVal==JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					if (checkExtension(file)){
						
						try {
							BufferedImage image = ImageIO.read(file);
							extentie = getExtension(file);
							parent.dispose();
							EditFrame frame = new EditFrame(image,model, extentie);
							frame.setVisible(true);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						//parent.repaint();
					}
					else {
						JOptionPane.showMessageDialog(null, "Het bestand dat u probeerde te openen heeft geen geldige extensie om als afbeelding toegevoegd te worden");
					}
				}
			}
		});
		btnSave = new JButton("Ok");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(imagePanel.isUsed())
				{
					model.getActiveItem().setFoto((BufferedImage)imagePanel.getFoto());
					model.getActiveItem().setExtentie(extentie);
					model.setNieuweAfbeelding(true);
					model.notifyChangeListeners();
				}
				model.setVensterOpen(false);
				parent.dispose();
			}
		});
		hoofdPanel = new JPanel();
		
		hoofdPanel.setLayout(new BorderLayout());
		btnPanel.setLayout(new GridLayout(1,2));
		btnPanel.add(btnOpslaan);
		btnPanel.add(btnSave);
		hoofdPanel.add(btnPanel,BorderLayout.SOUTH);
		BufferedImage b = (BufferedImage) bi;
		int breedte = b.getWidth();
		int hoogte = b.getHeight();
		while((breedte > 1000) || (hoogte > 700))
		{
			breedte /= 1.5;
			hoogte /= 1.5;
		}
		imagePanel.setPreferredSize(new Dimension(breedte, hoogte));
		hoofdPanel.add(imagePanel,BorderLayout.CENTER);
		getContentPane().add(hoofdPanel);
		pack();
		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		 
		// Determine the new location of the window
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		 
		// Move the window
		setLocation(x, y);
	}
	    public boolean checkExtension(File f) {
	    	boolean found=false;
	        String ext = null;
	        String s = f.getName();
	        int i = s.lastIndexOf('.');

	        if (i > 0 &&  i < s.length() - 1) {
	            ext = s.substring(i+1).toLowerCase();
	        }
	        if (ext!=null){
	        	for (String str : acceptableExtensions){
	        		if (str.equals(ext)){
	        			found=true;
	        		}
	        	}
	        }
	        if (found)
	        {
	        	extentie = ext;
	        	return true;
	        }
	        else return false;
	    }
	    public String getExtension(File f)
	    {
	    String ext = null;
	    String s = f.getName();
	    int i = s.lastIndexOf('.');

	    if (i > 0 && i < s.length() - 1)
	    ext = s.substring(i+1).toLowerCase();

	    if(ext == null)
	    return "";
	    return ext;
	    }
}
