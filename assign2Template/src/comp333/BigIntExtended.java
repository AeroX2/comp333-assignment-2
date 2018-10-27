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
	private static BigInt TEN = new BigInt("10");

	private static BigInt NEGATIVE_ONE = new BigInt("-1");

	// Euclid's algorithm, extended form.
	// Pre: a and b are positive big integers.
	// Returns: triple (d, x, y) such that
	//          d = gcd(a,b) = ax + by.
	public static int[] egcd(int a, int b) {
		int x0 = 1;
		int x1 = 0;
		int y0 = 0;
		int y1 = 1;

		while (b != 0) {
			int qdiv = a / b;
			int qmod = a % b;

			a = b;
			b = qmod;

			int t = x1;
			x1 = x0-x1*qdiv;
			x0 = t;

			t = y1;
			y1 = y0-y1*qdiv;
			y0 = t;
		}

		int[] result = new int[3];
		result[0] = a;
		result[1] = x0;
		result[2] = y0;
		return result;
	}
	
	// Modular inversion.
	// Pre: a and n are positive big integers which are coprime.
	// Returns: the inverse of a modulo n.
	public static int minv(int a, int n) {
		int[] egcd = egcd(a, n);
		assert(egcd[0] == 1);
		return egcd[1] % n;
	}
	
	// Chinese remainder algorithm.
	// Pre: p and q are different big prime numbers.
	//      0 <= a < p and 0 <= b < q.
	// Returns: the unique big int c such that
	//          c is congruent to a modulo p,
	//          c is congruent to b modulo q,
	//          and 0 <= c < pq.
	public static int cra(int p, int q, int a, int b) {
		int prod = p*q;

		int p1 = prod/p;
		int sum = a*minv(p1, p)*p1;

		int p2 = prod/q;
		sum += (b*minv(p2, q)*p2);

		return sum % prod;
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

	public static BigInt bigpow(BigInt a, BigInt b) {
		BigInt r = ONE;
		while (!b.lessOrEqual(ZERO)) {
			BigInt[] division = b.divide(TWO);
			if (division[1].isEqual(ONE)) {
				r = r.multiply(a);
			}

			b = division[0];
			a = a.multiply(a);
		}
		return r;
	}

	// Pre: a and b are nonnegative big integers of equal length.
	// Returns: the product of a and b using karatsuba's algorithm.
	public static BigInt karatsuba(BigInt a, BigInt b) {
		if (a.lessOrEqual(TEN) || b.lessOrEqual(TEN)) return a.multiply(b);

		String num1 = a.toString();
		String num2 = b.toString();

		int m = Math.max(num1.length(), num2.length());
		int m2 = m/2;

		BigInt low1  = new BigInt(num1.substring(0,m2));
		BigInt high1 = new BigInt(num1.substring(m2));
		BigInt low2  = new BigInt(num2.substring(0,m2));
		BigInt high2 = new BigInt(num2.substring(m2));

		BigInt z0 = karatsuba(low1, low2);
		BigInt z1 = karatsuba(low1.add(high1), low2.add(high2));
		BigInt z2 = karatsuba(high1, high2);

		BigInt s = z2.multiply(bigpow(TEN, new BigInt(String.valueOf(m))));
		BigInt t = z1.subtract(z2).subtract(z0).multiply(bigpow(TEN, new BigInt(String.valueOf(m2))));

		return s.add(t).add(z0);
	}

	//Return a random int between 0 and high (inclusive)
	public static int random(int high) {
		return (int)(Math.random()*(high+1));
	}

	public static BigInt randombigint(BigInt low, BigInt high) {
		if (high.lessOrEqual(low)) return null;

		//Generate a random BigInt between 0 and (high-low)
		String difference = high.subtract(low).toString();
		StringBuilder randomBigIntToAdd = new StringBuilder("0");

		//Pick a random amount of digits to add
		int length = difference.length();
		int randomLength = random(length);
		boolean restrictedRange = randomLength == length;
		for (int i = 0; i < randomLength; i++) {
			int digit;

			//Ensure we never exceed the difference by restricting the range, eg if we have a difference of
			//5678, if the first digit picked is less than 5 then we can pick whatever digit we want afterwards
			//However if we pick 5, the next digit must be restricted within the range 0-6
			if (restrictedRange) {
				int largest = difference.charAt(i)-'0';
				digit = random(largest);
				restrictedRange = digit == largest;
			} else {
				digit = random(9);
			}

			randomBigIntToAdd.append(digit);
		}

		//Add the random BigInt to the low BigInt
		return low.add(new BigInt(randomBigIntToAdd.toString()));
	}

	// Pre: n is an odd big integer greater than 4.
	//      s is an ordinary positive integer.
	// Returns: if n is prime then returns true with certainty,
	//          otherwise returns false with probability
	//          1 - 4^{-s}.
	public static boolean millerrabin(BigInt n, int s) {
		if (n.isEqual(TWO) || n.isEqual(THREE)) return true;
		if (n.divide(TWO)[1].isEqual(ZERO) || (n.lessOrEqual(TWO) && !n.isEqual(TWO))) return false;

		int s2 = 0;
		BigInt nMinusOne = n.subtract(ONE);
		BigInt d = nMinusOne;
		while (true) {
			BigInt[] divmod = d.divide(TWO);
			if (!divmod[1].isEqual(ZERO)) break;
			d = divmod[0];
			s2++;
		}

		witnessLoop:
		for (; s >= 0; s--) {
			BigInt a = randombigint(TWO,nMinusOne);
			BigInt x = modexp(a,d,n);
			if (x.isEqual(ONE) || (x.isEqual(nMinusOne))) continue;

			for (int i = s2-1; i >= 0; i--) {
				x = modexp(x,TWO,n);
				if (x.isEqual(ONE)) return false;
				if (x.isEqual(nMinusOne)) continue witnessLoop;
			}
			return false;
		}

		return true;
	}

	// Pre: l and s are ordinary positive integers.
	// Returns: a random "probable" big prime number n of length l decimal digits.
	//          The probability that n is not prime is less than 4^{-s}.
	public static BigInt randomprime(int l, int s) {
	    while (true) {
	    	BigInt random = randombigint();
		}
	}

	public static void main(String[] args) {

		// Implement a simple interactive RSA demo program here.

		BigInt a = new BigInt("100");
		BigInt b = new BigInt("100");
		BigInt c = bigpow(a,b).subtract(ONE);
		System.out.println(millerrabin(c,10));

//		System.out.println(new BigInt("34").subtract(new BigInt("10")));
////		System.out.println(new BigInt("10").subtract(new BigInt("34")));
//
//		System.out.println(karatsuba(new BigInt("12345"), new BigInt("6789")));
//		System.out.println(karatsuba(new BigInt("328931"), new BigInt("90328103")));
	}
	
	
}
