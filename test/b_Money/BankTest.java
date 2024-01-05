package b_Money;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BankTest
    {
    Currency SEK, DKK;
    Bank SweBank, Nordea, DanskeBank;

    @Before
    public void setUp() throws Exception
        {
        DKK = new Currency("DKK", 0.20);
        SEK = new Currency("SEK", 0.15);
        SweBank = new Bank("SweBank", SEK);
        Nordea = new Bank("Nordea", SEK);
        DanskeBank = new Bank("DanskeBank", DKK);
        SweBank.openAccount("Ulrika");
        SweBank.openAccount("Bob");
        Nordea.openAccount("Bob");
        DanskeBank.openAccount("Gertrud");
        }

    @Test
    public void testGetName() {
        assertEquals("SweBank", SweBank.getName());
        assertEquals("Nordea", Nordea.getName());
        assertEquals("DanskeBank", DanskeBank.getName());
    }

    @Test
    public void testGetCurrency() {
        assertEquals(SEK, SweBank.getCurrency());
        assertEquals(DKK, DanskeBank.getCurrency());
        assertEquals(SEK, Nordea.getCurrency());
    }

    @Test
    public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
        assertTrue(SweBank.accountExists("Ulrika"));
    }

    @Test
    public void testDeposit() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(1000000, SEK));
        assertEquals(Integer.valueOf(1000000), SweBank.getBalance("Ulrika"));
    }

    @Test
    public void testWithdraw() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(10000, SEK));
        SweBank.withdraw("Ulrika", new Money(5000, SEK));
        assertEquals(Integer.valueOf(5000), SweBank.getBalance("Ulrika"));
    }

    @Test
    public void testGetBalance() throws AccountDoesNotExistException {
        assertEquals(Integer.valueOf(0), SweBank.getBalance("Ulrika"));
    }

    @Test
    public void testTransfer() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(10000, SEK));
        SweBank.transfer("Ulrika", Nordea, "Bob", new Money(5000, SEK));
        assertEquals(Integer.valueOf(5000), Nordea.getBalance("Bob"));
        assertEquals(Integer.valueOf(5000), SweBank.getBalance("Ulrika"));
    }

    @Test
    public void testTimedPayment() throws AccountDoesNotExistException {
        SweBank.deposit("Ulrika", new Money(100000, SEK));
        SweBank.addTimedPayment("Ulrika", "payment1", 1, 1, new Money(1000, SEK), SweBank, "Bob");
        SweBank.tick();
        assertEquals(Integer.valueOf(0), Nordea.getBalance("Bob"));
        assertEquals(Integer.valueOf(99000), SweBank.getBalance("Ulrika"));
    }
}
