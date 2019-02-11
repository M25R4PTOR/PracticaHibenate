package tablas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Menu {

	private static SessionFactory factory;
	private static ServiceRegistry serviceRegistry;

	public static void main(String[] args) {
		Configuration config = new Configuration();
		config.configure();
		config.addAnnotatedClass(Departamento.class);
		config.addResource("Departamento.hbm.xml");
		config.addAnnotatedClass(Empleado.class);
		config.addResource("Empleado.hbm.xml");
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		factory = config.buildSessionFactory(serviceRegistry);

		menu();
	}

	private static void menu() {
		String eleccion = "5";
		do {
			eleccion = (String) JOptionPane.showInputDialog(null,
					"---Menu---\n1. Mostrar Empleados\n2. Insertar Empleado\n3. Actualizar Empleado\n4. Borrar Empleado\n5. Mostrar Departamentos\n6. Insertar Departamento\n7. Actualizar Departamento\n8. Borrar Departamento\n9. EjemploInsertarEmpleadoYDepartamentoMedianteElSET\n10. Mostrar empleados por Departamento\n0. Salir");
			if (eleccion == null) {
				eleccion = "0";
			}
			switch (eleccion) {
			case "1":
				List<Empleado> empleados = mostrarEmpleados();
				String ejemplo = "";
				for (Empleado em : empleados) {
					ejemplo += "Id empleado = " + em.getIdEmpleado() + ". Nombre empleado = " + em.getNombreEmp()
							+ ". Id del departamento al que pertenece = " + em.getDepartamento().getIdDepartamento()
							+ "\n";
					;
				}
				JOptionPane.showMessageDialog(null, ejemplo);
				break;
			case "2":
				String idEmpIns = (String) JOptionPane.showInputDialog(null, "Introduzca el ID del empleado");
				String nombreEmpIns = (String) JOptionPane.showInputDialog(null, "Introduzca el nombre del empleado");
				String idDepIns = (String) JOptionPane.showInputDialog(null,
						"Introduzca el departamento al que pertenece");
				Departamento de = new Departamento();
				de.setIdDepartamento(0);
				de.setNombreDep("Departemanto Zero");
				Empleado eIns = new Empleado(Integer.parseInt(idEmpIns), nombreEmpIns, de);
				insertarEmpleado(eIns);
				break;
			case "3":
				String idEmpAct = (String) JOptionPane.showInputDialog(null,
						"Introduzca el ID del empleado a actualizar");
				String nombreEmpAct = (String) JOptionPane.showInputDialog(null,
						"Introduzca el nuevo nombre del empleado");
				String idDepAct = (String) JOptionPane.showInputDialog(null,
						"Introduzca el nuevo departamento al que pertenece");
				Empleado eAct = new Empleado(Integer.parseInt(idEmpAct), nombreEmpAct, null);
				actualizarEmpleado(eAct);
				break;
			case "4":
				String idEmpBor = (String) JOptionPane.showInputDialog(null,
						"Introduzca el ID del empleado a eliminar");
				Empleado eBor = new Empleado(Integer.parseInt(idEmpBor), "", null);
				borrarEmpleado(eBor);
				break;
			case "5":
				List<Departamento> departamentos = mostrarDepartamentos();
				String auxiliar = "";
				for (Departamento dep : departamentos) {
					auxiliar += "Id departamento = " + dep.getIdDepartamento() + ". Nombre departamento = "
							+ dep.getNombreDep() + "\n";
				}
				JOptionPane.showMessageDialog(null, auxiliar);
				break;
			case "6":
				String idDep2 = (String) JOptionPane.showInputDialog(null, "Introduzca el ID del departamento");
				String nombreDep2 = (String) JOptionPane.showInputDialog(null, "Introduzca el nombre del departamento");
				Departamento d = new Departamento();
				d.setIdDepartamento(Integer.parseInt(idDep2));
				d.setNombreDep(nombreDep2);
				insertarDepartamento(d);
				break;
			case "7":
				String idDepAct2 = (String) JOptionPane.showInputDialog(null,
						"Introduzca el ID del departamento a actualizar");
				String nombreDepAct2 = (String) JOptionPane.showInputDialog(null,
						"Introduzca el nuevo nombre del departamento");
				Departamento dAct = new Departamento();
				dAct.setIdDepartamento(Integer.parseInt(idDepAct2));
				dAct.setNombreDep(nombreDepAct2);
				actualizarDepartamento(dAct);
				break;
			case "8":
				String idDepBor = (String) JOptionPane.showInputDialog(null,
						"Introduzca el ID del departamento a eliminar");
				Departamento dBor = new Departamento();
				dBor.setIdDepartamento(Integer.parseInt(idDepBor));
				borrarDepartamento(dBor);
				break;
			case "9":
				ejemploInsertarEmpleadoYDepartamentoMedianteElSET();
				break;
			case "10":
				List<Departamento> listaDep = mostrarDepartamentos();
				String ayuda = "";
				for (Departamento dep : listaDep) {
					ayuda += "Id departamento = " + dep.getIdDepartamento() + ". Nombre departamento = "
							+ dep.getNombreDep() + "\n";
				}

				List<Empleado> listaEmpleados = mostrarEmpleadosPorDepartamento(ayuda);
				if (listaEmpleados.isEmpty()) {
					JOptionPane.showMessageDialog(null, "El departamento no contiene empleados");
				} else {
					for (Empleado em : listaEmpleados) {
						JOptionPane.showMessageDialog(null,
								"Id empleado = " + em.getIdEmpleado() + ". Nombre empleado = " + em.getNombreEmp()
										+ ". Id del departamento al que pertenece = "
										+ em.getDepartamento().getIdDepartamento());
					}
				}
				break;
			case "0":
				break;
			default:
				eleccion = "5";
				JOptionPane.showMessageDialog(null, "Esa opción no es correcta");
				break;
			}
		} while (!eleccion.equals("0"));

	}

	private static void insertarDepartamento(Departamento d) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(d);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	private static void insertarEmpleado(Empleado e) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			session.save(e);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	private static void actualizarDepartamento(Departamento d) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(d);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	private static void actualizarEmpleado(Empleado e) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.update(e);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	private static void borrarDepartamento(Departamento d) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(d);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	private static void borrarEmpleado(Empleado e) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(e);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null) {
				tx.rollback();
			}
			ex.printStackTrace();
		} finally {
			session.close();
		}
	}

	private static List<Departamento> mostrarDepartamentos() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Departamento> departamentos = new ArrayList();
		try {
			tx = session.beginTransaction();
			departamentos = session.createQuery("From Departamento").list();
			tx.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return departamentos;
	}

	private static List<Empleado> mostrarEmpleados() {
		Session session = factory.openSession();
		Transaction tx = null;
		List<Empleado> empleados = new ArrayList();
		try {
			tx = session.beginTransaction();
			empleados = session.createQuery("From Empleado").list();
			tx.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return empleados;
	}

	private static void ejemploInsertarEmpleadoYDepartamentoMedianteElSET() {
		Departamento de = new Departamento();
		de.setIdDepartamento(0);
		de.setNombreDep("Informática");

		Empleado e1 = new Empleado();
		e1.setIdEmpleado(0);
		e1.setNombreEmp("Manuel");
		Empleado e2 = new Empleado();
		e2.setIdEmpleado(1);
		e2.setNombreEmp("Yeyo");

		de.getEmpleado().add(e1);

		e1.setDepartamento(de);
		e2.setDepartamento(de);

		insertarDepartamento(de);
		insertarEmpleado(e1);
		insertarEmpleado(e2);
	}

	private static List<Empleado> mostrarEmpleadosPorDepartamento(String ayuda) {
		String aux = (String) JOptionPane.showInputDialog(null, ayuda + "Introduzca el id deseado");

		Session session = factory.openSession();
		Transaction tx = null;
		List<Empleado> empleados = new ArrayList();
		try {
			tx = session.beginTransaction();
			String sql = "From Empleado WHERE idDep=:aux";
			Query query = session.createQuery(sql);
			query.setParameter("aux", aux);
			empleados = (List<Empleado>) query.list();
			tx.commit();
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} finally {
			session.close();
		}
		return empleados;
	}
}
