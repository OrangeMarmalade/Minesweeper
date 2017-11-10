
public class Cell {
	private int row;
	private int column;
	private boolean isMine;
	private int number;
	private boolean isSeen = false;
	private boolean isGuess = false;
	
	public Cell (int r, int c) {
		row = r;
		column = c;
		isMine = false;
	}
	public Cell (int r, int c, boolean hasMine) {
		row = r;
		column = c;
		isMine = hasMine;
	}
	public void resetNumber() {
		number = 0;
	}
	public boolean hasMine() {
		return isMine;
	}
	public void setMine(boolean hasBomb) {
		isMine = hasBomb;
	}
	public int number() {
		return number;
	}
	
	public void setNumber(int num) {
		number = num;
	}
	public void numberPlusPlus() {
		number ++;
	}
	
	public boolean haveSeen() {
		return isSeen;
	}
	public void setSeen(boolean hasSeen) {
		isSeen = hasSeen;
	}
	
	public boolean isGuess() {
		return isGuess;
	}
	public void setGuess(boolean hasGuess) {
		isGuess = hasGuess;
	}
	public int getRow() {
		return row;
		
	}
	public int getColumn() {
		return column;
	}

}
