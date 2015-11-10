package NET;

import java.io.Serializable;

public class Answer implements Serializable{
	boolean status;
	int num1;
	int num2;
	int sScore;
	int cScore;
	public Answer(boolean a,int first, int second, int s, int c){
		status= a;
		num1 = first;
		num2 = second;
		sScore = s;
		cScore = c;
	}
	
}
