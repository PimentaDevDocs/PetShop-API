package com.pimenta.petshop.mapper;

import com.pimenta.petshop.enums.ROLE;
import com.pimenta.petshop.model.UsuarioDTO;
import com.pimenta.petshop.model.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "ROLE", source = "ROLE", qualifiedByName = "mapPerfil")
    UsuarioEntity toEntity(UsuarioDTO dto);

    @Mapping(target = "ROLE", source = "ROLE", qualifiedByName = "unmapPerfil")
    UsuarioDTO toDto(UsuarioEntity entity);

    @Named("mapPerfil")
    default ROLE mapPerfil(String perfil) {
        if (!ROLE.isValid(perfil)) {
            throw new IllegalArgumentException("Perfil inválido: " + perfil);
        }
        return ROLE.valueOf(perfil.toUpperCase());
    }

    @Named("unmapPerfil")
    default String unmapPerfil(ROLE ROLE) {
        return ROLE.name();
    }

    @Mapping(target = "senha", ignore = true)
    UsuarioDTO toDtoWithoutPassword(UsuarioEntity entity);
}