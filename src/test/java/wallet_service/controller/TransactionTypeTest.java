package wallet_service.controller;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransactionTypeTest {

    @Before
    public void setUp() throws Exception {
    }

    //values() Этот тест проверяет правильность созданных констант перечисления TransactionType.
    // Две константы: DEBIT и CREDIT, и они должны быть на местах 0 и 1 соответственно.
    @Test
    public void values() {
        TransactionType[] types = TransactionType.values();

        assertEquals(2, types.length);
        assertEquals(TransactionType.DEBIT, types[0]);
        assertEquals(TransactionType.CREDIT, types[1]);
    }

    //valueOf() На вход подается строка "DEBIT" и тест проверяет,
    //что метод корректно возвращает соответствующее значение перечисления TransactionType.DEBIT.
    @Test
    public void valueOf() {
        TransactionType type = TransactionType.valueOf("DEBIT");

        assertEquals(TransactionType.DEBIT, type);
    }
}