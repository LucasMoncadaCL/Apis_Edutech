package com.edutech.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.pagos.models.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer>
{

}