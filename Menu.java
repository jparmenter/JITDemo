/*
*	Main menu Class. main GUI. This is where the user will spend most of their time while not taking a quiz
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
import java.*;
import javax.swing.event.*;

public class Menu extends JFrame implements ActionListener
{
	public static int WIDTH = 600;
	public static int HEIGHT = 400;
	public static jaklUtilities utility;
	private final User curr;
	private final Class currClass;

	// Used for changed Password
	private JLabel aErrorLbl;
	private JPasswordField cPassTxt;
	private JPasswordField nPassTxt;


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
	private JLabel teacherLbl;
	private JTextField teacherTxt;
	private JLabel createCErrorLbl;

	// Used for create Quiz
	private JTextField qTitleTxt;
	private JComboBox numList;

	// Used for create Students
	private JTextField studentTxt;
	private JLabel createSErrorLbl;

	//Menu, given the current user logged in and is not viewing a specific class
	public Menu(User _curr)
	{
		super("Just In Time Teaching");
		currClass = null;
		curr = _curr;
		utility = new jaklUtilities();

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //makes the GUI look natural on the os
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		//housekeeping
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		JTabbedPane jTab = new JTabbedPane();
		getContentPane().add(jTab);

		//Main menu split into 2 parts, Main menu and Class Menu. class deals strictly within ONE class
		JPanel hPanel = home("Main Menu");
		JPanel cPanel = view("Class Menu");
		JPanel aPanel = account();

		//All users have access to this functionality
		jTab.add("Home", hPanel);
		jTab.add("Class View", cPanel);
		jTab.add("Account View", aPanel);

		//Admins have access to more functionality (Creating new Accounts and new Classes)
		if (curr.getStatus() == 'a')
		{
			JPanel cAccountPanel = create("Create Account");
			JPanel cClassPanel = create("Create Class");
			jTab.add("Create Account", cAccountPanel);
			jTab.add("Create Class", cClassPanel);
		}
		//Allows us to clear all the fields when the tab is changed
		jTab.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			tabChanged();
		}});

	}

	//This is the Class Menu for when the user is "viewing" a specific class
	public Menu(Class _currClass, User _curr)
	{
		super("Just In Time Teaching");
		currClass = _currClass;
		curr = _curr;

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		//Housekeeping
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		JTabbedPane jTab = new JTabbedPane();
		getContentPane().add(jTab);

		JPanel hPanel = home(currClass.getTitle());
		JPanel qPanel = view("Quiz Menu");
		JPanel gPanel = grades();

		jTab.add("Home", hPanel);
		jTab.add("Quiz View", qPanel);

		//again, teachers and admins have more functionality here
		if (curr.getStatus() != 's')
		{
			JPanel cQuizPanel = create("Create Quiz");
			jTab.add("Create Quiz", cQuizPanel);
			JPanel cCreatePanel = create("Add Student");
			jTab.add("Add Student", cCreatePanel);
		}

		jTab.add("Grade View", gPanel);

		jTab.addChangeListener(new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			tabChanged();
		}});
	}

	//Panel behind addStudent and Create Class
	public JPanel create(String title)
	{
		JPanel createPanel = new JPanel();
		SpringLayout layout = new SpringLayout();
		createPanel.setLayout(layout);

		//These vars are for incrementing placement of components on the Panel
		int x = 25;
		int y = 25;
		int inc = 45;

		JLabel createLbl = new JLabel(title);
		createPanel.add(createLbl);

		//ALL layout constraints are responsible for placing the panels in the right spot on the UI
		layout.putConstraint(SpringLayout.WEST, createLbl, x, SpringLayout.WEST,
			createPanel);
		layout.putConstraint(SpringLayout.NORTH, createLbl, y, SpringLayout.NORTH,
			createPanel);

		y += inc;


		if (title.equals("Create Class"))
		{

			//Labels and text boxes assciated with creating a Class
			JLabel courseLbl = new JLabel("Course ID:");
			createPanel.add(courseLbl);

			//Adding the field for the Course Name, same process done on next few lines
			courseTxt = new JTextField("", 10);
			createPanel.add(courseTxt);

			JLabel titleLbl = new JLabel("Course Title:");
			createPanel.add(titleLbl);

			titleTxt = new JTextField("", 10);
			createPanel.add(titleTxt);

			JLabel descLbl = new JLabel("Course Description:");
			createPanel.add(descLbl);

			descTxt = new JTextArea(5, 20);

			descTxt.setWrapStyleWord(true);   //This allows the text to wrap within the Description Text Area
			descTxt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			JScrollPane pane = new JScrollPane(descTxt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                       JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			pane.setPreferredSize(new Dimension(200, 50));
			createPanel.add(pane);

			teacherLbl = new JLabel("Enter a Teacher ID:");
			createPanel.add(teacherLbl);

			teacherTxt = new JTextField("", 10);
			createPanel.add(teacherTxt);

			JButton createBtn = new JButton("Create Class");
			createBtn.addActionListener(this);
			createPanel.add(createBtn);

			JButton logoutBtn = new JButton("Logout");
			logoutBtn.addActionListener(this);
			createPanel.add(logoutBtn);
;

			createCErrorLbl = new JLabel("");
			createPanel.add(createCErrorLbl);

			//These constraits Only place things on the panel
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

			y += inc + 25;

			layout.putConstraint(SpringLayout.WEST, teacherLbl, (((WIDTH/2)/2)-50), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, teacherLbl, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, teacherTxt, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.WEST, teacherTxt, 20, SpringLayout.EAST,
				teacherLbl);

			y += inc;

			layout.putConstraint(SpringLayout.NORTH ,createBtn, (HEIGHT-110), SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.EAST, createBtn, -5, SpringLayout.WEST,
				logoutBtn);

			layout.putConstraint(SpringLayout.WEST, createCErrorLbl, ((WIDTH/2)-50), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, createCErrorLbl, y, SpringLayout.NORTH,
				createPanel);

			layout.putConstraint(SpringLayout.WEST, logoutBtn, (WIDTH-90), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, logoutBtn, (HEIGHT-110), SpringLayout.NORTH,
				createPanel);

		}
		else if (title.equals("Create Account"))
		{
			//Labels and text boxes for creating an account

			JLabel idLbl = new JLabel("ID:");
			createPanel.add(idLbl);

			idTxt = new JTextField("", 10);
			createPanel.add(idTxt);

			JLabel nameLbl = new JLabel("Name:");
			createPanel.add(nameLbl);

			nameTxt = new JTextField("", 10);
			createPanel.add(nameTxt);

			String[] users = { "Admin", "Teacher", "Student"}; //The type of accounts the Admin can create

			//Upon user creation admins can create any type of user
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

			JButton logoutBtn = new JButton("Logout");
			logoutBtn.addActionListener(this);
			createPanel.add(logoutBtn);


			//Responsible for putting components on the Panel
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

			layout.putConstraint(SpringLayout.WEST, logoutBtn, (WIDTH-90), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, logoutBtn, (HEIGHT-110), SpringLayout.NORTH,
				createPanel);

			layout.putConstraint(SpringLayout.WEST, createAErrorLbl, ((WIDTH/2)-50), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, createAErrorLbl, y, SpringLayout.NORTH,
				createPanel);
		}
		else if (title.equals("Add Student"))
		{
			//Everything associated with adding a Student
			JLabel id = new JLabel("Student ID: ");
			createPanel.add(id);

			studentTxt = new JTextField("", 10);
			createPanel.add(studentTxt);


			createSErrorLbl = new JLabel("");
			createPanel.add(createSErrorLbl);

			JButton addBtn = new JButton("Add");
			addBtn.addActionListener(this);
			createPanel.add(addBtn);

			JButton backBtn = new JButton("Main Menu");
			backBtn.addActionListener(this);
			createPanel.add(backBtn);

			layout.putConstraint(SpringLayout.WEST, id, (((WIDTH/2)/2)-50), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, id, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, studentTxt, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.WEST, studentTxt, 20, SpringLayout.EAST,
				id);

			layout.putConstraint(SpringLayout.NORTH ,addBtn, (HEIGHT-110), SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.EAST, addBtn, -5, SpringLayout.WEST,
				backBtn);

			y += inc;

			layout.putConstraint(SpringLayout.WEST, backBtn, (WIDTH-115), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, backBtn, (HEIGHT-110), SpringLayout.NORTH,
				createPanel);

			layout.putConstraint(SpringLayout.WEST, createSErrorLbl, ((WIDTH/2)-50), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, createSErrorLbl, y, SpringLayout.NORTH,
			createPanel);


		}
		else
		{
			//Everything associated with creating a quiz
			JLabel titleLbl = new JLabel("Quiz Number:");
			createPanel.add(titleLbl);

			qTitleTxt = new JTextField("", 15);
			createPanel.add(qTitleTxt);

			JLabel questionLbl = new JLabel("How many questions:");
			createPanel.add(questionLbl);

			int i = 1;
			String [] num = new String[99];

			//For dropdown box for number of questions

			while ((i-1) < num.length)
			{
				num[(i-1)] = Integer.toString(i);
				i++;
			}

			numList = new JComboBox(num);
			numList.setSelectedIndex(0);

			createPanel.add(numList);

			JButton contBtn = new JButton("Continue");
			contBtn.addActionListener(this);
			createPanel.add(contBtn);

			JButton backBtn = new JButton("Main Menu");
			backBtn.addActionListener(this);
			createPanel.add(backBtn);

			//Place the above components on the panel
			layout.putConstraint(SpringLayout.WEST, titleLbl, (((WIDTH/2/2)-50)), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, titleLbl, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, qTitleTxt, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.WEST, qTitleTxt, 20, SpringLayout.EAST,
				titleLbl);

			y += inc;

			layout.putConstraint(SpringLayout.WEST, questionLbl, (((WIDTH/2/2)-50)), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, questionLbl, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, numList, y, SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.WEST, numList, 20, SpringLayout.EAST,
				questionLbl);

			y += inc;

			layout.putConstraint(SpringLayout.NORTH , contBtn, (HEIGHT-110), SpringLayout.NORTH,
				createPanel);
			layout.putConstraint(SpringLayout.EAST, contBtn, -5, SpringLayout.WEST,
				backBtn);

			layout.putConstraint(SpringLayout.WEST, backBtn, (WIDTH-115), SpringLayout.WEST,
				createPanel);
			layout.putConstraint(SpringLayout.NORTH, backBtn, (HEIGHT-110), SpringLayout.NORTH,
				createPanel);
		}

		return createPanel;

	}

	public JPanel account()
	{
		//Panel associated with changing password

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

		//constraints put components in the proper spot

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

		layout.putConstraint(SpringLayout.WEST, aErrorLbl, ((WIDTH/2)-50), SpringLayout.WEST,
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

	//returns a panel that users can double click on (used in class and quiz selection)
	public JPanel view(String title)
	{
		JPanel cPanel = new JPanel();
		SpringLayout layout = new SpringLayout();
		cPanel.setLayout(layout);

		int x = 25;
		int y = 25;
		int inc = 45;

		JLabel titleLbl = new JLabel(title);
		cPanel.add(titleLbl);

		JLabel insLbl = new JLabel("Double click to view");
		cPanel.add(insLbl);

		JScrollPane pane = new JScrollPane();
		pane.setPreferredSize(new Dimension(300, 200));
		cPanel.add(pane);

		// If the selected panel requires a list of classes

		if (title.equals("Class Menu"))
		{
			//Thing below are used to set a JList to the current classes being taken or taught by the user

			int tempSize = curr.getNumClasses();

			String list[] = utility.showClasses(curr.getId());

			JList classList = new JList(list);
			classList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			classList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			classList.setLayoutOrientation(classList.VERTICAL);
			classList.setVisibleRowCount(4);
			classList.setSelectedIndex(-1);
			pane.setViewportView(classList);

			//Listener for the double click event, whatever you click on will be instanciated and opened
			classList.addMouseListener( new MouseAdapter() {
			      public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 2) {
				  int index = theList.locationToIndex(mouseEvent.getPoint());
				  if (index >= 0) {
				    Object o = theList.getModel().getElementAt(index);
				    String strId = o.toString();
				    int id = Integer.parseInt(strId);

				    utility.sendClassId(id);
				    Class tempClass = utility.openClass(id);
				    dispose();
				    Menu classView = new Menu(tempClass, curr);
				    classView.setVisible(true);
				  }
				}
			      }
			});

			JButton logoutBtn = new JButton("Logout");
			logoutBtn.addActionListener(this);
			cPanel.add(logoutBtn);

			layout.putConstraint(SpringLayout.WEST, logoutBtn, (WIDTH-90), SpringLayout.WEST,
				cPanel);
			layout.putConstraint(SpringLayout.NORTH, logoutBtn, (HEIGHT-110), SpringLayout.NORTH,
				cPanel);
		}
		else
		{
			//Does same as above but with quizIds

			String list[] = utility.showQuizes(utility.getClassId());

			JList quizList = new JList(list);
			quizList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			quizList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			quizList.setLayoutOrientation(quizList.VERTICAL);
			quizList.setVisibleRowCount(4);
			quizList.setSelectedIndex(-1);
			pane.setViewportView(quizList);

			//Listener for the double click
			quizList.addMouseListener( new MouseAdapter() {
			      public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 2) {
				  int index = theList.locationToIndex(mouseEvent.getPoint());
				  if (index >= 0) {
				    Object o = theList.getModel().getElementAt(index);
				    String s = o.toString();
					int id = Integer.parseInt(s);
				    System.out.println("Double-clicked on: " + s);
				    dispose();
				    Quiz quiz = utility.openQuiz(id);
				    QuizFrame gui = new QuizFrame(quiz, currClass, curr);
				    gui.setVisible(true);

				  }}
			      }
			});

			//backBtn takes you to the main menu

			JButton backBtn = new JButton("Main Menu");
			backBtn.addActionListener(this);
			cPanel.add(backBtn);

			layout.putConstraint(SpringLayout.WEST, backBtn, (WIDTH-115), SpringLayout.WEST,
				cPanel);
			layout.putConstraint(SpringLayout.NORTH, backBtn, (HEIGHT-110), SpringLayout.NORTH,
				cPanel);


		}



		layout.putConstraint(SpringLayout.WEST, titleLbl, x, SpringLayout.WEST,
			cPanel);
		layout.putConstraint(SpringLayout.NORTH, titleLbl, y, SpringLayout.NORTH,
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


		return cPanel;
	}

	//Home panel is where the user goes prior to login, here is where announcements are displayed

	public JPanel home(String home)
	{
		JPanel hPanel = new JPanel();
		SpringLayout layout = new SpringLayout();
		hPanel.setLayout(layout);

		int x = 25;
		int y = 25;
		int inc = 45;

		JLabel homeLbl = new JLabel(home);
		hPanel.add(homeLbl);

		JLabel helloLbl = new JLabel("Hello, " + curr.getName());
		hPanel.add(helloLbl);

		JLabel announceLbl = new JLabel("Announcements:");
		hPanel.add(announceLbl);

		if (home.equals("Main Menu"))
		{
			JButton logoutBtn = new JButton("Logout");
			logoutBtn.addActionListener(this);
			hPanel.add(logoutBtn);

			layout.putConstraint(SpringLayout.WEST, logoutBtn, (WIDTH-90), SpringLayout.WEST,
				hPanel);
			layout.putConstraint(SpringLayout.NORTH, logoutBtn, (HEIGHT-110), SpringLayout.NORTH,
				hPanel);
		}
		else
		{
			JButton backBtn = new JButton("Main Menu");
			backBtn.addActionListener(this);
			hPanel.add(backBtn);

			layout.putConstraint(SpringLayout.WEST, backBtn, (WIDTH-115), SpringLayout.WEST,
				hPanel);
			layout.putConstraint(SpringLayout.NORTH, backBtn, (HEIGHT-110), SpringLayout.NORTH,
				hPanel);
		}

		layout.putConstraint(SpringLayout.WEST, homeLbl, x, SpringLayout.WEST,
			hPanel);
		layout.putConstraint(SpringLayout.NORTH, homeLbl, y, SpringLayout.NORTH,
			hPanel);

		y += inc;

		layout.putConstraint(SpringLayout.WEST, helloLbl, x, SpringLayout.WEST,
			hPanel);
		layout.putConstraint(SpringLayout.NORTH, helloLbl, y, SpringLayout.NORTH,
			hPanel);
		y += inc;

		layout.putConstraint(SpringLayout.WEST, announceLbl, x, SpringLayout.WEST,
			hPanel);
		layout.putConstraint(SpringLayout.NORTH, announceLbl, y, SpringLayout.NORTH,
			hPanel);



		return hPanel;
	}


	// This is the panel that displays the users grades

	public JPanel grades()
	{
		JPanel gradesPanel = new JPanel();
		SpringLayout layout = new SpringLayout();
		gradesPanel.setLayout(layout);

		int x = 25;
		int y = 25;
		int inc = 45;

		JLabel titleLbl;
		JLabel qLbl;

		//Student and tacher grades are displated differently

		if(curr.getStatus() == 's')
		{
		titleLbl = new JLabel("Grades");
		gradesPanel.add(titleLbl);

		qLbl = new JLabel("QuizNumber");
		gradesPanel.add(qLbl);
		}
		else
		{
		titleLbl = new JLabel("Grades");
		gradesPanel.add(titleLbl);

		qLbl = new JLabel("QuizNumber-StudentID");
		gradesPanel.add(qLbl);
		}

		JLabel gLbl = new JLabel("Grades");
		gradesPanel.add(gLbl);

		JButton backBtn = new JButton("Main Menu");
		backBtn.addActionListener(this);
		gradesPanel.add(backBtn);


		layout.putConstraint(SpringLayout.WEST, titleLbl, x, SpringLayout.WEST,
			gradesPanel);
		layout.putConstraint(SpringLayout.NORTH, titleLbl, y, SpringLayout.NORTH,
			gradesPanel);

		y += inc;
		x = 100;

		layout.putConstraint(SpringLayout.WEST, qLbl, x , SpringLayout.WEST,
			gradesPanel);
		layout.putConstraint(SpringLayout.NORTH, qLbl, y, SpringLayout.NORTH,
			gradesPanel);
		layout.putConstraint(SpringLayout.NORTH, gLbl, y, SpringLayout.NORTH,
			gradesPanel);
		layout.putConstraint(SpringLayout.WEST, gLbl, 100, SpringLayout.EAST,
			qLbl);

		y += inc;

		grades(gradesPanel, layout, null, x, y);

		layout.putConstraint(SpringLayout.WEST, backBtn, (WIDTH-115), SpringLayout.WEST,
			gradesPanel);
		layout.putConstraint(SpringLayout.NORTH, backBtn, (HEIGHT-110), SpringLayout.NORTH,
			gradesPanel);

		return gradesPanel;
	}




//this retuns a panel with an actual list of grades, as opposed to above which is the outer shell

	public JPanel grades(JPanel panel, SpringLayout layout, User student, int x, int y)
	{

					JPanel tempPanel = new JPanel();
					int[] tempArray = curr.getClassList(); //populates an array with the current list of classes

					int i = 0;

					if(currClass.getNumQuizzes() > 0)
					{
						if(curr.getStatus() != 's')
						{
							//Teachers need to be able to see all the scores in their class

							int tempCount = utility.getQuizzesTaken(currClass.getId()); //instanciated with the total number of quizzes taken

							JLabel[] qTitleLbl = new JLabel[tempCount];
							JLabel [] qGradeLbl = new JLabel[tempCount];

							//while loop just sets different components on the Panel
							while(i < tempCount)
								{
									//While loop sets the information to be displayed and puts it on the panel

									//int tempId = Integer.parseInt(utility.getTeacherNameInfo(currClass.getId(), i+1));
									//tempId -= currClass.getId();
									qTitleLbl[i] = new JLabel(utility.getTeacherNameInfo(currClass.getId(), i+1));
									//qTitleLbl[i] = new JLabel(Integer.toString(tempId));
									panel.add(qTitleLbl[i]);
									qGradeLbl[i] = new JLabel(utility.getTeacherGrade(currClass.getId(), i+1));
									panel.add(qGradeLbl[i]);
									layout.putConstraint(SpringLayout.WEST, qTitleLbl[i], x+15 , SpringLayout.WEST, panel);
									layout.putConstraint(SpringLayout.NORTH, qTitleLbl[i], y, SpringLayout.NORTH, panel);
									layout.putConstraint(SpringLayout.NORTH, qGradeLbl[i], y, SpringLayout.NORTH, panel);
									layout.putConstraint(SpringLayout.WEST, qGradeLbl[i], 145, SpringLayout.EAST, qTitleLbl[i]);
									y += 15; //gap in the y direction

									i++;

								}


						}
						else
						{
							//else applies to students, who only need to view their scores.

							JLabel[] qTitleLbl = new JLabel[currClass.getNumQuizzes()];
							JLabel [] qGradeLbl = new JLabel[currClass.getNumQuizzes()];

							while(i < currClass.getNumQuizzes())
								{
									//while loop does the same thing but places the scores for each student

									qTitleLbl[i] = new JLabel(Integer.toString(currClass.showQuizId(i)));
									panel.add(qTitleLbl[i]);
									qGradeLbl[i] = new JLabel(utility.getGrade(curr.getId(), currClass.getId(), i+1));
									panel.add(qGradeLbl[i]);
									layout.putConstraint(SpringLayout.WEST, qTitleLbl[i], x , SpringLayout.WEST, panel);
									layout.putConstraint(SpringLayout.NORTH, qTitleLbl[i], y, SpringLayout.NORTH, panel);
									layout.putConstraint(SpringLayout.NORTH, qGradeLbl[i], y, SpringLayout.NORTH, panel);
									layout.putConstraint(SpringLayout.WEST, qGradeLbl[i], 100, SpringLayout.EAST, qTitleLbl[i]);
									y += 15;   //CHANGE

									i++;

								}

						}

					}
	//returns the sub-panel with the grades on it
	return tempPanel;
	}



	//Here is where all the events take place, the majority of them are onClick
	public void actionPerformed(ActionEvent e)
	{
		String buttonString = e.getActionCommand();

		//if user clicks logout, then they are logged out and taken to the loginFrame
		if (buttonString.equals("Logout"))
		{
			dispose();
			LoginFrame gui = new LoginFrame();
			gui.setVisible(true);
		}
		else if (buttonString.equals("Create Class")) //if Create Class is clicked check the info in the boxes and create the class
		{
			try
			{
				int id = Integer.parseInt(courseTxt.getText());
				String title = titleTxt.getText();
				String desc = descTxt.getText();
				int  nUser = Integer.parseInt(teacherTxt.getText());

				User tUser = utility.openUser(nUser); //this checks the database to ensure there is a teacher selected exists
				if (tUser.getStatus() == 't')
				{
					utility.writeClass(id, title, desc, nUser);
					utility.addClass(id, nUser);
					createCErrorLbl.setText("Class Created");
				}
				else
					createCErrorLbl.setText("Class NOT Created, Check teacherId"); //Meaningful Error Message

			}
			catch(Exception ex)
			{
				createCErrorLbl.setText("Incorrect Input");
			}
		}
		else if (buttonString.equals("Create Account")) //Same way, check the info in hte boxes
		{
			Object selected = userList.getSelectedItem();
			String combo = selected.toString();

			//Different accounts can be created. Admins can set their passwors. teachers and student passwords are auto-generated

			try {
				int id = Integer.parseInt(idTxt.getText());
				String name = nameTxt.getText();
				String pass;

				User tempUser = utility.openUser(id);

				if(tempUser == null) //ensures that the user does not exist
				{
					if (combo.equals("Admin"))
					{
						pass = passTxt.getText();
						Admin admin = new Admin(id, name, pass);
						utility.writeAdmin(id, name, pass, 'a');
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
							utility.writeTeacher(id, name, pass, 't');
							createAErrorLbl.setText("Teacher Created");
						}
						else
						{
							Student student = new Student(id, name, pass);
							utility.writeStudent(id, name, pass, 's'); //Write student should change
							createAErrorLbl.setText("Student Created");
						}
					}
				}
				else
				{
					createAErrorLbl.setText("That user already exists"); //If the id input exists, tell the user
				}
			}
			catch (Exception ex)
			{
				createAErrorLbl.setText("Incorrect Input");
			}
		}
		else if (buttonString.equals("Add"))
		{
			//for adding a student to a class
			try {
						int id = Integer.parseInt(studentTxt.getText());

						User person = utility.openUser(id); //This ensures that the student exists before we add them to the database

						if(person != null)
						{
							utility.addClass(currClass.getId(), id);
							createSErrorLbl.setText("Student Added");
							studentTxt.setText("");
						}
						else
							createSErrorLbl.setText("Not a valid Student ID, please contact Admins");

				}
				catch (Exception ex)
				{
					createSErrorLbl.setText("Incorrect Input");
				}
		}
		else if (buttonString.equals("Main Menu"))
		{
			dispose();
			Menu menu = new Menu(curr);
			menu.setVisible(true);
		}
		else if (buttonString.equals("Continue"))
		{
			//moves you into the QuizFrame with the appropriate quiz

			Object selected = numList.getSelectedItem();
			int num = Integer.parseInt(selected.toString());

			int tempQuizId = Integer.parseInt(qTitleTxt.getText());

			dispose(); //destroys the current frame
			QuizFrame q = new QuizFrame(tempQuizId, num, currClass, curr); // instanciates the selected quiz
			q.setVisible(true); //shows new panel
		}
		else
			System.out.println("Unexpected error.");
	}

	private void passwordChangeActionPerformed(ActionEvent event)
	{
		//Changing password
		try
		{
			String oldPass = new String(cPassTxt.getPassword());
			String newPass = new String(nPassTxt.getPassword());

			// DB
			 boolean flag = utility.changePass(curr.getId(), curr.getName(), oldPass, newPass);

			// Needs a boolean to notify if change was successful or not
			if (flag)
			aErrorLbl.setText("Password Change Successful!");
			else
				aErrorLbl.setText("Incorrect Password");
		}
		catch (Exception ex)
		{
			aErrorLbl.setText("Incorrect Input");
		}
	}

	//When creating a user, if youre creating an admin, you may set their password, otherwise it is autogenerated
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

	//Clears the data in the tabs upon switching
	private void tabChanged()
	{
		if (currClass == null)
		{
			aErrorLbl.setText("");
			cPassTxt.setText("");
			nPassTxt.setText("");

			if (curr.getStatus() == 'a')
			{
				idTxt.setText("");
				nameTxt.setText("");
				userList.setSelectedIndex(0);
				passTxt.setText("");
				createAErrorLbl.setText("");
				courseTxt.setText("");
				titleTxt.setText("");
				descTxt.setText("");
				teacherTxt.setText("");
				createCErrorLbl.setText("");
			}
		}
		else
		{
			if (curr.getStatus() == 'a')
			{
				qTitleTxt.setText("");
				numList.setSelectedIndex(-1);
				studentTxt.setText("");
				createSErrorLbl.setText("");
			}
		}
	}
}