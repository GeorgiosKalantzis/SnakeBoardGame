
package snakeGame_PartA;

import java.util.*;
import java.lang.Math;

public class Game {
	int round;

	public Game() {
		round = 0;
	}

	public Game(int r) {
		round = r;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int r) {
		this.round = r;
	}

	/*
	 * The method creates a Map , in which the keys are the random dices and the
	 * values are the player's ID , so it returns a sorted by key Map , with the
	 * corresponding ID's as values , !! CAUTION !! If the dices are same , then the
	 * Map will contain only one element, but we resolve that case later in the main
	 * method
	 */

	public Map<Integer, Integer> setTurns(ArrayList<Player> players) {
		// The return map
		Map<Integer, Integer> PlayersMap = new HashMap<Integer, Integer>();
		int dice;
		// To get access to the Player objects inside the ArrayList
		Player player;

		for (int i = 0; i < players.size(); i++) {
			dice = 1 + (int) (Math.random() * 6);
			player = players.get(i);
			PlayersMap.put(dice, player.getPlayerId());
		}

		return PlayersMap;
	}
	/*
	 * I create a new game , the board and then the players , which I add them in a
	 * ArrayList , to pass it as an argument to setTurns method , in order to
	 * determine the turns, later inside a for loop , the players play alternately ,
	 * once they are two , and finally the winner is printed , along with statistics
	 * and other info
	 */

	public static void main(String[] args) {

		int N = 20;
		int M = 10;

		Game game = new Game(0);

		Board board = new Board(N, M, 3, 3, 6);

		board.createBorad();
		board.createElementBoard();
		// The players
		Player player1 = new Player(0, "player1", 0, board);
		HeuristicPlayer player2 = new HeuristicPlayer(1, "player2", 0, board);

		ArrayList<Player> players = new ArrayList<Player>();

		players.add(player1);
		players.add(player2);
		// Call setTurns , to determine the turn
		Map<Integer, Integer> PlayersTurn = game.setTurns(players);

		// Cause we have only two players we can alternately select who is going to play
		// , by just multiplying by -1 the variable k with initial value 1
		int k = 1;

		// Set the first player
		for (Integer key : PlayersTurn.keySet()) {
			// In case the players had the same dice , by default I choose that the first
			// player will be the one that plays with random moves
			if (PlayersTurn.get(key) == 0 || PlayersTurn.size() == 1) {
				k = k * 1;
				break;
			}

			if (PlayersTurn.get(key) == 1) {

				k = k * (-1);
				break;

			}

		}

		int[] currentPosition = new int[players.size()];
		int newPosition = 0;
		String winnerName = null;
		String theFaster = null;
		int dice;
		// We use this variable to calculate later the exact number of the rounds
		double roundCounter = 1.0;

		for (int i = 0; i < players.size(); i++) {
			currentPosition[i] = 0;
		}

		System.out.println("*********** The game begins **********");
		System.out.println();

		for (;;) {
			roundCounter++;

			if (k == 1) {

				dice = 1 + (int) (Math.random() * 6);

				newPosition = player1.move(currentPosition[player1.getPlayerId()], dice)[0];
				currentPosition[player1.getPlayerId()] = newPosition;

				if (newPosition >= N * M) {

					// Player1 terminated first
					theFaster = player1.getName();
					// Does he have also the greatest score ?
					if (player1.getScore() > player2.getScore()) {

						winnerName = player1.getName();

					}

					break;
				}

			}

			if (k == -1) {

				newPosition = player2.getNextMove(currentPosition[player2.getPlayerId()]);
				currentPosition[player2.getPlayerId()] = newPosition;
				player2.statistics();

				if (newPosition >= N * M) {

					// Player2 terminated first
					theFaster = player2.getName();
					// Does he have also the greatest score ?
					if (player2.getScore() > player1.getScore()) {

						winnerName = player2.getName();

					}

					break;
				}

			}

			if (newPosition >= N * M) {
				break;
			}

			// Multiply by -1 , so the players play alternately
			k = k * (-1);

		}

		// If the first one that finished didn't also had bigger total score , then the
		// winner is the faster
		if (winnerName == null) {
			winnerName = theFaster;
		}

		// Cause the counter inside the for loop , counts twice every round
		// we use a upper division to calculate the integer number of rounds
		game.round = (int) Math.ceil(roundCounter / 2.0);

		System.out.println();
		System.out.println("*********** The game is over *********");
		System.out.println();
		System.out.println("Rounds played: " + game.round);

		for (int i = 0; i < players.size(); i++) {
			System.out.println(((Player) players.get(i)).getName() + " gathered " + ((Player) players.get(i)).getScore()
					+ " points");
		}
		System.out.println("***********  " + winnerName + " won the game!!!" + "  ************");

	}

}
