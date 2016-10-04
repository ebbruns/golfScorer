package golfScorer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class League {
	ArrayList<Golfer> golfers;
	BufferedReader reader;
	
	//This method calls the Golfer.update() method for every golfer in the league
	public void updateLeague(){
		for(Golfer player : golfers){
			player.update();
		}
	}
	
	//This method displays all the golfers in the league, from lowest average score to highest average score
	public void viewLeague() throws IOException{
		if(golfers.size() == 0){
			System.out.println("Sorry, but there are currently no golfers in the league");
			menu();
		}
		updateLeague();
		Collections.sort(golfers);
		for(Golfer player : golfers){
			System.out.println(player.name + " scored a total of " + player.total + " points in " + player.scores.size() + " games for an average of " + player.average + " points per game. Their scores were: " + player.scores.toString());
		}
		menu();
	}
	
	//The code for adding new golfers to the league
	public void addGolfer() throws IOException{
		while(true){
			System.out.println("Please enter the name of the golfer you'd like to add, or type \"done\" to return to the main menu.");
			String input = reader.readLine();
			if(input.toLowerCase().equals("done")){
				menu();
			}else if (input.equals("")){
				System.out.println("Please enter a name rather than a blank line");
				addGolfer();
			}
			golfers.add(new Golfer(input));
		}
	}
	
	// Allows deletions of golfers
	public void deleteGolfer( ) throws IOException{
		System.out.println("Please enter the number of the golfer you'd like to delete, or enter \"done\" to return to the main menu.");
		while(golfers.size()>0){
			int i = 0;
			for(Golfer player : golfers){
				System.out.println(player.name + " is golfer number " + i);
				i++;
			}
			String input = reader.readLine();
			if(input.equals("done")){
				menu();
			}else if (Integer.valueOf(input) >= golfers.size() || Integer.valueOf(input) < 0){
				System.out.println("Please select a number corresponding to a golfer on the list");
			}
			else{
				golfers.remove(golfers.get(Integer.valueOf(input)));
			}
		}
		System.out.println("All golfers have been deleted, returning to main menu.");
		menu();
	}
	
	//For adding scores to a given golfer in the league
	public void addScores(Golfer addTo) throws NumberFormatException, IOException{
		System.out.println("You can enter scores one at a time. Enter \"done\" to return to the main menu.");
		while(true){
			String input = reader.readLine();
			if(input.toLowerCase().equals("done")){
				menu();
			}else{
				addTo.scores.add(Integer.valueOf(input));
			}
		}
	}
	
	//For deleting scores from a given golfer in the league
	public void deleteScores(Golfer chosen) throws IOException{
		while(true){
			int i = 0;
			for(int score : chosen.scores){
				System.out.println("Score number " + i + " is " + score);
				i++;
			}
			System.out.println("Please enter the score number you'd like to delete, or type \"done\" to return to the main menu.");
			String input = reader.readLine();
			if(input.toLowerCase().equals("done")){
				menu();
			}else{
				chosen.scores.remove(chosen.scores.get(Integer.valueOf(input)));
			}
		}
	}
	
	//The main menu of the application
	public void menu() throws IOException{
		System.out.println("You're currently in the main menu. What would you like to do?");
		System.out.println("Type \"golfer\" to add a golfer, type \"scores\" to add scores to a golfer, type \"view\" to see the league sorted by average score, type \"save\" to save a league, \"load\" to load a league, and type \"exit\" to quit.");
		String input = reader.readLine().toLowerCase();
		
		if(input.equals("golfer")){
			golfersMenu();
		}
		if(input.equals("scores")){
			scoresMenu();
		}
		if(input.equals("view")){
			viewLeague();
		}
		if(input.equals("save")){
			this.save();
		}
		if(input.equals("load")){
			this.load();
		}
		if(input.equals("exit")){
			return;
		}
		
	}
	
	//The menu that can be used to add new golfers
	public void golfersMenu() throws IOException{
		while(true){
			System.out.println("Type \"add\" to add a new golfer, \"delete\" to remove an existing golfer, or \"done\" to return to the main menu.");
			String input = reader.readLine().toLowerCase();
			if(input.equals("add")){
				addGolfer();
			}else if(input.equals("delete")){
				deleteGolfer();
			}else if(input.equals("done")){
				menu();
			}
			else{
				System.out.println("Command not recognized, please try again!");
			}
		}
	}
	
	//The menu that can be used to enter new scores
	public void scoresMenu() throws NumberFormatException, IOException{
		if(golfers.isEmpty()){
			System.out.println("Sorry, there are no golfers currently in the system, please add one to enter a score.");
			menu();
		}else{
			int i = 0;
			System.out.println("Please enter the number of golfer you'd like to modify scores for:");
			for(Golfer player : golfers){
				System.out.println(player.name + " is golfer number " + i);
				i++;
			}
			String input = reader.readLine();
			Golfer chosen = golfers.get(Integer.valueOf(input));
			System.out.println("You have chosen " + chosen.name + ". Type \"add\" or \"delete\" to modify scores.");
			while(true){
				input = reader.readLine();			
				if(input.toLowerCase().equals("add")){
					addScores(chosen);
				}else if(input.toLowerCase().equals("delete")){
					deleteScores(chosen);
				}else{
					System.out.println("Sorry, that command wasn't recognized. Want to try again?");
				}
			}
		}
	}
	
	public void save() throws IOException{
		System.out.println("Please enter the name of the league");
		String leagueName = reader.readLine();
		ArrayList<String> lines = new ArrayList<String>();
		for(Golfer player : golfers){
			String scorelist = "";
			for(int score : player.scores){
				scorelist = scorelist + score + ",";
			}
			scorelist = scorelist.substring(0, scorelist.length()-1);
			lines.add(player.name + "," + scorelist);
		}
		Path file = Paths.get(leagueName + ".txt");
		Files.write(file, lines, Charset.forName("UTF-8"));	
		menu();
	}
	
	public void load(){
		
	}
	
	//A league is a set of golfers who will be compared. The league also includes a reader for stdin.
	public League(){
		golfers = new ArrayList<Golfer>();
		reader = new BufferedReader(new InputStreamReader(System.in));
	}
	
	//Initializes a league and opens the main menu
	public static void main(String [] args) throws IOException{
		System.out.println("Welcome to GolfScorer Pro!");
		League myLeague = new League();
		myLeague.menu();
	}
}

