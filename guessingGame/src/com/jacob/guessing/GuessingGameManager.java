package com.jacob.guessing;

import java.io.*;
import java.util.*;

/****************************************************************************
 * <b>Title</b>: GuessingGameManager.java
 * <b>Project</b>: smtlearning
 * <b>Description: </b> Guessing Game for Multiple players
 * <b>Copyright:</b> Copyright (c) 2020
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Jacob Rodgers
 * @version 1.0
 * @since Oct 5, 2020
 * @updates:
 ****************************************************************************/

public class GuessingGameManager {
	
	private int maximum = 100;
	private int target=1;
	private int numGuesses=0;
	private Map<String, ArrayList<Integer>> perPlayerGH = new HashMap<>();
	private Map<String,String> responsesEN = new HashMap<>();
		
	/**
	 * 
	 * @param max
	 */
	private void setMaximum(int max) {
		this.maximum = max;
		this.responsesEN.put("pickingNotify","Picking a number between 1 and " + this.maximum);
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		GuessingGameManager ggm = new GuessingGameManager();
		ggm.standardWorkflow();

	}
	
	/**
	 * Standard process of game
	 */
	public void standardWorkflow() {
		//Initiate console control
		initConsole();
		
				
		// Play Game, store results for each player
		playGame(this.perPlayerGH);
		
		
		// Finalize, cleanup, and show results per player
		finishGame();

	}
	
	/**
	 * Starts game in console and takes maximum
	 */
	private void initConsole() {
		//Create EN Dict
		this.responsesEN.put("initialsPrompt","Please input your initials: ");
		this.responsesEN.put("addPlayerPrompt", "Would you like to add another player? 'yes' or 'no': ");
		this.responsesEN.put("continueGamePrompt","Congrats! Would you like to continue? 'yes' or 'no': ");
		this.responsesEN.put("error","Invalid output call");
		this.responsesEN.put("welcomeNotify","Welcome to the guessing game!");
		this.responsesEN.put("maxPrompt","What do you want the maximum number to be?    ");
		this.responsesEN.put("currentPlayers","Current players: ");
		this.responsesEN.put("userPrompt", "Who's playing?");
		this.responsesEN.put("pickingNotify","Picking a number between 1 and " + this.maximum);
		this.responsesEN.put("tooHighNotify","Wrong, too high!");
		this.responsesEN.put("tooLowNotify","Wrong, too low!");
		this.responsesEN.put("correctNotify","Correct! It was " + this.target);
		this.responsesEN.put("resultsNotify","Thanks for playing! Here are your results:");
		
		output("welcomeNotify","p");
		output("maxPrompt","p");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			Integer input = Integer.parseInt(reader.readLine());
			this.setMaximum(input);
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		
	}
	
	
	/**
	 * Setups game; random number within bounds
	 */
	private String setup() {
		output("currentPlayers","p");
		String playerName = getUserInput(output("userPrompt")); //Who's Playing?
		output("pickingNotify","p");
		this.target = (int) (Math.random() * this.maximum) + 1;		
		return playerName;
	} 
	
	/**
	 * Function to read user input as String
	 * @param prompt
	 * @return String
	 */
	public String getUserInput(String prompt) {
		String inputLine = null;
		System.out.print(prompt + "    ");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			inputLine = reader.readLine();
			if (inputLine.length() == 0) return null;
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return inputLine;
	}
	
	/**
	 * Starts game for player 
	 * @param playerInitials
	 */
	 
	 
	private void playGame(Map<String, ArrayList<Integer>> perPlayerGH) {
		Integer userGuess = -1;
		String continueGame = "yes";
		while (continueGame.equals("yes")) {
			String playerName = setup();
			while (userGuess != this.target) {
				if (!perPlayerGH.containsKey(playerName)) {
					perPlayerGH.put(playerName, new ArrayList<Integer>());
				}
				userGuess = Integer.parseInt(getUserInput(playerName + ", enter a guess: "));
				checkUserGuess(playerName,userGuess);
//				System.out.println("Try: " + this.target); //DEBUG
//				System.out.println("You typed: " + userGuess); //DEBUG
			}
			userGuess=-1;
			continueGame = getUserInput(output("continueGamePrompt"));
		}
		System.out.println(this.perPlayerGH.entrySet()); //DEBUG
	}
	
	/**
	 * Checks user guess against target value
	 * @param guess
	 */
	private void checkUserGuess(String playerName, int guess) {
		numGuesses++;
		if (guess > this.target) {
			output("tooHighNotify","p");
		} 
		if (guess < this.target) {
			output("tooLowNotify","p");
		}
		if (guess == this.target) {
			this.responsesEN.put("correctNotify","Correct! It was " + this.target);
			output("correctNotify","p");
			// Append to history and reset numGuesses
			this.perPlayerGH.get(playerName).add(numGuesses);
			numGuesses=0;			
		}
	}
	
	/**
	 * Finalizes game and outputs results
	 */
	private void finishGame() {
				
		output("resultsNotify","p");
		for (Map.Entry<String, ArrayList<Integer>> entry : this.perPlayerGH.entrySet()) {
			System.out.println("Player: " + entry.getKey());
			int sum = 0;
			float average = 0;
			int base = 0;
			for (int i=0; i < entry.getValue().size(); i++) {
				System.out.println("Game " + (i+1) + ": Score " + entry.getValue().get(i));
				sum += entry.getValue().get(i);
				base++;
			}
			average = sum/base;
			System.out.println("Your average score across your games was: " + average);
		}
	}
	
	/**
	 * Output function
	 * @param message
	 * @return
	 */
	private String output(String message) {
		// Fill with static outputs
				
		if (this.responsesEN.containsKey(message)) {
			return this.responsesEN.get(message);
		} else {
			return this.responsesEN.get("error");
		}

		
	}
	
	/**
	 * output function to print to System.out
	 * @param message
	 * @param function
	 * @return String
	 */
	private void output(String message, String function) {
		//Fill with print statements
				
		if (this.responsesEN.containsKey(message)) {
			System.out.println(this.responsesEN.get(message));
		} else {
			System.out.println(this.responsesEN.get("error"));
		}
	
	}
}
