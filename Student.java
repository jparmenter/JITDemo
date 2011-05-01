import java.util.Vector;

public class Student extends User
{

	public Student(int _id, String _name, String _pass)
	{
		super(_id,_name,_pass);
		status = 's';
		classList = new Vector<Integer>(); // might as well instanciate it
		//grades = null;
	}

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

	//public void modifyGrade(int classNmbr, int grade)
	///{
		//int oldGrade = grades.get(--classNmbr);
		//oldGrade = grade;
	//}

	// Need to know if the change pass was successful

	public int[] getClassList()
	{
		int[] tempArray = new int[classList.size()];

		for(int i = 0; i < classList.size(); i++)
		{
			tempArray[i] = classList.get(i);
		}

		return tempArray;
	}

	public int getNumClasses()
	{
		return classList.size();
	}


	public boolean changePass(String oldPass, String newPass)
	{

		if((pass.compareTo(oldPass)) == 0)
		{
			pass = newPass;
			return true;
		}
		return false;
	}

	public void addClass(int classId)
	{
		if (classList == null)
		{
			classList = new Vector<Integer>();
		}

		classList.add(classId);
		//if (status == 's')
		//	grades.add(0);
	}

	/* getClass is a function in java have to name it something else
	public Vector<Class> getClasses()
	{
		return c;
	}*/

	public String toString()
	{
		return "\n\nStudent: " + super.toString();
	}
}