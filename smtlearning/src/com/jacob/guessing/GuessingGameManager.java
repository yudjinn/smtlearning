package com.jacob.guessing;
/****************************************************************************
 * <b>Title</b>: GuessingGameManager.java
 * <b>Project</b>: smtlearning
 * <b>Description: </b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2020
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author Jacob Rodgers
 * @version 3.0
 * @since Oct 5, 2020
 * @updates:
 ****************************************************************************/
import java.io.*;
import java.util.*;

public class GuessingGameManager {
	
	private int maximum = 100;
	private int target=1;
	private int numGuesses=0;
	private ArrayList<Integer> gameHistory = new ArrayList<Integer>();
	
	/**
	 * 
	 * @param max
	 */
	private void setMaximum(int max) {
		this.maximum = max;
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
	 * 
	 */
	public void standardWorkflow() {
		//Initiate console control
		initConsole();
		
		// Play Game, store results
		playGame();
		
		// Finalize, cleanup, and show results
		finishGame();

	}
	
	/**
	 * 
	 */
	private void initConsole() {
		System.out.println("Welcome to the guessing game!");
		System.out.print("What do you want the maximum number to be?   ");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			Integer input = Integer.parseInt(reader.readLine());
			this.setMaximum(input);
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
	}
	
	/**
	 * 
	 */
	private void setup() {
		System.out.println("Picking a number between 1 and " + this.maximum);
		this.target = (int) (Math.random() * this.maximum) + 1;
	} 
	
	/**
	 * 
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
	 * 
	 */
	private void playGame() {
		Integer userGuess = -1;
		String continueGame = "yes";
		while (continueGame.equals("yes")) {
			setup();
			while (userGuess != this.target) {
				userGuess = Integer.parseInt(getUserInput("Enter a guess: "));
				checkUserGuess(userGuess);
				System.out.println("Try: " + this.target);
				System.out.println("You typed: " + userGuess);
			}
			continueGame = getUserInput("Congrats! Would you like to continue? 'yes' or 'no': ");
		}
	}
	
	/**
	 * 
	 * @param guess
	 */
	private void checkUserGuess(int guess) {
		numGuesses++;
		if (guess > this.target) {
			System.out.println("Wrong, too high!");
		} 
		if (guess < this.target) {
			System.out.println("Wrong, too low!");
		}
		if (guess == this.target) {
			System.out.println("Correct! It was " + this.target);
			// Append to history and reset numGuesses
			this.gameHistory.add(numGuesses);
			numGuesses=0;			
		}
	}
	
	/**
	 * 
	 */
	private void finishGame() {
		int sum = 0;
		
		System.out.println("Thanks for playing! Here are your results:");
		for (int i=0; i < gameHistory.size(); i++) {
			System.out.println("Game " + (i+1) + ": Score " + gameHistory.get(i));
			sum += gameHistory.get(i);
		}
		float average = sum/gameHistory.size();
		System.out.println("Your average score across your games was: " + average);
	}

}
