/*
*	Account type for a Student
*
*
*
* Authors:
* Jeremy Parmenter
* Alex Holguin
* John Kevin Canez
*/

import java.util.Vector;

public class Student extends User
{
	//Constructor when there are no classes for the Student
	public Student(int _id, String _name, String _pass)
	{
		super(_id,_name,_pass);
		status = 's';
		classList = new Vector<Integer>(); // might as well instanciate it
	}

	//Constructor when Student does have classes
	public Student(int _id, String _name, String _pass, int[] tempClassArray)
	{
		super(_id, _name, _pass);
		status = 's';
		classList = new Vector<Integer>();

		for(int i = 0; i < tempClassArray.length;i++)
		{
			classList.add(i, tempClassArray[i]);
		}
	}

	// returns an array of the users current classes
	public int[] getClassList()
	{
		int[] tempArray = new int[classList.size()];

		for(int i = 0; i < classList.size(); i++)
		{
			tempArray[i] = classList.get(i);
		}

		return tempArray;
	}

	//returns the number of classes the current user is registered for
	public int getNumClasses()
	{
		return classList.size();
	}

	//used to flag successful password changes
	public boolean changePass(String oldPass, String newPass)
	{

		if((pass.compareTo(oldPass)) == 0)
		{
			pass = newPass;
			return true;
		}
		return false;
	}


	public String toString()
	{
		return "\n\nStudent: " + super.toString();
	}
}