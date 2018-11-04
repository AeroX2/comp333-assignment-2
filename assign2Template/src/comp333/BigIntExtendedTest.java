/*
 * Class BigIntExtendedTest. Simple test file
 * for some methods of class BigIntExtended.
 */
package comp333;

import static org.junit.Assert.*;
import org.junit.Test;

public class BigIntExtendedTest {
	@Test(timeout=3000)
	public void testEgcd() {
		int [] output = BigIntExtended.egcd(72,49);
		assertEquals(1, output[0]);
		assertEquals(-17, output[1]);
		assertEquals(25, output[2]);
	}
	
	@Test(timeout=3000)
	public void testMinv() {
		int x = BigIntExtended.minv(72,49);
		assertEquals(32, x);
	}
	
	@Test(timeout=3000)
	public void testCra() {
		int c = BigIntExtended.cra(7,13,4,5);
		assertEquals(18,c);
	}
	
	
}
