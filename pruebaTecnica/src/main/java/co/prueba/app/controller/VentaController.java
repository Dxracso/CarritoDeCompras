package co.prueba.app.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import co.prueba.app.ManejadorErrores;
import co.prueba.app.model.ErrorGenerico;
import co.prueba.app.model.Venta;
import co.prueba.app.repository.DetalleVentaRepository;
import co.prueba.app.repository.VentaRepository;

@RestController
@RequestMapping("/ventas")
public class VentaController {

	private VentaRepository ventaRepository;
	private DetalleVentaRepository detalleVentaRepository;

	@Autowired
	public VentaController(VentaRepository ventaRepository, DetalleVentaRepository detalleVentaRepository) {
		this.ventaRepository = ventaRepository;
		this.detalleVentaRepository = detalleVentaRepository;
	}

	// Creacion
	@PostMapping(value = { "/registro", "/registrar",
			"/r" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registrar(@RequestBody Venta venta) {
		try {
			venta.setFecha(new Date());// retirar si se requiere que sea una fecha distinta a la actual
			Venta ventaTemp = new Venta();
			ventaTemp.setDetalleVenta(venta.getDetalleVenta());
			venta.setDetalleVenta(null);
			ventaRepository.saveAndFlush(venta);
			venta.setDetalleVenta(ventaTemp.getDetalleVenta());
			venta.getDetalleVenta().forEach(t -> t.setIdVenta(venta));
			detalleVentaRepository.saveAll(venta.getDetalleVenta());
			
		} catch (Exception e) {
			ErrorGenerico erG = new ErrorGenerico("200", "Error al guardar venta", "ER-VEN-01", e.getMessage());
			ManejadorErrores.logError(erG);
			return new ResponseEntity<Object>(erG, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("OK", HttpStatus.CREATED);
	}

	// Consulta Por id
	@GetMapping({ "/{id}" })
	public ResponseEntity<Object> consulta(@PathVariable Long id) {
		try {
			if (ventaRepository.existsById(id)) {
				Venta venta = ventaRepository.findById(id).get();
				return new ResponseEntity<Object>(venta, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>("No Encontrado el venta de id: " + id, HttpStatus.OK);
			}
		} catch (Exception e) {
			ErrorGenerico erG = new ErrorGenerico("200", "Error al consultar venta de id" + id, "ER-VEN-02",
					e.getMessage());
			ManejadorErrores.logError(erG);
			return new ResponseEntity<Object>(erG, HttpStatus.OK);
		}
	}

	// Consulta de todos los items
	@GetMapping({ "/", "" })
	public ResponseEntity<Object> listarventas() {
		try {
			List<Venta> ventas = ventaRepository.findAll();
			return new ResponseEntity<Object>(ventas, HttpStatus.OK);

		} catch (Exception e) {
			ErrorGenerico erG = new ErrorGenerico("200", "Error al listar ventas", "ER-VEN-03", e.getMessage());
			ManejadorErrores.logError(erG);
			return new ResponseEntity<Object>(erG, HttpStatus.OK);
		}
	}

}
