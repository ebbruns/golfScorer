package golfScorer;

import java.util.ArrayList;

public class Golfer implements Comparable<Golfer>{
	ArrayList<Double> scores;
	String name;
	double average;
	double total;
	
	//updates golfer average to reflect scores
	public void update(){
		if(this.scores.size() == 0){
			this.average = 0;
			return;
		}
		double sum = 0;
		for(double number : this.scores){
			sum += number;
		}
		this.total = sum;
		this.average = sum/this.scores.size();
		return;
	}
	
	//needed to implement comparable interface to allow sorting
	public int compareTo(Golfer otherGolfer) {
		return (int)(this.average - otherGolfer.average);
	}
	
	//Every golfer has a name, an ArrayList of scores, and an average score.
	public Golfer(String newName){
		name = newName;
		scores = new ArrayList<Double>();
		average = 0;
	}
}
