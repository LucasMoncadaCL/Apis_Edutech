package com.edutech.usuarios.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edutech.usuarios.dto.*;
import com.edutech.usuarios.models.*;
import com.edutech.usuarios.repository.*;

import lombok.RequiredArgsConstructor;

@Service  // Indica que esta clase es un servicio de Spring
@RequiredArgsConstructor  // Genera constructor con argumentos para las dependencias final
@Transactional  // Todas las operaciones serán transaccionales
public class UsuarioService {

    // Repositorios inyectados para acceso a datos
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;  // Para encriptar contraseñas
    private final RolRepository rolRepository;
    private final ComunaRepository comunaRepository;

    /**
     * Obtiene todos los usuarios y los convierte a DTOs
     * @return Lista de UsuarioDTO
     */
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepo.findAll().stream()  // Obtiene todos los usuarios como stream
            .map(this::convertirADto)  // Convierte cada usuario a DTO
            .collect(Collectors.toList());  // Convierte el stream a lista
    }

    /**
     * Crea un nuevo usuario en el sistema
     * @param req Datos para crear el usuario
     * @return UsuarioDTO del usuario creado
     */
    public UsuarioDTO crearUsuario(CrearUsuarioRequest req) {
        validarRequestCreacion(req);

        Rol rol = rolRepository.findById(req.getRolId())
            .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + req.getRolId()));

        Comuna comuna = comunaRepository.findById(req.getComunaId())
            .orElseThrow(() -> new RuntimeException("Comuna no encontrada con ID: " + req.getComunaId()));

        Usuario usuario = new Usuario();
        usuario.setNombre(req.getNombre());
        usuario.setCorreo(req.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(req.getContrasena()));
        usuario.setFechaRegistro(req.getFechaRegistro()); // Asegúrate de que sea coherente con el DTO
        usuario.setEstadoUser(req.getEstadoUser());
        usuario.setRol(rol);
        usuario.setComuna(comuna);

        Usuario usuarioGuardado = usuarioRepo.save(usuario);
        return convertirADto(usuarioGuardado);
    }


    /**
     * Busca un usuario por su ID
     * @param id ID del usuario a buscar
     * @return UsuarioDTO del usuario encontrado
     */
    public UsuarioDTO buscarUsuarioPorId(Integer id) {
        // Busca el usuario o lanza excepción si no existe
        Usuario usuario = usuarioRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return convertirADto(usuario);
    }

    /**
     * Actualiza los datos de un usuario existente
     * @param id ID del usuario a actualizar
     * @param dto Datos actualizados
     * @return UsuarioDTO con los datos actualizados
     */
    public UsuarioDTO actualizarUsuario(Integer id, UsuarioDTO dto) {
        // Busca el usuario existente
        Usuario usuario = usuarioRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Obtiene el nuevo rol
        Rol rol = rolRepository.findById(dto.getRolId())
            .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + dto.getRolId()));

        // Actualiza los campos permitidos
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setRol(rol);

        // Guarda los cambios y retorna el DTO actualizado
        Usuario usuarioActualizado = usuarioRepo.save(usuario);
        return convertirADto(usuarioActualizado);
    }

    /**
     * Elimina un usuario del sistema
     * @param id ID del usuario a eliminar
     */
    public void eliminarUsuario(Integer id) {
        // Verifica que el usuario exista antes de eliminarlo
        if (!usuarioRepo.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepo.deleteById(id);
    }

    /**
     * Convierte una entidad Usuario a UsuarioDTO
     * @param usuario Entidad a convertir
     * @return DTO convertido
     */
    private UsuarioDTO convertirADto(Usuario usuario) {
        return UsuarioDTO.builder()
            .id(usuario.getId())
            .nombre(usuario.getNombre())
            .correo(usuario.getCorreo())
            .rolId(usuario.getRol().getIdRol())
            .build();
    }

    /**
     * Valida los datos de creación de usuario
     * @param req Datos a validar
     * @throws IllegalArgumentException si algún campo requerido es inválido
     */
    private void validarRequestCreacion(CrearUsuarioRequest req) {
        if (req.getContrasena() == null || req.getContrasena().isBlank()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }
        if (req.getCorreo() == null || req.getCorreo().isBlank()) {
            throw new IllegalArgumentException("El correo es requerido");
        }
        if (req.getNombre() == null || req.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es requerido");
        }
        if (req.getFechaRegistro() == null) {
            throw new IllegalArgumentException("La fecha de registro es requerida");
        }
        if (req.getRolId() == null) {
            throw new IllegalArgumentException("El ID del rol es requerido");
        }
        if (req.getComunaId() == null) {
            throw new IllegalArgumentException("El ID de la comuna es requerido");
        }
    }

}