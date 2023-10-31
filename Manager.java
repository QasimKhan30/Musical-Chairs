import java.util.ArrayList;
import java.util.Iterator;

public class Manager {

	ArrayList<Chair> chairs;
	String roundWinner;
	String roundLoser;
	int numPlayers;


	/**
	 * Constructor for manager object
	 * @param numPlayers number of players starting the game
	 */
	public Manager( int numPlayers) {
		chairs = new ArrayList<>();
		this.roundWinner = "";
		this.roundLoser = "";
		this.numPlayers = numPlayers;
	}




	/**
	 * Creates all the chairs needed and stores them in an array
	 */
	public void initChairs() {
		for (int i = 1; i < numPlayers; i++) {
			chairs.add(new Chair("C" + i));
		}
	}

	/**
	 * Turns the music on and lets the players circle chairs
	 * @throws InterruptedException
	 */
	public synchronized void circleChairs() throws InterruptedException{
		wait();
	}

	/**
	 * Turns the music off for all the players
	 */
	public synchronized void musicOff() {
		System.out.println("music off");
		notifyAll();
	}

	/**
	 * Removes the highest number chair and
	 * makes sure the rest of the chairs are not occupied by any player
	 */
	public synchronized void prepareChairsForNextRound(){

		chairs.remove(numPlayers-1);
		for(Chair c: chairs){
			c.occupied = false;
			c.occupiedBy = null;

		}

	}
	
}
