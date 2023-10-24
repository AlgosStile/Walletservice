package wallet_service.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import wallet_service.in.model.Transaction;
import wallet_service.out.dto.TransactionDto;

import java.util.List;

/**
 * Используя этот интерфейс TransactionMapper можно преобразовать данные между типами Transaction и TransactionDto
 * и их списками.
 *
 * @author Олег Тодор
 */
@Mapper
public interface TransactionMapper {

    /**
     * Экземпляр класса TransactionMapper, полученный с помощью Mappers.getMapper.
     */
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    /**
     * Преобразует объект Transaction в объект TransactionDto.
     *
     * @param transaction объект Transaction, который нужно преобразовать
     * @return объект TransactionDto, полученный из transaction
     */
    TransactionDto transactionToTransactionDto(Transaction transaction);

    /**
     * Преобразует объект TransactionDto в объект Transaction.
     *
     * @param transactionDto объект TransactionDto, который нужно преобразовать
     * @return объект Transaction, полученный из transactionDto
     */
    Transaction transactionDtoToTransaction(TransactionDto transactionDto);

    /**
     * Преобразует список объектов Transaction в список объектов TransactionDto.
     *
     * @param transactions список объектов Transaction
     * @return список объектов TransactionDto, полученный из transactions
     */
    List<TransactionDto> convertToDtoList(List<Transaction> transactions);
}
