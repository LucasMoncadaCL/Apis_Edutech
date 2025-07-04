package com.edutech.cursosycontenidos.repository;

import com.edutech.cursosycontenidos.models.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuloRepository extends JpaRepository<Modulo, Integer> {

    List<Modulo> findByCursoIdCurso(Integer cursoId);
}