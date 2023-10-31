import java.util.ArrayList;
import java.util.Collections;

public class Player extends Thread{

	String name;
	Manager manager;
	boolean alive;
	boolean cont;


	/**
	 * Constructor for Player object
	 * @param name name of player
	 * @param manager manager object that allows for message passing between the classes
	 */
	public Player(String name, Manager manager){
		this.name = name;
		this.manager = manager;
		alive = true;
		cont = false;

	}

	/**
	 * Algorithm used for each player to randomly select a chair
	 * shuffles the orders of the chairs before trying to acquire each one
	 * this helps to make sure the chairs being chosen are even more random
	 */
	public void findChair(){
		// shuffle order of chairs to increase randomness
		ArrayList<Chair> temp = new ArrayList<>(manager.chairs);
		Collections.shuffle(temp);
		// try to acquire every chiar
		for (Chair c : temp) {
			if (c.acquireChair(this)) {
				// as soon as a chair is acquired, print to stdout
				System.out.println(name + " sat in " + c);
				//record winner information, only used in last round
				manager.roundWinner = name;
				// continue to the next round
				cont = true;
				return;
			}
		}
		
	}
	/**
	 * Starting point for when Player thread is started
	 * waits for music to be stopped through notify all from manager
	 * then starts looking for chairs
	 * passes the loser and winner information to manager for synchronized printing
	 */
	@Override
	public void run(){

		while(alive){
			
			// turn the music on
			try {
				manager.circleChairs();
			} catch (InterruptedException e) {e.printStackTrace();}

			// look for chair
			findChair();

			// if this player thread lost the round, record information
			// and let the thread terminate by exiting while loop
			if(!cont){
				manager.roundLoser = name;
				alive = false;
			}
			// prepare for next round
			cont = false;
			
		}
	}

	
	public String toString(){
		return name;
	}
}
