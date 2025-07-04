package com.edutech.ubicacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.ubicacion.models.Provincia;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> 
{
    
}
