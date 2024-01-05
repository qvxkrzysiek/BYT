package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		assertEquals("SEK",SEK.getName());
		assertEquals("DKK",DKK.getName());
		assertEquals("EUR",EUR.getName());
	}
	
	@Test
	public void testGetRate() {
		assertEquals(0.15, SEK.getRate(), 0.0001);
		assertEquals(0.20, DKK.getRate(), 0.0001);
		assertEquals(1.5, EUR.getRate(), 0.0001);
	}
	
	@Test
	public void testSetRate() {
		SEK.setRate(1.25);
		assertEquals(1.25, SEK.getRate(), 0.001);
		DKK.setRate(3.00);
		assertEquals(3.00, DKK.getRate(), 0.001);
		EUR.setRate(1.5);
		assertEquals(1.5, EUR.getRate(), 0.001);
	}
	
	@Test
	public void testGlobalValue() {
		assertEquals(15,(int)SEK.universalValue(10000));
		assertEquals(20,(int)DKK.universalValue(10000));
		assertEquals(15,(int)EUR.universalValue(1000));
	}
	
	@Test
	public void testValueInThisCurrency() {
		assertEquals(15,(int)SEK.valueInThisCurrency(150,EUR));
	}

}
