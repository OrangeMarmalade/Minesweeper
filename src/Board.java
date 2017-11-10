import java.util.HashSet;

public class Board {
	int width;
	int height;
	int mineNum;
	
	Cell[][] grid;
	HashSet<Cell> mines;
	
	private String unknown = "?";
	private String mine = "*";
	
	public Board(int h, int w, int mineN) {
		height = h;
		width = w;
		mineNum = mineN;
		generateBoard();
		
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getMineNum() {
		return mineNum;
	}
	
	public boolean checkMine(int row, int col) {
		Cell cand = grid[row][col];
		if (cand.hasMine()) {
			cand.setSeen(true);
			return true;
		}
		
		
		return false;
	}
	public int clearSpace(int row, int col) {
		Cell cand = grid[row][col];
		return clearSpace(cand);
	}
	// TO DO:
	public int clearSpace(Cell cand) {
		int spacesCleared = 0;
		int row = cand.getRow();
		int col = cand.getColumn();
		if (!cand.haveSeen()) {
			cand.setSeen(true);
			spacesCleared ++;
		}
		if (cand.number() > 0) {
			return spacesCleared;
		} else {
			if (row > 0 && !grid[row-1][col].haveSeen()) {

				spacesCleared += clearSpace(grid[row-1][col]);			
			}
			if (col > 0 && !grid[row][col-1].haveSeen()) {
	
				spacesCleared += clearSpace(grid[row][col-1]);
			}
			if (col < width-1 && !grid[row][col+1].haveSeen()) {
				spacesCleared += clearSpace(grid[row][col+1]);
			}
			if (row < height - 1 && !grid[row+1][col].haveSeen()) {
				spacesCleared += clearSpace(grid[row+1][col]);
			}
			
		}
		return spacesCleared;

	}
	
	
	public void generateBoard() {
		grid = new Cell[height][width];
		mines = new HashSet<Cell>(mineNum);
		int mineCount = 0;
		int gridRemains = height * width;
		for (int i = 0; i < height; i++) {
			
			for (int j = 0; j < width; j++) {
				if (canPlaceMine(mineCount, gridRemains))  {
					grid[i][j] = new Cell(i, j, true);
					mines.add(grid[i][j]);
					mineCount++;
					
				} else {
					grid[i][j] = new Cell(i,j);	
				}
				gridRemains--;
			}	
		}
		processBoard();
	}
	public void resetBoard() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				grid[i][j].resetNumber();
				grid[i][j].setSeen(false);
			}
		}
	}
	public void shuffleBoard() {
		resetBoard();
		for (int i = 0; i < height; i ++) {
			for (int j = 0; j < width; j++) {
				int sRow = (int) (Math.random() * height);
				int sCol = (int) (Math.random() * width);
				
				
				
				if (grid[i][j].hasMine() && grid[sRow][sCol].hasMine()) {
					
				} else if (grid[i][j].hasMine() ) {
					mines.remove(grid[i][j]);
					mines.add(grid[sRow][sCol]);
				} else if (grid[sRow][sCol].hasMine()) {
					mines.add(grid[i][j]);
					mines.remove(grid[sRow][sCol]);
				}
				switchCell(grid[i][j], grid[sRow][sCol]);
				

			}
		}
		processBoard();
	}
	
	private void switchCell(Cell c1, Cell c2) {
		boolean c2IsMine = c2.hasMine();
		boolean c2IsSeen = c2.haveSeen();
		boolean c2IsGuess = c2.isGuess();
		
		c2.setGuess(c1.isGuess());
		c2.setMine(c1.hasMine());
		c2.setSeen(c1.haveSeen());
		c1.setGuess(c2IsGuess);
		c1.setMine(c2IsMine);
		c1.setSeen(c2IsSeen);
		
	}
	
	// setting up the number for each part of the bomb
	private void processBoard() {
		Cell[] temp = mines.toArray(new Cell[mines.size()]);
		for (int i = 0; i < temp.length; i ++) {
			updateNumbers(temp[i]);
		}
	}
	
	private void updateNumbers(Cell bombTile) {
		int row = bombTile.getRow();
		int column = bombTile.getColumn();
		
		if ((row - 1) >= 0) {
			if ((column - 1) >= 0) {
				grid[row-1][column-1].numberPlusPlus();
				
			}
			grid[row-1][column].numberPlusPlus();
			
			if ((column + 1) < width) {
				grid[row-1][column+1].numberPlusPlus();	
			}
		}
		
		if ((column - 1) >= 0) {
			grid[row][column-1].numberPlusPlus();
		}
		if ((column + 1) < width) {
			grid[row][column+1].numberPlusPlus();
		}
		
		if ((row + 1) < height) {
			if ((column - 1) >= 0) {
				grid[row+1][column-1].numberPlusPlus();
			}
			grid[row + 1][column].numberPlusPlus();
			
			if ((column + 1) < width) {
				grid[row+1][column+1].numberPlusPlus();
			}
		}
		
	}
	
	public void showSolution() {
		System.out.println("THIS IS THE SOLUTION BOARD:");
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Cell temp = grid[i][j];
				if (temp.hasMine()) {
					System.out.print(" " + mine);
				} else {
					System.out.print(" " + temp.number());
				}
				
			}
			System.out.println();
		}
	}
	public void showBoard() {
		System.out.println("THIS IS THE BOARD:");

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Cell temp = grid[i][j];
				if (temp.haveSeen()) {
					if (temp.hasMine()) {
						System.out.print(" " + mine);
					} else {
						System.out.print(" " + temp.number());
					}
					
				} else {
					System.out.print(" " + unknown);
				}
				
			}
			System.out.println();
		}
	}
	

	

	/*
	
	public Board(int h, int w, int mineN) {
		height = h;
		width = w;
		mineNum = mineN;
		
		generateBoard();
		
	}
	
	public void showBoard() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(" " + visible[i][j]);
			}
			System.out.println();
		}
	}
	
	private void generateBoard() {
		visible = new String[height][width];
		mines = new boolean[height][width];
		
		int mineCount = 0;
		int gridRemains = height * width;
		for (int i = 0; i < height; i++) {
			
			for (int j = 0; j < width; j++) {
				if (canPlaceMine(mineCount, gridRemains))  {
					mines[i][j] = true;
				} else {
					mines[i][j] = false;
				}
				
				visible[i][j] = unknown;
				
			}	
		}
	}*/
	
	
	private boolean canPlaceMine(int mineCount, int gridRemains) {
		int numCreated = (int) (Math.random() * gridRemains) + 1;
		if (numCreated <= (mineNum - mineCount)) {
			return true;
		}
		return false;
	}
	
}
