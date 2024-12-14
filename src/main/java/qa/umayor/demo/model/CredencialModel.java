package qa.umayor.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "desarrollo", name = "tbl_password")
public class CredencialModel {
	
	@Id
	private String rut;
		
	private String password;
	
	private String estado;

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getClave() {
		return password;
	}

	public void setClave(String clave) {
		this.password = clave;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}