package wallet_service.in.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wallet_service.out.controller.TransactionType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTypeTest {

    @BeforeEach
    public void setUp() {
    }

    @Test
    @DisplayName("Values Test")
    public void valuesTest() {
        TransactionType[] types = TransactionType.values();

        assertEquals(2, types.length);
        assertEquals(TransactionType.DEBIT, types[0]);
        assertEquals(TransactionType.CREDIT, types[1]);
    }

    @Test
    @DisplayName("Value Of Test")
    public void valueOfTest() {
        TransactionType type = TransactionType.valueOf("DEBIT");

        assertEquals(TransactionType.DEBIT, type);
    }
}
