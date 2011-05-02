/*
*	Login Panel, this is the first thing the user sees upon running the program
*
*
*
* Authors:
* Jeremy Parmenter
* Alex Holguin
* John Kevin Canez
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class LoginFrame extends JFrame implements ActionListener
{
	public final int WIDTH = 350;
	public final int HEIGHT = 250;
	private JLabel errorLbl;
	private final JTextField idTxt;
	private final JPasswordField passTxt;

	//Constructor sets the size of the Frame
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

		//Setting and placing variables

		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		Container contentPane = getContentPane();

		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);

		JLabel titleLbl = new JLabel("Login Screen");
		contentPane.add(titleLbl);

		JLabel idLbl = new JLabel("Id:");
		contentPane.add(idLbl);

		idTxt = new JTextField("", 10);
		contentPane.add(idTxt);

		JLabel passLbl = new JLabel("Password:");
		contentPane.add(passLbl);

		passTxt = new JPasswordField("", 10);
		contentPane.add(passTxt);

		errorLbl = new JLabel("");
		contentPane.add(errorLbl);

		JButton loginBtn = new JButton("Login");
		loginBtn.setMnemonic(KeyEvent.VK_ENTER);
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
		User curr = null;

		if (buttonString.equals("Login")) //When the user tries to login, check their userID name and password
		{


			try {
				int id = Integer.parseInt(idTxt.getText());
				String pass = new String(passTxt.getPassword());
				User temp;
				// With DB
				jaklUtilities utility = new jaklUtilities();

				temp = utility.openUser(id);

				if ((temp != null) && (pass.compareTo(temp.getPass()) == 0)) //Checks the info given against that in the database
					curr = temp;
				else
					errorLbl.setText("Id or Pass or incorrect");

				//If the login info is valid,take the user to the Menu
				if (curr != null)
				{
					dispose();
					Menu gui = new Menu(curr);
					gui.setVisible(true);
				}
				else
					errorLbl.setText("User does not exist"); //Meaningful error message
			}
			catch (Exception ex)
			{
				errorLbl.setText("Id or Pass or incorrect");
			}
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