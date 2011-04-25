import java.util.Vector;

abstract public class User
{
	protected int id;
	protected String name;
	protected String pass;
	protected Vector<Integer> classList;
	//private Vector<Integer> grades;
	protected char status;

	public User(int tempId, String tempName, String tempPass)
	{
		id = tempId;
		name = tempName;
		pass = tempPass;
	}

	// Need to know if the change pass was successful
	public boolean changePass(String oldPass, String newPass)
	{

		if((pass.compareTo(oldPass)) == 0)
		{
			pass = newPass;
			return true;
		}
		return false;
	}

	public void showClassIds()
	{
		int index = 0;
		while(index < classList.size())
		{
			System.out.println(classList.get(index).toString());
			index++;
		}
	}

	public int showClassId(int index)
	{

			return classList.get(index);

	}


	/*public void addClass(Class _c)
	{
		if (c == null)
			c = new Vector<Class>();

		c.add(_c);
		if (status == 's')
			grades.add(0);
	}

	// getClass is a function in java have to name it something else
	public Vector<Class> getClasses()
	{
		return c;
	}
*/
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

	public char getStatus()
	{
		return status;
	}
	public String toString()
	{
		return "\n\nID: " + id + "\nName: " + name + "\n";
	}
}
