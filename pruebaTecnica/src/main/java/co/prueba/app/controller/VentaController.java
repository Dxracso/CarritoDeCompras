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
import co.prueba.app.model.DetalleVenta;
import co.prueba.app.model.Venta;
import co.prueba.app.model.dto.CompletadoGenerico;
import co.prueba.app.model.dto.ErrorGenerico;
import co.prueba.app.repository.ClienteRepository;
import co.prueba.app.repository.DetalleVentaRepository;
import co.prueba.app.repository.ProductoRepository;
import co.prueba.app.repository.VentaRepository;

@RestController
@RequestMapping("/ventas")
public class VentaController {

	private VentaRepository ventaRepository;
	private DetalleVentaRepository detalleVentaRepository;
	private ClienteRepository clienteRepository;
	private ProductoRepository productoRepository;

	@Autowired
	public VentaController(VentaRepository ventaRepository, DetalleVentaRepository detalleVentaRepository,
			ClienteRepository clienteRepository, ProductoRepository productoRepository) {
		super();
		this.ventaRepository = ventaRepository;
		this.detalleVentaRepository = detalleVentaRepository;
		this.clienteRepository = clienteRepository;
		this.productoRepository = productoRepository;
	}

	private ResponseEntity<Object> prepareRegistrar(Venta venta) {
		if (!clienteRepository.existsById(venta.getIdCliente().getIdCliente())) {
			return new ResponseEntity<Object>(new ErrorGenerico("200", "No se encontro el cliente especificado", "ER-VEN-12", null)
					,HttpStatus.CREATED);
		}
		for (DetalleVenta item : venta.getDetalleVenta()) {
			if (!productoRepository.existsById(item.getIdProducto().getIdProducto())) {
				return new ResponseEntity<Object>(new ErrorGenerico("200", "No se encontro el Producto con el id: " + item.getIdProducto().getIdProducto(), "ER-VEN-12", null)
						,HttpStatus.CREATED);
			}
		}
		return null;
	}

	// Creacion
	@PostMapping(value = { "/registro", "/registrar",
			"/r" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registrar(@RequestBody Venta venta) {
		try {
			ResponseEntity<Object> responseTemp = prepareRegistrar(venta);
			if (responseTemp == null) {
				venta.setFecha(new Date());// retirar si se requiere que sea una fecha distinta a la actual
				Venta ventaTemp = new Venta();
				ventaTemp.setDetalleVenta(venta.getDetalleVenta());
				venta.setDetalleVenta(null);
				ventaRepository.saveAndFlush(venta);
				venta.setDetalleVenta(ventaTemp.getDetalleVenta());
				venta.getDetalleVenta().forEach(t -> t.setIdVenta(venta));
				detalleVentaRepository.saveAll(venta.getDetalleVenta());
				return new ResponseEntity<Object>(new CompletadoGenerico("200", "OK"), HttpStatus.CREATED);
			} else {// retorna el pocible error especifico//si no existen los registros necesarios
				return responseTemp;
			}
		} catch (Exception e) {
			ErrorGenerico erG = new ErrorGenerico("200", "Error al guardar venta", "ER-VEN-10", e.getMessage());
			ManejadorErrores.logError(erG);
			return new ResponseEntity<Object>(erG, HttpStatus.OK);
		}
	}

	// Consulta Por id
	@GetMapping({ "/{id}" })
	public ResponseEntity<Object> consulta(@PathVariable Long id) {
		try {
			if (ventaRepository.existsById(id)) {
				Venta venta = ventaRepository.findById(id).get();
				return new ResponseEntity<Object>(venta, HttpStatus.OK);
			} else {
				ErrorGenerico erG = new ErrorGenerico("200", "No Encontrado el venta de id: ", "ER-VEN-03", null);
				return new ResponseEntity<Object>(erG, HttpStatus.OK);
			}
		} catch (Exception e) {
			ErrorGenerico erG = new ErrorGenerico("200", "Error al consultar venta de id: " + id, "ER-VEN-02",
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
