/*
 * Coins.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */

import java.lang.Math;
import java.util.Arrays;

/**
 * A program to print the longest sequence of coins that can produce the desired amount.
 * 
 * Logic used to find all possible combinations:
 *        The program first finds all the possible combinations of my coins array using
 *        the boolean logic.
 *         	Example: if my coins array has the following coins { 1, 2 }
 *                   then according to boolean logic, the above set of coins should 
 *                   produce a total of 4 (2^(length of coins array)) combinations, 
 *                   out of which, one is a null set.
 *                   So, according to the boolean logic, all the possible combinations
 *                   would be as follows:
 *                       
 *                   coins array:   { 1,    2 }
 *                   combination-1:   1     1     <--- ROW-1  { 1, 2 }
 *                   combination-2:   1     0     <--- ROW-2  { 1 }
 *                   combination-3:   0     1     <--- ROW-3  { 2 }
 *                   combination-4:   0     0     <--- ROW-4  { }             
 *                                    ^     ^
 *                                    |     |
 *                            COLUMN-1    COLUMN-2
 *                             
 *                   the boolean logic indicates if a particular value should be used in the 
 *                   combination or not, if the the boolean value is 1, then you put the 
 *                   coin in the array, else, you don't.
 *
 * Method used to generate the the boolean logic:
 *        If we observe the columns of the boolean logic carefully then we will 
 *        find a pattern that can be used to generate all the combinations easily.     
 *        Looking at the first column and the second, we will notice that the
 *        boolean value toggles after a certain toggle point.
 *        for the first column the toggle point is 2, 
 *        for the second column the toggle point is 1
 *        
 *        We will observe that starting from the length of the combinations 
 *        array(which is 4 in this case) the toggle point gets divides by 2
 *        for every column.
 *        initial toggle point: 4/2 = 2
 *        next toggle point   : 2/2 = 1                
 *                 
 * Each recursion of the generateCombArr() method produces each column of the boolean logic
 * which determines whether or not the coin in that particular column
 * can be added at the given index in the combinations array    
 * 
 * @author Lahari Chepuri
 * @author Smita Subhadarshinee Mishra
 */

public class Coins {
	static int[] myCoins = { 1, 1, 5, 2, 25, 25, 25 };
	static int noOfCoins = myCoins.length;
	static int[] toPay = { 0, 1, 4, 5, 7, 8 };
    
	/**
	 * The main program.
	 *
	 * @param args command line arguments (ignored)
	 */

	public static void main( String[] args ) {

		// Creating an object of CoinChange class
		// to call the non-static methods of this class.
		Coins object = new Coins();

		// Length of the array that will store all the 
		// possible combinations of the myCoins array
		int combArrLen = ( int ) Math.pow( 2, noOfCoins );
		
        // an empty array with length = combArrLen to pass
		// it as a parameter into the generateCombArr recursive method
		String[] allCombArr = new String[combArrLen];

		// initializing each element of the bufferArr to a null string
		for ( int i = 0; i < combArrLen; i++ ) {
			allCombArr[i] = "";
		}

		// sorts the myCoins array
		Arrays.sort( myCoins );   
		allCombArr = object.generateCombArr( allCombArr, myCoins, 
				combArrLen / 2, 0 );
		
        // calls the findSequence method for each element in toPay array
		for ( int amt : toPay ) {
			object.findSequence( allCombArr, amt );
		}

	}

	/**
	 * Generates all possible combinations using the boolean logic.
	 * 
	 * @param combArr          an empty String array which can be used to store
	 *                         all the possible combinations of the myCoins
	 *                         array in form of a string
	 * @param coinArr          array of all coins
	 * @param togglePoint      length of combination/2
	 * @param currentCoinIndex 0, gets incremented by 1 after each recursion
	 * 
	 * @return combArr         all possible combinations of the coin array
	 */

	public String[] generateCombArr( String[] combArr, int[] coinArr,
			int togglePoint, int currentCoinIndex ) {
		
		// length of the combArr (2^(length of coins array))
		int outputLength = combArr.length;
 
		// determines whether the coin value should be 
		// used or not, based on the boolean logic
		boolean useValue = false;

		if ( togglePoint == 0 ) { // base case for the recursive function
			
			// returns the combArr with all the possible
			// combinations of the coinArr to the caller
			return combArr;
		} else {
			
			for ( int i = 0; i < outputLength; i++ ) {
				if ( i % togglePoint == 0 ) {
					
					// toggles the value in useValue variable
					useValue = !useValue;
				}

				// if the boolean logic is true(1) for the coin at that
				// particular index
				if ( useValue == true ) {

					// adds the coin as a string in the required combArr index
					combArr[i] += coinArr[currentCoinIndex] + ", ";
				}

			}
			
			// stores the index of the next coin from the coinArr
			currentCoinIndex += 1;

			// making a recursive call to generate combinations for all 
			// the coins in the coinArr
			return generateCombArr( combArr, coinArr, togglePoint / 2,
					currentCoinIndex );

		}

	}

	/**
	 * finds the longest sequence of coins that can be used to produce 
	 * the desired amount
	 * 
	 * @param combArray an array with all possible combinations of the coins
	 * @param amount    the amount needed to pay
	 */

	public void findSequence( String[] combArray, int amount ) {
 
		// a string to store the longest sequence of
		// coins that produce the amount
		String seq = "";

		// length of the current sequence
		int currSeqLength = 0;
		
		// length of the longest sequence
		int maxSeqLen = 0;

		// stores the total amount produced by the current combination of coins
		int sum = 0;
		
		// stores the sequence to be printed
		String printSeq = "";
		
		if ( amount == 0 ) {
			System.out.println( amount + " cents:    can not be paid" );
		} else {
			for ( int i = 0; i < combArray.length - 1; i++ ) {
				sum = 0;
				
				printSeq = "";
				
				// Takes the i(th) element of the combArray and splits it
				// to get an array of coins in that particular combination
				String[] splitSeq = combArray[i].split( ", " );
				currSeqLength = splitSeq.length;

				
				for ( String s : splitSeq ) {
					sum += Integer.parseInt( s );
					printSeq += s + " cents ";
				}

				// finding the longest sequence of coins that produces 
				// produces the amount
				if ( sum == amount ) {
					if ( currSeqLength > maxSeqLen ) {
						maxSeqLen = currSeqLength;
						seq = printSeq;
					}
				}

			}

			// if there is no such sequence of coins that can produce
			//the given  amount	
			if ( maxSeqLen == 0 ) {
				System.out.print( amount + " cents:    no; can not be paid " );
				System.out.print( "with the following sequence of coins: " );
				System.out.println( "[" + combArray[0] + "]" );
			}

			// printing the longest sequence of coins that produces the amount
			else {
				System.out.print( amount + " cents:    yes; used coins = " );
                System.out.print( seq );
				System.out.println();

			}
        
		}

	}

	
}
