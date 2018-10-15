package comp333;

import java.util.Scanner;

/**
 * Class to carry out a simple application of class BigInt.
 * @author Dr S. McCallum, September 2018
 *
 */

public class Application {

	/**
	 * Class to carry out a simple application of the class BigInt.
	 */
	public static void main(String[] args) {
		System.out.println("This is an application of the BigInt class.");
		BigInt bigIntA = new BigInt();
		System.out.println("Please enter big int A.");
		bigIntA.readBigInt();
		System.out.println("The big int entered was:");
		bigIntA.writeBigInt();
		BigInt bigIntB = new BigInt();
		System.out.println("Please enter big int B:");
		bigIntB.readBigInt();
		System.out.println("The big int entered was:");
		bigIntB.writeBigInt();
		System.out.print("Big int A is ");
		if (!bigIntA.lessOrEqual(bigIntB))
				System.out.print("not ");
		System.out.println("less than or equal to big int B.");
		BigInt bigIntC = bigIntA.add(bigIntB);
		System.out.println("The sum of A and B is:");
		bigIntC.writeBigInt();
		bigIntC = bigIntA.subtract(bigIntB);
		System.out.println("The difference of A and B is:");
		bigIntC.writeBigInt();
		bigIntC = bigIntA.multiplyBasic(bigIntB);
		System.out.println("The product of A and B (by basic method) is:");
		bigIntC.writeBigInt();
		bigIntC = bigIntA.multiply(bigIntB);
		System.out.println("The product of A and B (by efficient method) is:");
		bigIntC.writeBigInt();
		System.out.println("First use basic division method.");
		BigInt[] bigIntArray = bigIntA.divideBasic(bigIntB);
		System.out.println("The quotient of A and B is:");
		bigIntArray[0].writeBigInt();
		System.out.println("The remainder of A and B is:");
		bigIntArray[1].writeBigInt();
		System.out.println("Second use efficient division method.");
		bigIntArray = bigIntA.divide(bigIntB);
		System.out.println("The quotient of A and B is:");
		bigIntArray[0].writeBigInt();
		System.out.println("The remainder of A and B is:");
		bigIntArray[1].writeBigInt();
	}

}
