package co.prueba.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import co.prueba.app.ManejadorErrores;
import co.prueba.app.model.CompletadoGenerico;
import co.prueba.app.model.ErrorGenerico;
import co.prueba.app.model.Producto;
import co.prueba.app.repository.ProductoRepository;

@RestController
@RequestMapping("/productos")
public class ProductoController {

	private ProductoRepository productoRepository;

	@Autowired
	public ProductoController(ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}

	// Creacion
	@PostMapping(value = { "/registro", "/registrar",
			"/r" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> registrar(@RequestBody Producto producto) {
		try {
			productoRepository.save(producto);
		} catch (Exception e) {
			ErrorGenerico erG = new ErrorGenerico("200", "Error al guardar Producto", "ER-PRO-01", e.getMessage());
			ManejadorErrores.logError(erG);
			return new ResponseEntity<Object>(erG, HttpStatus.OK);
		}
		return new ResponseEntity<Object>(new CompletadoGenerico("200", "OK"), HttpStatus.CREATED);
	}

	// Actualizacion por id
	@PutMapping(value = { "/update/{id}", "/actualizar/{id}",
			"/a/{id}" }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> actualizar(@RequestBody Producto producto, @PathVariable Long id) {
		try {
			if (productoRepository.existsById(id)) {
				Producto updateProd = productoRepository.getOne(id);
				updateProd.setNombre(producto.getNombre());
				updateProd.setPrecio(producto.getPrecio());
				productoRepository.save(updateProd);
				return new ResponseEntity<Object>(new CompletadoGenerico("200", "OK"), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<Object>(
						new CompletadoGenerico("200", "No Encontrado el producto de id: " + id), HttpStatus.OK);
			}
		} catch (Exception e) {
			ErrorGenerico erG = new ErrorGenerico("200", "Error al guardar Producto", "ER-PRO-02", e.getMessage());
			ManejadorErrores.logError(erG);
			return new ResponseEntity<Object>(erG, HttpStatus.OK);
		}

	}

	// Consulta Por id
	@GetMapping({ "/{id}" })
	public ResponseEntity<Object> consulta(@PathVariable Long id) {
		try {
			if (productoRepository.existsById(id)) {
				Producto producto = productoRepository.findById(id).get();
				return new ResponseEntity<Object>(producto, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(
						new CompletadoGenerico("200", "No Encontrado el producto de id: " + id), HttpStatus.OK);
			}
		} catch (Exception e) {
			ErrorGenerico erG = new ErrorGenerico("200", "Error al consultar producto de id: " + id, "ER-PRO-03",
					e.getMessage());
			ManejadorErrores.logError(erG);
			return new ResponseEntity<Object>(erG, HttpStatus.OK);
		}
	}

	// Consulta de todos los items
	@GetMapping({ "/", "" })
	public ResponseEntity<Object> listarProductos() {
		try {
			List<Producto> productos = productoRepository.findAll();
			return new ResponseEntity<Object>(productos, HttpStatus.OK);

		} catch (Exception e) {
			ErrorGenerico erG = new ErrorGenerico("200", "Error al listar productos", "ER-PRO-04", e.getMessage());
			ManejadorErrores.logError(erG);
			return new ResponseEntity<Object>(erG, HttpStatus.OK);
		}
	}

	// Eliminacion por id
	@DeleteMapping({ "/borrar/{id}", "/delete/{id}", "/d/{id}", "/b/{id}" })
	public ResponseEntity<Object> borrar(@PathVariable Long id) {
		try {
			if (productoRepository.existsById(id)) {
				productoRepository.deleteById(id);
				return new ResponseEntity<Object>("Borrado el id: " + id, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(
						new CompletadoGenerico("200",
								"No Encontrado el consultar el producto de id: " + id + " No se a borrado nada"),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			ErrorGenerico erG = new ErrorGenerico("200", "Error al borrar Producto", "ER-PRO-05", e.getMessage());
			ManejadorErrores.logError(erG);
			return new ResponseEntity<Object>(erG, HttpStatus.OK);
		}
	}
}
