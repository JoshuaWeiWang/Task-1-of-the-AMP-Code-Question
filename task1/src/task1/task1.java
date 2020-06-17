package task1;

public class task1 {
	// The grid to hold the words.
	private static String[] grid;
	
	//The words to be filled.
	private static String[] words; 
	
	// The dimension of the grid
	private static int DIM = 10;	
	
	// The constant of positive char
	private static char POSITIVE = '+';
	
	// The constant of negative char
	private static char NEGATIVE = '-';
	
    public static void main(String[] args){
    	// Indicate that how many words have been filled
    	int index = 0;		
    	
        if(args.length == DIM + 1){ 	
        	// Verify the length of arguments
        	grid = new String[DIM];
    		for (int idx = 0; idx < DIM ; idx++){
    			if(args[idx].length() != DIM){
    				System.out.printf("Please reset the row %d of the input grid.", idx + 1);
            		System.exit(0);
    			} else {
    				grid[idx] = args[idx];
    			}
    		}
    		
        	words = args[DIM].split(";");

        	fillGrid(grid, words, index);
        } else{
        	System.out.println("Please reset the arguments");
        	System.exit(0);
        }
    }
    

	private static void fillGrid(String[] grid, String[] words, int index) {
		// Here is the function to fill the words in the given grid
		
		// The word to be filled in this recursion
		String word; 			
		
		// The updated grid after one recursion
		String[] updatedGrid;
		
		if (index < words.length) { 				
			// We have more words to be filled.
			word = words[index];
			
			for (int col = 0; col < DIM; col++) { 
				for (int row = 0; row <= DIM - word.length(); row++) { 
					updatedGrid = fillByColumn(grid, word, row, col); 
					if (updatedGrid != null) { 			
						// This current column can match this word
						fillGrid(updatedGrid, words, index + 1); 
					}
				}
			}
			
			for (int row = 0; row < DIM; row++) {
				for (int col = 0; col <= DIM - word.length(); col++) {
					updatedGrid = fillByRow(grid, word, row, col); 
					if (updatedGrid != null) {				
						// This current row can match this word
						fillGrid(updatedGrid, words, index + 1);
					} 
				}
			}
		} else { 									
			// All words have been filled, and then we show the result.
			for(String row: grid) {
				System.out.println(row);
			}
		}
	}
	

	private static String[] fillByRow(String[] grid, String word, int row, int col) {
		// Fill a specific word by rows
		StringBuilder build;
		
		// The index of the tail
		int idx;
		
		for (idx = 0; idx < word.length(); idx++) { 
			if (grid[row].charAt(col + idx) == NEGATIVE ||  grid[row].charAt(col + idx) == word.charAt(idx)) { 
				continue;
			} else { 
				// If this row is not suitable, return null 
				return null; 
			} 
		} 
		
		if ((col + idx < DIM && grid[row].charAt(col + idx) != POSITIVE) || 
				(col > 0 && grid[row].charAt(col - 1) == NEGATIVE)){
			// This current row is too broad. 
			return null;
		} else {
			// If this row is suitable, replace and return
			build = new StringBuilder(grid[row]);
			build.replace(col, col + word.length(), word);
			grid[row] = build.toString();
			return grid;
		}
	}
	
	
	private static String[] fillByColumn(String[] grid, String word, int row, int col) {
		// Fill a specific word by columns

		// A tool to replace the letters in grid
		StringBuilder build;							
		
		// The index of the tail
		int idx;
		
		for (idx = 0; idx < word.length(); idx++) { 
			if (grid[row + idx].charAt(col) == NEGATIVE || grid[row + idx].charAt(col) == word.charAt(idx)) { 
				// If the current entry of grid can be filled or already be filled by the match letter, continue to check next one
				continue;
			} else {  
				// If this column is not suitable, return null 
				return null; 
			} 
		}
		
		if ((row + idx < DIM && grid[row + idx].charAt(col) != POSITIVE) ||
				(row > 0 && grid[row - 1].charAt(col) == NEGATIVE)){
			// This column is too broad.
			return null;
		} else {
			for (idx = 0; idx < word.length(); idx++) { 
				// If this column is suitable, replace and return
				build = new StringBuilder(grid[row + idx]);
				build.replace(col, col + 1, String.valueOf(word.charAt(idx)));
				grid[row + idx] = build.toString();
			}
			return grid; 
		}
	}
}