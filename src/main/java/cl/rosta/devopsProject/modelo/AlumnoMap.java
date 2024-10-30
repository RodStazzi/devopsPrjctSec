package cl.rosta.devopsProject.modelo;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

public class AlumnoMap implements RowMapper<Alumno> {

    private Map<String, Alumno> alumnoMap = new HashMap<>();


    public List<Alumno> getAlumnoDetalleList() {
        return new ArrayList<>(alumnoMap.values());
    }

    public Alumno getAlumnoById(String rut) {
        return alumnoMap.get(rut);
    }
    
	@Override
	public Alumno mapRow(ResultSet rs, int rowNum) throws SQLException {
			

//        Long idAlumno = rs.getLong("id_alumno");
//        Alumno alumnoDetalle = alumnoMap.get(idAlumno);
      String rut = rs.getString("rut");
      Alumno alumnoDetalle = alumnoMap.get(rut);


        if (alumnoDetalle == null) {
            alumnoDetalle = new Alumno();
            alumnoDetalle.setId_alumno(rs.getLong("id_alumno"));
            alumnoDetalle.setNombre(rs.getString("nombre"));
            alumnoDetalle.setApellido(rs.getString("apellido"));
            alumnoDetalle.setCorreo(rs.getString("correo"));
            alumnoDetalle.setFecha_nacimiento(rs.getDate("fecha_nacimiento") != null ? 
            	    rs.getDate("fecha_nacimiento").toLocalDate() : null);
            alumnoDetalle.setNotas(new ArrayList<Nota>());
            alumnoDetalle.setAsistencias(new ArrayList<Asistencia>());
            alumnoDetalle.setIdEstadoAlumnoCurso(rs.getLong("idEstado"));
            alumnoDetalle.setEstado(rs.getString("estado"));
            alumnoDetalle.setCuiper(rs.getBoolean("cuiper"));
            alumnoDetalle.setRut(rs.getString("rut"));


            alumnoMap.put(rut, alumnoDetalle);
        }

        Nota nota = new Nota();
        nota.setId_nota(rs.getLong("id_nota"));
        nota.setId_alumno_curso(rs.getLong("id_alumno_curso_nota"));
        nota.setNota(rs.getDouble("nota"));
        nota.setNombre_trabajo(rs.getString("nombre_trabajo"));
        nota.setFecha(rs.getDate("fecha_nota") != null ? 
        	    rs.getDate("fecha_nota").toLocalDate() : null);
        if (!alumnoDetalle.getNotas().contains(nota)) {
        	alumnoDetalle.getNotas().add(nota);
        }

        Asistencia asistencia = new Asistencia();
        asistencia.setId_asistencia(rs.getLong("id_asistencia"));
        asistencia.setId_alumno_curso(rs.getLong("id_alumno_curso_asistencia"));
        asistencia.setPresentePrimera(rs.getBoolean("presentePrimera"));
        asistencia.setPresenteSegunda(rs.getBoolean("presenteSegunda"));
        asistencia.setFecha(rs.getDate("fecha_asistencia") != null ? 
        	    rs.getDate("fecha_asistencia").toLocalDate() : null);
        asistencia.setPagado(rs.getBoolean("pagado"));

        if (!alumnoDetalle.getAsistencias().contains(asistencia)) {
        	alumnoDetalle.getAsistencias().add(asistencia);
        }



        return alumnoDetalle;
	}
}