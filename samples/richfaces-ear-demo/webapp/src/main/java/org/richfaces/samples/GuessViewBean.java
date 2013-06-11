/**
 * 
 */
package org.richfaces.samples;

import java.io.Serializable;

import javax.faces.FacesException;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

/**
 * @author asmirnov
 * 
 */
public class GuessViewBean implements Serializable,
		HttpSessionActivationListener {

	private Guess guess;

	private int userGuess;

	/**
	 * @return the userGuess
	 */
	public int getUserGuess() {
		return userGuess;
	}

	/**
	 * @param userGuess
	 *            the userGuess to set
	 */
	public void setUserGuess(int userGuess) {
		this.userGuess = userGuess;
	}

	/**
	 * @return the guess
	 */
	public Guess getGuess() {
		if (guess == null) {
			try {
				InitialContext ic = new InitialContext();
				try {
					guess = (Guess) ic.lookup(Guess.class.getName());
				} catch (NameNotFoundException e) {
					// JBOSS Server uses different JNDI name
					guess = (Guess) ic.lookup("richfacesEAR/GuessBean/remote");
				}
			} catch (NamingException e) {
				throw new FacesException("Local Guess EJB not found", e);
			}
		}

		return guess;
	}

	/**
	 * @param guess
	 *            the guess to set
	 */
	public void setGuess(Guess guess) {
		this.guess = guess;
	}

	/**
	 * @return
	 * @see org.richfaces.samples.Guess#getAttempts()
	 */
	public int getAttempts() {
		return getGuess().getAttempts();
	}

	/**
	 * @return
	 * @see org.richfaces.samples.Guess#getMax()
	 */
	public int getMax() {
		return getGuess().getMax();
	}

	/**
	 * @return
	 * @see org.richfaces.samples.Guess#getMaxAttempts()
	 */
	public int getMaxAttempts() {
		return getGuess().getMaxAttempts();
	}

	/**
	 * @return
	 * @see org.richfaces.samples.Guess#getMin()
	 */
	public int getMin() {
		return getGuess().getMin();
	}

	/**
	 * @return
	 * @see org.richfaces.samples.Guess#guess()
	 */
	public int guess() {
		return getGuess().guess();
	}

	/**
	 * 
	 * @see org.richfaces.samples.Guess#start()
	 */
	public void start() {
		getGuess().start();
	}

	public void sessionDidActivate(HttpSessionEvent se) {
		// do nothing.

	}

	public void sessionWillPassivate(HttpSessionEvent se) {
		// remove Statefull session bin.
		synchronized (this) {
			if (null != guess) {
				guess.destroy();
				guess = null;
			}
		}
	}

}
