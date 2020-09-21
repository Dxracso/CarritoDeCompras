package co.prueba.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import co.prueba.app.ManejadorErrores;
import co.prueba.app.model.Cliente;
import co.prueba.app.model.CompletadoGenerico;
import co.prueba.app.model.ErrorGenerico;
import co.prueba.app.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	private ClienteRepository clienteRepository;

	@Autowired
	public ClienteController(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	@PostMapping(value = {"/registro","/registrar","/R"},consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registrar(@RequestBody Cliente cliente) {
		try {
			clienteRepository.save(cliente);
		} catch (Exception e) {
			ErrorGenerico erG=new ErrorGenerico("200","Error al guardar Cliente","ER-CLI-01",e.getMessage());
			ManejadorErrores.logError(erG);
			return new ResponseEntity<Object>(erG,HttpStatus.OK);
		}
		return new ResponseEntity<Object>(new CompletadoGenerico("200", "OK"),HttpStatus.CREATED);
	}

}
