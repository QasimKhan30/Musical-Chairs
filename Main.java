public class Main {
	public static void main(String[] args) throws InterruptedException {
		int n = 10;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		}

		// create emcee
		Emcee emcee = new Emcee(n);
		// initialize necessary objects
		emcee.manager.initChairs();
		emcee.initPlayers();
		// start game
		emcee.start();

	}
}
