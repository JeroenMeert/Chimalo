import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.TextArea;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class NieuwItemFrame extends JFrame {

	private JPanel contentPane;
	private Model model;
	private JTextField txtTitel;
	private JTextField txtLink;
	private final String [] types ={"--Kies een type--","Foto","Postkaart","Brochure","Prent","Tekening","Andere"};
	private final String [] statuut={"--Kies een statuut--", "Geklasseerd", "Andere"};
	private NieuwItemFrame parent;
	private ImagePanel FotoPanel;
	private TextArea txtNuttigeInfo;
	private JComboBox cbType;
	private JComboBox cbStatuut;
	private JComboBox cbErfgoed;
	private TextArea txtGeschiedenis;
	public static void main(String []args){
		NieuwItemFrame n = new NieuwItemFrame(new Model("s"));
		n.setVisible(true);
		
	}
	
	public NieuwItemFrame(Model m) {
		model=m;
		parent=this;
		setTitle("Nieuw item");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 763, 668);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitel = new JLabel("Titel *");
		lblTitel.setBounds(21, 22, 108, 14);
		contentPane.add(lblTitel);
		
		JLabel lblErfgoed = new JLabel("Erfgoed *");
		lblErfgoed.setBounds(21, 72, 97, 14);
		contentPane.add(lblErfgoed);
		
		JLabel lblStatuut = new JLabel("Statuut *");
		lblStatuut.setBounds(21, 134, 135, 14);
		contentPane.add(lblStatuut);
		
		JLabel lblType = new JLabel("Type *");
		lblType.setBounds(21, 188, 97, 14);
		contentPane.add(lblType);
		
		JLabel lblGeschiedenis = new JLabel("Geschiedenis");
		lblGeschiedenis.setBounds(282, 22, 108, 14);
		contentPane.add(lblGeschiedenis);
		
		JLabel lblNuttigeInfo = new JLabel("Nuttige info");
		lblNuttigeInfo.setBounds(282, 300, 84, 14);
		contentPane.add(lblNuttigeInfo);
		
		JLabel lblLink = new JLabel("Link");
		lblLink.setBounds(21, 244, 46, 14);
		contentPane.add(lblLink);
		
		JLabel lblFoto = new JLabel("Media");
		lblFoto.setBounds(21, 300, 46, 14);
		contentPane.add(lblFoto);
		
		FotoPanel = new ImagePanel("/fotoToevoegen.png");
		FotoPanel.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				EditFrame frame = new EditFrame(model,parent);
				frame.setVisible(true);
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		FotoPanel.setBounds(21, 325, 228, 295);
		contentPane.add(FotoPanel);
		
		txtGeschiedenis = new TextArea();
		txtGeschiedenis.setBounds(282, 42, 319, 252);
		contentPane.add(txtGeschiedenis);
		
		txtNuttigeInfo = new TextArea();
		txtNuttigeInfo.setBounds(282, 325, 319, 295);
		contentPane.add(txtNuttigeInfo);
		
		cbType = new JComboBox(types);
		cbType.setBounds(21, 213, 228, 20);
		contentPane.add(cbType);
		
		cbStatuut = new JComboBox(statuut);
		cbStatuut.setBounds(21, 159, 228, 20);
		contentPane.add(cbStatuut);
		
		String [] e = new String [model.getErfgoeden().size()];

		cbErfgoed = new JComboBox();
		cbErfgoed.setBounds(21, 97, 228, 20);
		cbErfgoed.addItem("--Kies een erfgoed--");
		for (int i = 0;i<model.getErfgoeden().size();i++){
			cbErfgoed.addItem( model.getErfgoeden().get(i));
		}
		contentPane.add(cbErfgoed);
		
		txtTitel = new JTextField();
		txtTitel.setBounds(21, 41, 228, 20);
		contentPane.add(txtTitel);
		txtTitel.setColumns(10);
		
		txtLink = new JTextField();
		txtLink.setBounds(21, 269, 228, 20);
		contentPane.add(txtLink);
		txtLink.setColumns(10);
		
		JPanel pnlBewaren = new ImagePanel("/save-icon.png");
		pnlBewaren.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				if(txtTitel.getText()!="" && cbErfgoed.getSelectedIndex()!=0&&cbType.getSelectedIndex()!=0){
				Calendar cal = new GregorianCalendar() ;
				java.sql.Date jsqlD = new java.sql.Date( cal.getTime().getTime());
					
				Item i = new Item(txtTitel.getText(),model.getUser(),jsqlD,txtNuttigeInfo.getText(),String.valueOf(cbStatuut.getSelectedItem()),(BufferedImage)FotoPanel.getFoto(),String.valueOf(cbErfgoed.getSelectedItem()),txtGeschiedenis.getText(),txtLink.getText());
				model.schrijfNieuwItem(i);
				parent.dispose();}
				else
					JOptionPane.showMessageDialog(null,"Alle verplichte velden moeten ingevuld zijn voor dat u een nieuw item kan aanmaken");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		pnlBewaren.setBounds(618, 528, 108, 92);
		contentPane.add(pnlBewaren);
		
		JLabel lblBewaren = new JLabel("Bewaren");
		lblBewaren.setBounds(618, 502, 68, 14);
		contentPane.add(lblBewaren);
		
		ImagePanel pnlWissen = new ImagePanel("/icon-wissen.png");
		pnlWissen.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				txtGeschiedenis.setText("");
				txtTitel.setText("");
				txtNuttigeInfo.setText("");
				cbErfgoed.setSelectedIndex(0);
				cbStatuut.setSelectedIndex(0);
				cbType.setSelectedIndex(0);
				//setFoto("/search-icon");
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		pnlWissen.setBounds(618, 405, 108, 92);
		contentPane.add(pnlWissen);
		
		JLabel lblAllesWissen = new JLabel("Alles wissen");
		lblAllesWissen.setBounds(619, 374, 107, 14);
		contentPane.add(lblAllesWissen);
	}
	public void setFoto(BufferedImage bi){
		contentPane.remove(FotoPanel);
		FotoPanel=new ImagePanel(bi);
		FotoPanel.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				EditFrame frame = new EditFrame(model,parent);
				frame.setVisible(true);
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		FotoPanel.setBounds(21, 325, 228, 295);
		contentPane.add(FotoPanel);
		
		contentPane.repaint();
		
	}
	public void setFoto(String s){
		contentPane.remove(FotoPanel);
		FotoPanel=new ImagePanel(s);
		FotoPanel.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				EditFrame frame = new EditFrame(model,parent);
				frame.setVisible(true);
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		FotoPanel.setBounds(21, 325, 228, 295);
		contentPane.add(FotoPanel);
		contentPane.repaint();
		
	}
}
