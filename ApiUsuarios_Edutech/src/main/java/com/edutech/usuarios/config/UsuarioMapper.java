package com.edutech.usuarios.config;

import org.springframework.stereotype.Component;
import com.edutech.usuarios.dto.UsuarioDTO;
import com.edutech.usuarios.models.Usuario;

@Component
public class UsuarioMapper {
    public UsuarioDTO usuarioToDto(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setRolId(usuario.getRol().getIdRol());
        return dto;
    }
}