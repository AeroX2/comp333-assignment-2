/*
 * Class BigInt. This class represents nonnegative big integers.
 * Some arithmetic operations are provided for this class.
 * See COMP333 Assignment 2 description for some background information
 * on big integer representation.
 * Author: Dr. S. McCallum, September 2018.
 */
package comp333;

import java.util.ArrayList;
import java.util.Scanner;

public class BigInt {
	private ArrayList<Integer> digitList;
	
	// No parameter constructor, creates 0.
	public BigInt() {
		digitList = new ArrayList<Integer>();
	}

	// One parameter constructor.
	// Creates big integer d, where d is in range 1 <= d <= 9.
	public BigInt(int d) {
		digitList = new ArrayList<Integer>();
		digitList.add(d);
	}
	
	// One parameter constructor.
	// Creates big integer from String digits.
	public BigInt(String digits) {
		digitList = new ArrayList<Integer>();
		int number; char ch;
		int len = digits.length();
		for (int i = len-1; i >= 0; i--) {
			ch = digits.charAt(i);
			number = (int) ch - (int) '0';
			digitList.add(number);
		}
		// Delete trailing zeros.
		int n = digitList.size();
		while (n > 0 && digitList.get(n-1) == 0) {
			digitList.remove(n-1);
			n--;
		}
	}
	
	public String toString() {
		int n = digitList.size();
		if (n == 0)
			return "0";
		int digitNumber; char digitChar; 
		String result = "";
		for (int i = n-1; i >=0; i--) {
			digitNumber = digitList.get(i);
			digitChar = (char) (digitNumber + (int) '0');
			result = result + digitChar;
		}
		return result;
	}
	
	// Read the big integer entered, save it in the calling 
	// (this) big integer.
	public void readBigInt() {
		System.out.println("Please enter a big integer:");
		Scanner keyboard = new Scanner(System.in);
		String digitString = keyboard.nextLine();
	    digitList.clear();
		int number; char ch;
		int len = digitString.length();
		for (int i = len-1; i >= 0; i--) {
			ch = digitString.charAt(i);
			number = (int) ch - (int) '0';
			digitList.add(number);
		}
		// Delete trailing zeros.
		int n = digitList.size();
		while (n > 0 && digitList.get(n-1) == 0) {
			digitList.remove(n-1);
			n--;
		}
	}
	
	// Write the calling (this) big integer.
	public void writeBigInt() {
		int n = digitList.size();
		if (n == 0)
			System.out.print("0");
		int digitNumber;
		for (int i = n-1; i >=0; i--) {
			digitNumber = digitList.get(i);
			System.out.print(digitNumber);
		}
		System.out.println();
	}
	
	// Return true if calling (this) big int a equals the big int b passed in.
	// Time complexity: O(L(a) + L(b)).
	// Reason: method makes one pass through the digit lists.
	public boolean isEqual(BigInt otherBigInt) {
		if (digitList.size() != otherBigInt.digitList.size())
			return false;
		int n = digitList.size();
		for (int i = 0; i < n; i++) {
			if (digitList.get(i) != otherBigInt.digitList.get(i))
				return false;
		}
		return true;
	}
	
	// Return true if calling big int is less than or equal to big int passed in.
	public boolean lessOrEqual(BigInt otherBigInt) {
		int m = digitList.size();
		int n = otherBigInt.digitList.size();
		if (m < n)
			return true;
		if (m > n)
			return false;
		// So at this point (if no return yet) we must have m == n.
		// We need to check carefully in this case.
		boolean answer = true;
		for (int i = 0; i < m; i++) {
			if (digitList.get(i) < otherBigInt.digitList.get(i))
				answer = true;
			else if (digitList.get(i) > otherBigInt.digitList.get(i))
				answer = false;
		}
		return answer;
	}
	
	// Returns the sum of the calling big int a and the big int b passed in.
	// Time complexity: O(L(a) + L(b)).
	// Reason: method makes one pass through the digit lists.
	public BigInt add(BigInt otherBigInt) {
		int a, b, c, sum;
		int carry = 0;
		BigInt result = new BigInt();
		int m = digitList.size();
		int n = otherBigInt.digitList.size();
		int i = 0, j = 0;
		while (i < m || j < n) {
			if (i < m) {
				a = digitList.get(i);
				i++;
			}
			else
				a = 0;
			if (j < n) {
				b = otherBigInt.digitList.get(j);
				j++;
			}
			else
				b = 0;
			sum = a + b + carry;
			c = sum % 10;
			carry = sum / 10;
			result.digitList.add(c);
		}
		if (carry == 1)
		    result.digitList.add(carry);
		return result;
	}
	
	// Returns the difference of calling big int a and big int b passed in.
	// Precondition: Assume a >= b.
	// Time complexity: O(L(a)).
	// Reason: method makes one pass through the digit lists.
	public BigInt subtract(BigInt otherBigInt) {
		if (this.lessOrEqual(otherBigInt) && !this.isEqual(otherBigInt)) {
			throw new Error("Error other BigInt is larger, this implementation doesn't support negative numbers");
		}

		int a, b, c, diff;
		int carry = 0;
		BigInt result = new BigInt();
		int m = digitList.size();
		int n = otherBigInt.digitList.size();
		int i = 0, j = 0;
		while (i < m) {
			a = digitList.get(i);
			i++;
			if (j < n) {
				b = otherBigInt.digitList.get(j);
				j++;
			}
			else
				b = 0;
			diff = 10 + a - b - carry;
			c = diff % 10;
			carry = 1 - (diff/10);
			result.digitList.add(c);
		}
		// delete trailing zeros
		int p = result.digitList.size();
		while (p > 0 && result.digitList.get(p-1) == 0) {
			result.digitList.remove(p-1);
			p--;
		}
		return result;
	}
	
	// Returns the product of calling (this) big int a and other big int b.
	// BASIC VERSION.
	// Time complexity: O(10^L(a)), that is, big-O of 10 raised to power L(a).
	public BigInt multiplyBasic(BigInt otherBigInt) {
		BigInt result = new BigInt();
		BigInt one = new BigInt(1);
		for (BigInt I = one; I.lessOrEqual(this); I = I.add(one))
			result = result.add(otherBigInt);
		return result;
	}
	
	// Computes the quotient and remainder when calling (this)
	// big int a is divided by other big int b.
	// Precondition: a >= b > 0.
	// Returns: array of length 2 whose first element is quotient,
	//          and whose second element is remainder.
	// BASIC VERSION.
	// Time complexity: O(10^(L(a) - L(b) + 1)).
	public BigInt[] divideBasic(BigInt otherBigInt) {
		BigInt one = new BigInt(1);
		BigInt quotient = new BigInt();
		BigInt remainder = this;
		while (otherBigInt.lessOrEqual(remainder)) {
			remainder = remainder.subtract(otherBigInt);
			quotient = quotient.add(one);
		}
		BigInt[] result = new BigInt[2];
		result[0] = quotient;
		result[1] = remainder;
		return result;
	}
	
	// Addresses same task as multiplyBasic.
	// Time complexity: O(L(a)*L(b)).
	public BigInt multiply(BigInt otherBigInt) {
		BigInt zero = new BigInt();
		BigInt result = new BigInt();
		BigInt partialProduct = new BigInt();
		int n = otherBigInt.digitList.size();
		int shiftAmt = 0;
		int b;
		for (int j = 0; j < n; j++) {
			b = otherBigInt.digitList.get(j);
			// multiply calling big int by b to obtain partial product
			partialProduct = zero;
			for (int index = 1; index <= b; index++)
				partialProduct = add(partialProduct);
			// if partialProduct != 0 shift it by shiftAmt places
			if (!zero.isEqual(partialProduct))
				for (int index = 0; index < shiftAmt; index++)
					partialProduct.digitList.add(0,0);
			shiftAmt++;
			result = result.add(partialProduct);
		}
		return result;
	}
	
	// Addresses same task as divideBasic.
	// Time complexity: O(L(a)*(L(a) - L(b) + 1)).
	public BigInt[] divide(BigInt otherBigInt) {
		BigInt[] result = new BigInt[2];
		BigInt quotient = new BigInt();
		BigInt zero = new BigInt();
		BigInt remainder = this.add(zero);
		BigInt divisorShifted = otherBigInt.add(zero);
		BigInt powerOf10 = new BigInt(1);
		// find largest divisor shifted
		while (divisorShifted.lessOrEqual(this)) {
			divisorShifted.digitList.add(0,0); // multiply divShifted by 10
			powerOf10.digitList.add(0,0); // multiply powerOf10 by 10
		}
		while (!divisorShifted.isEqual(otherBigInt)) {
			divisorShifted.digitList.remove(0); // divide divShifted by 10
			powerOf10.digitList.remove(0); // divide powerOf10 by 10
			while (divisorShifted.lessOrEqual(remainder)) {
				remainder = remainder.subtract(divisorShifted);
				quotient = quotient.add(powerOf10);
			}
		}
		result[0] = quotient;
		result[1] = remainder;
		return result; 
	}

}
