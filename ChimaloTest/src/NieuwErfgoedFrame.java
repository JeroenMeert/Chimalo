import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class NieuwErfgoedFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtNaam;
	private JTextField txtLocatie;
	private JTextField txtLink;
	private String [] typ ={"--Kies een type--","Landschap","Dorpsgezicht","Gebouw","Archeologische site", "andere"};
	private Model model ; 
	private JTextArea textArea;
	private JTextArea textArea_1;
	private JComboBox cbType;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NieuwErfgoedFrame frame = new NieuwErfgoedFrame(new Model("Jeroen"));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NieuwErfgoedFrame(Model m) {
		setTitle("Nieuw erfgoed");
		model = m;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 864, 388);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNaam = new JLabel("Naam");
		lblNaam.setBounds(24, 40, 46, 14);
		contentPane.add(lblNaam);
		
		JLabel lblLocatie = new JLabel("Locatie");
		lblLocatie.setBounds(24, 83, 46, 14);
		contentPane.add(lblLocatie);
		
		JLabel lblType = new JLabel("Type");
		lblType.setBounds(24, 129, 46, 14);
		contentPane.add(lblType);
		
		txtNaam = new JTextField();
		txtNaam.setBounds(90, 37, 212, 20);
		contentPane.add(txtNaam);
		txtNaam.setColumns(10);
		
		txtLocatie = new JTextField();
		txtLocatie.setBounds(90, 80, 212, 20);
		contentPane.add(txtLocatie);
		txtLocatie.setColumns(10);
		
		cbType = new JComboBox(typ);
		cbType.setBounds(90, 126, 212, 20);
		contentPane.add(cbType);
		
		JButton btnBewaar = new JButton("Bewaar");
		btnBewaar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtLink.getText()!=""&&txtLocatie.getText()!=""&&txtNaam.getText()!=""&&cbType.getSelectedIndex()!=0&&textArea.getText()!=""&&textArea_1.getText()!=""){
				Erfgoed er = new Erfgoed(txtNaam.getText(),txtLocatie.getText(),String.valueOf(cbType.getSelectedItem()),txtLink.getText(),textArea.getText(),textArea_1.getText());
				model.schrijfNieuwErfgoed(er);
				}
				else
					JOptionPane.showMessageDialog(null,"U moet alle velden invullen om een nieuw erfgoed op te slaan");
			}
		});
		btnBewaar.setBounds(24, 269, 113, 23);
		contentPane.add(btnBewaar);
		
		JButton btnWis = new JButton("Wissen");
		btnWis.setBounds(174, 269, 129, 23);
		contentPane.add(btnWis);
		
		JLabel lblGeschiedenis = new JLabel("Geschiedenis");
		lblGeschiedenis.setBounds(341, 21, 145, 14);
		contentPane.add(lblGeschiedenis);
		
		JLabel lblNuttigeInfo = new JLabel("Nuttige info");
		lblNuttigeInfo.setBounds(595, 21, 262, 14);
		contentPane.add(lblNuttigeInfo);
		
		textArea = new JTextArea();
		textArea.setBounds(340, 40, 217, 279);
		contentPane.add(textArea);
		
		textArea_1 = new JTextArea();
		textArea_1.setBounds(595, 40, 217, 279);
		contentPane.add(textArea_1);
		
		JLabel lblLink = new JLabel("Link");
		lblLink.setBounds(24, 171, 46, 14);
		contentPane.add(lblLink);
		
		txtLink = new JTextField();
		txtLink.setBounds(90, 168, 212, 20);
		contentPane.add(txtLink);
		txtLink.setColumns(10);
	}
}
