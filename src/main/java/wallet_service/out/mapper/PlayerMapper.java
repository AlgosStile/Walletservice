package wallet_service.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import wallet_service.in.model.Player;
import wallet_service.out.dto.PlayerDto;


/**
 * Интерфейс {@code PlayerMapper} предназначен для преобразования экземпляров классов {@link Player} и {@link PlayerDto} друг в друга.
 * Используется библиотека MapStruct, предоставляющая функционал для быстрого "картирования" (маппинга) одних объектов на другие.
 *
 * @author Олег Тодор
 */
@Mapper
public interface PlayerMapper {

    /**
     * Экземпляр интерфейса {@link PlayerMapper}. Используется для доступа к методам интерфейса без необходимости создания реализации.
     */
    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    /**
     * Преобразует экземпляр класса {@link Player} в экземпляр класса {@link PlayerDto}.
     *
     * @param player Экземпляр класса {@link Player}, который необходимо преобразовать.
     * @return Экземпляр класса {@link PlayerDto}, содержащий информацию, извлеченную из указанного экземпляра класса {@link Player}.
     */
    PlayerDto playerToPlayerDto(Player player);

    /**
     * Преобразует экземпляр класса {@link PlayerDto} в экземпляр класса {@link Player}.
     *
     * @param playerDto Экземпляр класса {@link PlayerDto}, который необходимо преобразовать.
     * @return Экземпляр класса {@link Player}, содержащий информацию, извлеченную из указанного экземпляра класса {@link PlayerDto}.
     */
    Player playerDtoToPlayer(PlayerDto playerDto);
}
