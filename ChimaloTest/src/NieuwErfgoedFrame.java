import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;


public class NieuwErfgoedFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NieuwErfgoedFrame frame = new NieuwErfgoedFrame();
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
	public NieuwErfgoedFrame() {
		setTitle("Nieuw erfgoed");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 505, 257);
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
		
		textField = new JTextField();
		textField.setBounds(90, 37, 376, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(90, 80, 376, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(90, 126, 376, 20);
		contentPane.add(comboBox);
	}
}
