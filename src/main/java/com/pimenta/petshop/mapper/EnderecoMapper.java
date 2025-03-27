package com.pimenta.petshop.mapper;


import com.pimenta.petshop.model.EnderecoDTO;
import com.pimenta.petshop.model.EnderecoEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    @Mapping(source = "cpf", target = "cliente.cpf")
    EnderecoEntity toEntity(EnderecoDTO dto);

    @Mapping(source = "cliente.cpf", target = "cpf")
    EnderecoDTO toDto(EnderecoEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "cpf", target = "cliente.cpf")
    void updateEntityFromDto(EnderecoDTO dto, @MappingTarget EnderecoEntity entity);
}