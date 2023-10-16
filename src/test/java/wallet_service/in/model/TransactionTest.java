package wallet_service.in.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wallet_service.in.controller.TransactionType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {

    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    @DisplayName("Get Id Test")
    public void GetIdTest() {
        Transaction transaction = new Transaction("id", 100.0, TransactionType.DEBIT);

        Integer id = transaction.getId();

        assertEquals("id", id);
    }

    @Test
    @DisplayName("Get Amount Test")
    public void GetAmountTest() {
        Transaction transaction = new Transaction("id", 100.0, TransactionType.DEBIT);

        double amount = transaction.getAmount();

        assertEquals(100.0, amount, 0.0);
    }

    @Test
    @DisplayName("Get Type Test")
    public void getTypeTest() {
        Transaction transaction = new Transaction("id", 100.0, TransactionType.DEBIT);

        TransactionType type = transaction.getType();

        assertEquals(TransactionType.DEBIT, type);
    }
}
