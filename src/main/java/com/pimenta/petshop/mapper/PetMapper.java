package com.pimenta.petshop.mapper;

import com.pimenta.petshop.model.PetDTO;
import com.pimenta.petshop.model.PetEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {RacaMapper.class})
public interface PetMapper {

    @Mapping(source = "cpf", target = "cliente.cpf")
    @Mapping(source = "idRaca", target = "raca.id")
    PetEntity toEntity(PetDTO dto);

    @Mapping(source = "cliente.cpf", target = "cpf")
    @Mapping(source = "raca.id", target = "idRaca")
    @Mapping(source = "fotos", target = "fotos")
    PetDTO toDto(PetEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "cpf", target = "cliente.cpf")
    @Mapping(source = "idRaca", target = "raca.id")
    void updateEntityFromDto(PetDTO dto, @MappingTarget PetEntity entity);
}