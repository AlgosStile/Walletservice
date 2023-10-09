package wallet_service.controller;

/**
 * TransactionType enum представляет тип финансовой транзакции.
 * Он определяет два возможных значения: DEBIT и CREDIT.
 * Значение DEBIT указывает на операцию, по которой средства списываются со счета.
 * Значение CREDIT указывает на операцию, по которой деньги добавляются на счет.
 * Это перечисление используется в пакете wallet_service.controller для различения различных типов транзакций.
 *
 * @author Олег Тодор
 */
public enum TransactionType {
    DEBIT, CREDIT
}
