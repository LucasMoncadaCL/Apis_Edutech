package com.edutech.pagos.services;

import com.edutech.pagos.dto.*;
import com.edutech.pagos.models.*;
import com.edutech.pagos.repository.CarritoRepository;
import com.edutech.pagos.repository.CuponRepository;
import com.edutech.pagos.repository.PagoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PagoService {

    @Autowired
    private PagoRepository repository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CuponRepository cuponRepository;

    // Guarda un nuevo pago, enlazando el carrito y el cupón.
    public PagoDTO guardar(PagoDTO dto) {
        Pago pago = toEntity(dto);
        Pago saved = repository.save(pago);
        return toDTO(saved);
    }

    // Devuelve una lista de todos los pagos.
    public List<PagoDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Busca un pago específico por su ID.
    public Optional<PagoDTO> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    // Actualiza los datos de un pago existente.
    public Optional<PagoDTO> actualizar(Integer id, PagoDTO dto) {
        return repository.findById(id)
                .map(pago -> {
                    // Actualiza los campos simples
                    pago.setFormaPago(dto.getFormaPago());
                    pago.setFechaPago(dto.getFechaPago());
                    pago.setMontoTotal(dto.getMontoTotal());
                    pago.setEstadoPago(dto.getEstadoPago());
                    pago.setDescripcion(dto.getDescripcion());
                    
                    // Reutiliza la lógica para actualizar las relaciones
                    toEntityRelationships(pago, dto);
                    
                    Pago actualizado = repository.save(pago);
                    return toDTO(actualizado);
                });
    }

    // Elimina un pago de la base de datos.
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // --- Métodos auxiliares---

    // Método central para asignar relaciones a una entidad Pago desde un DTO.
    private void toEntityRelationships(Pago pago, PagoDTO dto) {
        if (dto.getCarrito() != null && dto.getCarrito().getIdInscripcion() != null) {
            Carrito carrito = carritoRepository.findById(dto.getCarrito().getIdInscripcion())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el Carrito con ID: " + dto.getCarrito().getIdInscripcion()));
            pago.setCarrito(carrito);
        }

        if (dto.getCupon() != null && dto.getCupon().getIdCupon() != null) {
            Cupon cupon = cuponRepository.findById(dto.getCupon().getIdCupon())
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró el Cupón con ID: " + dto.getCupon().getIdCupon()));
            pago.setCupon(cupon);
        } else {
            pago.setCupon(null); // Permite pagos sin cupón.
        }
    }

    // Convierte una entidad Pago a un DTO, incluyendo sus relaciones.
    private PagoDTO toDTO(Pago pago) {
        PagoDTO dto = new PagoDTO();
        dto.setIdPago(pago.getIdPago());
        dto.setFormaPago(pago.getFormaPago());
        dto.setFechaPago(pago.getFechaPago());
        dto.setMontoTotal(pago.getMontoTotal());
        dto.setEstadoPago(pago.getEstadoPago());
        dto.setDescripcion(pago.getDescripcion());

        if (pago.getCupon() != null) {
            dto.setCupon(toCuponDTO(pago.getCupon()));
        }

        if (pago.getCarrito() != null) {
            dto.setCarrito(toCarritoInfoDTO(pago.getCarrito()));
        }
        
        return dto;
    }

    // Convierte un DTO a una entidad Pago, incluyendo sus relaciones.
    private Pago toEntity(PagoDTO dto) {
        Pago pago = new Pago();
        pago.setIdPago(dto.getIdPago());
        pago.setFormaPago(dto.getFormaPago());
        pago.setFechaPago(dto.getFechaPago() != null ? dto.getFechaPago() : LocalDate.now());
        pago.setMontoTotal(dto.getMontoTotal());
        pago.setEstadoPago(dto.getEstadoPago());
        pago.setDescripcion(dto.getDescripcion());
        
        // Reutiliza la lógica para asignar las relaciones.
        toEntityRelationships(pago, dto);
        
        return pago;
    }
    
    // --- Métodos de conversión para los DTOs anidados ---

    private CuponDTO toCuponDTO(Cupon cupon) {
        CuponDTO dto = new CuponDTO();
        dto.setIdCupon(cupon.getIdCupon());
        dto.setCodigo(cupon.getCodigo());
        dto.setDescuento(cupon.getDescuento());
        dto.setValidoHasta(cupon.getValidoHasta());
        return dto;
    }

    private CarritoInfoDTO toCarritoInfoDTO(Carrito carrito) {
        CarritoInfoDTO dto = new CarritoInfoDTO();
        dto.setIdInscripcion(carrito.getIdInscripcion());
        dto.setFechaInscripcion(carrito.getFechaInscripcion());
        if (carrito.getUsuario() != null) {
            dto.setUsuario(toUsuarioInfoDTO(carrito.getUsuario()));
        }
        if (carrito.getCurso() != null) {
            dto.setCurso(toCursoInfoDTO(carrito.getCurso()));
        }
        return dto;
    }

    private UsuarioInfoDTO toUsuarioInfoDTO(Usuario usuario) {
        UsuarioInfoDTO dto = new UsuarioInfoDTO();
        dto.setIdUser(usuario.getIdUser());
        // Se omiten datos sensibles como el email aquí, pero puedes agregarlos si es necesario.
        return dto;
    }

    private CursoInfoDTO toCursoInfoDTO(Curso curso) {
        CursoInfoDTO dto = new CursoInfoDTO();
        dto.setIdCurso(curso.getIdCurso());
        dto.setNombreCurso(curso.getNombreCurso());
        dto.setPrecio(curso.getPrecio());
        return dto;
    }
}
