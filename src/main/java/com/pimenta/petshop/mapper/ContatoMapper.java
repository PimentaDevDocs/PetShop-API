package com.pimenta.petshop.mapper;

import com.pimenta.petshop.model.ContatoDTO;
import com.pimenta.petshop.model.ContatoEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ContatoMapper {

    @Mapping(source = "cpf", target = "cliente.cpf")
    ContatoEntity toEntity(ContatoDTO dto);

    @Mapping(source = "cliente.cpf", target = "cpf")
    ContatoDTO toDto(ContatoEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "cpf", target = "cliente.cpf")
    void updateEntityFromDto(ContatoDTO dto, @MappingTarget ContatoEntity entity);
}