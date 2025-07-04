package com.edutech.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.usuarios.models.Provincia;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> 
{
    
}
