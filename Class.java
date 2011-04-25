import java.util.*;

public class Class {
	private int id;
	private String title;
	private String desc;
	private int teacherId;
	private Vector<Integer> quizIds;
	//private Vector<User> roster;

	public Class(int _id, String _title, String _desc, int _teacherId)
	{
		id = _id;
		title = _title;
		desc = _desc;
		teacherId = _teacherId;
		quizIds = new Vector<Integer>();
		//roster = null;
	}

	public Class(int _id, String _title, String _desc, int[] tempQuizIds, int _teacherId)
	{
		id = _id;
		title = _title;
		desc = _desc;
		teacherId = _teacherId;
		quizIds = new Vector<Integer>();
		for(int i = 0; i < tempQuizIds.length;i++)
		{
			quizIds.add(i, tempQuizIds[i]);
		}
	}

	public int getNumQuizes()
	{
		return quizIds.size();
	}

	public int showQuizId(int index)
	{
		return quizIds.get(index);
	}

	public void changeDesc(String nDesc)
	{
		desc = nDesc;
	}

	public void addQuiz(int tempId)			// be sure to write to db
	{
			quizIds.add(tempId);
	}

	/*public void addStudent(User student)
	{
		if (roster == null)
			roster = new Vector<User>();

		roster.add(student);
	}*/

	public int getTeacher()
	{
		return teacherId;
	}

	public int getId()
	{
		return id;
	}

	public String getTitle()
	{
		return title;
	}

	public String getDesc()
	{
		return desc;
	}

	public String toString()
	{
		return "ID: " + id + "\tCourse: " + title;
	}

	public int getNumQuizzes()
	{
		return quizIds.size();
	}
}
