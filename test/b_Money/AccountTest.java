package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {
		testAccount.addTimedPayment("id1", 2, 1,
				new Money(1000000, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("id1"));
		testAccount.removeTimedPayment("id1");
		assertFalse(testAccount.timedPaymentExists("id1"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("id1", 2, 1,
				new Money(1000000, SEK), SweBank, "Alice");
		testAccount.tick();
		testAccount.tick();
		assertEquals(new Money(1001000, SEK).getAmount(),
				SweBank.getBalance("Alice"));
	}

	@Test
	public void testAddWithdraw() {
		testAccount.deposit(new Money(5000, SEK));
		assertEquals(new Money(10005000, SEK).toString(),
				testAccount.getBalance().toString());
		testAccount.withdraw(new Money(2000, SEK));
		assertEquals(new Money(10003000, SEK).toString(),
				testAccount.getBalance().toString());
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(new Money(10000000, SEK).toString(),
				testAccount.getBalance().toString());
	}
}
