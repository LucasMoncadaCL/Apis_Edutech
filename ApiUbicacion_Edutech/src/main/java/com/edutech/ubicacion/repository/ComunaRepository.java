package com.edutech.ubicacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.ubicacion.models.Comuna;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer>
{

}
