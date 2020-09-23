package co.prueba.app.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import co.prueba.app.model.Cliente;
import co.prueba.app.model.DetalleVenta;
import co.prueba.app.model.Producto;
import co.prueba.app.model.Venta;
import co.prueba.app.repository.ClienteRepository;
import co.prueba.app.repository.DetalleVentaRepository;
import co.prueba.app.repository.ProductoRepository;
import co.prueba.app.repository.VentaRepository;

@SpringBootTest
public class VentaControllerTest {
	@Autowired
	private ProductoRepository productoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private VentaRepository ventaRepository;
	@Autowired
	private DetalleVentaRepository detalleVentaRepository;
	private VentaController controller;
	private String nombreProductoRandom;
	private Float precioProductoRandom;
	private String nombreClienteRandom;
	private String dniCliente;
	private String telCliente;
	private String correoCliente;

	@BeforeEach
	void init() {
		controller = new VentaController(ventaRepository, detalleVentaRepository, clienteRepository,
				productoRepository);
		nombreClienteRandom = String.valueOf(Math.abs(Math.random() * 100000));
		nombreProductoRandom = String.valueOf(Math.abs(Math.random() * 100000));
		precioProductoRandom = (float) (Math.random() * 100000);
		nombreClienteRandom = String.valueOf(Math.abs(Math.random() * 100000));
		dniCliente = "dni123";
		telCliente = "tel123";
		correoCliente = "correo123";
	}

	@Test
	void crearVenta() {
		productoRepository.save(new Producto(nombreProductoRandom, precioProductoRandom));
		clienteRepository
				.save(new Cliente(nombreClienteRandom, nombreProductoRandom, dniCliente, telCliente, correoCliente));
		Venta venta = new Venta();
		venta.setFecha(new Date());
		venta.setIdCliente(new Cliente(1L));

		DetalleVenta dv = new DetalleVenta();
		dv.setIdProducto(new Producto(1L));
		venta.setDetalleVenta(new ArrayList<>());
		venta.getDetalleVenta().add(dv);

		assertEquals(HttpStatus.CREATED, controller.registrar(venta).getStatusCode());

		buscarVenta();
	}

	void buscarVenta() {
		Venta v = (Venta) controller.consulta(1L).getBody();
		assertEquals(nombreClienteRandom, v.getIdCliente().getNombre());
		assertEquals(1, v.getDetalleVenta().size());
		assertEquals(nombreProductoRandom, v.getDetalleVenta().get(0).getIdProducto().getNombre());
	}

}
