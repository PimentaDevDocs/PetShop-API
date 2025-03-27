package com.pimenta.petshop.mapper;

import com.pimenta.petshop.model.ClienteDTO;
import com.pimenta.petshop.model.ClienteEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ContatoMapper.class, EnderecoMapper.class})
public interface ClienteMapper {
    @Mapping(target = "contatos", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    ClienteEntity toEntity(ClienteDTO dto);

    ClienteDTO toDto(ClienteEntity entity);

    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "contatos", ignore = true)
    @Mapping(target = "enderecos", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ClienteDTO dto, @MappingTarget ClienteEntity entity);

    @AfterMapping
    default void linkContatos(@MappingTarget ClienteEntity cliente) {
        if (cliente.getContatos() != null) {
            cliente.getContatos().forEach(contato -> contato.setCliente(cliente));
        }
    }

    @AfterMapping
    default void linkEnderecos(@MappingTarget ClienteEntity cliente) {
        if (cliente.getEnderecos() != null) {
            cliente.getEnderecos().forEach(endereco -> endereco.setCliente(cliente));
        }
    }
}