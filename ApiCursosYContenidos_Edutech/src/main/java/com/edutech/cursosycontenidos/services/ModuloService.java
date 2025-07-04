package com.edutech.cursosycontenidos.services;

import com.edutech.cursosycontenidos.dto.ModuloDTO;
import com.edutech.cursosycontenidos.models.Curso;
import com.edutech.cursosycontenidos.models.Modulo;
import com.edutech.cursosycontenidos.repository.CursoRepository;
import com.edutech.cursosycontenidos.repository.ModuloRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ModuloService {

    @Autowired
    private ModuloRepository repository;

    @Autowired
    private CursoRepository cursoRepository;

    public ModuloDTO guardar(ModuloDTO dto) {
        Modulo modulo = toEntity(dto);
        Modulo saved = repository.save(modulo);
        return toDTO(saved);
    }

    public List<ModuloDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ModuloDTO> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    public Optional<ModuloDTO> actualizar(Integer id, ModuloDTO dto) {
        return repository.findById(id)
                .map(modulo -> {
                    modulo.setTitulo(dto.getTitulo());
                    modulo.setOrden(dto.getOrden());
                    modulo.setDescripcion(dto.getDescripcion());
                    
                    if (dto.getCursoId() != null) {
                        Curso curso = cursoRepository.findById(dto.getCursoId())
                                .orElseThrow(() -> new EntityNotFoundException("No se encontró el curso con el ID: " + dto.getCursoId()));
                        modulo.setCurso(curso);
                    }
                    
                    Modulo actualizado = repository.save(modulo);
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

    public List<ModuloDTO> buscarPorCursoId(Integer cursoId) {
        return repository.findByCursoIdCurso(cursoId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ModuloDTO toDTO(Modulo modulo) {
        ModuloDTO dto = new ModuloDTO();
        dto.setIdModulo(modulo.getIdModulo());
        dto.setTitulo(modulo.getTitulo());
        dto.setOrden(modulo.getOrden());
        dto.setDescripcion(modulo.getDescripcion());
        if (modulo.getCurso() != null) {
            dto.setCursoId(modulo.getCurso().getIdCurso());
        }
        return dto;
    }

    private Modulo toEntity(ModuloDTO dto) {
        Modulo modulo = new Modulo();
        modulo.setIdModulo(dto.getIdModulo());
        modulo.setTitulo(dto.getTitulo());
        modulo.setOrden(dto.getOrden());
        modulo.setDescripcion(dto.getDescripcion());
        
        if (dto.getCursoId() != null) {
            Curso curso = cursoRepository.findById(dto.getCursoId())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el curso con el ID: " + dto.getCursoId()));
            modulo.setCurso(curso);
        }
        
        return modulo;
    }
}