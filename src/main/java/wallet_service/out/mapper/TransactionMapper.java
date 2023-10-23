package wallet_service.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import wallet_service.in.model.Transaction;
import wallet_service.out.dto.TransactionDto;

import java.util.List;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionDto transactionToTransactionDto(Transaction transaction);
    Transaction transactionDtoToTransaction(TransactionDto transactionDto);

    List<TransactionDto> convertToDtoList(List<Transaction> transactions);
}