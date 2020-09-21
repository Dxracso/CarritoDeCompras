package co.prueba.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.prueba.app.model.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {}
