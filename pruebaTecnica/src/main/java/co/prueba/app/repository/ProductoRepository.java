package co.prueba.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.prueba.app.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {}
