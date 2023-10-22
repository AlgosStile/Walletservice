package wallet_service.out.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import wallet_service.in.model.Player;


@Mapper
public interface PlayerMapper {

    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    PlayerDto playerToPlayerDto(Player player);
    Player playerDtoToPlayer(PlayerDto playerDto);
}