package co.prueba.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import co.prueba.app.controller.ClienteController;
import co.prueba.app.model.Cliente;

@SpringBootTest
class PruebaTecnicaApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void crearCliente() {
		//envio un cliente
		Cliente cliente=new Cliente("Oscar","Ariza","1033781","3138980674","Ooscar1995@gmail.com");
		
	}

}
