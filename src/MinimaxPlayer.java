/**
 * A Connect-4 player that thinks more than one move ahead.
 * 
 * @author Jade Singer
 *
 */
public class MinimaxPlayer implements Player{
	
	int id;
	int cols;
	int opponent_id;
	
    @Override
    public String name() {
        return "Minnie";
    }

    @Override
    public void init(int id, int msecPerMove, int rows, int cols) {
    	this.id =id; //your player id is id, opponent's is (3-id)
    	this.cols = cols;
    	opponent_id = 3-id;
    }

    @Override
    public void calcMove(
        Connect4Board board, int oppMoveCol, Arbitrator arb) 
        throws TimeUpException {
        // Make sure there is room to make a move.
        if (board.isFull()) {
            throw new Error ("Complaint: The board is full!");
        }
        
        int maxCol = 0;
        int maxDepth = 1;
      
        // while there's time left and maxDepth <= number of moves remaining  
        while(!arb.isTimeUp() && maxDepth <= board.numEmptyCells()) { 
          	//do miniMax search
        	// start the first level of miniMax, set move as you'rre finding the bestScore
        	int bestScore = -1000;
        	
    			for(cols = 0; cols < 7; cols++) {//for each possible next move do
    				
    				if (board.isValidMove(cols)) {
    					board.move(cols,id);
    					
    					int score = miniMax(board, maxDepth - 1, false, arb);
    					
    					if (score > bestScore) {
    						bestScore = score;
    						maxCol = cols;	
    					}
    					board.unmove(cols,id);
    				}
    			}
            	arb.setMove(maxCol);
              	maxDepth++;
              	System.out.println("pruning" + maxDepth);
        }
    }
 
    public int miniMax(Connect4Board board, int depth, boolean isMaximizing, Arbitrator arb) {	   

    	if (depth == 0 || board.numEmptyCells() == 0 || arb.isTimeUp()) {
    		return score(board);
    	}
    	
    	int bestScore;
    	
    	
    	if(isMaximizing == true) { //maximizing player
    		bestScore = -1000;
    		
    		for(cols = 0; cols < 7; cols++) { //for each possible next move do
    			
    			
    			if (board.isValidMove(cols)) {
    				
    				board.move(cols, id);
    				
    				bestScore = Math.max(bestScore, miniMax(board, depth - 1, false, arb));
    						
    				board.unmove(cols,id);
    			}
    		}
    		return bestScore;
    }
    	else { 	//minimizing player
    		bestScore = 1000;
    		
    		for(cols = 0; cols < 7; cols++) { //for each possible next move do
    			
    			
    			if (board.isValidMove(cols)) {
    				
    				board.move(cols, opponent_id);
    				
    				bestScore = Math.min(bestScore, miniMax(board, depth - 1, true, arb));
    						
    				board.unmove(cols, opponent_id);
    				}
    			}
    		return bestScore;
    	}
    }
    
    public int score(Connect4Board board) {
    	return calcScore(board, id) - calcScore(board, opponent_id);
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