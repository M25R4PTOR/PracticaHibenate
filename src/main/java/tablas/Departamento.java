package tablas;

import java.util.HashSet;
import java.util.Set;

public class Departamento {

	private int idDepartamento;
	private String nombreDep;
	private Set<Empleado> empleado = new HashSet<Empleado>();

	public int getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(int idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getNombreDep() {
		return nombreDep;
	}

	public void setNombreDep(String nombreDep) {
		this.nombreDep = nombreDep;
	}

	public Set<Empleado> getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Set<Empleado> empleado) {
		this.empleado = empleado;
	}

	public Departamento(int idDepartamento, String nombreDep, Set<Empleado> empleado) {
		super();
		this.idDepartamento = idDepartamento;
		this.nombreDep = nombreDep;
		this.empleado = empleado;
	}

	public Departamento() {
		super();
	}

	@Override
	public String toString() {
		return "Departamento [idDepartamento=" + idDepartamento + ", nombreDep=" + nombreDep + ", empleado=" + empleado
				+ "]";
	}

}
