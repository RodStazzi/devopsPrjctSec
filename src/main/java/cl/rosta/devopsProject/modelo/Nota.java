package cl.rosta.devopsProject.modelo;
import java.time.LocalDate;
import java.util.Objects;

public class Nota {
    private Long id_nota;
    private Long id_alumno_curso;
	private double nota;
    private LocalDate fecha;
    private String nombre_trabajo;

    public Long getId_nota() {
        return id_nota;
    }

    public void setId_nota(Long id_nota) {
        this.id_nota = id_nota;
    }

    public Long getId_alumno_curso() {
        return id_alumno_curso;
    }

    public void setId_alumno_curso(Long id_alumno_curso) {
        this.id_alumno_curso = id_alumno_curso;
    }
    public double getNota() {
		return nota;
	}
	public void setNota(double nota) {
		this.nota = nota;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
    public String getNombre_trabajo() {
		return nombre_trabajo;
	}
	public void setNombre_trabajo(String nombre_trabajo) {
		this.nombre_trabajo = nombre_trabajo;
	}
	
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nota nota1 = (Nota) o;
        return Double.compare(nota1.nota, nota) == 0 &&
                Objects.equals(fecha, nota1.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nota, fecha);
    }
	
	@Override
	public String toString() {
		return "Nota [nota=" + nota + ", fecha=" + fecha + "]";
	}

	
}