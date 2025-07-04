package com.edutech.cursosycontenidos.repository;

import com.edutech.cursosycontenidos.models.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenidoRepository extends JpaRepository<Contenido, Integer> {

    List<Contenido> findByModuloIdModulo(Integer moduloId);
}