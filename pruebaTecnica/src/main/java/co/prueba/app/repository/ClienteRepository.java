package co.prueba.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.prueba.app.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {}
