package wallet_service.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import wallet_service.in.model.Action;
import wallet_service.out.dto.ActionDto;

/**
 * Интерфейс-маппер для преобразования объектов Action в объекты ActionDto и наоборот.
 *
 * @author Олег Тодор
 */
@Mapper
public interface ActionMapper {
    ActionMapper INSTANCE = Mappers.getMapper(ActionMapper.class);

    /**
     * Преобразует объект Action в объект ActionDto.
     *
     * @param action Объект Action, который требуется преобразовать.
     * @return Объект ActionDto, полученный в результате преобразования.
     */
    ActionDto actionToActionDto(Action action);


    Action actionDtoToAction(ActionDto actionDto);
}