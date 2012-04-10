
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel
{
/*the default image to use*/
private String imageFile = null;
private Image image=null;
private boolean used = false;


public boolean isUsed() {
	return used;
}

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
	if(b != null)
	{
		used = true;
	}
}

public Image getFoto(){
	if(image!=null)
		return image;
	else{ ImageIcon imageIcon = new ImageIcon(getClass().getResource(imageFile));
	image = imageIcon.getImage();
	return image; }
}
public ImageIcon getIcon() {
	if(imageFile != null)
	return new ImageIcon(getClass().getResource(imageFile));
	else
		return new ImageIcon(image);
}

@Override
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


