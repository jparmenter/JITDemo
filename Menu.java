import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame implements ActionListener
{
	public static int WIDTH = 600;
	public static int HEIGHT = 500;

	public Menu()
	{
		super("Just In Time Teaching");
	
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

		JTabbedPane jTab = new JTabbedPane();
		getContentPane().add(jTab);

		JPanel hPanel = home();
		JPanel cPanel = course();
		JPanel aPanel = account();
		JPanel createPanel = create();

		jTab.add("Home", hPanel);
		jTab.add("Class View", cPanel);
		jTab.add("Account View", aPanel);
		jTab.add("Create View", createPanel); 

	}

	public JPanel create()
	{
		JPanel createPanel = new JPanel();
		SpringLayout layout = new SpringLayout();
		createPanel.setLayout(layout);
		
		int x = 25;
		int y = 25;
		int inc = 45;

		JLabel createLbl = new JLabel("Create View");
		createPanel.add(createLbl);

		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(this);
		createPanel.add(logoutBtn);

		layout.putConstraint(SpringLayout.WEST, createLbl, x, SpringLayout.WEST, 
			createPanel);
		layout.putConstraint(SpringLayout.NORTH, createLbl, y, SpringLayout.NORTH, 
			createPanel); 

		layout.putConstraint(SpringLayout.WEST, logoutBtn, (WIDTH-90), SpringLayout.WEST, 
			createPanel);
		layout.putConstraint(SpringLayout.NORTH, logoutBtn, (HEIGHT-110), SpringLayout.NORTH, 
			createPanel); 

		return createPanel;

	}

	public JPanel account()
	{
		JPanel aPanel = new JPanel();
		SpringLayout layout = new SpringLayout();
		aPanel.setLayout(layout);
		
		int x = 25;
		int y = 25;
		int inc = 45;

		JLabel accountLbl = new JLabel("Account Mangement");
		aPanel.add(accountLbl);

		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(this);
		aPanel.add(logoutBtn);

		layout.putConstraint(SpringLayout.WEST, accountLbl, x, SpringLayout.WEST, 
			aPanel);
		layout.putConstraint(SpringLayout.NORTH, accountLbl, y, SpringLayout.NORTH, 
			aPanel); 

		layout.putConstraint(SpringLayout.WEST, logoutBtn, (WIDTH-90), SpringLayout.WEST, 
			aPanel);
		layout.putConstraint(SpringLayout.NORTH, logoutBtn, (HEIGHT-110), SpringLayout.NORTH, 
			aPanel); 

		return aPanel;

	}

	public JPanel course()
	{
		JPanel cPanel = new JPanel();
		SpringLayout layout = new SpringLayout();
		cPanel.setLayout(layout);
		
		int x = 25;
		int y = 25;
		int inc = 45;

		JLabel classLbl = new JLabel("Class Menu");
		cPanel.add(classLbl);

		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(this);
		cPanel.add(logoutBtn);

		layout.putConstraint(SpringLayout.WEST, classLbl, x, SpringLayout.WEST, 
			cPanel);
		layout.putConstraint(SpringLayout.NORTH, classLbl, y, SpringLayout.NORTH, 
			cPanel); 

		layout.putConstraint(SpringLayout.WEST, logoutBtn, (WIDTH-90), SpringLayout.WEST, 
			cPanel);
		layout.putConstraint(SpringLayout.NORTH, logoutBtn, (HEIGHT-110), SpringLayout.NORTH, 
			cPanel); 


		return cPanel;
	}

	public JPanel home()
	{
		JPanel hPanel = new JPanel();
		SpringLayout layout = new SpringLayout();
		hPanel.setLayout(layout);
		
		String name = "Bob";
		int x = 25;
		int y = 25;
		int inc = 45;
		JLabel homeLbl = new JLabel("Hello, " + name);
		hPanel.add(homeLbl);

		JLabel announceLbl = new JLabel("Announcements:");
		hPanel.add(announceLbl);

		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(this);
		hPanel.add(logoutBtn);

		layout.putConstraint(SpringLayout.WEST, homeLbl, x, SpringLayout.WEST, 
			hPanel);
		layout.putConstraint(SpringLayout.NORTH, homeLbl, y, SpringLayout.NORTH, 
			hPanel); 

		y += inc;

		layout.putConstraint(SpringLayout.WEST, announceLbl, x, SpringLayout.WEST,
			hPanel);
		layout.putConstraint(SpringLayout.NORTH, announceLbl, y, SpringLayout.NORTH,
			hPanel);
		
		layout.putConstraint(SpringLayout.WEST, logoutBtn, (WIDTH-90), SpringLayout.WEST, 
			hPanel);
		layout.putConstraint(SpringLayout.NORTH, logoutBtn, (HEIGHT-110), SpringLayout.NORTH, 
			hPanel); 

		return hPanel;
	
	}

	public void actionPerformed(ActionEvent e)
	{
		String buttonString = e.getActionCommand();

		if (buttonString.equals("Logout"))
		{
			dispose();
			LoginFrame gui = new LoginFrame();
			gui.setVisible(true);
			//errorLbl.setText("Error: ");	
		}
		else
			System.out.println("Unexpected error.");	
	}
}
