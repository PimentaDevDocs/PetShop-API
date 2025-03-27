package com.pimenta.petshop.mapper;

import com.pimenta.petshop.model.FotoDTO;
import com.pimenta.petshop.model.FotoEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FotoMapper {

    @Mapping(source = "cpf", target = "cliente.cpf")
    @Mapping(source = "idPet", target = "pet.id")
    FotoEntity toEntity(FotoDTO dto);

    @Mapping(source = "cliente.cpf", target = "cpf")
    @Mapping(source = "pet.id", target = "idPet")
    FotoDTO toDto(FotoEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "cpf", target = "cliente.cpf")
    @Mapping(source = "idPet", target = "pet.id")
    void updateEntityFromDto(FotoDTO dto, @MappingTarget FotoEntity entity);

    List<FotoDTO> toDto(List<FotoEntity> entities);
    List<FotoEntity> toEntity(List<FotoDTO> dtos);
}
