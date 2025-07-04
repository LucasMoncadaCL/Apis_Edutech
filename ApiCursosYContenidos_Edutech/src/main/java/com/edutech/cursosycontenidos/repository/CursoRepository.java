package com.edutech.cursosycontenidos.repository;

import com.edutech.cursosycontenidos.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer> {

    List<Curso> findByCategoriaIdCategoria(Integer categoriaId);

    List<Curso> findByUsuarioIdUser(Integer usuarioId);
}