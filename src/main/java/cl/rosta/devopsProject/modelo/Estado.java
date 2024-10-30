package cl.rosta.devopsProject.modelo;

import org.springframework.data.annotation.Id;

public class Estado {


	@Id
	private Long id_estado_alumno_curso;
	private String descripcion;
	
	
	public Long getId_estado_alumno_curso() {
		return id_estado_alumno_curso;
	}
	public void setId_estado_alumno_curso(Long id_estado_alumno_curso) {
		this.id_estado_alumno_curso = id_estado_alumno_curso;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Override
	public String toString() {
		return "Estado [id_estado_alumno_curso=" + id_estado_alumno_curso + ", descripcion=" + descripcion + "]";
	}
	

}