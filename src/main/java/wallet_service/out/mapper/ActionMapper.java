package wallet_service.out.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import wallet_service.in.model.Action;
import wallet_service.out.dto.ActionDto;

@Mapper
public interface ActionMapper {
    ActionMapper INSTANCE = Mappers.getMapper(ActionMapper.class);

    ActionDto actionToActionDto(Action action);
    Action actionDtoToAction(ActionDto actionDto);
}