import java.util.ArrayList;

public class HeuristicPlayer extends Player {

	ArrayList<Integer[]> path;

	// Constructor that calls the Player's class constructor
	public HeuristicPlayer(int id, String n, int s, Board b) {
		super(id, n, s, b);
		path = new ArrayList<Integer[]>();

	}

	// Copy constructor
	public HeuristicPlayer(HeuristicPlayer obj) {
		playerId = obj.playerId;
		name = obj.name;
		score = obj.score;
		// Use the copy constructor of board to create a new one independent from the
		// original
		board = new Board(obj.board);

	}

	/*
	 * I create a new HeuristicPlayer object using the copy constructor and inside
	 * the copy constructor , I call Board's copy constructor to create a new copy
	 * board. Finally the method implements the evaluation function that I defined
	 * and returns it.
	 */
	public double evaluate(int currentPos, int dice) {

		HeuristicPlayer objClone = new HeuristicPlayer(this);

		int[] moveTable = new int[5];

		// Make the move
		moveTable = objClone.move(currentPos, dice);

		// Evaluation function
		double evalFunc = 0.7 * (moveTable[0] - currentPos) + 0.3 * (objClone.score);

		return evalFunc;
	}

	public int getNextMove(int currentPos) {

		// Array for possibleMoves
		double[] possibleMoves = new double[6];

		int[] moveTable = new int[5];

		// To save the final dice
		int bestMove = 0;
		// For comparisons , in order to find the best dice
		double maxScore = 0;

		for (int i = 1; i <= possibleMoves.length; i++) {

			possibleMoves[i - 1] = this.evaluate(currentPos, i);

			if (possibleMoves[i - 1] >= maxScore) {

				maxScore = possibleMoves[i - 1];

				bestMove = i;

			}

		}

		// Actual move with our original object's board
		moveTable = this.move(currentPos, bestMove);

		int points = this.getScore();

		// The info for the ArrayList
		Integer[] info = { bestMove, moveTable[0] - currentPos, points, moveTable[1], moveTable[2], moveTable[3],
				moveTable[4] };

		this.path.add(info);

		return moveTable[0];
	}
	/*
	 * I save the ArrayList's size to a variable round, which is the current round
	 * then I am printing some info about the heuristic player and finally , I
	 * iterate over my ArrayList to gather all the statistics for the player and
	 * print them
	 */

	public void statistics() {
		
		//Round's number
		int Round = this.path.size();

		System.out.println("The player chose dice " + this.path.get(Round - 1)[0] + " at round " + Round
				+ " was bitten by " + this.path.get(Round - 1)[3] + " snakes" + " and climbed "
				+ this.path.get(Round - 1)[4] + " ladders ");
		
			
		// To collect all the information we want till the current round
		int snakeVisits = 0;
		int ladderVisits = 0;
		int redAppleVisits = 0;
		int blackAppleVisits = 0;

		for (int i = 0; i < Round; i++) {

			snakeVisits = snakeVisits + this.path.get(i)[3];

			ladderVisits = ladderVisits + this.path.get(i)[4];

			redAppleVisits = redAppleVisits + this.path.get(i)[5];

			blackAppleVisits = blackAppleVisits + this.path.get(i)[6];

		}
		

		System.out.println("Player , so far , visited " + snakeVisits + " snakes");

		System.out.println("Player, so far , visited " + ladderVisits + " ladders");

		System.out.println("Player, so far , ate " + redAppleVisits + " red apples");

		System.out.println("Player, so far , ate " + blackAppleVisits + " black apples");

	}

}
