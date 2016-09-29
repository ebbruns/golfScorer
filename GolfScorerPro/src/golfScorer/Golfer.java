package golfScorer;

import java.util.ArrayList;

public class Golfer implements Comparable<Golfer>{
	ArrayList<Integer> scores;
	String name;
	int average;
	
	//updates golfer average to reflect scores
	public void update(){
		int sum = 0;
		for(int number : this.scores){
			sum += number;
		}
		this.average = sum/this.scores.size();
	}
	
	//needed to implement comparable interface to allow sorting
	public int compareTo(Golfer otherGolfer) {
		return this.average - otherGolfer.average;
	}
	
	//Every golfer has a name, an ArrayList of scores, and an average score.
	public Golfer(String newName){
		name = newName;
		scores = new ArrayList<Integer>();
		average = 0;
	}
}
