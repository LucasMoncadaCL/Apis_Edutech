package com.edutech.cursosycontenidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.cursosycontenidos.models.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>
{

}
