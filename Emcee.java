import java.util.ArrayList;


public class Emcee extends Thread{
	
	int numPlayers;
	ArrayList<Player> players;
	Manager manager;
	int currentRound;
	int totalRounds;



	/**
	 * Constructor for Emcee
	 * Creates the manager object and array of players
	 * Also stores the current round number and total number of rounds needed
	 * @param n number of players
	 */
	public Emcee(int n){
		numPlayers = n;
		manager = new Manager(n);
		players = new ArrayList<>();
		currentRound = 1;
		totalRounds = n-1;
	}

	/**
	 * Create all the players and store them in an array.
	 * Provides each player with the manager object to allow for message passing
	 */
	public void initPlayers(){
		for(int i = 1; i <= numPlayers; i++){
			players.add(new Player(("P" + i),manager));
		}
	}


	/**
	 * This method is used to remove the loser player from the list of active players
	 * @param name name of player to be removed
	 */
	public synchronized void removePlayer(String name){
		// find player to be removed
		for(Player p: players){
			if(p.name.equals(name)){
				players.remove(p);
				manager.numPlayers--;
				break;
			}
		}
	}

	

	/**
	 * Starting point for emcee thread
	 * turns the music on and off
	 * notifies threads when to start looking for chair
	 */
	@Override
	public void run(){

		System.out.println("BEGIN " + numPlayers + " players\n");

		// start all the players, they will be circling the chairs 
		// as music is on when they start moving
		for (Player p : players) {
			p.start();
		}

		// loop to run all rounds needed for game
		while(currentRound <= totalRounds){
			System.out.println("round " + currentRound);

			// System.out.println("music on");

			//let the music play for a while before turning it off
			try {
				sleep(2000);
			} catch (InterruptedException e) {e.printStackTrace();}

			// turns the music off and notifies all players to start finding chairs
			// at the same time
			manager.musicOff();

			//let the players finish finding their chairs
			try {
				sleep(1000);
			} catch (InterruptedException e) {e.printStackTrace();}

			// once all the players have found chairs, state the one
			// who was too slow to find a player
			System.out.println(manager.roundLoser + " lost\n");

			// remove the player who lost from the list of active players
			removePlayer(manager.roundLoser);

			// resets all chairs for the next round
			manager.prepareChairsForNextRound();
			
			currentRound++;
		}
		// once all the rounds are done, state the only player who won
		// in the last round, since its a 1v1 this will always be the
		// last winner
		System.out.println(manager.roundWinner + " wins!\nEND");
		// ensure that emcee thread exits when done
		System.exit(0);
	}



	/**
	 * Main method that initiates the game of musical chairs
	 * @param args command line arguments where we get number of players from if specified
	 * @throws InterruptedException
	 */

}
