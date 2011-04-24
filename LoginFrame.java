import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame implements ActionListener
{
	public static final int WIDTH = 350;
	public static final int HEIGHT = 250;
	private JLabel errorLbl;

	public LoginFrame()
	{
		super("Just In Time Learning");
		int y = 10;
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}

		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		Container contentPane = getContentPane();

		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);

		JLabel titleLbl = new JLabel("Login Screen");
		contentPane.add(titleLbl);	

		JLabel idLbl = new JLabel("Id:");
		contentPane.add(idLbl);		

		JTextField idTxt = new JTextField("", 10);
		contentPane.add(idTxt);	

		JLabel passLbl = new JLabel("Password:");
		contentPane.add(passLbl);

		JPasswordField passTxt = new JPasswordField("", 10);	
		contentPane.add(passTxt);

		errorLbl = new JLabel("");
		contentPane.add(errorLbl);

		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(this);

		contentPane.add(loginBtn);	

		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(this);
		contentPane.add(closeBtn);

		layout.putConstraint(SpringLayout.WEST, titleLbl, ((WIDTH/2)-50), SpringLayout.WEST,
			contentPane);
		layout.putConstraint(SpringLayout.NORTH, titleLbl, y, SpringLayout.NORTH,
			contentPane); 
		y += 45;

		layout.putConstraint(SpringLayout.WEST, idLbl, (((WIDTH/2)/2)-50), SpringLayout.WEST,
			contentPane);
		layout.putConstraint(SpringLayout.NORTH, idLbl, y, SpringLayout.NORTH,
			contentPane);
		layout.putConstraint(SpringLayout.NORTH, idTxt, y, SpringLayout.NORTH,
			contentPane);
		layout.putConstraint(SpringLayout.WEST, idTxt, 20, SpringLayout.EAST,
			idLbl);

		y += 35;

		layout.putConstraint(SpringLayout.WEST, passLbl, (((WIDTH/2)/2)-50), SpringLayout.WEST,
			contentPane);
		layout.putConstraint(SpringLayout.NORTH, passLbl, y, SpringLayout.NORTH,
			contentPane);
		layout.putConstraint(SpringLayout.NORTH, passTxt, y, SpringLayout.NORTH,
			contentPane);
		layout.putConstraint(SpringLayout.WEST, passTxt, 20, SpringLayout.EAST,
			passLbl);

		y += 45;

		layout.putConstraint(SpringLayout.WEST, errorLbl, ((WIDTH/2)-50), SpringLayout.WEST,
			contentPane);
		layout.putConstraint(SpringLayout.NORTH, errorLbl, y, SpringLayout.NORTH,
			contentPane);

		y += 25;

		layout.putConstraint(SpringLayout.WEST, loginBtn, (WIDTH-75), SpringLayout.WEST, 
			contentPane);
		layout.putConstraint(SpringLayout.NORTH, loginBtn, (HEIGHT-75), SpringLayout.NORTH, 
			contentPane); 
		layout.putConstraint(SpringLayout.NORTH ,closeBtn, (HEIGHT-75), SpringLayout.NORTH, 
			contentPane);
		layout.putConstraint(SpringLayout.EAST, closeBtn, -5, SpringLayout.WEST, 
			loginBtn);
	}

	public void actionPerformed(ActionEvent e)
	{
		String buttonString = e.getActionCommand();

		if (buttonString.equals("Login"))
		{
			dispose();
			Menu gui = new Menu();
			gui.setVisible(true);
			//errorLbl.setText("Error: ");	
		}
		else
		if (buttonString.equals("Close"))
		{
			dispose();
			System.exit(0);
		}
		else
			System.out.println("Unexpected error.");	
	}
}
