package com.edutech.cursosycontenidos.services;

import com.edutech.cursosycontenidos.dto.ContenidoDTO;
import com.edutech.cursosycontenidos.models.Contenido;
import com.edutech.cursosycontenidos.models.Modulo;
import com.edutech.cursosycontenidos.repository.ContenidoRepository;
import com.edutech.cursosycontenidos.repository.ModuloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContenidoService {

    @Autowired
    private ContenidoRepository repository;

    @Autowired
    private ModuloRepository moduloRepository;

    public ContenidoDTO guardar(ContenidoDTO dto) {
        Contenido contenido = toEntity(dto);
        Contenido saved = repository.save(contenido);
        return toDTO(saved);
    }

    public List<ContenidoDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ContenidoDTO> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    public Optional<ContenidoDTO> actualizar(Integer id, ContenidoDTO dto) {
        return repository.findById(id)
                .map(contenido -> {
                    contenido.setTipo(dto.getTipo());
                    contenido.setUrlArchivo(dto.getUrlArchivo());

                    if (dto.getModuloId() != null) {
                        Modulo modulo = moduloRepository.findById(dto.getModuloId())
                                .orElseThrow(() -> new EntityNotFoundException("No se encontró el módulo con el ID: " + dto.getModuloId()));
                        contenido.setModulo(modulo);
                    }
                    
                    Contenido actualizado = repository.save(contenido);
                    return toDTO(actualizado);
                });
    }

    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ContenidoDTO> buscarPorModuloId(Integer moduloId) {
        return repository.findByModuloIdModulo(moduloId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ContenidoDTO toDTO(Contenido contenido) {
        ContenidoDTO dto = new ContenidoDTO();
        dto.setIdContenido(contenido.getIdContenido());
        dto.setTipo(contenido.getTipo());
        dto.setUrlArchivo(contenido.getUrlArchivo());
        if (contenido.getModulo() != null) {
            dto.setModuloId(contenido.getModulo().getIdModulo());
        }
        return dto;
    }

    private Contenido toEntity(ContenidoDTO dto) {
        Contenido contenido = new Contenido();
        contenido.setIdContenido(dto.getIdContenido());
        contenido.setTipo(dto.getTipo());
        contenido.setUrlArchivo(dto.getUrlArchivo());

        if (dto.getModuloId() != null) {
            Modulo modulo = moduloRepository.findById(dto.getModuloId())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el módulo con el ID: " + dto.getModuloId()));
            contenido.setModulo(modulo);
        }
        
        return contenido;
    }
}