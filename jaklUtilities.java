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

import java.util.*;
import java.io.*;
import java.sql.*;

public class jaklUtilities
{
	private String url = "jdbc:postgresql://70.190.95.77:5432/jitt";
	public int currClass = 0;
	public jaklUtilities()
	{
		try
		{
			java.lang.Class.forName("org.postgresql.Driver");
		}
		catch(Exception e)
		{
			System.out.println("This should never happen");
		}
	}

	public void writeAdmin(int id, String name, String pass, char acctType)
	{
		 try
		 {
			// Load the JDBC driver.
			//java.lang.Class.forName("org.postgresql.Driver");

			// Establish the connection to the database
			//String url = "jdbc:postgresql://localhost:5432/jakl";
			Connection conn = DriverManager.getConnection(url,"postgres","jakl");

			//This is how you write. just create a statement and execute a sql query.

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


	public void writeTeacher(int id, String name, String pass, char acctType)
		{
			 try
			 {
				// Load the JDBC driver.
				//java.lang.Class.forName("org.postgresql.Driver");

				// Establish the connection to the database
				//String url = "jdbc:postgresql://localhost:5432/jakl";
				Connection conn = DriverManager.getConnection(url,"postgres","jakl");

				//This is how you write. just create a statement and execute a sql query.

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

	public void writeStudent(int id, String name, String pass, char acctType)
		{
			 try
			 {
				Connection conn = DriverManager.getConnection(url,"postgres","jakl");

				//This is how you write. just create a statement and execute a sql query.

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


	public User openUser(int id)
		{
			String strUser = null;
			String strPass = null;
			String strType = null;
			Character charType;
			String numClasses;
			try
			{
				//java.lang.Class.forName("org.postgresql.Driver");

				//String url = "jdbc:postgresql://localhost:5432/jakl";
				Connection conn = DriverManager.getConnection(url,"postgres","jakl");

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
				//SELECT array_to_string(ARRAY[class], ',') FROM "user" WHERE id=9999
			   ResultSet classes = stmt.executeQuery("SELECT array_to_string(ARRAY[class], ',') from \"user\" WHERE id=" + id);
			   classes.next();
			   numClasses = classes.getString(1);
			   int[] tempArray = getClassArray(numClasses);
			   conn.close();

			charType = strType.charAt(0);
			if (charType == 't')
			{
				if(tempArray != null)
				{
				Teacher teach = new Teacher(id, strUser, strPass, tempArray);
				return teach;
				}
				else
				{
				Teacher teach = new Teacher(id, strUser, strPass);
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
				Student student = new Student(id,strUser,strPass, tempArray);
				return student;
				}
				else
				{
				Student student = new Student(id, strUser,strPass);
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

	public void writeClass(int id, String name, String description, int teacher)
	{
		 try
		 {
			Connection conn = DriverManager.getConnection(url,"postgres","jakl");

			//This is how you write. just create a statement and execute a sql query.

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

public Quiz openQuiz(int quizId)
		{
			//System.out.println("HERESZFDFKLDSHSHDHGJLKSD");
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

		public void writeQuiz(int quizId, String[] questions, String[] answers1, String[] answers2, String[] answers3, String[] answers4, int[] correctAnswers, int classId)
		{
			String class_quiz_id = Integer.toString(classId) + Integer.toString(quizId);
			int final_class_quiz_id = Integer.parseInt(class_quiz_id);
				try
				{
					Connection conn = DriverManager.getConnection(url,"postgres","jakl");
					Statement st = conn.createStatement();
					st.executeUpdate("INSERT INTO \"quiz\"\nVALUES\n(" + final_class_quiz_id + ", '{" + sArrayToString(questions) + "}', '{" + sArrayToString(answers1)+ "}', '{" + sArrayToString(answers2) + "}', '{" + sArrayToString(answers3) + "}', '{" + sArrayToString(answers4) + "}', '{" + intArrayToString(correctAnswers) + "}')");
					Statement st1 = conn.createStatement();
					st1.executeUpdate("UPDATE \"class\" SET quizids = quizids || ARRAY["+ final_class_quiz_id + "] WHERE id=" + classId);

					System.out.println("success!");
					conn.close();
				}
				catch (Exception e)
				{
					System.out.println(e.getMessage());
				}
		}

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



	/*public int[] getRoster(int classId)
	{
			int i = 0;
			try
			{
				Connection conn = DriverManager.getConnection(url, "postgres","jakl");
				Statement st = conn.createStatement();
				ResultSet count = st.executeQuery("SELECT COUNT(correctanswer) FROM \"user\" WHERE id =" + classId);
				count.next();
				i = count.getInt(1);
				int[] correctAnswers = new int[i];
				count.close();
				ResultSet users = st.executeQuery("SELECT array_to_string(ARRAY[correctanswer], ',') from \"quiz\" WHERE id=" + quizId);
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

	}*/


}





/*OLD CODE //////////////////////////////////////////////////////////////////////////////////////////////////





	public Admin openAdmin(int id)
	{
		String user = null;
		String pass = null;
			try {
			FileInputStream fstream = new FileInputStream("/home/alex/workspace/prototype/users/admin/" + id + ".txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));


			String strLine;
			//Read File Line By Line
			user = br.readLine();
			pass = br.readLine();
			//Close the input stream
			in.close();
			}catch (Exception e){//Catch exception if any
			return null;
			}

		Admin admin = new Admin(id, user, pass);
		return admin;
	}


	public Admin writeAdmin(int id, String user, String pass)
	{
		Writer writer = null;
		String text = null;
		System.out.println("Enter in data to be entered");
		Scanner scan = new Scanner(System.in);
		text = scan.nextLine();

		try {
		    //String text = "This is a text file";

		    File file = new File("/home/alex/workspace/prototype/users/admin/" + id + ".txt");
		    writer = new BufferedWriter(new FileWriter(file));
		    writer.write(text);
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
			}
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
	    }
	}
}
*/