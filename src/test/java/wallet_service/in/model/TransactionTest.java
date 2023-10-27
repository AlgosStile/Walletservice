package wallet_service.in.model;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import wallet_service.in.controller.TransactionType;

import static junit.framework.TestCase.assertEquals;

public class TransactionTest {

    @Test
    @DisplayName("Test Transaction Get Amount")
    public void testGetAmount() {
        Transaction transaction = new Transaction(1, 1000.00, TransactionType.CREDIT);
        assertEquals(1000.00, transaction.getAmount());
    }

    @Test
    @DisplayName("Test Transaction Get Type")
    public void testGetType() {
        Transaction transaction = new Transaction(2, 2000.00, TransactionType.DEBIT);
        assertEquals(TransactionType.DEBIT, transaction.getType());
    }
}
