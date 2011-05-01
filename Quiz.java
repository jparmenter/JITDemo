import java.util.*;

public class Quiz
{

	private int quizId;
	private static String[] question;
	private String[] answer1;
	private String[] answer2;
	private String[] answer3;
	private String[] answer4;
	private int[] correctAnswer;

	public Quiz(int temp_quizId, String[] temp_question, String[] temp_ans1, String[] temp_ans2,
					String[] temp_ans3, String[] temp_ans4, int[] temp_correctAnswer)
	{
		quizId = temp_quizId;
		question = temp_question;
		answer1 = temp_ans1;
		answer2 = temp_ans2;
		answer3 = temp_ans3;
		answer4 = temp_ans4;
		correctAnswer = temp_correctAnswer;
	}

	/*public int grade(Vector<String> a)
	{
		int sum = 0;
		Question tempQ;
		String tempA;
		for (int i = 0; i < question.size(); i++)
		{
			tempQ = question.get(i);
			tempA = a.get(i);
			if (tempQ.grade(tempA))
				sum++;
		}

		return (sum / question.size());
	}

	public Vector<Question> getQuestion()
	{
		return question;
	}*/

	public int[] getCorrectAnswers()
	{
		return correctAnswer;
	}

	public static int numQuestions()
	{
		return question.length;
	}

	public String getQuestion(int index)
	{
		return question[index];
	}

	public String getAnswer1(int questionNum)
	{
		return answer1[questionNum];
	}

	public String getAnswer2(int questionNum)
	{
		return answer2[questionNum];
	}

	public String getAnswer3(int questionNum)
	{
		return answer3[questionNum];
	}

	public String getAnswer4(int questionNum)
	{
		return answer4[questionNum];
	}

	public int getId()
	{
		return quizId;
	}

	public String toString(int index)
	{
		return index + ":\t" + question[index] + "\n\t" + answer1[index] + "\n\t" + answer2[index] + "\n\t" + answer3[index] + "\n\t" + answer4[index];
	}

	/*public boolean checkAnswer(String userAnswer, int index)
	{
		if(correctAnswer[index].equals(userAnswer))
			return true;
		else
			return false;
	}*/
}