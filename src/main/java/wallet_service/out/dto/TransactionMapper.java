package wallet_service.out.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import wallet_service.in.model.Transaction;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDto transactionToTransactionDto(Transaction transaction);
    Transaction transactionDtoToTransaction(TransactionDto transactionDto);
}