package cl.rosta.devopsProject.modelo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Alumno {

	@Id
	private Long id_alumno;
	private String nombre;
	private String correo;
	private String apellido;
	private LocalDate fecha_nacimiento;
	private List<Nota> notas;
    private List<Asistencia> asistencias;
	private Long IdEstadoAlumnoCurso;
	private String estado;
	private boolean cuiper;
	private String rut;
    
	public String getRut() {
		return rut;
	}
	public void setRut(String rut) {
		this.rut = rut;
	}
	public boolean isCuiper() {
		return cuiper;
	}
	public void setCuiper(boolean cuiper) {
		this.cuiper = cuiper;
	}
	public Long getIdEstadoAlumnoCurso() {
		return IdEstadoAlumnoCurso;
	}
	public void setIdEstadoAlumnoCurso(Long idEstadoAlumnoCurso) {
		IdEstadoAlumnoCurso = idEstadoAlumnoCurso;
	}
    public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Long getId_alumno() {
		return id_alumno;
	}
	public void setId_alumno(Long id_alumno) {
		this.id_alumno = id_alumno;
	}
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
	public LocalDate getFecha_nacimiento() {
		return fecha_nacimiento;
	}
	public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}
    public List<Nota> getNotas() {
		return notas;
	}
	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}
	public List<Asistencia> getAsistencias() {
		return asistencias;
	}
	public void setAsistencias(List<Asistencia> asistencias) {
		this.asistencias = asistencias;
	}
	
	
	@Override
	public String toString() {
		return "Alumno [id_alumno=" + id_alumno + ", nombre=" + nombre + ", correo=" + correo + ", apellido=" + apellido
				+ ", fecha_nacimiento=" + fecha_nacimiento + ", notas=" + notas + ", asistencias=" + asistencias + "]";
	}

	
}