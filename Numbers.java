/*
 * Numbers.java
 *
 * Version:
 *     $Id$
 *
 * Revisions:
 *     $Log$
 */

import java.lang.Math;
import java.util.regex.*;

/**
 * This program prints all the numbers between the range of MIN to MAX for which
 * there exists an exponent 'n' such that the sum produced by adding each digit
 * to its power raised to 'n' will produce the number itself.
 * 
 * @author Smita Subhadarshinee Mishra
 * @author Lahari Chepuri
 */

public class Numbers {
	final static int MIN_NUMBER = 0;
	final static int MAX_NUMBER = 4150;

	/**
	 * The main program.
	 *
	 * @param args command line arguments (ignored)
	 */

	public static void main( String[] args ) {

		// Creating an object of CoinChange class
		// to call the non-static methods of this class.
		Numbers object = new Numbers();

		// storing a regular expression which generates all
		// the combinations of 1's and 0's, to eliminate them later
		String ignoreDig = "[-+10]*";
		
		// used to convert the number value into a string
		// to match it with the 'ignoreDig' pattern
		String num = "";

		// holds the absolute value of the number
		int absValue = 0;
		
		// stores the number of elements between MIN_NUMBER to
		// MAX_NUMBER that has the given property.
		int checkNoNumbers = 0;

		// finds all the numbers that have the given property
		for ( int i = MIN_NUMBER; i <= MAX_NUMBER; i++ ) {
			num = String.valueOf( i );

			if ( !( Pattern.matches( ignoreDig, num ) ) || ( i == 1 ) || ( i == -1 ) ) {
				
				// the number is converted to a positive number in
				// case it is negative
				absValue = Math.abs( i );
				checkNoNumbers += object.printNumberPpt( absValue );
			}

		}

		// prints if there are no such numbers in the range that have the
		// given property.
		if ( checkNoNumbers == 0 ) {
			System.out.println( "No such numbers exist in the given range" );
		}

	}

	/**
	 * Prints if there exists an 'n' for the given number that such that the
	 * value produced by adding each digit of the number raised to the power of
	 * 'n' will produce the number itself.
	 * 
	 * @param number   absolute value of the number to check the property
	 *              
	 * @return hasPpt returns 1 if the number has the property else, returns 0
	 */

	public int printNumberPpt( int number ) {

		int exponent = 1;
		int sum = 0;
		int hasPpt = 0;


		// stores the formula used to find the number has satisfies the 
		// property, to print it later
		String printCal = "";

		// finding the exponent that produces the required property
		while ( sum < number ) {
			sum = 0;
			printCal = "";

			// converting the number into an array of digits, where each
			// index contains each digit of the number
			String[] splitDigits = String.valueOf( number ).split("");

			// sum produced by adding each digit raised to the exponent value
			for ( String a : splitDigits ) {
				int digit = Integer.parseInt( a );
				
				// adding the value of sum to digit^exponent
				sum += Math.pow( digit, exponent );
				printCal += a + " ^ " + exponent + ";";
			}

			// generating the formula used to find the number has satisfies 
			// the property 
			printCal = String.join( "  +  ", printCal.split(";") );

			// prints when it finds the number that has the given property
			if ( sum == number ) {
				hasPpt += 1;
				System.out.print( number + "   ==   " );
				System.out.println( sum + " has the desired property " );
				System.out.println( "  " + printCal );
				System.out.println();

				break;
			} else {
				exponent += 1;
				continue;
			}

		}

		// returns 1 if the number has the property
		// returns 0 if the number does not have the property
		return hasPpt;

	}

}