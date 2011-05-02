/*
* Basic User Class, abstract so we can instanciate subusers as Users (ex: User x = new ____(stuff);
*
*
*
* Authors:
* Jeremy Parmenter
* Alex Holguin
* John Kevin Canez
*/

import java.util.Vector;

abstract public class User
{
	protected int id;
	protected String name;
	protected String pass;
	protected Vector<Integer> classList;
	protected char status;

	//Constructor to assign basic data
	public User(int tempId, String tempName, String tempPass)
	{
		id = tempId;
		name = tempName;
		pass = tempPass;
	}

	//Change password, this method mainly used as a test
	public boolean changePass(String oldPass, String newPass)
	{

		if((pass.compareTo(oldPass)) == 0)
		{
			pass = newPass;
			return true;
		}
		return false;
	}

	//Test method to show users classes
	public void showClassIds()
	{
		int index = 0;
		while(index < classList.size())
		{
			System.out.println(classList.get(index).toString());
			index++;
		}
	}

	//returns selected class
	public int showClassId(int index)
	{
			return classList.get(index);
	}

	public String getName()
	{
		return name;
	}

	public int getId()
	{
		return id;
	}

	public String getPass()
	{
		return pass;
	}

	//Status used often, used to check what type of account user has
	public char getStatus()
	{
		return status;
	}
	public String toString()
	{
		return "\n\nID: " + id + "\nName: " + name + "\n";
	}

	//two methods every subclass must have, classes are assigned in them.
	abstract int[] getClassList();

	abstract int getNumClasses();
}