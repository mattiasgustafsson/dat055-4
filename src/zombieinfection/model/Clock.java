package zombieinfection.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import zombieinfection.view.highscore.Highscore;

// Clock that counts down second by second
public class Clock extends Thread {

	private PropertyChangeSupport pcs;
	// number of seconds to live
	private int secondsLeft;
	// if the clock is started or not
	private boolean ticking;

	public Clock() {
		pcs = new PropertyChangeSupport(this);
		ticking = false;
		start();// when creating, thread runs
	}

	public int getSecondsLeft() {
		return secondsLeft;

	}

	// use this to connect a view to this model
	public void addPropertyChangeListener(PropertyChangeListener l) {
		pcs.addPropertyChangeListener(l);
	}

	// call this method to start the clock-when a new game starts-
	public void startTicking(int initialSeconds) {
		updateSecondsLeft(initialSeconds);
		ticking = true;
	}

	// call this to stop the clock
	public void stopTicking() {
		ticking = false;
	}

	@Override
	// This is the thread loop
	public void run() {
		while (!isInterrupted()) {
			try {
				sleep(1000);
			} catch (InterruptedException ex) {
				break;
			}
			if (ticking) {
				updateSecondsLeft(secondsLeft - 1);
				Player player = GameEngine.getInstance().getPlayer();
				if (player.isInfected()) {
					player.setHealth(player.getHealth() - 1);
				}
			}
		}
	}

	// update and fire property change
	private void updateSecondsLeft(int newValue) {
		int oldValue = secondsLeft;
		if (newValue < 0) {
			newValue = 0;
		}
		if (oldValue != newValue) {
			secondsLeft = newValue;

			pcs.firePropertyChange("secondsLeft", oldValue, newValue);
		}

		if (newValue == 0) {
			ticking = false;
			GameEngine.getInstance().setGameOver();
			pcs.firePropertyChange("gameOver", false, true);
			if (GameEngine.getInstance().showLoserMsg()) {
				new Highscore(0);
			}

		}
	}

}