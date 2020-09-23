package co.prueba.app.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import co.prueba.app.model.Producto;
import co.prueba.app.repository.ProductoRepository;

@SpringBootTest
public class ProductoControllerTest {
	@Autowired
	private ProductoRepository productoRepository;
	private ProductoController controller;
	private String nombreRandom;
	private Float precioRandom;

	@BeforeEach
	void init() {
		controller = new ProductoController(productoRepository);
		nombreRandom = String.valueOf(Math.abs(Math.random() * 100000));
		precioRandom = (float) (Math.random() * 100000);

	}

	@Test
	void crearProducto() {
		Producto p = new Producto(nombreRandom, precioRandom);
		assertEquals(HttpStatus.CREATED, controller.registrar(p).getStatusCode());
		buscarProducto();
		ActualizarProducto();
		borrar();
	}

	void buscarProducto() {
		List<Producto> body = (List<Producto>) controller.listarProductos().getBody();
		List<Producto> productos = body;
		assertEquals(1, productos.size());
		assertEquals(1, productos.stream().filter(// se encuenta un registro
				p -> p.getNombre().equals(nombreRandom) && p.getPrecio() == precioRandom && p.getIdProducto() != null)
				.count());
	}

	void ActualizarProducto() {
		String nombreRandomNuevo = String.valueOf(Math.abs(Math.random() * 100000));
		assertEquals(HttpStatus.OK,
				controller.actualizar(new Producto(nombreRandomNuevo, precioRandom), 1L).getStatusCode());
		Producto p = productoRepository.findById(1L).get();
		assertEquals((Float) p.getPrecio(), (Float) precioRandom);
		assertNotEquals(p.getNombre(), nombreRandom);
	}

	void borrar() {
		Producto p = productoRepository.findById(1L).get();
		assertNotEquals(p, null);
		Long cantidadInicial = productoRepository.count();
		assertEquals(HttpStatus.OK, controller.borrar(1L).getStatusCode());
		Long cantidadFinal = productoRepository.count();
		assertEquals((Long) cantidadFinal, (Long) (cantidadInicial - 1));
	}

}
