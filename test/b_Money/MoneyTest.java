package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals(10000, (int) SEK100.getAmount());
		assertEquals(1000, (int) EUR10.getAmount());
		assertEquals(20000, (int) SEK200.getAmount());
		assertEquals(2000, (int) EUR20.getAmount());
		assertEquals(0, (int) SEK0.getAmount());
		assertEquals(0, (int) EUR0.getAmount());
		assertEquals(-10000, (int) SEKn100.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
		assertEquals(SEK, SEK200.getCurrency());
		assertEquals(EUR, EUR20.getCurrency());
		assertEquals(SEK, SEK0.getCurrency());
		assertEquals(EUR, EUR0.getCurrency());
		assertEquals(SEK, SEKn100.getCurrency());
	}

	@Test
	public void testToString() {
		assertEquals("100,00 SEK", SEK100.toString());
		assertEquals("20,00 EUR", EUR20.toString());
		assertEquals("-100,00 SEK", SEKn100.toString());
		assertEquals("0,00 EUR", EUR0.toString());
	}

	@Test
	public void testGlobalValue() {
		assertEquals(200, SEK100.universalValue(), 0.001);
		assertEquals(200, EUR10.universalValue(), 0.001);
		assertEquals(5000, SEK200.universalValue(), 0.001);
		assertEquals(5000, EUR20.universalValue(), 0.001);
		assertEquals(0, SEK0.universalValue(), 0.001);
		assertEquals(0, EUR0.universalValue(), 0.001);
		assertEquals(-5000, SEKn100.universalValue(), 0.001);
		assertEquals(-5000, EUR10.universalValue(), 0.001);
	}

	@Test
	public void testEqualsMoney() {
		assertTrue(SEK100.equals(new Money(10000, SEK)));
		assertFalse(EUR20.equals(new Money(2500, EUR)));
		assertTrue(SEK0.equals(EUR0));
		assertFalse(SEKn100.equals(new Money(-5000, SEK)));
	}

	@Test
	public void testAdd() {
		Money result = SEK100.add(EUR10);
		assertEquals(10100, (int) result.getAmount());
		assertEquals(SEK, result.getCurrency());
	}

	@Test
	public void testSub() {
		Money result1 = SEK200.sub(SEK100);
		assertEquals(10000, (int) result1.getAmount());
		assertEquals(SEK, result1.getCurrency());
		Money result2 = EUR20.sub(EUR10);
		assertEquals(1000, (int) result2.getAmount());
		assertEquals(EUR, result2.getCurrency());
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertFalse(EUR20.isZero());
		assertFalse(SEKn100.isZero());
	}

	@Test
	public void testNegate() {
		Money negate1 = SEK100.negate();
		assertEquals(-1000, (int) negate1.getAmount());
		assertEquals(SEK, negate1.getCurrency());
		Money negate2 = EUR20.negate();
		assertEquals(-1000, (int) negate2.getAmount());
		assertEquals(EUR, negate2.getCurrency());
	}

	@Test
	public void testCompareTo() {
		assertTrue(SEK100.compareTo(SEK200) < 0);
		assertTrue(EUR20.compareTo(EUR10) > 0);
		assertTrue(SEKn100.compareTo(SEK0) < 0);
		assertEquals(0, SEK0.compareTo(EUR0));
	}
}