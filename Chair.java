
public class Chair{
	String name;
	volatile boolean occupied;
	volatile Player occupiedBy;

	/**
	 * Constructor for chair object
	 * nobody is using the chair yet
	 * @param name name of chair
	 */
	public Chair(String name){
		this.name = name;
		this.occupied = false;
		occupiedBy = null;
	}

	/**
	 * Method used by player to sit on a chair
	 * if nobody is sitting on the chair then the player sits on the chair
	 * synchronized to ensure that only one player can sit on a chair at one time
	 * @param p player who is trying to sit on chair
	 * @return boolean true if player succesfully sits on chair, false otherwise
	 */
	public synchronized boolean acquireChair(Player p){
		if(!occupied){
			occupied = true;
			occupiedBy = p;
			return true;
		}
		return false;
	}
	public String toString(){
		return name;
	}

}