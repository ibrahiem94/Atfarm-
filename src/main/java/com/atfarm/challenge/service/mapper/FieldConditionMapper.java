package com.atfarm.challenge.service.mapper;

import com.atfarm.challenge.domain.FieldCondition;
import com.atfarm.challenge.service.dto.FieldConditionDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link FieldCondition} and its DTO {@link FieldConditionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FieldConditionMapper extends EntityMapper<FieldConditionDTO, FieldCondition> {



    default FieldCondition fromId(Long id) {
        if (id == null) {
            return null;
        }
        FieldCondition fieldCondition = new FieldCondition();
        fieldCondition.setId(id);
        return fieldCondition;
    }
}
