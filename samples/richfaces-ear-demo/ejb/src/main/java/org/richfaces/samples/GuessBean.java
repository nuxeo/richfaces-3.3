/**
 * 
 */
package org.richfaces.samples;

import java.io.Serializable;

import javax.ejb.Init;
import javax.ejb.Remove;
import javax.ejb.Stateful;

/**
 * @author asmirnov
 *
 */
@Stateful
public class GuessBean implements Guess,Serializable {
	
	int random;
	
	int min;
	
	int max;
	
	int attempts;
	
	int maxAttempts;
	
	int userGuess;
	
	public GuessBean() {
		min = 0;
		max = 100;
		attempts=0;
		maxAttempts = 10;
	}
	
	@Init
	public void init(){
		
	}

	@Remove
	public void destroy(){
		
	}
	
	/**
	 * @return the random
	 */
	public int getRandom() {
		return random;
	}

	/**
	 * @param random the random to set
	 */
	public void setRandom(int random) {
		this.random = random;
	}

	/**
	 * @return the min
	 */
	public int getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(int min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}

	/**
	 * @return the attempts
	 */
	public int getAttempts() {
		return attempts;
	}

	/**
	 * @param attempts the attempts to set
	 */
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}



	/**
	 * @return the maxAttempts
	 */
	public int getMaxAttempts() {
		return maxAttempts;
	}



	/**
	 * @param maxAttempts the maxAttempts to set
	 */
	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}



	/**
	 * @return the userGuess
	 */
	public int getUserGuess() {
		return userGuess;
	}



	/**
	 * @param userGuess the userGuess to set
	 */
	public void setUserGuess(int userGuess) {
		this.userGuess = userGuess;
	}
	
	public void start(){
		
	}
	
	public int guess(){
		return userGuess-random;
	}

}
