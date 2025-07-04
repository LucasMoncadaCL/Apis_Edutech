package com.edutech.pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.pagos.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>
{

}
