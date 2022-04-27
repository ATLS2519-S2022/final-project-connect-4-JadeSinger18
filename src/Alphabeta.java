/**
 * A Connect-4 player that makes greedy moves.
 * 
 * @author Jade Singer
 *
 */
public class Alphabeta implements Player{
	
	int id;
	int cols;
	int opponent_id;
	
    @Override
    public String name() {
        return "Pruning";
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
        
        int [] scores = new int [cols];
        
        int col = 0;
        int maxDepth = 1;
        int alpha = -1000;
        int beta = 1000;
      
        // while there's time left and maxDepth <= number of moves remaining  
        while(!arb.isTimeUp() && maxDepth <= board.numEmptyCells()) { 
          	//do miniMax search
        	// start the first level of miniMax, set move as you'rre finding the bestScore
        	int bestScore = -1000;
        	
    			for(cols = 0; cols < 7; cols++) {//for each possible next move do
    				
    				if (board.isValidMove(cols)) {
    					board.move(cols,id);
    					
    					int score = alphabeta(board, maxDepth - 1, alpha, beta, false, arb);
    					
    					if (score > bestScore) {
    						bestScore = score;
    						col = cols;}
    				
    					board.unmove(cols,id);
    				}
    			}
            	
            	arb.setMove(col);
              		
              	maxDepth++;   
    		}

          	System.out.println("pruning" + maxDepth);
          }

        
  
 
    public int alphabeta(Connect4Board board, int depth, int alpha, int beta, boolean isMaximizing, Arbitrator arb) {	   

    	if (depth == 0 || board.numEmptyCells() == 0 || arb.isTimeUp()) {
    		return score(board);
    	}
    	
    	cols = 0;
    	int bestScore;
    	
    	if(isMaximizing == true) { //if maximizing player
    		bestScore = -1000;
    		
    		for(cols = 0; cols < 7; cols++) {
    			if (board.isValidMove(cols++)) {
    				//board.move( cols,  id);
    				bestScore = Math.max(bestScore, alphabeta(board, depth - 1,alpha, beta, false, arb));
    				alpha = Math.max(alpha, bestScore);
    				if (alpha >= beta)break;
    						//board.unmove(cols,id);
    			}
    		}
    		return bestScore;
    }

    	else { 	//minimizing player
    		bestScore = 1000;
    		
    		for(cols = 0; cols < 7; cols++) {
    			if (board.isValidMove(cols)) {
    				//board.move( cols,  id);
    				bestScore = Math.min(bestScore, alphabeta(board, depth - 1,alpha, beta, true, arb));
    				beta = Math.min(beta, bestScore);
    				if (alpha >= beta)break;
    						//board.unmove(cols,id);
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