package com.edutech.cursosycontenidos.services;

import com.edutech.cursosycontenidos.dto.CategoriaDTO;
import com.edutech.cursosycontenidos.dto.CategoriaInfoDTO;
import com.edutech.cursosycontenidos.dto.CursoDTO;
import com.edutech.cursosycontenidos.models.Categoria;
import com.edutech.cursosycontenidos.models.Curso;
import com.edutech.cursosycontenidos.models.Usuario;
import com.edutech.cursosycontenidos.repository.CategoriaRepository;
import com.edutech.cursosycontenidos.repository.CursoRepository;
import com.edutech.cursosycontenidos.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoService {

    @Autowired
    private CursoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public CursoDTO guardar(CursoDTO dto) {
        Curso curso = toEntity(dto);
        Curso saved = repository.save(curso);
        return toDTO(saved);
    }

    public List<CursoDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CursoDTO> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    public Optional<CursoDTO> actualizar(Integer id, CursoDTO dto) {
        return repository.findById(id)
                .map(cursoExistente -> {
                    cursoExistente.setNombreCurso(dto.getNombreCurso());
                    cursoExistente.setDescripcion(dto.getDescripcion());
                    cursoExistente.setEstado(dto.getEstado());
                    cursoExistente.setPrecio(dto.getPrecio());

                    Categoria categoria = categoriaRepository.findById(dto.getCategoria().getIdCategoria())
                            .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + dto.getCategoria().getIdCategoria()));
                    cursoExistente.setCategoria(categoria);

                    Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + dto.getUsuarioId()));
                    cursoExistente.setUsuario(usuario);

                    Curso actualizado = repository.save(cursoExistente);
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

    public List<CursoDTO> buscarPorCategoriaId(Integer categoriaId) {
        return repository.findByCategoriaIdCategoria(categoriaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<CursoDTO> buscarPorUsuarioId(Integer usuarioId) {
        return repository.findByUsuarioIdUser(usuarioId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private CursoDTO toDTO(Curso curso) {
        CursoDTO dto = new CursoDTO();
        dto.setIdCurso(curso.getIdCurso());
        dto.setNombreCurso(curso.getNombreCurso());
        dto.setDescripcion(curso.getDescripcion());
        dto.setFechaCreacion(curso.getFechaCreacion());
        dto.setEstado(curso.getEstado());
        dto.setPrecio(curso.getPrecio());

        if (curso.getUsuario() != null) {
            dto.setUsuarioId(curso.getUsuario().getIdUser());
        }

        if (curso.getCategoria() != null) {
            // Crea el DTO simple que NO tiene el campo "links"
            CategoriaInfoDTO categoriaInfo = new CategoriaInfoDTO();
            categoriaInfo.setIdCategoria(curso.getCategoria().getIdCategoria());
            categoriaInfo.setNombreCate(curso.getCategoria().getNombreCate());
            dto.setCategoria(categoriaInfo); // Asigna el DTO simple
        }

        return dto;
    }

    private Curso toEntity(CursoDTO dto) {
        Curso curso = new Curso();

        if (dto.getIdCurso() != null) {
            curso.setIdCurso(dto.getIdCurso());
        }

        curso.setNombreCurso(dto.getNombreCurso());
        curso.setDescripcion(dto.getDescripcion());
        curso.setFechaCreacion(dto.getFechaCreacion() != null ? dto.getFechaCreacion() : LocalDate.now());
        curso.setEstado(dto.getEstado());
        curso.setPrecio(dto.getPrecio());

        if (dto.getCategoria() != null && dto.getCategoria().getIdCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoria().getIdCategoria())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró la categoría con el ID: " + dto.getCategoria().getIdCategoria()));
            curso.setCategoria(categoria);
        }

        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el usuario con el ID: " + dto.getUsuarioId()));
            curso.setUsuario(usuario);
        }

        return curso;
    }
}