package tablas;

public class Empleado {

	private int idEmpleado;
	private String nombreEmp;
	private Departamento departamento;

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getNombreEmp() {
		return nombreEmp;
	}

	public void setNombreEmp(String nombreEmp) {
		this.nombreEmp = nombreEmp;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Empleado(int idEmpleado, String nombreEmp, Departamento departamento) {
		super();
		this.idEmpleado = idEmpleado;
		this.nombreEmp = nombreEmp;
		this.departamento = departamento;
	}

	public Empleado() {
		super();
	}

	@Override
	public String toString() {
		return "Empleado [idEmpleado=" + idEmpleado + ", nombreEmp=" + nombreEmp + ", departamento=" + departamento
				+ "]";
	}
}
