package com.pimenta.petshop.mapper;


import com.pimenta.petshop.model.RacaDTO;
import com.pimenta.petshop.model.RacaEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RacaMapper {

    RacaEntity toEntity(RacaDTO dto);

    RacaDTO toDto(RacaEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(RacaDTO dto, @MappingTarget RacaEntity entity);
}