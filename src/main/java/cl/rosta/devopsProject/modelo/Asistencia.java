package cl.rosta.devopsProject.modelo;
import java.time.LocalDate;
import java.util.Objects;

public class Asistencia {

    private Long id_asistencia;
    private Long id_alumno_curso;
    private boolean presentePrimera;
    private boolean presenteSegunda;
//    private Date fecha;
    private LocalDate fecha;
    private boolean pagado;
    
    public Long getId_asistencia() {
        return id_asistencia;
    }

    public void setId_asistencia(Long id_asistencia) {
        this.id_asistencia = id_asistencia;
    }

    public Long getId_alumno_curso() {
        return id_alumno_curso;
    }

    public void setId_alumno_curso(Long id_alumno_curso) {
        this.id_alumno_curso = id_alumno_curso;
    }
	public boolean isPresentePrimera() {
		return presentePrimera;
	}
	public void setPresentePrimera(boolean presentePrimera) {
		this.presentePrimera = presentePrimera;
	}
	public boolean isPresenteSegunda() {
		return presenteSegunda;
	}
	public void setPresenteSegunda(boolean presenteSegunda) {
		this.presenteSegunda = presenteSegunda;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public boolean isPagado() {
		return pagado;
	}
	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}
	
	
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asistencia that = (Asistencia) o;
        return presentePrimera == that.presentePrimera &&
                presenteSegunda == that.presenteSegunda &&
                Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(presentePrimera, presenteSegunda, fecha);
    }
	
	@Override
	public String toString() {
		return "Asistencia [presentePrimera=" + presentePrimera + ", presenteSegunda=" + presenteSegunda + ", fecha="
				+ fecha + "]";
	}
    
	
}