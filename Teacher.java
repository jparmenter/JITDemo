/*
*	Account type for a Teacher
*
*
*
* Authors:
* Jeremy Parmenter
* Alex Holguin
* John Kevin Canez
*/

import java.util.*;

public class Teacher extends User
{
	//Constructor for a teacher with no classes
	public Teacher(int _id, String _name, String _pass)
	{
		super(_id, _name, _pass);
		status = 't';
		classList = new Vector<Integer>();
	}

	//Constructor for a teacher WITH classes
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

	//returns an array of classIds
	public int[] getClassList()
	{
		int[] tempArray = new int[classList.size()];

		for(int i = 0; i < classList.size(); i++)
		{
			tempArray[i] = classList.get(i);
		}

		return tempArray;
	}

	//returns the number of classes the teacher is teaching
	public int getNumClasses()
	{
		return classList.size();
	}

	//Old create User method, used for testing
	public User createStudents(int id, String name, String pass)
	{
		Student student = new Student(id, name, pass);
		return student;
	}

	public String toString()
	{
		return "\n\nTeacher: " + super.toString();
	}

}