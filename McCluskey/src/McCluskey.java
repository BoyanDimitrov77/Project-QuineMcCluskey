import java.awt.EventQueue;
import java.awt.im.InputContext;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JFormattedTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

public class McCluskey {

	public static List<String> InputListFromJTP = new ArrayList<String>();
	private JFrame frmMccluskey;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					McCluskey window = new McCluskey();
					window.frmMccluskey.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public McCluskey() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMccluskey = new JFrame();
		frmMccluskey.setTitle("McCluskey");
		frmMccluskey.setBounds(100, 100, 550, 350);
		frmMccluskey.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMccluskey.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("INPUT");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(74, 14, 370, 14);
		frmMccluskey.getContentPane().add(lblNewLabel);

		JTextPane textPane = new JTextPane();
		textPane.setBounds(216, 48, 101, 173);
		frmMccluskey.getContentPane().add(textPane);

		textPane.setEditable(true);

		JButton GoButton = new JButton("GO");
		GoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StepByStepResult dialog = new StepByStepResult();
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
				    @Override
				    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				        if (JOptionPane.showConfirmDialog(dialog, 
				            "Are you sure to close this window?", "Really Closing?", 
				            JOptionPane.YES_NO_OPTION,
				            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
				            System.exit(0);
				            
				        }
				    }
				});
				dialog.setModal(true);
				
				int flag = 0;

				String input[] = textPane.getText().split("\\s+");
				for (String part : input) {
					if (part.length() ==4  &&part.matches("[0-1]+") ) {

						InputListFromJTP.add(part);

					} else {

						JOptionPane.showMessageDialog(null, "please enter correct input");
						flag = 1;
						dialog.setVisible(false);

					}

				}

				if (!InputListFromJTP.isEmpty() && flag == 0) {
					dialog.setVisible(true);
				}
				
				textPane.setText(null);
				
			}
		});
		GoButton.setBounds(209, 237, 111, 23);
		frmMccluskey.getContentPane().add(GoButton);

		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(404, 39, 101, 173);
		frmMccluskey.getContentPane().add(scrollPane);
		
		JLabel lblMccluskey = new JLabel("");
		lblMccluskey.setIcon(new ImageIcon("C:\\Users\\skybo_000\\Pictures\\Logo.JPG"));
		lblMccluskey.setHorizontalAlignment(SwingConstants.CENTER);
		lblMccluskey.setBounds(10, 39, 384, 173);
		frmMccluskey.getContentPane().add(lblMccluskey);
	}
}
