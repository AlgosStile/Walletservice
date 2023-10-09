package wallet_service.model;

import org.junit.Before;
import org.junit.Test;
import wallet_service.controller.TransactionType;

import static org.junit.Assert.assertEquals;

public class TransactionTest {

    @Before
    public void setUp() throws Exception {
    }

    /**
     * testGetId() Проверяет, что метод getId() класса Transaction возвращает правильный id транзакции.
     */
    @Test
    public void testGetId() {
        Transaction transaction = new Transaction("id", 100.0, TransactionType.DEBIT);

        String id = transaction.getId();

        assertEquals("id", id);
    }

    /**
     * testGetAmount() Проверяет, что метод getAmount() класса Transaction возвращает правильную сумму транзакции.
     */
    @Test
    public void testGetAmount() {
        Transaction transaction = new Transaction("id", 100.0, TransactionType.DEBIT);

        double amount = transaction.getAmount();

        assertEquals(100.0, amount, 0.0);
    }

    /**
     * getType() Проверяет, что метод getType() класса Transaction возвращает правильный тип транзакции.
     */
    @Test
    public void getType() {
        Transaction transaction = new Transaction("id", 100.0, TransactionType.DEBIT);

        TransactionType type = transaction.getType();

        assertEquals(TransactionType.DEBIT, type);
    }
}