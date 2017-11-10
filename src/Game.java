import java.util.Scanner;

public class Game {
	Scanner sc = new Scanner(System.in).useDelimiter("\\s");
	Board board;
	public void initialize() {
		
		
		boolean hasParams = false;
		int height = 10;
		int width = 10;
		int mineNum = 5;
		
		System.out.println("Welcome to minesweeper!");
		while (!hasParams) {
			
			System.out.println("Please enter dimensions (height width mineNum):");
			height = sc.nextInt();
			width = sc.nextInt();
			mineNum = sc.nextInt();
			
			if ((mineNum - 1) >= height * width) {
				System.out.println("Please enter another dimension, your mines are more than the grid! (height width mineNum):");
			
			} else {
				hasParams = true;
			}
		}
		board = new Board(height, width, mineNum);
		
	}
	public void play() {
		
		int area = board.getHeight() * board.getWidth();
		int spacesCleared = 0;
		boolean finished = false;
		int turn = 0;
		
		Integer row = null;
		Integer col = null;
		
		
		do {
			turn++;
			boolean done = false;
			//board.showSolution();
			System.out.println("Turn " + turn);
			System.out.println("This is the current board:");
			board.showBoard();
			while (true) {
				
				try {
					System.out.println("Input the row and column number respectively:");
					row = sc.nextInt() - 1;
					col = sc.nextInt() - 1;
					break;
				
				} catch (Exception e) {
					System.out.println("Input valid numbers (row column pair) please");
					
					sc.next(); // avoiding double println again
					continue;
				}
			} 
			
			
			if (board.checkMine(row, col)) {
				finished = true;
				System.out.println("You hit a mine at row: " + (row+1) + ", col: " + (col+1));
				board.showBoard();
				System.out.println("Here is the solution:");
				board.showSolution();
				System.out.println("Game Over");
			} else {
				spacesCleared += board.clearSpace(row, col);
				if (spacesCleared == (area - board.getMineNum())) {
					board.showBoard();
					System.out.println("Congrats you win on turn " + turn + "!");
					finished = true;
				}
			}
			if (finished) {
				while (true) {

					System.out.println("Do you want to play again? y/n");
					// using this to avoid double println ^
					sc.nextLine();
					String response = sc.next();
					if (response.equals("y")) {
						board.shuffleBoard();
						finished = false;
						turn = 0;
						spacesCleared = 0;
						break;
					} else if (response.equals("n")) {
						System.out.println("Goodbye!");
						break;
					} else {
						continue;
					}

				}
				
			}
			
		} while (!finished); 
		
		
	}
	public static void main(String[] args) {
	
		Game game = new Game();
		
		game.initialize();
		game.play();
	}
	
	

}
