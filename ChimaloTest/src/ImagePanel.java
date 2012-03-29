
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel
{
/*the default image to use*/
String imageFile = null;
Image image=null;
public ImagePanel()
{
super();
}

public ImagePanel(String image)
{
super();
this.imageFile = image;
}

public ImagePanel(BufferedImage bi){
	super();
	image = bi;
	
}

public ImagePanel(LayoutManager layout)
{
super(layout);
}

public void setNewFoto(BufferedImage b){
	image = b;
	repaint();
}

public Image getFoto(){
	if(image!=null)
		return image;
	else{ ImageIcon imageIcon = new ImageIcon(getClass().getResource(imageFile));
	image = imageIcon.getImage();
	return image; }
}

public void paintComponent(Graphics g)
{
/*create image icon to get image*/
if (imageFile != null){
ImageIcon imageicon = new ImageIcon(getClass().getResource(imageFile));
image = imageicon.getImage();

}
/*Draw image on the panel*/
super.paintComponent(g);

if (image != null)
g.drawImage(image, 0, 0, getWidth(), getHeight(), this);


}
}
