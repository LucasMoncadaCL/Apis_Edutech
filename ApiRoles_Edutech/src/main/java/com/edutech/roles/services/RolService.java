package com.edutech.roles.services;

import com.edutech.roles.dto.RolDTO;
import com.edutech.roles.models.Rol;
import com.edutech.roles.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RolService {

    @Autowired
    private RolRepository repository;

    //Para Guardar los Roles nuevos
    public RolDTO guardar(RolDTO dto) {
        Rol rol = toEntity(dto);
        Rol saved = repository.save(rol);
        return toDTO(saved);
    }

    //Para obtener una Lista de RolDTO con todos los roles Existentes
    public List<RolDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    //Buscar rol por ID
    public Optional<RolDTO> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO);
    }


    //Actualiza los datos de un rol Existente
    public Optional<RolDTO> actualizar(Integer id, RolDTO dto) {
        return repository.findById(id)
                .map(rol -> {
                    rol.setNombreRol(dto.getNombreRol());
                    Rol actualizado = repository.save(rol);
                    return toDTO(actualizado);
                });
    }


    //Para Eliminar un rol de la base de datos
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- Métodos auxiliares---

    //Convierte una entidad Rol a un DTO
    private RolDTO toDTO(Rol rol) {
        RolDTO dto = new RolDTO();
        dto.setIdRol(rol.getIdRol());
        dto.setNombreRol(rol.getNombreRol());
        return dto;
    }


    //Convierte un DTO a una entidad Rol
    private Rol toEntity(RolDTO dto) {
        Rol rol = new Rol();
        rol.setIdRol(dto.getIdRol());
        rol.setNombreRol(dto.getNombreRol());
        return rol;
    }

}
