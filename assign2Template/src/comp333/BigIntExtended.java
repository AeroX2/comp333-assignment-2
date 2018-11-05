/* Template for class BigIntExtended.
 * This class is intended to extend the functionality provided 
 * by class BigInt.
 * This template is provided as part of Assignment 2 for COMP333.
 * Every JUnit test routine used in marking each method you write
 * will conform to the stated precondition and description of
 * output for the method.
 */
 package comp333;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
		return Math.floorMod(egcd[1],n);
	}

	// Chinese remainder algorithm.
	// Pre: p and q are different big prime numbers.
	//      0 <= a < p and 0 <= b < q.
	// Returns: the unique big int c such that
	//          c is congruent to a modulo p,
	//          c is congruent to b modulo q,
	//          and 0 <= c < pq.
	public static int cra(int p, int q, int a, int b) {
		int sum = a*q*minv(q,p) +
		  	      b*p*minv(p,q);

		return Math.floorMod(sum, p*q);
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
		//Euler thereom
		//a^b mod m = a^(b mod phi(m)) mod m

		//phi(p) where p is prime is p-1
		return modexp(a, b.divide(n.subtract(ONE))[1], n);
	}

	// Modular exponentiation, version 3.
	// This method returns a^b mod n, in case n = pq, for different
	// primes p and q.
	public static int modexpv3(int a, int b, int p, int q) {
		BigInt ab = new BigInt(String.valueOf(a));
		BigInt bb = new BigInt(String.valueOf(b));
		BigInt pb = new BigInt(String.valueOf(p));
		BigInt qb = new BigInt(String.valueOf(q));

		BigInt cd1b = modexpv2(ab,bb,pb);
		BigInt cd2b = modexpv2(ab,bb,qb);

		int cd1 = Integer.valueOf(cd1b.toString());
		int cd2 = Integer.valueOf(cd2b.toString());

		return cra(p,q,cd1,cd2);
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
		if (a.lessOrEqual(new BigInt("2000")) || b.lessOrEqual(new BigInt("2000"))) return a.multiply(b);

		String num1 = a.toString();
		String num2 = b.toString();

		int m = Math.max(num1.length(), num2.length());
		int m2 = m/2;

//		num1 = String.format("%1$"+m+"s", num1);
//		num2 = String.format("%1$"+m+"s", num2);

		BigInt high1  = new BigInt(num1.substring(0,num1.length()-m2));
		BigInt low1 = new BigInt(num1.substring(num1.length()-m2));
		BigInt high2  = new BigInt(num2.substring(0,num2.length()-m2));
		BigInt low2 = new BigInt(num2.substring(num2.length()-m2));

		BigInt z0 = karatsuba(low1, low2);
		BigInt z1 = karatsuba(low1.add(high1), low2.add(high2));
		BigInt z2 = karatsuba(high1, high2);

		BigInt s = z2.multiply(bigpow(TEN, new BigInt(String.valueOf(m2*2))));
		BigInt t = z1.subtract(z2).subtract(z0).multiply(bigpow(TEN, new BigInt(String.valueOf(m2))));

		return s.add(t).add(z0);
	}

//	public static BigInt randombigint(BigInt low, BigInt high) {
//		if (high.lessOrEqual(low)) return null;
//
//		StringBuilder randomNumber = new StringBuilder();
//		String lowS = low.toString();
//		String highS = high.toString();
//
//		//Pick the amount of digits that the final result is going to be
//		int randomLength = randomrange(lowS.length(), highS.length());
//		//System.out.println("Random length: "+randomLength);
//
//		boolean restrictLow = randomLength == lowS.length();
//		boolean restrictHigh = randomLength == highS.length();
//		for (int i = 0; i < randomLength; i++) {
//			int lowDigit = (i==0) ? 1 : 0;
//			int highDigit = 9;
//
//			if (restrictLow) lowDigit = lowS.charAt(i)-'0';
//			if (restrictHigh) highDigit = highS.charAt(i)-'0';
//
//			int digit = randomrange(lowDigit, highDigit);
//			if (restrictLow && digit != lowDigit) restrictLow = false;
//			if (restrictHigh && digit != highDigit) restrictHigh = false;
//
//			//System.out.println("Appending digit: "+digit);
//			randomNumber.append(digit);
//		}
//
//		return new BigInt(randomNumber.toString());
//	}

	static BigInt a = new BigInt("1140671485");
	static BigInt c = new BigInt("128201163");
	static BigInt m = bigpow(TWO, new BigInt("24"));
	static BigInt rand = new BigInt(String.valueOf((int)(Math.random()*(Integer.MAX_VALUE-1))));

	public static BigInt randombigint(BigInt low, BigInt high) {
		rand = a.multiply(rand).add(c).divide(m)[1];

		//BigInt r = rand.multiply(high.subtract(low).add(ONE)).divide(m)[0];
		BigInt r = karatsuba(rand, high.subtract(low).add(ONE)).divide(m)[0];

		return low.add(r);
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
	// Returns: a randomrange "probable" big prime number n of length l decimal digits.
	//          The probability that n is not prime is less than 4^{-s}.
	public static BigInt randomprime(int l, int s) {
		List<Character> evens = Arrays.asList('0','2','4','6','8');

	    while (true) {
	    	BigInt low = new BigInt("1"+(String.join("", Collections.nCopies(l-1, "0"))));
		    BigInt high = new BigInt(String.join("", Collections.nCopies(l, "9")));
		    //System.out.println(low);
		    //System.out.println(high);
	    	BigInt random = randombigint(low, high);

		    //System.out.println("Before");
		    //System.out.println(random);

	    	//Check if the last digit is even and if so replace it
		    String randomString = random.toString();
		    int n = randomString.length()-1;
	    	char lastChar = randomString.charAt(n);
	        if (evens.contains(lastChar)) {
	            lastChar += 1;
	            random = new BigInt(randomString.substring(0,n)+lastChar);
	        }

		    //System.out.println("After");
	        //System.out.println(random);

	        if (millerrabin(random, s)) return random;
		}
	}

	public static void encrypt(Scanner scanner) {
		while (true) {
			try {
				//TODO: BigInt doesn't support negative numbers, so egcd doesn't support BigInt
				System.out.print("Length of keys [1 to 3]: ");
				String s = scanner.nextLine();
				int length = Integer.valueOf(s);
				if (length <= 0 || length > 3) {
					throw new Exception();
				}

				BigInt p = randomprime(length, 32);
				BigInt q = randomprime(length, 32);
				BigInt n = p.multiply(q); //TODO: Replace with karatsuba
				BigInt totient = p.subtract(ONE).multiply(q.subtract(ONE)); //TOOD: Replace with karatsuba

				BigInt publicKey;
				while (true) {
					publicKey = randomprime(totient.toString().length(), 32);

					int a = Integer.valueOf(totient.toString());
					int b = Integer.valueOf(publicKey.toString());

					if (publicKey.lessOrEqual(totient) && egcd(a,b)[0] == 1) break;
				}

				int a = Integer.valueOf(publicKey.toString());
				int b = Integer.valueOf(totient.toString());
				int privateKey = minv(a,b);

				System.out.println("Public key: " + publicKey);
				System.out.println("Private key: " + privateKey);
				System.out.println("Modulus: " + n);
//				System.out.println("Totient: " + totient);

				System.out.print("Data to encrypt: ");
				s = scanner.nextLine();
				for (char c : s.toCharArray()) {
//					BigInt qq = new BigInt(String.valueOf((int)c));
//					BigInt zz = modexp(qq, publicKey, n);

					int pki = Integer.valueOf(publicKey.toString());
					int pi = Integer.valueOf(p.toString());
					int qi = Integer.valueOf(q.toString());

					int zz = modexpv3((int)c, pki, pi, qi);
					System.out.print(zz+" ");
				}
				System.out.println('\n');

				break;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Incorrect input");
			}
		}
	}

	public static void decrypt(Scanner scanner) {
		while (true) {
			try {
				System.out.print("Private key: ");
				String s = scanner.nextLine();
				BigInt privateKey = new BigInt(s);

				System.out.print("Modulus: ");
				s = scanner.nextLine();
				BigInt n = new BigInt(s);

				System.out.print("Data: ");
				s = scanner.nextLine();
				for (String v : s.split(" ")) {
					BigInt d = new BigInt(v);
					int c = Integer.parseInt(modexp(d, privateKey, n).toString());
					System.out.print((char)c);
				}
				System.out.println('\n');

				break;
			} catch (Exception e) {
				System.out.println("Incorrect input");
			}
		}
	}

	public static void main(String[] args) {
		// Implement a simple interactive RSA demo program here.

		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.print("Encrypt or Decrypt? [E/D]: ");
			String s = scanner.nextLine().toLowerCase();
			if (s.charAt(0) == 'e') {
				encrypt(scanner);
			} else if (s.charAt(0) == 'd') {
				decrypt(scanner);
			} else {
				System.out.println("Incorrect input");
			}
		}
	}
	
	
}
