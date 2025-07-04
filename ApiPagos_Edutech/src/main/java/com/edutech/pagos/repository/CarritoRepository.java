package com.edutech.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.pagos.models.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Integer>
{

}
