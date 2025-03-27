package com.pimenta.petshop.service;

import com.pimenta.petshop.enums.TIPO_FOTO;
import com.pimenta.petshop.mapper.ClienteMapper;
import com.pimenta.petshop.mapper.FotoMapper;
import com.pimenta.petshop.mapper.PetMapper;
import com.pimenta.petshop.model.ClienteDTO;
import com.pimenta.petshop.model.FotoDTO;
import com.pimenta.petshop.model.FotoEntity;
import com.pimenta.petshop.model.PetDTO;
import com.pimenta.petshop.repository.FotoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FotoServiceTest {

    @Mock
    private FotoRepository fotoRepository;

    @Mock
    private ClienteService clienteService;

    @Mock
    private PetService petService;

    @Mock
    private FotoMapper fotoMapper;

    @Mock
    private ClienteMapper clienteMapper;

    @Mock
    private PetMapper petMapper;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private FotoService fotoService;

    private FotoDTO fotoDTO;
    private FotoEntity fotoEntity;
    private ClienteDTO clienteDTO;
    private PetDTO petDTO;
    private String cpf;

    @BeforeEach
    public void setup() {

        clienteMapper = mock(ClienteMapper.class);

        fotoDTO = new FotoDTO();
        fotoDTO.setId(1L);
        fotoDTO.setTipoFoto(TIPO_FOTO.JPEG);

        fotoEntity = new FotoEntity();
        fotoEntity.setId(1L);
        fotoEntity.setTipoFoto(TIPO_FOTO.JPEG);

        clienteDTO = new ClienteDTO();
        clienteDTO.setCpf("12345678901");

        petDTO = new PetDTO();
        petDTO.setId(1L);

        cpf = "12345678901";
    }

    @Test
    public void testAddFotoCliente() throws IOException {
        when(clienteService.getClienteByCpf(cpf)).thenReturn(clienteDTO);
        when(fotoRepository.save(any(FotoEntity.class))).thenReturn(fotoEntity);
        when(fotoMapper.toDto(fotoEntity)).thenReturn(fotoDTO);
        when(multipartFile.getBytes()).thenReturn(new byte[]{1, 2, 3});

        FotoDTO result = fotoService.addFotoCliente(cpf, TIPO_FOTO.JPEG, multipartFile);

        assertNotNull(result);
        assertEquals(TIPO_FOTO.JPEG, result.getTipoFoto());
        verify(fotoRepository, times(1)).save(any(FotoEntity.class));
    }

    @Test
    public void testAddFotoPet() throws IOException {
        when(petService.getPetById(1L)).thenReturn(petDTO);
        when(fotoRepository.save(any(FotoEntity.class))).thenReturn(fotoEntity);
        when(fotoMapper.toDto(fotoEntity)).thenReturn(fotoDTO);
        when(multipartFile.getBytes()).thenReturn(new byte[]{1, 2, 3});

        FotoDTO result = fotoService.addFotoPet(1L, TIPO_FOTO.JPEG, multipartFile);

        assertNotNull(result);
        assertEquals(TIPO_FOTO.JPEG, result.getTipoFoto());
        verify(fotoRepository, times(1)).save(any(FotoEntity.class));
    }

    @Test
    public void testGetFotosPorCliente() {
        FotoEntity foto1 = new FotoEntity();
        foto1.setId(1L);
        foto1.setTipoFoto(TIPO_FOTO.JPEG);
        FotoEntity foto2 = new FotoEntity();
        foto2.setId(2L);
        foto2.setTipoFoto(TIPO_FOTO.PNG);
        String cpf = "12345678901";

        when(fotoRepository.findByClienteCpf(cpf)).thenReturn(Arrays.asList(foto1, foto2));
        when(fotoMapper.toDto(anyList())).thenReturn(Arrays.asList(fotoDTO, fotoDTO));

        List<FotoDTO> result = fotoService.getFotosPorCliente(cpf);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetFotosPorPet() {
        FotoEntity foto1 = new FotoEntity();
        foto1.setId(1L);
        foto1.setTipoFoto(TIPO_FOTO.JPEG);
        foto1.setPet(petMapper.toEntity(petDTO));

        FotoEntity foto2 = new FotoEntity();
        foto2.setId(2L);
        foto2.setTipoFoto(TIPO_FOTO.PNG);
        foto2.setPet(petMapper.toEntity(petDTO));

        when(fotoRepository.findByPetId(1L)).thenReturn(Arrays.asList(foto1, foto2));
        when(fotoMapper.toDto(anyList())).thenReturn(Arrays.asList(fotoDTO, fotoDTO));

        List<FotoDTO> result = fotoService.getFotosPorPet(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testUpdateFoto() {
        FotoEntity fotoEntity = new FotoEntity();
        fotoEntity.setTipoFoto(TIPO_FOTO.JPEG);
        fotoEntity.setId(1L);
        when(fotoRepository.findById(1L)).thenReturn(Optional.of(fotoEntity));

        when(fotoRepository.save(any(FotoEntity.class))).thenAnswer(invocation -> {
            FotoEntity updatedFoto = invocation.getArgument(0);
            updatedFoto.setTipoFoto(TIPO_FOTO.JPEG);
            return updatedFoto;
        });

        FotoDTO fotoDTO = new FotoDTO();
        fotoDTO.setTipoFoto(TIPO_FOTO.JPEG);
        when(fotoMapper.toDto(any(FotoEntity.class))).thenReturn(fotoDTO);

        FotoDTO result = fotoService.updateFoto(1L, TIPO_FOTO.JPEG, multipartFile);

        assertNotNull(result);
        assertEquals(TIPO_FOTO.JPEG, result.getTipoFoto());
        verify(fotoRepository, times(1)).save(any(FotoEntity.class));
    }


    @Test
    public void testDeleteFoto() {
        when(fotoRepository.findById(1L)).thenReturn(Optional.of(fotoEntity));

        fotoService.deleteFoto(1L);

        verify(fotoRepository, times(1)).delete(fotoEntity);
    }

    @Test
    public void testAddFotoCliente_Exception() {
        when(clienteService.getClienteByCpf(anyString())).thenReturn(new ClienteDTO());
        when(fotoRepository.save(any(FotoEntity.class))).thenReturn(new FotoEntity());

        FotoDTO fotoDTO = fotoService.addFotoCliente(cpf, TIPO_FOTO.JPEG, multipartFile);
    }


    @Test
    public void testDeleteFoto_NotFound() {
        when(fotoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> fotoService.deleteFoto(1L));
        assertTrue(ex.getMessage().contains("Foto não encontrado(a)"));
    }
}
