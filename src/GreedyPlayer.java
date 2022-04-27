/**
 * A Connect-4 player that makes greedy moves.
 * 
 * @author Jade Singer
 *
 */
public class GreedyPlayer implements Player{

	int id;
	int cols;
	
    @Override
    public String name() {
        return "Greedy boi";
    }

    @Override
    public void init(int id, int msecPerMove, int rows, int cols) {
    	this.id =id; //your player id is id, opponent's is (3-id)
    	this.cols = cols;
    	
    }

    @Override
    public void calcMove(
        Connect4Board board, int oppMoveCol, Arbitrator arb) 
        throws TimeUpException {
        // Make sure there is room to make a move.
        if (board.isFull()) {
            throw new Error ("Complaint: The board is full!");
        }
        
        int[] scores = new int [cols]; //total number
        
       
        
        //Calculate the relative score for all possible moves and pick best or 
        
        for (int c = 0; c < cols; c++) { //c is column looking at
        	
        	if(board.isValidMove(c)) {
        		
        		board.move(c, id);
        
        			scores[c] = calcScore(board, id) - calcScore(board, 3 - id);
        
        				board.unmove(c, id);
        	
        	}
        	else scores[c] = -1000;
        }
        
        //iterate through the array and find the max
        
        double max = scores[0]; // max score going through array
        int maxCol = 0; // column that is corresponding to the max score
        
        for( int i = 0; i < scores.length; i++) {
        	
        	if (scores[i] > max) {
        		
        		maxCol = i;
        		
        		max = scores[i];
        	}
        }
      
        		
        arb.setMove(maxCol);
    }
 
    
    

    //Return the number of connect-4s that player #id has.
	public int calcScore(Connect4Board board, int id)
	{
		final int rows = board.numRows();
		final int cols = board.numCols();
		int score = 0;
		// Look for horizontal connect-4s.
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c <= cols - 4; c++) {
				if (board.get(r, c + 0) != id) continue;
				if (board.get(r, c + 1) != id) continue;
				if (board.get(r, c + 2) != id) continue;
				if (board.get(r, c + 3) != id) continue;
				score++;
			}
		}
		// Look for vertical connect-4s.
		for (int c = 0; c < cols; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c) != id) continue;
				if (board.get(r + 1, c) != id) continue;
				if (board.get(r + 2, c) != id) continue;
				if (board.get(r + 3, c) != id) continue;
				score++;
			}
		}
		// Look for diagonal connect-4s.
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c + 0) != id) continue;
				if (board.get(r + 1, c + 1) != id) continue;
				if (board.get(r + 2, c + 2) != id) continue;
				if (board.get(r + 3, c + 3) != id) continue;
				score++;
			}
		}
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = rows - 1; r >= 4 - 1; r--) {
				if (board.get(r - 0, c + 0) != id) continue;
				if (board.get(r - 1, c + 1) != id) continue;
				if (board.get(r - 2, c + 2) != id) continue;
				if (board.get(r - 3, c + 3) != id) continue;
				score++;
			}
		}
		return score;
	}
}