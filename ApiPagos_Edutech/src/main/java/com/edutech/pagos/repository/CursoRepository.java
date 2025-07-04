package com.edutech.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.pagos.models.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Integer>
{

}
