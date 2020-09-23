package co.prueba.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.prueba.app.model.DetalleVenta;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
	List<DetalleVenta> findAllByIdVenta_IdVenta(Long idVenta);

    @Query("select dv from DetalleVenta dv inner join Venta v on v.idCliente.idCliente=?1 ")
    List<DetalleVenta> findAllByIdCliente_idCliente(Long idCliente);
}
