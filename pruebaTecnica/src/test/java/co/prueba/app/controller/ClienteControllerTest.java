package co.prueba.app.controller;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import co.prueba.app.model.Cliente;
import co.prueba.app.repository.ClienteRepository;

@SpringBootTest
public class ClienteControllerTest {
	@Autowired
	private ClienteRepository clienteRepository;
	private ClienteController controller;
	private String nombreRandom;
	private String dni;
	private String tel;
	private String correo;

	@BeforeEach
	void init() {
		controller = new ClienteController(clienteRepository);
		nombreRandom = String.valueOf(Math.abs(Math.random() * 100000));
		dni = "din123";
		tel = "Tel123";
		correo = "Email@ejemplo.com";

	}

	@Test
	void crearCliente() {
		Cliente c = new Cliente(nombreRandom, nombreRandom, dni, tel, correo);
		assertEquals(HttpStatus.CREATED, controller.registrar(c).getStatusCode());

	}

	@AfterEach
	void revicion() {// busco que solo encuentre un item, el que se guardo
		assertEquals(1L,
				clienteRepository.findAll().stream()
						.filter(cli -> cli.getApellido().equals(nombreRandom) && cli.getNombre().equals(nombreRandom)
								&& cli.getTelefono().equals(tel) && cli.getEmail().equals(correo)
								&& cli.getDni().equals(dni) && cli.getIdCliente() != null)
						.count());

	}
}
