package com.edutech.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.pagos.models.Cupon;

@Repository
public interface CuponRepository extends JpaRepository<Cupon, Integer>
{

}
