package com.pimenta.petshop.service;

import com.pimenta.petshop.enums.TIPO_FOTO;
import com.pimenta.petshop.exception.NotFoundException;
import com.pimenta.petshop.mapper.ClienteMapper;
import com.pimenta.petshop.mapper.FotoMapper;
import com.pimenta.petshop.mapper.PetMapper;
import com.pimenta.petshop.model.*;
import com.pimenta.petshop.repository.FotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FotoService {

    private final FotoRepository fotoRepository;
    private final ClienteService clienteService;
    private final PetService petService;
    private final FotoMapper fotoMapper;
    private final ClienteMapper clienteMapper;
    private final PetMapper petMapper;

    @Transactional
    @PreAuthorize("hasRole('ADMIN') || @securityService.isOwner(authentication, cpf)")
    public FotoDTO addFotoCliente(String cpf, TIPO_FOTO tipoFoto, MultipartFile foto) {
        ClienteDTO clienteDTO = clienteService.getClienteByCpf(cpf);
        FotoEntity fotoEntity = new FotoEntity();
        fotoEntity.setCliente(clienteMapper.toEntity(clienteDTO));
        fotoEntity.setTipoFoto(tipoFoto);

        try {
            fotoEntity.setDados(foto.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar o arquivo da foto", e);
        }

        fotoEntity = fotoRepository.save(fotoEntity);
        return fotoMapper.toDto(fotoEntity);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') || @securityService.isPetOwner(authentication, petId)")
    public FotoDTO addFotoPet(Long petId, TIPO_FOTO tipoFoto, MultipartFile foto) {
        PetDTO petDTO = petService.getPetById(petId);

        FotoEntity fotoEntity = new FotoEntity();
        fotoEntity.setPet(petMapper.toEntity(petDTO));
        fotoEntity.setTipoFoto(tipoFoto);

        try {
            fotoEntity.setDados(foto.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar o arquivo da foto", e);
        }

        fotoEntity = fotoRepository.save(fotoEntity);
        return fotoMapper.toDto(fotoEntity);
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') || @securityService.isOwner(authentication, #cpf)")
    public List<FotoDTO> getFotosPorCliente(String cpf) {
        List<FotoEntity> fotos = fotoRepository.findByClienteCpf(cpf);

        if (fotos == null || fotos.isEmpty()) {
            throw new NotFoundException(FotoEntity.class, cpf);
        }

        fotos.forEach(FotoEntity::getDados);

        return fotoMapper.toDto(fotos);
    }


    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') || @securityService.isPetOwner(authentication, #petId)")
    public List<FotoDTO> getFotosPorPet(Long petId) {
        List<FotoEntity> fotos = fotoRepository.findByPetId(petId);

        if (fotos == null || fotos.isEmpty()) {
            throw new NotFoundException(FotoEntity.class, petId.toString());
        }

        fotos.forEach(FotoEntity::getDados);

        return fotoMapper.toDto(fotos);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') || @securityService.isFotoOwner(authentication, #fotoId)")
    public FotoDTO updateFoto(Long fotoId, TIPO_FOTO tipoFoto, MultipartFile foto) {
        FotoEntity fotoEntity = fotoRepository.findById(fotoId).orElseThrow(() -> new NotFoundException(FotoEntity.class, fotoId.toString()));

        fotoEntity.setTipoFoto(tipoFoto);

        try {
            fotoEntity.setDados(foto.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao processar o arquivo da foto", e);
        }

        fotoEntity = fotoRepository.save(fotoEntity);
        return fotoMapper.toDto(fotoEntity);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') || @securityService.isFotoOwner(authentication, #fotoId)")
    public void deleteFoto(Long fotoId) {
        FotoEntity fotoEntity = fotoRepository.findById(fotoId).orElseThrow(() -> new NotFoundException(FotoEntity.class, fotoId.toString()));
        fotoRepository.delete(fotoEntity);
    }
}
