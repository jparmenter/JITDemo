import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.*;
import javax.swing.event.*;

public class Menu extends JFrame 
	implements ActionListener
{
	public static int WIDTH = 600;
	public static int HEIGHT = 400;
	public static jaklUtilities utility;

	// Used for Errors!
	private JLabel errorLbl;

	// Used for changed Password
	private JLabel aErrorLbl;
	private JPasswordField cPassTxt;
	private JPasswordField nPassTxt;
	private final User curr;

	// Used for create Accounts
	private JTextField idTxt;
	private JTextField nameTxt;
	private JComboBox userList;
	private JTextField passTxt;
	private JLabel createAErrorLbl;

	// Used for create Classes
	private JTextField courseTxt;
	private JTextField titleTxt;
	private JTextArea descTxt;
	private JLabel createCErrorLbl;

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

		/*jTab.addMouseListener(new MouseAdapter() {
		      public void mouseClicked(MouseEvent mouseEvent) {
			if (mouseEvent.getClickCount() == 1) {
				tabChanged();
			}
		     }
		});*/	

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

			courseTxt = new JTextField("", 10);
			createPanel.add(courseTxt);

			JLabel titleLbl = new JLabel("Course Title:");
			createPanel.add(titleLbl);

			titleTxt = new JTextField("", 10);
			createPanel.add(titleTxt);

			JLabel descLbl = new JLabel("Course Description:");
			createPanel.add(descLbl);

			descTxt = new JTextArea(5, 20);

			descTxt.setWrapStyleWord(true);
			descTxt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			JScrollPane pane = new JScrollPane(descTxt);
			pane.setPreferredSize(new Dimension(200, 100));
			createPanel.add(pane);

			JButton createBtn = new JButton("Create Class");
			createBtn.addActionListener(this);
			createPanel.add(createBtn);

			createCErrorLbl = new JLabel("");
			createPanel.add(createCErrorLbl);

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

			layout.putConstraint(SpringLayout.WEST, createCErrorLbl, ((WIDTH/2)-50), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, createCErrorLbl, y, SpringLayout.NORTH,
				createPanel);
			
		}
		else
		{

			JLabel idLbl = new JLabel("ID:");
			createPanel.add(idLbl);
			
			idTxt = new JTextField("", 10);
			createPanel.add(idTxt);

			JLabel nameLbl = new JLabel("Name:");
			createPanel.add(nameLbl);	

			nameTxt = new JTextField("", 10);	
			createPanel.add(nameTxt);

			String[] users = { "Admin", "Teacher", "Student"}; 

			userList = new JComboBox(users);
			userList.setSelectedIndex(0);
			userList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ev)
				{
					comboBoxActionPerformed(ev);
				}
			});

			createPanel.add(userList);

			JLabel passLbl = new JLabel("Password:");
			createPanel.add(passLbl);

			passTxt = new JTextField("", 10);
			createPanel.add(passTxt);

			createAErrorLbl = new JLabel("");
			createPanel.add(createAErrorLbl);

			JButton createBtn = new JButton("Create Account");
			createBtn.addActionListener(this);
			createPanel.add(createBtn);

			layout.putConstraint(SpringLayout.WEST, idLbl, (((WIDTH/2)/2)-50), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, idLbl, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, idTxt, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.WEST, idTxt, 20, SpringLayout.EAST,
				nameLbl);
			layout.putConstraint(SpringLayout.NORTH, userList, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.WEST, userList, 20, SpringLayout.EAST,
				idTxt);

			y += inc;

			layout.putConstraint(SpringLayout.WEST, nameLbl, (((WIDTH/2)/2)-50), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, nameLbl, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, nameTxt, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.WEST, nameTxt, 20, SpringLayout.EAST,
				nameLbl);

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

			layout.putConstraint(SpringLayout.WEST, createAErrorLbl, ((WIDTH/2)-50), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, createAErrorLbl, y, SpringLayout.NORTH,
				createPanel);
		}

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

		cPassTxt = new JPasswordField("", 10);	
		aPanel.add(cPassTxt);

		JLabel nPassLbl = new JLabel("New Password:");
		aPanel.add(nPassLbl);

		nPassTxt = new JPasswordField("", 10);
		aPanel.add(nPassTxt);
	
		aErrorLbl = new JLabel("");
		aPanel.add(aErrorLbl);

		JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev)
			{
				passwordChangeActionPerformed(ev);	
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

		layout.putConstraint(SpringLayout.WEST, aErrorLbl, ((WIDTH/2)/2), SpringLayout.WEST,
			aPanel);
		layout.putConstraint(SpringLayout.NORTH, aErrorLbl, y, SpringLayout.NORTH,
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
		else if (buttonString.equals("Create Class"))
		{
		}
		else if (buttonString.equals("Create Account"))
		{
			Object selected = userList.getSelectedItem(); 
			String combo = selected.toString();
			
			try {
				int id = Integer.parseInt(idTxt.getText());
				String name = nameTxt.getText();
				String pass;	

				if (combo.equals("Admin"))
				{
					pass = passTxt.getText();
					Admin admin = new Admin(id, name, pass);
					//utility.writeAdmin(id, name, pass, 'a');
					createAErrorLbl.setText("Admin Created");
	
				} 
				else
				{
					pass = Long.toHexString(Double.doubleToLongBits(Math.random())); // Our Random pass generator*
					pass = pass.substring(0, 8);

					passTxt.setText(pass);

					if (combo.equals("Teacher"))
					{
						Teacher teacher = new Teacher(id, name, pass);
						//utility.writeTeacher(id, name, pass, 't');
						createAErrorLbl.setText("Teacher Created");
					}
					else
					{
						Student student = new Student(id, name, pass);
						//utility.writeStudent(id, name, pass, 's'); //Write student should change
						createAErrorLbl.setText("Student Created");
					}
				}
			}
			catch (Exception ex)
			{
				createAErrorLbl.setText("Incorrect Input");
			}
		}
		else
			System.out.println("Unexpected error.");	
	}

	private void passwordChangeActionPerformed(ActionEvent event)
	{
		try
		{
			String oldPass = new String(cPassTxt.getPassword());
			String newPass = new String(nPassTxt.getPassword());

			// DB	
			// utility.changePass(curr.getId(), curr.getName(), oldPass, newPass);

			// Needs a boolean to notify if change was successful or not
			// if (flag) 
			aErrorLbl.setText("Password Change Successful!");
			//else 
				//aErrorLbl.setText("Incorrect Password");
		}
		catch (Exception ex)
		{
			aErrorLbl.setText("Incorrect Input");
		}
	} 

	private void comboBoxActionPerformed(ActionEvent event)
	{
		Object selected = userList.getSelectedItem(); 
		String s = selected.toString();

		if ((s.compareTo("Admin")) == 0)
		{
			passTxt.setEditable(true);
		}
		else
		{
			passTxt.setText("");
			passTxt.setEditable(false);
		}
	}

	private void tabChanged()
	{
		System.out.println("Hello");
		aErrorLbl.setText("");
		cPassTxt.setText("");
		nPassTxt.setText("");

		if (curr.getStatus() == 'a')
		{
			nameTxt.setText("");
			userList.setSelectedIndex(0);
			passTxt.setText("");
			createAErrorLbl.setText("");
			courseTxt.setText("");
			titleTxt.setText("");
			descTxt.setText("");
			createCErrorLbl.setText("");
		}
	}
}
