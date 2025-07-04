package com.edutech.ubicacion.services;

import com.edutech.ubicacion.dto.ComunaDTO;
import com.edutech.ubicacion.models.Comuna;
import com.edutech.ubicacion.repository.ComunaRepository;
import com.edutech.ubicacion.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComunaService {

    @Autowired
    private ComunaRepository repository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    public ComunaDTO guardar(ComunaDTO dto) {
        Comuna comuna = toEntity(dto);
        Comuna saved = repository.save(comuna);
        return toDTO(saved);
    }

    public List<ComunaDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<ComunaDTO> obtenerPorId(Integer id) {
        return repository.findById(id).map(this::toDTO);
    }

    public Optional<ComunaDTO> actualizar(Integer id, ComunaDTO dto) {
        return repository.findById(id)
            .map(comuna -> {
                comuna.setNombre(dto.getNombre());
                if (dto.getProvinciaId() != null) {
                    provinciaRepository.findById(dto.getProvinciaId()).ifPresent(comuna::setProvincia);
                }
                Comuna actualizada = repository.save(comuna);
                return toDTO(actualizada);
            });
    }

    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- Métodos de Conversión ---
    private ComunaDTO toDTO(Comuna comuna) {
        ComunaDTO dto = new ComunaDTO();
        dto.setIdComuna(comuna.getIdComuna());
        dto.setNombre(comuna.getNombre());
        if (comuna.getProvincia() != null) {
            dto.setProvinciaId(comuna.getProvincia().getIdProvincia());
        }
        return dto;
    }

    private Comuna toEntity(ComunaDTO dto) {
        Comuna comuna = new Comuna();
        comuna.setIdComuna(dto.getIdComuna());
        comuna.setNombre(dto.getNombre());
        if (dto.getProvinciaId() != null) {
            provinciaRepository.findById(dto.getProvinciaId())
                .ifPresentOrElse(comuna::setProvincia, 
                () -> { throw new RuntimeException("Provincia no encontrada con id: " + dto.getProvinciaId()); });
        }
        return comuna;
    }
}