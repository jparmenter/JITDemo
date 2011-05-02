/*
*
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

public class QuizFrame extends JFrame implements ActionListener
{
	public static int WIDTH = 600;
	public static int HEIGHT = 400;

	private final Class currClass;
	private final User curr;
	private final int title;
	private final int size;
	private final Quiz quiz;
	private static int i;
	public static jaklUtilities utility;

	private JTextField questionTxt;
	private JButton cancelBtn;
	private JButton submitBtn;
	private JRadioButton radio1;
	private JRadioButton radio2;
	private JRadioButton radio3;
	private JRadioButton radio4;
	private JTextField answer1;
	private JTextField answer2;
	private JTextField answer3;
	private JTextField answer4;
	private JLabel qLbl;
	private JLabel errorLbl;
	private JPanel takeQuizDisplay;

	private static Object source;
	private static String[] questions;
	private static String[] ans1;
	private static String[] ans2;
	private static String[] ans3;
	private static String[] ans4;
	private static int[] correctAnswers;

	private static int[] usersAnswers;

	public QuizFrame(int _quizId, int _size, Class _currClass, User _curr)
	{
		super("Just In Time Learning");
		utility = new jaklUtilities();
		currClass = _currClass;
		curr = _curr;
		title = _quizId;
		quiz = null;
		size = _size;
		i = 0;

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
		setResizable(false);
		setLocationRelativeTo(null);

		JPanel panel = createQuiz();
		add(panel);
	}

	public QuizFrame(Quiz _quiz, Class _currClass, User _curr)
	{
		super("Just In Time Learning");
		utility = new jaklUtilities();
		currClass = _currClass;
		curr = _curr;
		quiz = _quiz;
		title = quiz.getId();
		size = quiz.numQuestions(); // Amount of Quiz Questions
		i = 0;
		System.out.println(quiz.getId() + " " + quiz.getAnswer1(0));
		correctAnswers = quiz.getCorrectAnswers();


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
		setResizable(false);
		setLocationRelativeTo(null);
		takeQuizDisplay = takeQuiz();

		add(takeQuizDisplay);
	}

	public JPanel createQuiz()
	{

		System.out.println(i + " " + size);

		JPanel panel = new JPanel();
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);

		int x = 25;
		int y = 25;
		int inc = 45;

		JLabel titleLbl = new JLabel(title + " Creation Page");
		panel.add(titleLbl);

		JLabel questionLbl = new JLabel("Enter the Question:");
		panel.add(questionLbl);

		questionTxt = new JTextField("", 20);
		panel.add(questionTxt);

		radio1 = new JRadioButton();
		radio1.addActionListener(this);
		panel.add(radio1);

		radio2 = new JRadioButton();
		radio2.addActionListener(this);
		panel.add(radio2);

		radio3 = new JRadioButton();
		radio3.addActionListener(this);
		panel.add(radio3);

		radio4 = new JRadioButton();
		radio4.addActionListener(this);
		panel.add(radio4);

		ButtonGroup group = new ButtonGroup();
		group.add(radio1);
		group.add(radio2);
		group.add(radio3);
		group.add(radio4);

		answer1 = new JTextField("", 15);
		panel.add(answer1);

		answer2 = new JTextField("", 15);
		panel.add(answer2);

		answer3 = new JTextField("", 15);
		panel.add(answer3);

		answer4 = new JTextField("", 15);
		panel.add(answer4);

		errorLbl = new JLabel("");
		panel.add(errorLbl);

		submitBtn = new JButton("Continue");
		submitBtn.addActionListener(this);
		panel.add(submitBtn);

		if (i == size)
		{
			submitBtn.setText("Submit");
		}

		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		panel.add(cancelBtn);

		layout.putConstraint(SpringLayout.WEST, titleLbl, ((WIDTH/2)-100), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, titleLbl, y, SpringLayout.NORTH,
			panel);

		y += inc;

		layout.putConstraint(SpringLayout.WEST, questionLbl, (((WIDTH/2/2)-100)), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, questionLbl, y, SpringLayout.NORTH,
			panel);
		layout.putConstraint(SpringLayout.NORTH, questionTxt, y, SpringLayout.NORTH,
			panel);
		layout.putConstraint(SpringLayout.WEST, questionTxt, 20, SpringLayout.EAST,
			questionLbl);

		y += inc;

		layout.putConstraint(SpringLayout.WEST, radio1, (((WIDTH/2)/2)-50), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, radio1, y, SpringLayout.NORTH,
			panel);
		layout.putConstraint(SpringLayout.NORTH, answer1, y, SpringLayout.NORTH,
			panel);
		layout.putConstraint(SpringLayout.WEST, answer1, 20, SpringLayout.EAST,
			radio1);

		y += inc;


		layout.putConstraint(SpringLayout.WEST, radio2, (((WIDTH/2)/2)-50), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, radio2, y, SpringLayout.NORTH,
			panel);
		layout.putConstraint(SpringLayout.NORTH, answer2, y, SpringLayout.NORTH,
			panel);
		layout.putConstraint(SpringLayout.WEST, answer2, 20, SpringLayout.EAST,
			radio2);

		y += inc;

		layout.putConstraint(SpringLayout.WEST, radio3, (((WIDTH/2)/2)-50), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, radio3, y, SpringLayout.NORTH,
			panel);
		layout.putConstraint(SpringLayout.NORTH, answer3, y, SpringLayout.NORTH,
			panel);
		layout.putConstraint(SpringLayout.WEST, answer3, 20, SpringLayout.EAST,
			radio3);

		y += inc;

		layout.putConstraint(SpringLayout.WEST, radio4, (((WIDTH/2)/2)-50), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, radio4, y, SpringLayout.NORTH,
			panel);
		layout.putConstraint(SpringLayout.NORTH, answer4, y, SpringLayout.NORTH,
			panel);
		layout.putConstraint(SpringLayout.WEST, answer4, 20, SpringLayout.EAST,
			radio4);

		y += inc;

		layout.putConstraint(SpringLayout.WEST, errorLbl, ((WIDTH/2)-50), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, errorLbl, y, SpringLayout.NORTH,
			panel);

		layout.putConstraint(SpringLayout.NORTH ,cancelBtn, (HEIGHT-70), SpringLayout.NORTH,
			panel);
		layout.putConstraint(SpringLayout.EAST, cancelBtn, -5, SpringLayout.WEST,
			submitBtn);
		layout.putConstraint(SpringLayout.WEST, submitBtn, (WIDTH-100), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, submitBtn, (HEIGHT-70), SpringLayout.NORTH,
			panel);

		return panel;

	}

	public JPanel takeQuiz()
	{

		JPanel panel = new JPanel();
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);

		int x = 25;
		int y = 25;
		int inc = 45;

		JLabel titleLbl = new JLabel("Quiz Title");
		panel.add(titleLbl);

		qLbl = new JLabel("Quesion");
		panel.add(qLbl);

		radio1 = new JRadioButton();
		radio1.addActionListener(this);
		panel.add(radio1);

		radio2 = new JRadioButton();
		radio2.addActionListener(this);
		panel.add(radio2);

		radio3 = new JRadioButton();
		radio3.addActionListener(this);
		panel.add(radio3);

		radio4 = new JRadioButton();
		radio4.addActionListener(this);
		panel.add(radio4);

		ButtonGroup group = new ButtonGroup();
		group.add(radio1);
		group.add(radio2);
		group.add(radio3);
		group.add(radio4);

		errorLbl = new JLabel("");
		panel.add(errorLbl);

		submitBtn = new JButton("Continue");
		submitBtn.setActionCommand("qCont");
		submitBtn.addActionListener(this);
		panel.add(submitBtn);

		/*if (i == size)
		{
			submitBtn.setText("Submit");
		}*/

		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		panel.add(cancelBtn);


		System.out.println(i);
		question();

		layout.putConstraint(SpringLayout.WEST, titleLbl, ((WIDTH/2)-100), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, titleLbl, y, SpringLayout.NORTH,
			panel);

		y += inc;

		layout.putConstraint(SpringLayout.WEST, qLbl, (((WIDTH/2))-50), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, qLbl, y, SpringLayout.NORTH,
			panel);

		y += inc;

		layout.putConstraint(SpringLayout.WEST, radio1, (((WIDTH/2)/2)-50), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, radio1, y, SpringLayout.NORTH,
			panel);

		y += inc;


		layout.putConstraint(SpringLayout.WEST, radio2, (((WIDTH/2)/2)-50), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, radio2, y, SpringLayout.NORTH,
			panel);

		y += inc;

		layout.putConstraint(SpringLayout.WEST, radio3, (((WIDTH/2)/2)-50), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, radio3, y, SpringLayout.NORTH,
			panel);

		y += inc;

		layout.putConstraint(SpringLayout.WEST, radio4, (((WIDTH/2)/2)-50), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, radio4, y, SpringLayout.NORTH,
			panel);

		y += inc;

		layout.putConstraint(SpringLayout.WEST, errorLbl, ((WIDTH/2)-50), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, errorLbl, y, SpringLayout.NORTH,
			panel);

		layout.putConstraint(SpringLayout.NORTH ,cancelBtn, (HEIGHT-70), SpringLayout.NORTH,
			panel);
		layout.putConstraint(SpringLayout.EAST, cancelBtn, -5, SpringLayout.WEST,
			submitBtn);
		layout.putConstraint(SpringLayout.WEST, submitBtn, (WIDTH-100), SpringLayout.WEST,
			panel);
		layout.putConstraint(SpringLayout.NORTH, submitBtn, (HEIGHT-70), SpringLayout.NORTH,
			panel);

		return panel;
	}

	public void question()
	{// DB

		qLbl.setText(quiz.getQuestion(i));
		radio1.setText(quiz.getAnswer1(i));
		radio2.setText(quiz.getAnswer2(i));
		radio3.setText(quiz.getAnswer3(i));
		radio4.setText(quiz.getAnswer4(i));

	/*
		System.out.println(utility.getQuestion(i, quiz.getId()));
		qLbl.setText(utility.getQuestion(i, quiz.getId()));

		radio1.setText(utility.getAnswer(i, 1, quiz.getId()));

		radio2.setText(utility.getAnswer(i, 2, quiz.getId()));

		radio3.setText(utility.getAnswer(i, 3, quiz.getId()));

		radio4.setText(utility.getAnswer(i, 4, quiz.getId()));*/
	}


public void actionPerformed(ActionEvent event)
{
	//System.out.println(event.getActionCommand());
	//System.out.println(event.getActionCommand().equals("qCont"));

	if(event.getActionCommand().equals("Cancel"))
	{
		Menu gui = new Menu(currClass, curr);
		gui.setVisible(true);
		this.dispose();
	}


	else if(event.getActionCommand().equals("Continue"))
	{

		if((answer1.getText().length() > 0) && (answer2.getText().length() > 0) && (answer3.getText().length() > 0) &&
			(answer4.getText().length() > 0) && (questionTxt.getText().length() > 0))
		{

			if (i < size)
			{
				if(i == 0)
				{
				//System.out.println("INSTANCIATED STUFF");
				source = event.getSource();
				questions = new String[size];
				ans1 = new String[size];
				ans2 = new String[size];
				ans3 = new String[size];
				ans4 = new String[size];
				correctAnswers = new int[size];
				}

			//System.out.println("GOT TO PULLING DATA");


				questions[i] = questionTxt.getText();
				ans1[i] = answer1.getText();
				ans2[i] = answer2.getText();
				ans3[i] = answer3.getText();
				ans4[i] = answer4.getText();

				boolean answer1Flag = radio1.isSelected();
				boolean answer2Flag = radio2.isSelected();
				boolean answer3Flag = radio3.isSelected();
				boolean answer4Flag = radio4.isSelected();

				if(answer1Flag)
				{
					correctAnswers[i] = 1;
				}
				else if(answer2Flag)
				{
					correctAnswers[i] = 2;
				}
				else if(answer3Flag)
				{
					correctAnswers[i] = 3;
				}
				else if(answer4Flag)
				{
					correctAnswers[i] = 4;
				}

				i++;

				if(i == size)
				{
					int tempClassId = currClass.getId();
					utility.writeQuiz(title, questions, ans1, ans2, ans3, ans4, correctAnswers, tempClassId);
					Menu gui = new Menu(currClass, curr);
					gui.setVisible(true);
					this.dispose();
				}

					questionTxt.setText("");
					answer1.setText("");
					answer2.setText("");
					answer3.setText("");
					answer4.setText("");
					errorLbl.setText("Question Added");
				}
		}
		else
		{
			errorLbl.setText("Please Fill in ALL answers");
		}
	}

	else if(event.getActionCommand().equals("qCont"))
	{
		//System.out.println(i + " " + size);

		if(i == 0)
		{
			usersAnswers = new int[quiz.numQuestions()];
			//System.out.println("INSTANCIATED");
		}

		boolean answer1 = radio1.isSelected();
		boolean answer2 = radio2.isSelected();
		boolean answer3 = radio3.isSelected();
		boolean answer4 = radio4.isSelected();

		if(answer1)
		{
			usersAnswers[i] = 1;
		}
		else if(answer2)
		{
			usersAnswers[i] = 2;
		}
		else if(answer3)
		{
			usersAnswers[i] = 3;
		}
		else if(answer4)
		{
			usersAnswers[i] = 4;
		}

		i++;

		if(i == size)
		{

			//THIS IS WEHERE WE WILL GRADE
			System.out.println("Right before writeGrade call");
			utility.writeGrade(quiz.getId(), grade(), curr.getId(), currClass.getId());
			System.out.println("Right after writeGrade call");
			Menu gui = new Menu(currClass, curr);
			gui.setVisible(true);
			this.dispose();
		}
		else
		{
			this.remove(takeQuizDisplay);
			takeQuizDisplay = takeQuiz();
			this.add(takeQuizDisplay);
			this.validate();
			this.repaint();
		}

	}

}

public int grade()
{
	int j = 0;
	int tempGrade = 0;
	System.out.println(size);
	while(j < size)
	{
		//System.out.println("inside the grad() while loop");
		System.out.println(usersAnswers[j] + " : " + correctAnswers[j]);
		if(usersAnswers[j] == correctAnswers[j])
		{
			tempGrade++;

		}

		j++;

	}

	System.out.println(tempGrade);
	double tempFinalGrade = ((tempGrade*100)/size);
	System.out.println(tempFinalGrade);
	return (int)tempFinalGrade;
	}


}