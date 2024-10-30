package cl.rosta.devopsProject.modelo;

import org.springframework.data.annotation.Id;

public class Curso {


	@Id
	private Long id_curso;
	private String nombre_curso;
	
	public Long getId_curso() {
		return id_curso;
	}
	public void setId_curso(Long id_curso) {
		this.id_curso = id_curso;
	}
	public String getNombre_curso() {
		return nombre_curso;
	}
	public void setNombre_curso(String nombre_curso) {
		this.nombre_curso = nombre_curso;
	}
	
	@Override
	public String toString() {
		return "Curso [id_curso=" + id_curso + ", nombre_curso=" + nombre_curso + "]";
	}

	
	
}