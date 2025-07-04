package com.edutech.pagos.services;

import com.edutech.pagos.dto.CuponDTO;
import com.edutech.pagos.models.Cupon;
import com.edutech.pagos.repository.CuponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CuponService {

    @Autowired
    private CuponRepository repository;

    // Guarda un nuevo cupón en la base de datos.
    public CuponDTO guardar(CuponDTO dto) {
        Cupon cupon = toEntity(dto);
        Cupon saved = repository.save(cupon);
        return toDTO(saved);
    }

    // Devuelve una lista de todos los cupones existentes.
    public List<CuponDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Busca un cupón específico por su ID.
    public Optional<CuponDTO> obtenerPorId(Integer id) {
        return repository.findById(id)
                .map(this::toDTO);
    }

    // Actualiza los datos de un cupón que ya existe.
    public Optional<CuponDTO> actualizar(Integer id, CuponDTO dto) {
        return repository.findById(id)
                .map(cupon -> {
                    cupon.setCodigo(dto.getCodigo());
                    cupon.setDescuento(dto.getDescuento());
                    cupon.setValidoHasta(dto.getValidoHasta());
                    Cupon actualizado = repository.save(cupon);
                    return toDTO(actualizado);
                });
    }

    // Elimina un cupón de la base de datos por su ID.
    public boolean eliminar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    // Convierte una entidad (de la BD) a un DTO (para la API).
    private CuponDTO toDTO(Cupon cupon) {
        CuponDTO dto = new CuponDTO();
        dto.setIdCupon(cupon.getIdCupon());
        dto.setCodigo(cupon.getCodigo());
        dto.setDescuento(cupon.getDescuento());
        dto.setValidoHasta(cupon.getValidoHasta());
        return dto;
    }

    // Convierte un DTO (de la API) a una entidad (para la BD).
    private Cupon toEntity(CuponDTO dto) {
        Cupon cupon = new Cupon();
        cupon.setIdCupon(dto.getIdCupon());
        cupon.setCodigo(dto.getCodigo());
        cupon.setDescuento(dto.getDescuento());
        cupon.setValidoHasta(dto.getValidoHasta());
        return cupon;
    }
}
