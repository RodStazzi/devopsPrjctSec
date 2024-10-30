package cl.rosta.devopsProject.dto;

import java.time.LocalDate;

public class AlumnoDTO {

    private String nombre;
    private String correo;
    private String apellido;
    private LocalDate fechaNacimiento;
	private String rut;
    private boolean cuiper;
    
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public boolean isCuiper() {
		return cuiper;
	}
	public void setCuiper(boolean cuiper) {
		this.cuiper = cuiper;
	}
    public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	
	@Override
	public String toString() {
		return "AlumnoDTO [nombre=" + nombre + ", correo=" + correo + ", apellido=" + apellido + ", fechaNacimiento="
				+ fechaNacimiento + ", rut=" + rut + ", cuiper=" + cuiper + "]";
	}
	
}
