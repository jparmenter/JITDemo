import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.*;

public class Menu extends JFrame implements ActionListener
{
	public static int WIDTH = 600;
	public static int HEIGHT = 400;
	private JLabel errorLbl;
	private final User curr;
	public static jaklUtilities utility;

	public Menu(User _curr)
	{
		super("Just In Time Teaching");
		curr = _curr;
		utility = new jaklUtilities();

	
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


		jTab.add("Home", hPanel);
		jTab.add("Class View", cPanel);
		jTab.add("Account View", aPanel);

		if (curr.getStatus() == 'a')
		{
			JPanel cAccountPanel = create("Create Account");
			JPanel cClassPanel = create("Create Class");
			jTab.add("Create Account", cAccountPanel); 
			jTab.add("Create Class", cClassPanel);
		}
	}

	public JPanel create(String title)
	{
		JPanel createPanel = new JPanel();
		SpringLayout layout = new SpringLayout();
		createPanel.setLayout(layout);
		
		int x = 25;
		int y = 25;
		int inc = 45;

		JLabel createLbl = new JLabel(title);
		createPanel.add(createLbl);

		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(this);
		createPanel.add(logoutBtn);

		layout.putConstraint(SpringLayout.WEST, createLbl, x, SpringLayout.WEST, 
			createPanel);
		layout.putConstraint(SpringLayout.NORTH, createLbl, y, SpringLayout.NORTH, 
			createPanel); 

		y += inc;

		layout.putConstraint(SpringLayout.WEST, logoutBtn, (WIDTH-90), SpringLayout.WEST, 
			createPanel);
		layout.putConstraint(SpringLayout.NORTH, logoutBtn, (HEIGHT-110), SpringLayout.NORTH, 
			createPanel); 


		if (title.equals("Create Class"))
		{
			JLabel courseLbl = new JLabel("Course ID:");
			createPanel.add(courseLbl);

			JTextField courseTxt = new JTextField("", 10);
			createPanel.add(courseTxt);

			JLabel titleLbl = new JLabel("Course Title:");
			createPanel.add(titleLbl);

			JTextField titleTxt = new JTextField("", 10);
			createPanel.add(titleTxt);

			JLabel descLbl = new JLabel("Course Description:");
			createPanel.add(descLbl);

			JTextArea descTxt = new JTextArea(5, 20);

			descTxt.setWrapStyleWord(true);
			descTxt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			JScrollPane pane = new JScrollPane(descTxt);
			pane.setPreferredSize(new Dimension(200, 100));
			createPanel.add(pane);

			JButton createBtn = new JButton("Create Class");
			createBtn.addActionListener(this);
			createPanel.add(createBtn);

			layout.putConstraint(SpringLayout.WEST, courseLbl, (((WIDTH/2)/2)-50), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, courseLbl, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, courseTxt, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.WEST, courseTxt, 20, SpringLayout.EAST,
				courseLbl);	

			y += inc;
			
			layout.putConstraint(SpringLayout.WEST, titleLbl, (((WIDTH/2)/2)-50), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, titleLbl, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, titleTxt, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.WEST, titleTxt, 20, SpringLayout.EAST,
				titleLbl);	

			y += inc;
			
			layout.putConstraint(SpringLayout.WEST, descLbl, (((WIDTH/2)/2)-50), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, descLbl, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, pane, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.WEST, pane, 20, SpringLayout.EAST,
				descLbl);	

			y += inc;

			layout.putConstraint(SpringLayout.NORTH ,createBtn, (HEIGHT-110), SpringLayout.NORTH, 
				createPanel);
			layout.putConstraint(SpringLayout.EAST, createBtn, -5, SpringLayout.WEST, 
				logoutBtn);
			
		}
		else
		{
			JLabel nameLbl = new JLabel("Name:");
			createPanel.add(nameLbl);	

			JTextField nameTxt = new JTextField("", 10);	
			createPanel.add(nameTxt);

			String[] users = { "Admin", "Teacher", "Student"}; 

			JComboBox userList = new JComboBox(users);
			userList.setSelectedIndex(0);
			userList.addActionListener(this);
			createPanel.add(userList);

			JLabel passLbl = new JLabel("Password:");
			createPanel.add(passLbl);

			JPasswordField passTxt = new JPasswordField("", 10);
			createPanel.add(passTxt);

			JButton createBtn = new JButton("Create Account");
			createBtn.addActionListener(this);
			createPanel.add(createBtn);

			layout.putConstraint(SpringLayout.WEST, nameLbl, (((WIDTH/2)/2)-50), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, nameLbl, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, nameTxt, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.WEST, nameTxt, 20, SpringLayout.EAST,
				nameLbl);
			layout.putConstraint(SpringLayout.NORTH, userList, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.WEST, userList, 20, SpringLayout.EAST,
				nameTxt);

			y += inc;

			layout.putConstraint(SpringLayout.WEST, passLbl, (((WIDTH/2/2)-50)), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, passLbl, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, passTxt, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.WEST, passTxt, 20, SpringLayout.EAST,
				passLbl);

			y += inc;

			layout.putConstraint(SpringLayout.NORTH ,createBtn, (HEIGHT-110), SpringLayout.NORTH, 
				createPanel);
			layout.putConstraint(SpringLayout.EAST, createBtn, -5, SpringLayout.WEST, 
				logoutBtn);

		}

		errorLbl = new JLabel("");
		createPanel.add(errorLbl);

		layout.putConstraint(SpringLayout.WEST, errorLbl, ((WIDTH/2)-50), SpringLayout.WEST,
			createPanel);
		layout.putConstraint(SpringLayout.NORTH, errorLbl, y, SpringLayout.NORTH,
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

		JLabel titleLbl = new JLabel("Change Pass");
		aPanel.add(titleLbl);

		JLabel cPassLbl = new JLabel("Current Password:");
		aPanel.add(cPassLbl);	

		final JPasswordField cPassTxt = new JPasswordField("", 10);	
		aPanel.add(cPassTxt);

		JLabel nPassLbl = new JLabel("New Password:");
		aPanel.add(nPassLbl);

		final JPasswordField nPassTxt = new JPasswordField("", 10);
		aPanel.add(nPassTxt);
	
		errorLbl = new JLabel("");
		aPanel.add(errorLbl);

		JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev)
			{
				try
				{
					String oldPass = new String(cPassTxt.getPassword());
					String newPass = new String(nPassTxt.getPassword());
			
//					utility.changePass(curr.getId(), curr.getName(), oldPass, newPass);

					boolean flag = curr.changePass(oldPass, newPass);
				
					if (flag)
						errorLbl.setText("Change successful!");
					else
						errorLbl.setText("Incorrect Password");
				}
				catch (Exception ex)
				{
					errorLbl.setText("Incorrect Input");
				}
			}
		});

		aPanel.add(submitBtn);
		
		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(this);
		aPanel.add(logoutBtn);

		layout.putConstraint(SpringLayout.WEST, accountLbl, x, SpringLayout.WEST, 
			aPanel);
		layout.putConstraint(SpringLayout.NORTH, accountLbl, y, SpringLayout.NORTH, 
			aPanel); 

		y += inc;

		layout.putConstraint(SpringLayout.WEST, titleLbl, ((WIDTH/2)-50), SpringLayout.WEST,
			aPanel);
		layout.putConstraint(SpringLayout.NORTH, titleLbl, y, SpringLayout.NORTH,
			aPanel);

		y += inc;

		layout.putConstraint(SpringLayout.WEST, cPassLbl, (((WIDTH/2)/2)-50), SpringLayout.WEST,
			aPanel);
		layout.putConstraint(SpringLayout.NORTH, cPassLbl, y, SpringLayout.NORTH,
			aPanel);
		layout.putConstraint(SpringLayout.NORTH, cPassTxt, y, SpringLayout.NORTH,
			aPanel);
		layout.putConstraint(SpringLayout.WEST, cPassTxt, 20, SpringLayout.EAST,
			cPassLbl);

		y += inc;

		layout.putConstraint(SpringLayout.WEST, nPassLbl, (((WIDTH/2/2)-50)), SpringLayout.WEST,
			aPanel);
		layout.putConstraint(SpringLayout.NORTH, nPassLbl, y, SpringLayout.NORTH,
			aPanel);
		layout.putConstraint(SpringLayout.NORTH, nPassTxt, y, SpringLayout.NORTH,
			aPanel);
		layout.putConstraint(SpringLayout.WEST, nPassTxt, 20, SpringLayout.EAST,
			nPassLbl);

		y += inc;

		layout.putConstraint(SpringLayout.WEST, errorLbl, ((WIDTH/2)-50), SpringLayout.WEST,
			aPanel);
		layout.putConstraint(SpringLayout.NORTH, errorLbl, y, SpringLayout.NORTH,
			aPanel);

		layout.putConstraint(SpringLayout.WEST, logoutBtn, (WIDTH-90), SpringLayout.WEST, 
			aPanel);
		layout.putConstraint(SpringLayout.NORTH, logoutBtn, (HEIGHT-110), SpringLayout.NORTH, 
			aPanel);
		layout.putConstraint(SpringLayout.NORTH ,submitBtn, (HEIGHT-110), SpringLayout.NORTH, 
			aPanel);
		layout.putConstraint(SpringLayout.EAST, submitBtn, -5, SpringLayout.WEST, 
			logoutBtn);
 

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

		String list[] = {"22722 CSE 340 Intro to Software Engineering",
			"232424 CSE 205 Java Programming",
			"235353 CSE 240 Into to PL",
			"345454 CSE 310 Data Structs",
			"334433 CSE 205 Java Programming",
			"553355 CSE 120 Digital Design"};

		JList classList = new JList(list);
		classList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		classList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		classList.setLayoutOrientation(classList.VERTICAL);
		classList.setVisibleRowCount(4);
		classList.setSelectedIndex(-1);
		JScrollPane pane = new JScrollPane(classList);
		pane.setPreferredSize(new Dimension(300, 200));

		MouseListener mouseListener = new MouseAdapter() {
		      public void mouseClicked(MouseEvent mouseEvent) {
			JList theList = (JList) mouseEvent.getSource();
			if (mouseEvent.getClickCount() == 2) {
			  int index = theList.locationToIndex(mouseEvent.getPoint());
			  if (index >= 0) {
			    Object o = theList.getModel().getElementAt(index);
			    char [] input = o.toString().toCharArray();
			    int i = 0;
			    char c = input[i];
			    String s = "";
		
			    while (Character.isDigit(c))
			    {
				s += c;
				i++;
				c = input[i];	
			    }
			  
			    int id = Integer.parseInt(s);
			    System.out.println("Double-clicked on: " + id);
			    // Class tempClass = utility.openClass(option);
			  }
			}
		      }
		};

		classList.addMouseListener(mouseListener);

		cPanel.add(pane);

		JLabel insLbl = new JLabel("Double click a class to view");
		cPanel.add(insLbl);

		JButton logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(this);
		cPanel.add(logoutBtn);

		layout.putConstraint(SpringLayout.WEST, classLbl, x, SpringLayout.WEST, 
			cPanel);
		layout.putConstraint(SpringLayout.NORTH, classLbl, y, SpringLayout.NORTH, 
			cPanel); 

		y += inc;

		layout.putConstraint(SpringLayout.WEST, insLbl, (((WIDTH/2)/2)-50), SpringLayout.WEST,
			cPanel);
		layout.putConstraint(SpringLayout.NORTH, insLbl, y, SpringLayout.NORTH,
			cPanel);

		y += 25;
	
		layout.putConstraint(SpringLayout.WEST, pane, (((WIDTH/2)/2)-50), SpringLayout.WEST,
			cPanel);
		layout.putConstraint(SpringLayout.NORTH, pane, y, SpringLayout.NORTH,
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
		
		int x = 25;
		int y = 25;
		int inc = 45;
		JLabel homeLbl = new JLabel("Hello, " + curr.getName());
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
		}
		else
			System.out.println("Unexpected error.");	
	}
}
