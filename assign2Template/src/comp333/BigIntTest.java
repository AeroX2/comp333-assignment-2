/*
 * Class BigIntTest. This JUnit test routine file
 * was used in the development and testing of class BigInt.
 * It is not so relevant for COMP333 in 2018.
 * However, it is provided in case it is of help.
 */
package comp333;

import static org.junit.Assert.*;
import org.junit.Test;

public class BigIntTest {
	@Test(timeout=3000)
	public void test1ConstructorToString() {
		BigInt A = new BigInt("0");
		assertEquals("0", A.toString());
	}
	
	
	@Test(timeout=3000)
	public void test2ConstructorToString() {
		BigInt A = new BigInt("000");
		assertEquals("0", A.toString());
	}
	
	
	@Test(timeout=3000)
	public void test3ConstructorToString() {
		BigInt A = new BigInt("123456789");
		assertEquals("123456789", A.toString());
	}
	
	@Test(timeout=3000)
	public void test4ConstructorToString() {
		BigInt A = new BigInt("00000123456789");
		assertEquals("123456789", A.toString());
	}
	
	@Test(timeout=3000)
	public void test5ConstructorToString() {
		BigInt A = new BigInt("9876543210");
		assertEquals("9876543210", A.toString());
	}
	
	@Test(timeout=3000)
	public void test6ConstructorToString() {
		BigInt A = new BigInt("11111111111111111111");
		assertEquals("11111111111111111111", A.toString());
	}
	
	@Test(timeout=3000)
	public void test1IsEqual() {
		BigInt bigIntA = new BigInt("1");
		BigInt bigIntB = new BigInt("0");
		assertFalse(bigIntA.isEqual(bigIntB));
	}
	
	@Test(timeout=3000)
	public void test2IsEqual() {
		BigInt bigIntA = new BigInt("12345678901234567890");
		BigInt bigIntB = new BigInt("12345678901234567891");
		assertFalse(bigIntA.isEqual(bigIntB));
	}
	
	@Test(timeout=3000)
	public void test3IsEqual() {
		BigInt bigIntA = new BigInt("111111111111111111111");
		BigInt bigIntB = new BigInt("11111111111111111111");
		assertFalse(bigIntA.isEqual(bigIntB));
	}
	
	@Test(timeout=3000)
	public void test1IsEqualRec() {
		BigInt bigIntA = new BigInt("12345678901234567890");
		BigInt bigIntB = new BigInt("12345678901234567891");
		assertFalse(bigIntA.isEqual(bigIntB));
	}
	
	@Test(timeout=3000)
	public void test2IsEqualRec() {
		BigInt bigIntA = new BigInt("12345678901234567890");
		BigInt bigIntB = new BigInt("92345678901234567890");
		assertFalse(bigIntA.isEqual(bigIntB));
	}
	
	@Test(timeout=3000)
	public void test3IsEqualRec() {
		BigInt bigIntA = new BigInt("11111111111111111111");
		BigInt bigIntB = new BigInt("11111111111111111111");
		assertTrue(bigIntA.isEqual(bigIntB));
	}
	
	@Test(timeout=3000)
	public void test1Add() {
		BigInt bigIntA = new BigInt("12345678901234567890");
		BigInt bigIntB = new BigInt("12345678901234567890");
		BigInt bigIntC = bigIntA.add(bigIntB);
		assertEquals("24691357802469135780", bigIntC.toString());
	}
	
	@Test(timeout=3000)
	public void test2Add() {
		BigInt bigIntA = new BigInt("123456789012345678901234567890");
		BigInt bigIntB = new BigInt("98765432109876543210");
		BigInt bigIntC = bigIntA.add(bigIntB);
		assertEquals("123456789111111111011111111100", bigIntC.toString());
	}
	
	@Test(timeout=3000)
	public void test3Add() {
		BigInt bigIntA = new BigInt("99999999999999999999");
		BigInt bigIntB = new BigInt("11111111111111111111");
		BigInt bigIntC = bigIntA.add(bigIntB);
		assertEquals("111111111111111111110", bigIntC.toString());
	}
	
	@Test(timeout=3000)
	public void test1Subtract() {
		BigInt bigIntA = new BigInt("12345678901234567890");
		BigInt bigIntB = new BigInt("12345678901234567890");
		BigInt bigIntC = bigIntA.subtract(bigIntB);
		assertEquals("0", bigIntC.toString());
	}
	
	@Test(timeout=3000)
	public void test2Subtract() {
		BigInt bigIntA = new BigInt("123456789012345678901234567890");
		BigInt bigIntB = new BigInt("98765432109876543210");
		BigInt bigIntC = bigIntA.subtract(bigIntB);
		assertEquals("123456788913580246791358024680", bigIntC.toString());
	}
	
	@Test(timeout=3000)
	public void test3Subtract() {
		BigInt bigIntA = new BigInt("99999999999999999999");
		BigInt bigIntB = new BigInt("11111111111111111111");
		BigInt bigIntC = bigIntA.subtract(bigIntB);
		assertEquals("88888888888888888888", bigIntC.toString());
	}
	
	@Test(timeout=3000)
	public void test1MultiplyBasic() {
		BigInt bigIntA = new BigInt("3265");
		BigInt bigIntB = new BigInt("248");
		BigInt bigIntC = bigIntA.multiplyBasic(bigIntB);
		assertEquals("809720", bigIntC.toString());
	}

	@Test(timeout=3000)
	public void test2MultiplyBasic() {
		BigInt bigIntA = new BigInt("1000");
		BigInt bigIntB = new BigInt("303");
		BigInt bigIntC = bigIntA.multiplyBasic(bigIntB);
		assertEquals("303000", bigIntC.toString());
	}
	
	@Test(timeout=3000)
	public void test3MultiplyBasic() {
		BigInt bigIntA = new BigInt("123456");
		BigInt bigIntB = new BigInt("123456");
		BigInt bigIntC = bigIntA.multiplyBasic(bigIntB);
		assertEquals("15241383936", bigIntC.toString());
	}
	
	@Test(timeout=3000)
	public void test1DivideBasic() {
		BigInt bigIntA = new BigInt("9543");
		BigInt bigIntB = new BigInt("9523");
		BigInt[] result = bigIntA.divideBasic(bigIntB);
		assertEquals("1", result[0].toString());
		assertEquals("20", result[1].toString());
	}
	
	@Test(timeout=3000)
	public void test2DivideBasic() {
		BigInt bigIntA = new BigInt("1005");
		BigInt bigIntB = new BigInt("13");
		BigInt[] result = bigIntA.divideBasic(bigIntB);
		assertEquals("77", result[0].toString());
		assertEquals("4", result[1].toString());
	}
	
	@Test(timeout=3000)
	public void test3DivideBasic() {
		BigInt bigIntA = new BigInt("123456789");
		BigInt bigIntB = new BigInt("54321");
		BigInt[] result = bigIntA.divideBasic(bigIntB);
		assertEquals("2272", result[0].toString());
		assertEquals("39477", result[1].toString());
	}
	
	@Test(timeout=3000)
	public void test1Multiply() {
		BigInt bigIntA = new BigInt("12345678901234567890");
		BigInt bigIntB = new BigInt("12345678901234567890");
		BigInt bigIntC = bigIntA.multiply(bigIntB);
		assertEquals("152415787532388367501905199875019052100", bigIntC.toString());
	}
	
	@Test(timeout=3000)
	public void test2Multiply() {
		BigInt bigIntA = new BigInt("6700417");
		BigInt bigIntB = new BigInt("641");
		BigInt bigIntC = bigIntA.multiply(bigIntB);
		assertEquals("4294967297", bigIntC.toString());
	}
	
	@Test(timeout=3000)
	public void test3Multiply() {
		BigInt bigIntA = new BigInt("987654321987654321");
		BigInt bigIntB = new BigInt("987654321");
		BigInt bigIntC = bigIntA.multiply(bigIntB);
		assertEquals("975461058765432098789971041", bigIntC.toString());
	}
	
	@Test(timeout=3000)
	public void test1Divide() {
		BigInt bigIntA = new BigInt("152415787532388367501905199875019052100");
		BigInt bigIntB = new BigInt("12345678901234567890");
		BigInt[] result = bigIntA.divide(bigIntB);
		assertEquals("12345678901234567890", result[0].toString());
		assertEquals("0", result[1].toString());
	}
	
	@Test(timeout=3000)
	public void test2Divide() {
		BigInt bigIntA = new BigInt("4294967300");
		BigInt bigIntB = new BigInt("641");
		BigInt[] result = bigIntA.divide(bigIntB);
		assertEquals("6700417", result[0].toString());
		assertEquals("3", result[1].toString());
	}
	
	@Test(timeout=3000)
	public void test3Divide() {
		BigInt bigIntA = new BigInt("975461058765432098789971041");
		BigInt bigIntB = new BigInt("987654321987654321");
		BigInt[] result = bigIntA.divide(bigIntB);
		assertEquals("987654321", result[0].toString());
		assertEquals("0", result[1].toString());
	}
}
