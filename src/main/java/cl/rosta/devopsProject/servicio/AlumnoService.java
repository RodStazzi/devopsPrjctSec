package cl.rosta.devopsProject.servicio;

import java.time.LocalDate;
import java.util.List;

import cl.rosta.devopsProject.modelo.Alumno;
import cl.rosta.devopsProject.modelo.Curso;
import cl.rosta.devopsProject.modelo.Estado;


public interface AlumnoService {

    Alumno getAllAlumnobyId(String rut);
    boolean insertAlumno(Alumno alumno);
	public List<Alumno> getAllAlumnos();
	boolean updateAlumno(Alumno alumno);
	public List<Alumno> getAllAlumnosFront();
	boolean deleteNota(Long id);
	boolean deleteAsistencia(Long id);
	boolean deleteAlumno(Long id);
	boolean insertAsistencia(Long idAlumnoCurso, LocalDate fecha, boolean presentePrimera, boolean presenteSegunda,
			boolean pagado);
	boolean modificAsistencia(Long idAlumnoCurso, Long idAsistencia, LocalDate fecha, boolean presentePrimera, boolean presenteSegunda,
			boolean pagado);
	boolean modificNota(float nota, LocalDate fecha, Long idNota);
	boolean insertNota(Long idAlumnoCurso, float nota, LocalDate fecha);
	boolean insertAlumnoCurso(Long id_alumno, Long id_curso, Long id_estado_alumno_curso);
	boolean modificAlumno(String nombre, String correo, String apellido, LocalDate fechaNacimiento, boolean cuiper, String rut, Long id_alumno);
	boolean modificAlumnoCurso( Long id_alumno, Long id_curso, Long id_estado_alumno_curso, Long id_alumno_curso);
	List<Curso> getAllCursos();
	List<Estado> getAllEstados();
	boolean insertarAlumno(String nombre, String correo, String apellido, LocalDate fechaNacimiento, boolean cuiper, String rut);
	//boolean modificAlumno(String nombre, String correo, String apellido, LocalDate fechaNacimiento, boolean cuiper, String rut, Long idAlumno);
}