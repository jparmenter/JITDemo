/*
*	Administrator account type
*
*
*
* Authors:
* Jeremy Parmenter
* Alex Holguin
* John Kevin Canez
*/

import java.util.Vector;

public class Admin extends User
{
	//Constructor for admin with no classes assigned
	public Admin(int _id, String _name, String _pass)
	{
		super(_id, _name, _pass);
		status = 'a';
		classList = new Vector<Integer>();
	}

	//Constructor for admin WITH classes
	public Admin(int _id, String _name, String _pass, int[] tempClassArray)
	{
		super(_id, _name, _pass);
		status = 'a';
		classList = new Vector<Integer>();

		for(int i = 0; i < tempClassArray.length;i++)
		{
			classList.add(i, tempClassArray[i]);
		}
	}
	//returns the number of classes admin is assigned
	public int getNumClasses()
	{
		return classList.size();
	}

	//create Admin. Used in testing
	public Admin createAdmin(int id, String name, String pass)
	{
		Admin admin = new Admin(id, name, pass);
		return admin;
	}

	//Returns the admins list of classes
	public int[] getClassList()
	{
		int[] tempArray = new int[classList.size()];

		for(int i = 0; i < classList.size(); i++)
		{
			tempArray[i] = classList.get(i);
		}

		return tempArray;
	}

	//create teacher, used in testing
	public Teacher createTeacher(int id, String name, String pass)
	{
		Teacher teacher = new Teacher(id, name, pass);
		return teacher;
	}

	public String toString()
	{
		return "\n\nAdmin: " + super.toString();
	}
}