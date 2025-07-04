// src/main/java/com/edutech/ubicacion/services/ProvinciaService.java
package com.edutech.ubicacion.services;

import com.edutech.ubicacion.dto.ComunaDTO;
import com.edutech.ubicacion.dto.ProvinciaDTO;
import com.edutech.ubicacion.models.Provincia;
import com.edutech.ubicacion.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProvinciaService {

    @Autowired
    private ProvinciaRepository repository;

    public ProvinciaDTO guardar(ProvinciaDTO dto) {
        Provincia provincia = toEntity(dto);
        Provincia saved = repository.save(provincia);
        return toDTO(saved);
    }

    public List<ProvinciaDTO> listar() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<ProvinciaDTO> obtenerPorId(Integer id) {
        return repository.findById(id).map(this::toDTO);
    }

    public Optional<ProvinciaDTO> actualizar(Integer id, ProvinciaDTO dto) {
        return repository.findById(id)
            .map(provincia -> {
                provincia.setNombre(dto.getNombre());
                Provincia actualizada = repository.save(provincia);
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
    private ProvinciaDTO toDTO(Provincia provincia) {
        ProvinciaDTO dto = new ProvinciaDTO();
        dto.setIdProvincia(provincia.getIdProvincia());
        dto.setNombre(provincia.getNombre());
        if (provincia.getComunas() != null) {
            dto.setComunas(provincia.getComunas().stream().map(comuna -> {
                ComunaDTO comunaDTO = new ComunaDTO();
                comunaDTO.setIdComuna(comuna.getIdComuna());
                comunaDTO.setNombre(comuna.getNombre());
                comunaDTO.setProvinciaId(provincia.getIdProvincia());
                return comunaDTO;
            }).collect(Collectors.toList()));
        }
        return dto;
    }

    private Provincia toEntity(ProvinciaDTO dto) {
        Provincia provincia = new Provincia();
        provincia.setIdProvincia(dto.getIdProvincia());
        provincia.setNombre(dto.getNombre());
        // La conversión de la lista de comunas se maneja desde la entidad Comuna
        return provincia;
    }
}