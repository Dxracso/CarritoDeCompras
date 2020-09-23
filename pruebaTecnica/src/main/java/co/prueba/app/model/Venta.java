package co.prueba.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Venta implements Serializable {
	private static final long serialVersionUID = 4845879058174609347L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idVenta;
	@ManyToOne(cascade = CascadeType.REMOVE)
	private Cliente idCliente;
	@Column(nullable = false)
	private Date fecha;
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<DetalleVenta> detalleVenta;

	public Venta() {
		super();
	}

	public Venta(Cliente idCliente, Date fecha) {
		super();
		this.idCliente = idCliente;
		this.fecha = fecha;
	}

	public Long getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(Long idVenta) {
		this.idVenta = idVenta;
	}

	public Cliente getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Cliente idCliente) {
		this.idCliente = idCliente;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<DetalleVenta> getDetalleVenta() {
		return detalleVenta;
	}

	public void setDetalleVenta(List<DetalleVenta> detalleVenta) {
		this.detalleVenta = detalleVenta;
	}

}
