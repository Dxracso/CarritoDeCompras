package co.prueba.app.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class DetalleVenta implements Serializable {

	private static final long serialVersionUID = -2419027516999422225L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idDetalleVenta;
	@XmlTransient
	@ManyToOne(cascade = CascadeType.REMOVE)
	private Venta idVenta;
	@ManyToOne(cascade = CascadeType.REMOVE)
	private Producto idProducto;
	

	public DetalleVenta() {
		super();
	}

	public DetalleVenta(Venta idVenta, Producto idProducto) {
		super();
		this.idVenta = idVenta;
		this.idProducto = idProducto;
	}

	public DetalleVenta(Long idDetalleVenta) {
		super();
		this.idDetalleVenta = idDetalleVenta;
	}

	public Long getIdDetalleVenta() {
		return idDetalleVenta;
	}

	public void setIdDetalleVenta(Long idDetalleVenta) {
		this.idDetalleVenta = idDetalleVenta;
	}

	public Venta getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Venta idVenta) {
		this.idVenta = idVenta;
	}

	public Producto getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Producto idProducto) {
		this.idProducto = idProducto;
	}

}
