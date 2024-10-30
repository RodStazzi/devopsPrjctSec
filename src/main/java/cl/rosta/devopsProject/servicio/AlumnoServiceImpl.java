package cl.rosta.devopsProject.servicio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cl.rosta.devopsProject.modelo.Alumno;
import cl.rosta.devopsProject.modelo.AlumnoMap;
import cl.rosta.devopsProject.modelo.Asistencia;
import cl.rosta.devopsProject.modelo.Curso;
import cl.rosta.devopsProject.modelo.Estado;
import cl.rosta.devopsProject.modelo.Nota;



@Service
public class AlumnoServiceImpl implements AlumnoService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Alumno getAllAlumnobyId(String rut) {

		String sql = "SELECT " + "    al.id_alumno, " + "    al.nombre, " + "    al.apellido, " + "    al.correo, "
				+ "    al.fecha_nacimiento, al.cuiper, al.rut, " + "    a.id_asistencia, "
				+ "    a.id_alumno_curso AS id_alumno_curso_asistencia, " + "    a.presentePrimera, "
				+ "    a.presenteSegunda, " + "    a.fecha AS fecha_asistencia, " + "    a.pagado, " + "    n.id_nota, "
				+ "    n.id_alumno_curso AS id_alumno_curso_nota, " + "    n.nota,   (select nombre_trabajo from trabajo where id_trabajo = n.id_trabajo) AS nombre_trabajo, "
				+ "    n.fecha AS fecha_nota, eac.id_estado_alumno_curso as idEstado ,"
				+ "    eac.descripcion AS estado  " + " FROM " + "    alumno AS al "
				+ "    LEFT JOIN alumno_curso AS ac ON al.id_alumno = ac.id_alumno "
				+ "    LEFT JOIN nota AS n ON ac.id_alumno_curso = n.id_alumno_curso "
				+ "    LEFT JOIN asistencia AS a ON ac.id_alumno_curso = a.id_alumno_curso "
				+ "    LEFT JOIN estado_alumno_curso AS eac ON ac.id_estado_alumno_curso = eac.id_estado_alumno_curso"
				+ " WHERE " + "    al.rut = ? ";

		AlumnoMap rowMapper = new AlumnoMap();
		jdbcTemplate.query(sql, new Object[] { rut }, rowMapper);
	    System.out.println("Alumno "+rowMapper.getAlumnoById(rut));
		return rowMapper.getAlumnoById(rut);
	}

	@Override
	public boolean insertAlumno(Alumno alumno) {
		// Insertar el alumno
		System.out.println("Alumno "+alumno);
		String sqlAlumno = "INSERT INTO alumno (nombre, correo, apellido, fecha_nacimiento, cuiper, rut) VALUES (?, ?, ?, ?, ?, ?)";
		int resultAlumno = jdbcTemplate.update(sqlAlumno, alumno.getNombre(), alumno.getCorreo(), alumno.getApellido(),
				alumno.getFecha_nacimiento(), alumno.isCuiper(), alumno.getRut());

		if (resultAlumno > 0) {
			// Obtener el ID del alumno recién insertado
			Long idAlumno = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

			// Insertar en Alumno_curso (asumiendo que tienes un curso y estado por defecto)
			String sqlAlumnoCurso = "INSERT INTO alumno_curso (id_alumno, id_curso, id_estado_alumno_curso) VALUES (?, ?, ?)";
			int resultAlumnoCurso = jdbcTemplate.update(sqlAlumnoCurso, idAlumno, 1, 1); // Reemplaza 1 con los valores
																							// adecuados

			if (resultAlumnoCurso > 0) {
				// Obtener el ID del alumno_curso recién insertado
				Long idAlumnoCurso = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

				if (alumno.getAsistencias() != null && !alumno.getAsistencias().isEmpty()) {
					for (Asistencia asistencia : alumno.getAsistencias()) {
						if (asistencia.getFecha() != null) {
							String sqlAsistencia = "INSERT INTO asistencia (id_alumno_curso, fecha, presentePrimera, presenteSegunda, pagado) VALUES (?, ?, ?, ?, ?)";
							jdbcTemplate.update(sqlAsistencia, idAlumnoCurso, asistencia.getFecha(),
									asistencia.isPresentePrimera(), asistencia.isPresenteSegunda(),
									asistencia.isPagado());
						}
					}
				}

				// Insertar las notas si existen
				if (alumno.getNotas() != null && !alumno.getNotas().isEmpty()) {
					for (Nota nota : alumno.getNotas()) {
						if (nota.getFecha() != null) {
							String sqlNota = "INSERT INTO nota (id_alumno_curso, nota, fecha) VALUES (?, ?, ?)";
							jdbcTemplate.update(sqlNota, idAlumnoCurso, nota.getNota(), nota.getFecha());
						}
					}
				}

				return true;
			}
		}

		return false;
	}

	@Override
	public List<Alumno> getAllAlumnos() {
		String sql = "SELECT al.id_alumno, al.nombre, al.apellido, al.correo, al.fecha_nacimiento, al.cuiper,  al.rut, a.id_asistencia, "
				+ " a.id_alumno_curso AS id_alumno_curso_asistencia, a.presentePrimera, a.presenteSegunda, a.fecha AS fecha_asistencia, "
				+ " a.pagado, n.id_nota, n.id_alumno_curso AS id_alumno_curso_nota, "
				+ " n.nota,   (select nombre_trabajo from trabajo where id_trabajo = n.id_trabajo) AS nombre_trabajo, "
				+ " n.fecha AS fecha_nota, eac.id_estado_alumno_curso as idEstado , eac.descripcion AS estado"
				+ " FROM alumno AS al " + "LEFT JOIN alumno_curso AS ac ON al.id_alumno = ac.id_alumno "
				+ "LEFT JOIN asistencia AS a ON ac.id_alumno_curso = a.id_alumno_curso "
				+ "LEFT JOIN nota AS n ON ac.id_alumno_curso = n.id_alumno_curso "
				+ "LEFT JOIN estado_alumno_curso AS eac ON ac.id_estado_alumno_curso = eac.id_estado_alumno_curso";

		AlumnoMap rowMapper = new AlumnoMap();
		jdbcTemplate.query(sql, rowMapper);

		return rowMapper.getAlumnoDetalleList();
	}

	@Override
	public boolean updateAlumno(Alumno alumno) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Alumno> getAllAlumnosFront() {

		String sql = "SELECT al.id_alumno, al.nombre, al.apellido, al.correo, al.fecha_nacimiento, al.cuiper, a.id_asistencia, a.id_alumno_curso AS id_alumno_curso_asistencia, a.presentePrimera, a.presenteSegunda, a.fecha AS fecha_asistencia, a.pagado, n.id_nota, n.id_alumno_curso AS id_alumno_curso_nota, n.nota, n.fecha AS fecha_nota, eac.id_estado_alumno_curso as idEstado , eac.descripcion AS estado"
				+ " FROM alumno AS al " + "LEFT JOIN alumno_curso AS ac ON al.id_alumno = ac.id_alumno "
				+ "LEFT JOIN asistencia AS a ON ac.id_alumno_curso = a.id_alumno_curso "
				+ "LEFT JOIN nota AS n ON ac.id_alumno_curso = n.id_alumno_curso "
				+ "LEFT JOIN estado_alumno_curso AS eac ON ac.id_estado_alumno_curso = eac.id_estado_alumno_curso "
				+ " where eac.descripcion in ('Inscrito', 'En Curso', 'Suspendido')";

		AlumnoMap rowMapper = new AlumnoMap();
		jdbcTemplate.query(sql, rowMapper);

		return rowMapper.getAlumnoDetalleList();
	}

	@Override
	public boolean deleteNota(Long id) {
		String sql = "delete from nota where id_nota =  ?";
		int result = jdbcTemplate.update(sql, id);
		if (result > 0)
			return true;

		return false;
	}

	@Override
	public boolean deleteAsistencia(Long id) {
		String sql = "delete from asistencia where id_asistencia  = ?";
		int result = jdbcTemplate.update(sql, id);
		if (result > 0)
			return true;

		return false;
	}

	@Override
	public boolean deleteAlumno(Long id) {
		int result = 0;
		try {
			String sql = "UPDATE alumno_curso SET id_estado_alumno_curso = 5 WHERE id_alumno = ?";
			result = jdbcTemplate.update(sql, id);
		} catch (Exception e) {

		}

		if (result > 0)
			return true;

		return false;
	}

	@Override
	public boolean insertAsistencia(Long idAlumnoCurso, LocalDate fecha, boolean presentePrimera,
			boolean presenteSegunda, boolean pagado) {
	      String sqlAsistencia = "INSERT INTO asistencia (id_alumno_curso, fecha, presentePrimera, presenteSegunda, pagado) VALUES (?, ?, ?, ?, ?)";

	      int resultAsistencia = jdbcTemplate.update(sqlAsistencia, idAlumnoCurso, fecha, presentePrimera, presenteSegunda, pagado);
	        System.out.println("Nueva asistencia insertada: " + resultAsistencia);

	        if (resultAsistencia > 0) return true;
	        
			return false;
	}

	@Override
	public boolean modificAsistencia(Long idAlumnoCurso, Long idAsistencia, LocalDate fecha, boolean presentePrimera,
			boolean presenteSegunda, boolean pagado) {
        String sqlAsistencia = "UPDATE asistencia SET fecha = ?, presentePrimera = ?, presenteSegunda = ?, pagado = ? WHERE id_asistencia = ? AND id_alumno_curso = ? ";
        
        int resultAsistencia = jdbcTemplate.update(sqlAsistencia, fecha, presentePrimera, presenteSegunda, pagado, idAsistencia, idAlumnoCurso);
        System.out.println("Asistencia actualizada: " + resultAsistencia);
        
        if (resultAsistencia > 0) return true;
        
		return false;
	}

	@Override
	public boolean modificNota(float nota, LocalDate fecha, Long idNota) {
        String sqlNota = "UPDATE nota SET nota = ?, fecha = ? WHERE id_nota = ?";
        
        int resultNota = jdbcTemplate.update(sqlNota, nota, fecha, idNota);
        System.out.println("Nota actualizada: " + resultNota);
        
        if (resultNota > 0) return true;
        
		return false;
	}

	@Override
	public boolean insertNota(Long idAlumnoCurso, float nota, LocalDate fecha) {
        String sqlNota = "INSERT INTO nota (id_alumno_curso, nota, fecha) VALUES (?, ?, ?)";
        
        int resultNota = jdbcTemplate.update(sqlNota, idAlumnoCurso, nota, fecha);
        System.out.println("Nueva nota insertada: " + resultNota);

        if (resultNota > 0) return true;
        
		return false;
	}

	@Override
	public boolean insertAlumnoCurso(Long id_alumno, Long id_curso, Long id_estado_alumno_curso) {
        String sqlAlumnoCurso = "INSERT INTO alumno_curso ( id_alumno, id_curso, id_estado_alumno_curso) VALUES (?, ?, ?)";
        
        int resultAlumnoCurso = jdbcTemplate.update(sqlAlumnoCurso, id_alumno, id_curso, id_estado_alumno_curso);
        System.out.println("Nuevo resultAlumnoCurso insertada: " + resultAlumnoCurso);

        if (resultAlumnoCurso > 0) return true;
        
		return false;
	}

	@Override
	public boolean modificAlumno( String nombre, String correo, String apellido, LocalDate fecha_nacimiento, boolean cuiper, String rut, Long id_alumno) {

        String sqlAlumno = "UPDATE alumno SET nombre = ?, correo = ?, apellido = ?, fecha_nacimiento = ?, cuiper = ?, rut = ? WHERE id_alumno = ?  ";
        System.out.println("AQUI 1");
        int resultAlumno = jdbcTemplate.update(sqlAlumno, nombre, correo, apellido, fecha_nacimiento, cuiper, rut, id_alumno);
        System.out.println("Asistencia actualizada: " + resultAlumno);
        
        if (resultAlumno > 0) return true;
        
		return false;
	}

	@Override
	public boolean modificAlumnoCurso(Long id_alumno, Long id_curso, Long id_estado_alumno_curso,
			Long id_alumno_curso) {
        String sqlAlumno = "UPDATE alumno_curso SET id_alumno = ?, id_curso = ?, id_estado_alumno_curso = ? WHERE id_alumno_curso = ?";
        
        int resultAlumno = jdbcTemplate.update(sqlAlumno, id_alumno, id_curso, id_estado_alumno_curso, id_alumno_curso);
        System.out.println("Asistencia actualizada: " + resultAlumno);
        
        if (resultAlumno > 0) return true;
        
		return false;
	}

	@Override
	public List<Curso> getAllCursos() {
		String sql = "SELECT id_curso, nombre_curso FROM curso "
				+ "WHERE id_curso IN ( "
				+ "    SELECT MIN(id_curso) "
				+ "    FROM curso "
				+ "    GROUP BY nombre_curso "
				+ ")";

	    List<Curso> cursos = jdbcTemplate.query(sql, (rs, rowNum) -> {
	        Curso curso = new Curso();
	        curso.setId_curso(rs.getLong("id_curso"));
	        curso.setNombre_curso(rs.getString("nombre_curso"));
	        return curso;
	    });

	    return cursos;
	}

	@Override
	public List<Estado> getAllEstados() {
		String sql = "SELECT id_estado_alumno_curso, descripcion "
				+ "FROM estado_alumno_curso "
				+ "WHERE id_estado_alumno_curso IN ( "
				+ "    SELECT MIN(id_estado_alumno_curso) "
				+ "    FROM estado_alumno_curso "
				+ "    GROUP BY descripcion "
				+ ")";

	    List<Estado> estados = jdbcTemplate.query(sql, (rs, rowNum) -> {
	    	Estado estado = new Estado();
	    	estado.setId_estado_alumno_curso(rs.getLong("id_estado_alumno_curso"));
	        estado.setDescripcion(rs.getString("descripcion"));
	        return estado;
	    });

	    return estados;
	}

	@Override
	public boolean insertarAlumno(String nombre, String correo, String apellido, LocalDate fecha_nacimiento, boolean cuiper, String rut) {
		// Insertar el alumno

		String sqlAlumno = "INSERT INTO alumno (nombre, correo, apellido, fecha_nacimiento, cuiper, rut) VALUES (?, ?, ?, ?, ?, ?)";
		int resultAlumno = jdbcTemplate.update(sqlAlumno, nombre, correo, apellido,
				fecha_nacimiento,cuiper, rut);

		if (resultAlumno > 0) {
			// Obtener el ID del alumno recién insertado
			Long idAlumno = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

			// Insertar en Alumno_curso (asumiendo que tienes un curso y estado por defecto)
			String sqlAlumnoCurso = "INSERT INTO alumno_curso (id_alumno, id_curso, id_estado_alumno_curso) VALUES (?, ?, ?)";
			int resultAlumnoCurso = jdbcTemplate.update(sqlAlumnoCurso, idAlumno, 1, 1); // Reemplaza 1 con los valores
																							// adecuados
		}

		return false;
	}

}