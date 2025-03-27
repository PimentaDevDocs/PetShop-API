package com.pimenta.petshop.mapper;

import com.pimenta.petshop.model.AtendimentoDTO;
import com.pimenta.petshop.model.AtendimentoEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class, PetMapper.class})
public interface AtendimentoMapper {
    @Mapping(source = "cpf", target = "cliente.cpf")
    @Mapping(source = "idPet", target = "pet.id")
    AtendimentoEntity toEntity(AtendimentoDTO dto);

    @Mapping(source = "cliente.cpf", target = "cpf")
    @Mapping(source = "pet.id", target = "idPet")
    AtendimentoDTO toDto(AtendimentoEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AtendimentoDTO dto, @MappingTarget AtendimentoEntity entity);
}