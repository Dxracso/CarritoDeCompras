package co.prueba.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import rx.Observable;
import rx.Subscription;

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
			ErrorGenerico erG = new ErrorGenerico("200", "No se encontro el cliente especificado", "ER-VEN-12", null);
			ManejadorErrores.logError(erG, this.getClass());
			return new ResponseEntity<Object>(erG, HttpStatus.CREATED);
		}
		for (DetalleVenta item : venta.getDetalleVenta()) {
			if (!productoRepository.existsById(item.getIdProducto().getIdProducto())) {
				ErrorGenerico erG = new ErrorGenerico("200",
						"No se encontro el Producto con el id: " + item.getIdProducto().getIdProducto(), "ER-VEN-12",
						null);
				ManejadorErrores.logError(erG, this.getClass());
				return new ResponseEntity<Object>(erG, HttpStatus.CREATED);
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
				// List<DetalleVenta> detalles = venta.getDetalleVenta();
				// venta.setDetalleVenta(null);
				ventaRepository.saveAndFlush(venta);
				// venta.setDetalleVenta(ventaTemp.getDetalleVenta());
				venta.getDetalleVenta().forEach(t -> t.setIdVenta(venta));
				detalleVentaRepository.saveAll(venta.getDetalleVenta());
				detalleVentaRepository.flush();
				return new ResponseEntity<Object>(new CompletadoGenerico("201", "OK"), HttpStatus.CREATED);
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
				/*
				 * Uso de Lambda para obtener detalles venta por ID_VENTA
				 */
				List<DetalleVenta> detalleVentas = detalleVentaRepository.findAll().stream()
						.filter(dv -> dv.getIdVenta().getIdVenta().equals(id)).collect(Collectors.toList());

				detalleVentas.forEach(d -> d.setIdVenta(null));// se nulea para evitar la Ciclicidad de la relacion
																// detalle_venta
				venta.setDetalleVenta(new ArrayList<>());
				venta.getDetalleVenta().addAll(detalleVentas);

				return new ResponseEntity<Object>(venta, HttpStatus.OK);
			} else {
				ErrorGenerico erG = new ErrorGenerico("200", "No Encontrado el venta de id: " + id, "ER-VEN-03", null);
				ManejadorErrores.logError(erG, this.getClass());
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
		try {// uso de lamda para encontrar los detalle venta
			List<Venta> ventas = ventaRepository.findAll();
			ventas.forEach(venta -> {

				venta.setDetalleVenta(detalleVentaRepository.findAll().stream()
						.filter(detalleVenta -> detalleVenta.getIdVenta().getIdVenta().equals(venta.getIdVenta()))
						.collect(Collectors.toList()));
			});

			return new ResponseEntity<Object>(ventas, HttpStatus.OK);

		} catch (Exception e) {
			ErrorGenerico erG = new ErrorGenerico("200", "Error al listar ventas", "ER-VEN-03", e.getMessage());
			ManejadorErrores.logError(erG);
			return new ResponseEntity<Object>(erG, HttpStatus.OK);
		}
	}

	@GetMapping("venta/{idVenta}")
	private Subscription getVentaDetalle(@PathVariable Long idVenta) {
		return Observable.just(detalleVentaRepository.findAllByIdVenta_IdVenta(idVenta)).subscribe();
	}

	@GetMapping("cliente/{idCliente}")
	private Subscription getVentaCliente(@PathVariable Long idCliente) {
		return Observable.just(detalleVentaRepository.findAllByIdCliente_idCliente(idCliente)).subscribe();
	}

}
