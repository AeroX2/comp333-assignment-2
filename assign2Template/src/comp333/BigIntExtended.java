/* Template for class BigIntExtended.
 * This class is intended to extend the functionality provided 
 * by class BigInt.
 * This template is provided as part of Assignment 2 for COMP333.
 * Every JUnit test routine used in marking each method you write
 * will conform to the stated precondition and description of
 * output for the method.
 */
 package comp333;

import java.math.BigInteger;
import java.util.Arrays;

public class BigIntExtended {

	private static BigInt ZERO = new BigInt("0");
	private static BigInt ONE = new BigInt("1");
	private static BigInt TWO = new BigInt("2");
	private static BigInt THREE = new BigInt("3");
	private static BigInt NEGATIVE_ONE = new BigInt("-1");

	// Euclid's algorithm, extended form.
	// Pre: a and b are positive big integers.
	// Returns: triple (d, x, y) such that
	//          d = gcd(a,b) = ax + by.
	public static BigInt[] egcd(BigInt a, BigInt b) {
		BigInt x0 = ONE;
		BigInt x1 = ZERO;
		BigInt y0 = ZERO;
		BigInt y1 = ONE;

		while (!b.isEqual(ZERO)) {
			BigInt t = new BigInt(b.toString());
			BigInt[] q = a.divide(b);
			a = t;
			b = q[1];

			t = new BigInt(x1.toString());
			x1 = x0.subtract(x1.multiply(q[0]));
			x0 = t;

			t = new BigInt(y1.toString());
			y1 = y0.subtract(y1.multiply(q[0]));
			y0 = t;
		}

		BigInt[] result = new BigInt[3];
		result[0] = a;
		result[1] = x0;
		result[2] = y0;
		return result;
	}
	
	// Modular inversion.
	// Pre: a and n are positive big integers which are coprime.
	// Returns: the inverse of a modulo n.
	public static BigInt minv(BigInt a, BigInt n) {
		BigInt[] egcd = egcd(a, n);
		return egcd[0].divide(n)[1];
	}
	
	// Chinese remainder algorithm.
	// Pre: p and q are different big prime numbers.
	//      0 <= a < p and 0 <= b < q.
	// Returns: the unique big int c such that
	//          c is congruent to a modulo p,
	//          c is congruent to b modulo q,
	//          and 0 <= c < pq.
	public static BigInt cra(BigInt p, BigInt q, BigInt a, BigInt b) {

		BigInt prod = p.multiply(q);

		BigInt p1 = prod.divide(p)[0];
		BigInt sum = a.multiply(minv(p1, p)).multiply(p1);

		BigInt p2 = prod.divide(q)[0];
		sum = sum.add(b.multiply(minv(p2, q)).multiply(p2));

		return sum.divide(prod)[1];
	}
	
	// Modular exponentiation.
	// Pre: a and b are nonnegative big integers.
	//      n is a positive big integer.
	// Returns: this method returns a^b mod n.
	public static BigInt modexp(BigInt a, BigInt b, BigInt n) {
		BigInt r = ONE;
		while (!b.lessOrEqual(ZERO)) {
			BigInt[] division = b.divide(TWO);
			if (division[1].isEqual(ONE)) {
				r = r.multiply(a).divide(n)[1];
			}

			b = division[0];
			a = a.multiply(a).divide(n)[1];
		}
		return r;
	}
	
	// Modular exponentiation, version 2.
	// This method returns a^b mod n, in case n is prime.
	public static BigInt modexpv2(BigInt a, BigInt b, BigInt n) {
        BigInt result = new BigInt();
		
		// Complete this code.
		
		return result;
	}
	
	// Modular exponentiation, version 3.
	// This method returns a^b mod n, in case n = pq, for different
	// primes p and q.
	public static BigInt modexpv3(BigInt a, BigInt b, BigInt n) {
		BigInt result = new BigInt();

		// Complete this code.

		return result;
	}
		
	// Pre: a and b are nonnegative big integers of equal length.
	// Returns: the product of a and b using karatsuba's algorithm.
	public static BigInt karatsuba(BigInt a, BigInt b) {
		BigInt result = new BigInt();
		
		// Complete this code.
		
		return result;
	}
	
	// Pre: n is an odd big integer greater than 4.
	//      s is an ordinary positive integer.
	// Returns: if n is prime then returns true with certainty,
	//          otherwise returns false with probability
	//          1 - 4^{-s}.
	public static boolean millerrabin(BigInt n, int s) {
		if (n.isEqual(TWO) || n.isEqual(THREE)) return true;
		if (n.divide(TWO)[1].isEqual(ZERO) || (n.lessOrEqual(TWO) && !n.isEqual(TWO))) return false;



		return true;
	}
	
	// Pre: l and s are ordinary positive integers.
	// Returns: a random "probable" big prime number n of length l decimal digits.
	//          The probability that n is not prime is less than 4^{-s}.
	public static BigInt randomprime(int l, int s) {


		// Complete this code.
		
		return result;
	}

	public static void main(String[] args) {
		
		// Implement a simple interactive RSA demo program here.

		System.out.println(Arrays.toString(egcd(new BigInt("270"), new BigInt("192"))));

	}
	
	
}
