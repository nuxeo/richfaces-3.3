/**
 * 
 */
package org.richfaces.samples;

import javax.ejb.Local;
import javax.ejb.LocalHome;
import javax.ejb.Remote;
import javax.ejb.Remove;

/**
 * @author asmirnov
 *
 */
@Remote
public interface Guess {

	/**
	 * @return the min
	 */
	public int getMin();

	/**
	 * @param min the min to set
	 */
	public void setMin(int min);

	/**
	 * @return the max
	 */
	public int getMax();

	/**
	 * @param max the max to set
	 */
	public void setMax(int max);

	/**
	 * @return the attempts
	 */
	public int getAttempts();

	/**
	 * @param attempts the attempts to set
	 */
	public void setAttempts(int attempts);

	/**
	 * @return the maxAttempts
	 */
	public int getMaxAttempts();

	/**
	 * @param maxAttempts the maxAttempts to set
	 */
	public void setMaxAttempts(int maxAttempts);

	/**
	 * @return the userGuess
	 */
	public int getUserGuess();

	/**
	 * @param userGuess the userGuess to set
	 */
	public void setUserGuess(int userGuess);

	public void start();

	public int guess();

	@Remove
	public void destroy();

}
