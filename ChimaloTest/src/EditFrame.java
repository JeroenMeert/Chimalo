import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.io.File;
import java.io.IOException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class EditFrame extends JFrame{
	
	//wijziging
	 public final static String jpeg = "jpeg";
	    public final static String jpg = "jpg";
	    public final static String gif = "gif";
	    public final static String png = "png";
	    private NieuwItemFrame ParentFrame;
	    private String [] acceptableExtensions= new String[]{"jpeg","jpg","gif","png"};
	    private JFileChooser kiesFile;
	    private Model model;
	private JPanel btnPanel; 
	private ImagePanel imagePanel;
	private JButton btnWijzig; 
	private JButton btnSave;
	private JPanel hoofdPanel;
	private EditFrame parent;
	public EditFrame(Image bi, Model m){
		btnPanel = new JPanel();
		imagePanel=new ImagePanel();
		btnWijzig = new JButton("Wijzigen");
		parent=this;
		model=m;
		btnWijzig.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal=fc.showOpenDialog(null);
				
				if (returnVal==JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					if (checkExtension(file)){
						
						try {
							imagePanel.setNewFoto(ImageIO.read(file));
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
		btnSave = new JButton("Bewaren");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.getActiveItem().setFoto((BufferedImage)imagePanel.getFoto());
				model.overschrijfActive();
				model.notifyChangeListeners();
				parent.dispose();
			}
		});
		hoofdPanel = new JPanel();
		
		hoofdPanel.setLayout(new BorderLayout());
		btnPanel.setLayout(new GridLayout(1,2));
		btnPanel.add(btnWijzig);
		btnPanel.add(btnSave);
		hoofdPanel.add(btnPanel,BorderLayout.SOUTH);
		imagePanel.setNewFoto((BufferedImage)bi);
		hoofdPanel.add(imagePanel,BorderLayout.CENTER);
		getContentPane().add(hoofdPanel);
		setPreferredSize(new Dimension(500,500));
		pack();
	}
	

	public EditFrame(Model m,NieuwItemFrame nif){
		ParentFrame=nif;
		parent=this;
		btnPanel = new JPanel();
		imagePanel=new ImagePanel();
		//imagePanel.setNewFoto("/NoFoto.png");
		btnWijzig = new JButton("Wijzigen");
		parent=this;
		model=m;
		btnWijzig.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int returnVal=fc.showOpenDialog(null);
				
				if (returnVal==JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					if (checkExtension(file)){
						
						try {
							imagePanel.setNewFoto(ImageIO.read(file));
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						parent.repaint();
					}
					else {
						JOptionPane.showMessageDialog(null, "Het bestand dat u probeerde te openen heeft geen geldige extensie om als afbeelding toegevoegd te worden");
					}
				}
			}
		});
		btnSave = new JButton("Bewaren");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ParentFrame.setFoto((BufferedImage)imagePanel.getFoto());
				
				parent.dispose();
			}
		});
		hoofdPanel = new JPanel();
		
		hoofdPanel.setLayout(new BorderLayout());
		btnPanel.setLayout(new GridLayout(1,2));
		btnPanel.add(btnWijzig);
		btnPanel.add(btnSave);
		hoofdPanel.add(btnPanel,BorderLayout.SOUTH);
		//imagePanel.setNewFoto((BufferedImage)bi);
		hoofdPanel.add(imagePanel,BorderLayout.CENTER);
		getContentPane().add(hoofdPanel);
		setPreferredSize(new Dimension(450,490));
		pack();
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
	        	return true;
	        else return false;
	    }
}
