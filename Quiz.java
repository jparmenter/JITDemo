/*
*
*	Quiz Class, hold arrays of questions and answers
*
*
* Authors:
* Jeremy Parmenter
* Alex Holguin
* John Kevin Canez
*/

import java.util.*;

public class Quiz
{

	private int quizId;					//QuizId is the unique number of each quiz (concatinated with class ususally class:9999 quiz:1 = 99991.)
	private static String[] question;	//String array of questions
	private String[] answer1;			//String array of answers
	private String[] answer2;
	private String[] answer3;
	private String[] answer4;
	private int[] correctAnswer;		//int array of the right answers, used in grading

	//Constructor that takes in all of the data
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

	//returns the correct answer array
	public int[] getCorrectAnswers()
	{
		return correctAnswer;
	}

	//returns the number of questions in the quiz
	public static int numQuestions()
	{
		return question.length;
	}

	//returns question
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

}