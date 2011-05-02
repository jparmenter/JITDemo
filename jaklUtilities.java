/*
*	This utility class handles ALL of the database IO
*	The format of most of the methods is the same
*	Execute sqlQuery and return the value if need be
*
*
* Authors:
* Jeremy Parmenter
* Alex Holguin
* John Kevin Canez
*/

import java.util.*;
import java.io.*;
import java.sql.*;

public class jaklUtilities
{
	private String url = "jdbc:postgresql://70.190.95.77:5432/jitt"; //URL of our database
	public int currClass = 0;
	public jaklUtilities()
	{
		try
		{
			java.lang.Class.forName("org.postgresql.Driver"); //jdbc Driver
		}
		catch(Exception e)
		{
			System.out.println("Could not find jdbc driver"); // If you do not have the driver in your classpath
		}
	}

	//Executes the sql query for writing an admin with the data the user gives it
	public void writeAdmin(int id, String name, String pass, char acctType)
	{
		 try
		 {
			Connection conn = DriverManager.getConnection(url,"postgres","jakl"); //connection to db

			Statement st = conn.createStatement(); //st is linked to the connection

			st.execute("INSERT INTO \"user\"\nVALUES\n(" + id + ", '" + name + "', '" + pass + "', '" + acctType + "');");

			conn.close(); //connection closed

		}

		 catch (Exception e)
		 {
		   System.out.println("Got an exception!");
		   System.out.println(e.getMessage());
		 }
	}

	//Writes the teacher to the database with the data given
	public void writeTeacher(int id, String name, String pass, char acctType)
		{
			 try
			 {
				Connection conn = DriverManager.getConnection(url,"postgres","jakl");

				Statement st = conn.createStatement();
				st.execute("INSERT INTO \"user\"\nVALUES\n(" + id + ", '" + name + "', '" + pass + "', '" + acctType + "');");

				conn.close();

			}

			 catch (Exception e)
			 {
			   System.out.println("Got an exception! ");
			   System.out.println(e.getMessage());
			 }
	}

	//Writes student same way as above
	public void writeStudent(int id, String name, String pass, char acctType)
		{
			 try
			 {
				Connection conn = DriverManager.getConnection(url,"postgres","jakl");

				Statement st = conn.createStatement();
				st.execute("INSERT INTO \"user\"\nVALUES\n(" + id + ", '" + name + "', '" + pass + "', '" + acctType + "');");
				conn.close();
			}
			 catch (Exception e)
			 {
			   System.out.println("Got an exception! ");
			   System.out.println(e.getMessage());
			 }
		}


	//Open user takes a user id and returns the user
	public User openUser(int id)
		{
			//Data to pull from the database

			String strUser = null;
			String strPass = null;
			String strType = null;
			Character charType;
			String numClasses;
			try
			{
				Connection conn = DriverManager.getConnection(url,"postgres","jakl");

			   //One by one, the data lised above is pulled from the db and stored into their variable

			   Statement stmt = conn.createStatement();
			   ResultSet user = stmt.executeQuery("SELECT username from \"user\" WHERE id=" + id);
			   ResultSetMetaData rsmd1 = user.getMetaData();
			   while (user.next())
			   {
				   strUser = user.getString(1);
			   }
			   user.close();
			   ResultSet pass = stmt.executeQuery("SELECT pass from \"user\" WHERE id=" + id);
			   ResultSetMetaData rsmd2 = pass.getMetaData();
			   while (pass.next())
			   {
				   strPass = pass.getString(1);
			   }
				pass.close();

			   ResultSet type = stmt.executeQuery("SELECT acctype from \"user\" WHERE id=" + id);
			   ResultSetMetaData rsmd3 = type.getMetaData();
			   while (type.next())
			   {
				   strType = type.getString(1);
			   }
			   type.close();
			   ResultSet classes = stmt.executeQuery("SELECT array_to_string(ARRAY[class], ',') from \"user\" WHERE id=" + id);
			   classes.next();
			   numClasses = classes.getString(1);
			   int[] tempArray = getClassArray(numClasses);
			   conn.close();

			//Checks to see what kind of user is going to be returned

			charType = strType.charAt(0);
			if (charType == 't')
			{
				if(tempArray != null)
				{
				Teacher teach = new Teacher(id, strUser, strPass, tempArray); //If the teacher does have classes
				return teach;
				}
				else
				{
				Teacher teach = new Teacher(id, strUser, strPass);  //If the teacher has NO classes
				return teach;
				}
			}
			else if (charType == 'a')
			{
				Admin admin = new Admin(id, strUser, strPass);
				return admin;
			}

			else
			{
				if(tempArray != null)
				{
				Student student = new Student(id,strUser,strPass, tempArray); //If the Student is registered for classes constructor
				return student;
				}
				else
				{
				Student student = new Student(id, strUser,strPass); //if the student has no classes
				return student;
				}
			}
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
				return null;
			}

		}

	//Writes given class to the database
	public void writeClass(int id, String name, String description, int teacher)
	{
		 try
		 {
			Connection conn = DriverManager.getConnection(url,"postgres","jakl");

			Statement st = conn.createStatement();
			st.execute("INSERT INTO \"class\"\nVALUES\n(" + id + ", '" + name + "', '" + description + "', NULL," + teacher + ")");

			conn.close();

		}

		 catch (Exception e)
		 {
		   System.out.println("The course was not saved successfully");
		   System.out.println(e.getMessage());
		 }
	}

	//Opens class from hte database
	// Data is pulled one by one as it was above
	// the temp variables are then instanciated into a class and returned
	public Class openClass(int courseId)
	{
		 String title = null;
		 String desc = null;
		 int[] quizId = null;
		 int teachId = 0;
		try
		{

			 Connection conn = DriverManager.getConnection(url,"postgres","jakl");
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT * FROM \"class\" WHERE id=" + courseId);
			 ResultSet className = stmt.executeQuery("SELECT classname from \"class\" WHERE id=" + courseId);
			 ResultSetMetaData rsmd1 = className.getMetaData();
		     while (className.next())
		     {
			   title = className.getString(1);
		     }
		     className.close();

		     ResultSet description = stmt.executeQuery("SELECT description from \"class\" WHERE id=" + courseId);
		     ResultSetMetaData rsmd2 = description.getMetaData();
		     while (description.next())
		     {
		 	   desc = description.getString(1);
		     }
		     description.close();

			 ResultSet teacher = stmt.executeQuery("SELECT teacher from \"class\" WHERE id=" + courseId);
			 ResultSetMetaData rsmd3 = teacher.getMetaData();
			 while (teacher.next())
			 {
			   teachId = teacher.getInt(1);
			 }
			 teacher.close();

			 ResultSet quizs = stmt.executeQuery("SELECT array_to_string(ARRAY[quizids], ',') from \"class\" WHERE id=" + courseId);
			 ResultSetMetaData rsmd4 = quizs.getMetaData();
			 while (quizs.next())
			 {
			   quizId = getClassArray(quizs.getString(1));
			 }
			 quizs.close();
			 conn.close();
			 Class tClass = new Class(courseId, title, desc,quizId, teachId);
			 return tClass;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			return null;
		}

	}

	//returns the number of classes the user is registered for
	public int getNumClasses(int id)
	{
		int i = 0;
		try
		{
		   Connection conn = DriverManager.getConnection(url,"postgres","jakl");
		   Statement stmt = conn.createStatement();
		   ResultSet count = stmt.executeQuery("SELECT array_upper(class, 1) FROM \"user\" WHERE id=" + id);
		   count.next();
		   i = count.getInt(1);
		   count.close();
		   conn.close();
		   return i;
	   }
	   catch(Exception e)
	   {
		   System.out.println(e.getMessage());
		   return 0;
	   }
   }

	//Returns an array of classes, used in the frames where you double click
	public String[] showClasses(int id)
	{
		int i = 0;
		try
		{
		   Connection conn = DriverManager.getConnection(url,"postgres","jakl");
		   Statement stmt = conn.createStatement();
		   ResultSet counting = stmt.executeQuery("SELECT array_upper(class, 1) FROM \"user\" WHERE id=" + id);
		   while (counting.next())
		   {
		   	   i = counting.getInt(1);
		   }
		   counting.close();
		   ResultSet rs = stmt.executeQuery("SELECT array_to_string(ARRAY[class], ',') from \"user\" WHERE id=" + id);

		   String[] classes = new String[i];
		   	while (rs.next())
		   	{
				classes = getQuizArray(rs.getString(1));
			}
			rs.close();
			conn.close();
			return classes;
		}
		catch (Exception e)
		{
			System.out.println("FFFAAIIIILLLLZZZZZZZZZZ");
			System.out.println(e.getMessage());
			return null;
		}
	}

	//Same as above, used in the frame where you have to double quick a quiz (In Menu)
	public String[] showQuizes(int classId)
		{
			int i = 0;
			try
			{
			   Connection conn = DriverManager.getConnection(url,"postgres","jakl");
			   Statement stmt = conn.createStatement();
			   ResultSet counting = stmt.executeQuery("SELECT array_upper(quizids, 1) FROM \"class\" WHERE id=" + classId);
			   while (counting.next())
			   {
			   	   i = counting.getInt(1);
			   }
			   counting.close();
			   ResultSet rs = stmt.executeQuery("SELECT array_to_string(ARRAY[quizids], ',') from \"class\" WHERE id=" + classId);

			   String[] quizs = new String[i];
			   	while (rs.next())
			   	{
					quizs = getQuizArray(rs.getString(1));
				}
				rs.close();
				conn.close();
				return quizs;
			}
			catch (Exception e)
			{
				System.out.println("FFFAAIIIILLLLZZZZZZZZZZ");
				System.out.println(e.getMessage());
				return null;
			}
	}

	//Executes query to add class
	public void addClass(int classId, int id)
	{
		try
		{
			Connection conn = DriverManager.getConnection(url,"postgres","jakl");
			Statement st = conn.createStatement();
			st.executeUpdate("UPDATE \"user\" SET class = class || ARRAY["+ classId + "] WHERE id=" + id);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	//method used to change the password
	// returns wether the change was successful or not
	public boolean changePass(int id, String user, String pass, String nPass)
	{
		String strPass = null;
		String newPass = nPass;
		try
		{

			Connection conn = DriverManager.getConnection(url,"postgres","jakl");

		   Statement stmt = conn.createStatement();
		   ResultSet oldPass = stmt.executeQuery("SELECT pass from \"user\" WHERE id=" + id);
		   oldPass.next();
		   strPass = oldPass.getString(1);
		   oldPass.close();
		   if (pass.equals(strPass))
		   {
			   stmt.executeUpdate("UPDATE \"user\" SET pass = \'" + newPass + "\' WHERE id=\'" + id + "\'");
			   conn.close();
			   return true;

		   }
		   else
		   {
			  conn.close();
			  return false;
		   }
	   }

		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}

//Returns teh quiz with the given ID... if it exists.
public Quiz openQuiz(int quizId)
		{
			//All of these variables are filled over several queries
			int tempQuestionNumber;
			String[] tempQuestion = null;
			String[] tempAnswer1 = null;
			String[] tempAnswer2 = null;
			String[] tempAnswer3 = null;
			String[] tempAnswer4 = null;
			int[] tempCorrectAnswer = null;

			try
			{
				Connection conn = DriverManager.getConnection(url,"postgres","jakl");

			   Statement stmt = conn.createStatement();

			   ResultSet tQuestNum = stmt.executeQuery("SELECT id from \"quiz\" WHERE id=" + quizId);
			   tQuestNum.next();

			   tempQuestionNumber = tQuestNum.getInt(1);

			   tQuestNum.close();

			   ResultSet tQuest = stmt.executeQuery("SELECT array_to_string(ARRAY[question], ',') from \"quiz\" WHERE id=" + quizId);
			   while (tQuest.next())
			   {
				   tempQuestion = getQuizArray(tQuest.getString(1));
			   }
				tQuest.close();

			   ResultSet ans1 = stmt.executeQuery("SELECT array_to_string(ARRAY[answer1], ',') from \"quiz\" WHERE id=" + quizId);
			   while (ans1.next())
			   {
				   tempAnswer1 = getQuizArray(ans1.getString(1));
			   }
			   ans1.close();

			   ResultSet ans2 = stmt.executeQuery("SELECT array_to_string(ARRAY[answer2], ',') from \"quiz\" WHERE id=" + quizId);
			   while (ans2.next())
			   {
				   tempAnswer2 = getQuizArray(ans2.getString(1));
			   }
			   ans2.close();

			   ResultSet ans3 = stmt.executeQuery("SELECT array_to_string(ARRAY[answer3], ',') from \"quiz\" WHERE id=" + quizId);
			   while (ans3.next())
			   {
				   tempAnswer3 = getQuizArray(ans3.getString(1));
			   }
			   ans3.close();

			   ResultSet ans4 = stmt.executeQuery("SELECT array_to_string(ARRAY[answer4], ',') from \"quiz\" WHERE id=" + quizId);
			   while (ans4.next())
			   {
				   tempAnswer4 = getQuizArray(ans4.getString(1));
			   }
			   ans4.close();

			   ResultSet corr = stmt.executeQuery("SELECT array_to_string(ARRAY[correctanswer], ',') from \"quiz\" WHERE id=" + quizId);
			   while (corr.next())
			   {
				   tempCorrectAnswer = getClassArray(corr.getString(1));
			   }
			   corr.close();
			   conn.close();

			   Quiz tempQuiz = new Quiz(quizId, tempQuestion, tempAnswer1, tempAnswer2, tempAnswer3, tempAnswer4, tempCorrectAnswer);
			   return tempQuiz;

		   }
			catch (Exception e)
			{
				System.out.println(e.getMessage());
				return null;
			}

		}

		//Quiz is written with the data it was handed.
		// prior to going into the database the arrays must be placed in strings
		public void writeQuiz(int quizId, String[] questions, String[] answers1, String[] answers2, String[] answers3, String[] answers4, int[] correctAnswers, int classId)
		{
			String class_quiz_id = Integer.toString(classId) + Integer.toString(quizId);
			int final_class_quiz_id = Integer.parseInt(class_quiz_id);
				try
				{
					Connection conn = DriverManager.getConnection(url,"postgres","jakl");
					Statement st = conn.createStatement();
					//This is where the data is converted into a string then added

					st.executeUpdate("INSERT INTO \"quiz\"\nVALUES\n(" + final_class_quiz_id + ", '{" + sArrayToString(questions) + "}', '{" + sArrayToString(answers1)+ "}', '{" + sArrayToString(answers2) + "}', '{" + sArrayToString(answers3) + "}', '{" + sArrayToString(answers4) + "}', '{" + intArrayToString(correctAnswers) + "}')");
					Statement st1 = conn.createStatement();

					//The quiz is added to the class here

					st1.executeUpdate("UPDATE \"class\" SET quizids = quizids || ARRAY["+ final_class_quiz_id + "] WHERE id=" + classId);

					System.out.println("success!");
					conn.close();
				}
				catch (Exception e)
				{
					System.out.println(e.getMessage());
				}
		}

		//Returns the array of correct answers given the prper quiz id
		public int[] getCorrectAnswerArray(int quizId)
		{
			int i = 0;
			try
			{
				Connection conn = DriverManager.getConnection(url, "postgres","jakl");
				Statement st = conn.createStatement();
				ResultSet count = st.executeQuery("SELECT COUNT(correctanswer) FROM \"quiz\" WHERE id =" + quizId);
				ResultSet answers = st.executeQuery("SELECT array_to_string(ARRAY[correctanswer], ',') from \"quiz\" WHERE id=" + quizId);
				count.next();
				i = count.getInt(1);
				int[] correctAnswers = new int[i];
				count.close();
				while (answers.next())
				{
					correctAnswers = getClassArray(answers.getString(1));
				}
				answers.close();
				conn.close();
				return correctAnswers;
			}
			catch (Exception e)
			{
				return null;
			}
		}

		//returns the question at a given index (always used within a loop)
		public String getQuestion(int index, int quizId)
		{
			String question = null;
			try
			{
				Connection conn = DriverManager.getConnection(url, "postgres","jakl");
				Statement st = conn.createStatement();
				ResultSet quest = st.executeQuery("SELECT question[" + index + "] FROM \"quiz\" WHERE id= " + quizId);
				quest.next();
				question = quest.getString(1);
				quest.close();
				conn.close();
				return question;
			}
			catch(Exception e)
			{
				//System.out.println("rawrrr");
				System.out.println(e.getMessage());
				return null;
			}
		}

		//Retuns the answer of a given question and index

		public String getAnswer(int questIndex, int ansIndex, int quizId)
		{
			String answer = null;
			try
			{
				Connection conn = DriverManager.getConnection(url, "postgres","jakl");
				Statement st = conn.createStatement();
				ResultSet ans = st.executeQuery("SELECT answer" + ansIndex + "[" + questIndex + "] FROM \"quiz\" WHERE id= " + quizId);
				ans.next();
				answer = ans.getString(1);
				ans.close();
				conn.close();
				return answer;
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
				return null;
			}
		}


	//Returns an array of classes that the user is registered for
	public int[] getClassArray(String str)
	{
		Vector<Integer> classListVect = new Vector<Integer>();

		String testString = str;
		StringTokenizer st = new StringTokenizer(testString, ",");

		while(st.hasMoreTokens())
		{
			classListVect.add(Integer.parseInt(st.nextToken()));
		}

		int[] temp = new int[classListVect.size()];

		for(int i =0; i < classListVect.size(); i++)
		{
			temp[i] = classListVect.get(i);
		}

		return temp;
	}

	//The users grade is written to the database
	public void writeGrade(int quizId, int grade, int id, int classId)
	{
		String student_class_quiz_id =  Integer.toString(id) + Integer.toString(classId) + Integer.toString(quizId);
			try
			{
				Connection conn = DriverManager.getConnection(url,"postgres","jakl");
				Statement st = conn.createStatement();
				st.execute("INSERT INTO \"grades\"\nVALUES\n(" + student_class_quiz_id + ", " + id + ", " + classId + ", " + quizId + ", " + grade + ");");
				st.close();
				conn.close();
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
			}
	}




	//Returns the grade of a user (used in loops)
	public String getGrade(int id, int currClass, int index)
		{
			try
			{
				String tempGrade = "";
				Connection conn = DriverManager.getConnection(url,"postgres","jakl");
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("SELECT grade FROM \"grades\" WHERE studentid =" + id + "AND classid=" + currClass);

				while(index > 0)
				{
				rs.next();
				index--;
				}
				tempGrade = rs.getString(1);

				rs.close();
				st.close();
				conn.close();

				return tempGrade;
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
				return null;
			}
		}

//Get grades for a teacher, used in loops
public String getTeacherGrade(int currClassId, int index)
{
			try
			{
				String tempGrade = "";
				Connection conn = DriverManager.getConnection(url,"postgres","jakl");
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("SELECT grade FROM \"grades\" WHERE classid=" + currClass);

				while(index > 0)
				{
				rs.next();
				index--;
				}
				tempGrade = rs.getString(1);

				rs.close();
				st.close();
				conn.close();

				return tempGrade;
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
				return null;
			}

}

//Returns The Quiz id for a certain sttudent
public String getTeacherNameInfo(int currClassId, int index)
{
			try
			{
				String tempGrade = "";
				Connection conn = DriverManager.getConnection(url,"postgres","jakl");
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("SELECT quizid, studentid FROM \"grades\" WHERE classid=" + currClass);

				while(index > 0)
				{
				rs.next();
				index--;
				}
				tempGrade = rs.getString(1);
				tempGrade = tempGrade.concat("-" + rs.getString(2));

				rs.close();
				st.close();
				conn.close();

				return tempGrade;
			}
			catch (Exception e)
			{
				System.out.println(e.getMessage());
				return null;
			}

}





//Used to convert a string into an array, some queries return arrays as (5,5,5,5)
	public String[] getQuizArray(String str)
	{
		Vector<String> quizStuff = new Vector<String>();

		String testString = str;
		StringTokenizer st = new StringTokenizer(testString, ",");

		while(st.hasMoreTokens())
		{
			quizStuff.add(st.nextToken());
		}

		String[] temp = new String[quizStuff.size()];

		for(int i =0; i < quizStuff.size(); i++)
		{
			temp[i] = quizStuff.get(i);
		}

		return temp;
	}

	//Converts an array to a string
	public String sArrayToString(String[] tempArray)
	{
		String s = "";

		for(int i = 0; i < tempArray.length; i++)
		{
			s = s.concat(tempArray[i].toString() + ", ");

		}
		s = s.substring(0, s.length()-2);


		return s;
	}


	public void sendClassId(int id)
	{
		currClass = id;
	}

	public int getClassId()
	{
		return currClass;
	}

	public String getUserName(int userId)
	{
		String name = null;
		try
		{
			Connection conn = DriverManager.getConnection(url, "postgres","jakl");
			Statement st = conn.createStatement();
			ResultSet uName = st.executeQuery("SELECT username FROM \"user\" WHERE id= " + userId);
			uName.next();
			name = uName.getString(1);
			uName.close();
			conn.close();
			return name;
		}
		catch(Exception e)
		{
			//System.out.println("rawrrr");
			System.out.println(e.getMessage());
			return null;
		}
	}

	//converts an int array to a string (used for placing data in the datatbase)
	public String intArrayToString(int[] temp)
	{
		String s = "";

		for(int i = 0; i < temp.length; i++)
		{
			s = s.concat(temp[i] + ", ");

		}
		s = s.substring(0, s.length()-2);


		return s;

	}

	//Returns the number of quizzes a class has taken
	public int getQuizzesTaken(int classId)
	{
		int i = 0;
		try
		{
		   Connection conn = DriverManager.getConnection(url,"postgres","jakl");
		   Statement stmt = conn.createStatement();
		   ResultSet count = stmt.executeQuery("SELECT classid FROM \"grades\" WHERE classid=" + classId);
		   if(!count.isLast())
		   {
		   count.next();
	   	   }
		   i = count.getRow();
		   count.close();
		   conn.close();
		   return i;
	   }
	   catch(Exception e)
	   {
		   System.out.println(e.getMessage());
		   return 0;
	   }
   }

}