package com.pimenta.petshop.service;

import com.pimenta.petshop.exception.NotFoundException;
import com.pimenta.petshop.mapper.RacaMapper;
import com.pimenta.petshop.model.ClienteEntity;
import com.pimenta.petshop.model.RacaDTO;
import com.pimenta.petshop.model.RacaEntity;
import com.pimenta.petshop.repository.RacaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RacaService {

    private final RacaRepository racaRepository;
    private final RacaMapper racaMapper;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public RacaDTO createRaca(RacaDTO racaDTO) {
        RacaEntity raca = racaMapper.toEntity(racaDTO);
        return racaMapper.toDto(racaRepository.save(raca));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public RacaDTO updateRaca(Long id, RacaDTO racaDTO) {
        RacaEntity raca = racaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(RacaEntity.class, id.toString()));

        racaMapper.updateEntityFromDto(racaDTO, raca);
        return racaMapper.toDto(racaRepository.save(raca));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRaca(Long id) {
        if (!racaRepository.existsById(id)) {
            throw new NotFoundException(RacaEntity.class, id.toString());
        }
        racaRepository.deleteById(id);
    }

    public RacaDTO getRacaById(Long id) {
        return racaRepository.findById(id)
                .map(racaMapper::toDto)
                .orElseThrow(() -> new NotFoundException(ClienteEntity.class, id.toString()));
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('ADMIN')")
    public List<RacaDTO> getAllRacas() {
        return racaRepository.findAll()
                .stream()
                .map(racaMapper::toDto)
                .toList();
    }
}