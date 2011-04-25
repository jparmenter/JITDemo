/* Team Name: Jakl
*  Teacher class
*  Last Modified By: Jeremy Parmenter
*  Last Modified On: 4/1/11
*/

/*
*	TODO: create quiz?
*/

import java.util.*;

public class Teacher extends User
{

	//public static Vector<Class> classList;

	public Teacher(int _id, String _name, String _pass)
	{
		super(_id, _name, _pass);
		status = 't';
		classList = new Vector<Integer>();
	}

	public Teacher(int _id, String _name, String _pass, int[] tempClassArray)
	{
		super(_id, _name, _pass);
		status = 't';
		classList = new Vector<Integer>();

		for(int i = 0; i < tempClassArray.length;i++)
		{
			classList.add(i, tempClassArray[i]);
		}
	}

	public void createQuiz()
	{
		return;
	}

	public User createStudents(int id, String name, String pass)
	{
		Student student = new Student(id, name, pass);
		return student;
	}

	public String toString()
	{
		return "\n\nTeacher: " + super.toString();
	}

	public void addClass()
	{
		//ADD
	}
}
