package cl.rosta.devopsProject.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.util.List;

//import org.apache.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.rosta.devopsProject.dto.AlumnoDTO;
import cl.rosta.devopsProject.modelo.Alumno;
import cl.rosta.devopsProject.modelo.Curso;
import cl.rosta.devopsProject.modelo.Estado;
import cl.rosta.devopsProject.servicio.AlumnoService;
import jakarta.annotation.security.RolesAllowed;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/v1")
public class AlumnoController {

//	private static Logger logger = Logger.getLogger(ServiceController.class);

	@Autowired
	private AlumnoService alumnoService;

	@GetMapping("/alumnos")
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<List<Alumno>> getAllAlumnos(){
		List<Alumno> listaAlumnos = alumnoService.getAllAlumnos();
		return new ResponseEntity(listaAlumnos, HttpStatus.OK);
	}
	
	
	@GetMapping("/alumnosFr")
	@RolesAllowed("ROLE_USER")
	public ResponseEntity<List<Alumno>> getAllAlumnosFront(){
		List<Alumno> listaAlomnosFr = alumnoService.getAllAlumnos();
		return new ResponseEntity(listaAlomnosFr, HttpStatus.OK);
	}
	
	@GetMapping("/alumno/{rut}")
	@RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
	public ResponseEntity<Alumno> getAlumnoById(@PathVariable String rut) {
	    Alumno alumno = alumnoService.getAllAlumnobyId(rut);
	    if (alumno != null) {
	        return ResponseEntity.ok(alumno);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
    @PostMapping("/insertarAlumno")
	@RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> insertAlumno(@RequestBody Alumno alumno) {
    	System.out.println(alumno);
        boolean isInserted = alumnoService.insertAlumno(alumno);
        if (isInserted) {
            return ResponseEntity.ok("Alumno insertado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al insertar el alumno");
        }
    }
    
    @PutMapping("/modificarAlumno")
	@RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> updateAlumno(@RequestBody Alumno alumno) {
    	
        boolean isUpdated = alumnoService.updateAlumno(alumno);
        if (isUpdated) {
            return ResponseEntity.ok("Alumno actualizado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el alumno");
        }
    }
	
    @DeleteMapping("/borrarNota/{id}")
	@RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> deleteNota(@PathVariable Long id) {
        boolean isDeleted = alumnoService.deleteNota(id);
        if (isDeleted) {
            return ResponseEntity.ok("Nota eliminada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el item");
        }
    }
    
    @DeleteMapping("/borrarAsistencia/{id}")
	@RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> deleteAsistencia(@PathVariable Long id) {
        boolean isDeleted = alumnoService.deleteAsistencia(id);
        if (isDeleted) {
            return ResponseEntity.ok("Asistencia eliminada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el item");
        }
    }
    
    @DeleteMapping("/borrarAlumno/{id}")
	@RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String>  deleteAlumno(@PathVariable Long id) {
        try {
            boolean isDeleted = alumnoService.deleteAlumno(id);
            if (isDeleted) {
                return ResponseEntity.ok("Alumno eliminado exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Alumno no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el alumno");
        }
    }
    
    @GetMapping("/test/protected")
	@RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> protectedEndpoint() {
        return ResponseEntity.ok("You have access to this protected endpoint");
    }
    
    @PostMapping("/insertaAsistencia/{idAlumnoCurso}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> insertarAsistencia(
            @PathVariable Long idAlumnoCurso,
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("presentePrimera") boolean presentePrimera,
            @RequestParam("presenteSegunda") boolean presenteSegunda,
            @RequestParam("pagado") boolean pagado) {

        boolean isInserted = alumnoService.insertAsistencia(idAlumnoCurso, fecha, presentePrimera, presenteSegunda, pagado);
        if (isInserted) {
            return ResponseEntity.ok("Asistencia insertada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al insertar Asistencia");
        }
    }
	
    @PutMapping("/modificaAsistencia/{idAlumnoCurso}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> modificarAsistencia(
            @PathVariable Long idAlumnoCurso,
            @RequestParam("idAsistencia") Long idAsistencia,
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("presentePrimera") boolean presentePrimera,
            @RequestParam("presenteSegunda") boolean presenteSegunda,
            @RequestParam("pagado") boolean pagado) {

        boolean isUpdated = alumnoService.modificAsistencia(idAlumnoCurso, idAsistencia, fecha, presentePrimera, presenteSegunda, pagado);
        if (isUpdated) {
            return ResponseEntity.ok("Asistencia actualizada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al insertar Asistencia");
        }
    }


    @PostMapping("/insertaNota/{idAlumnoCurso}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> insertarNota(
            @PathVariable Long idAlumnoCurso,
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("nota") float nota) {

        boolean isInserted = alumnoService.insertNota(idAlumnoCurso, nota, fecha);
        if (isInserted) {
            return ResponseEntity.ok("Nota insertada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al insertar Asistencia");
        }
    }


    @PutMapping("/modificaNota/{idAlumnoCurso}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> modificarNota(
            @PathVariable Long idAlumnoCurso,
            @RequestParam("idNota") Long idNota,
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("nota") float nota) {

        boolean isUpdated = alumnoService.modificNota(nota, fecha, idNota);
        if (isUpdated) {
            return ResponseEntity.ok("Nota actualizada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al insertar Asistencia");
        }
    }
    
    @PutMapping("/modificaAlumno/{idAlumno}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> modificarAlumno(
            @PathVariable Long idAlumno,
            @RequestBody AlumnoDTO alumnoDto) {
    	System.out.println("alumnoDto "+alumnoDto);
        boolean isUpdated = alumnoService.modificAlumno(
            alumnoDto.getNombre(), 
            alumnoDto.getCorreo(), 
            alumnoDto.getApellido(), 
            alumnoDto.getFechaNacimiento(), 
            alumnoDto.isCuiper(),
            alumnoDto.getRut(),
            idAlumno 
        );
        if (isUpdated) {
            return ResponseEntity.ok("Alumno actualizado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar Alumno");
        }
    }
    
    @PostMapping("/insertaAlumnoCurso/{idAlumno}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> insertAlumnoCurso(
            @PathVariable Long idAlumno,
            @RequestParam("idCurso") Long idCurso,
            @RequestParam("idEstadoAlumnoCurso") Long idEstadoAlumnoCurso) {

        boolean isUpdated = alumnoService.insertAlumnoCurso(idAlumno, idCurso, idEstadoAlumnoCurso);
        if (isUpdated) {
            return ResponseEntity.ok("AlumnoCurso insertado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al insertar Asistencia");
        }
    }
    
    @PutMapping("/modificaAlumnoCurso/{id_alumno_curso}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<String> modificAlumnoCurso(
    		@PathVariable("id_alumno_curso") Long idAlumnoCurso,
            @RequestParam("idAlumno") Long idAlumno,
            @RequestParam("idCurso") Long idCurso,
            @RequestParam("idEstadoAlumnoCurso") Long idEstadoAlumnoCurso) {
        boolean isUpdated = alumnoService.modificAlumnoCurso(idAlumno, idCurso, idEstadoAlumnoCurso, idAlumnoCurso);
        if (isUpdated) {
            return ResponseEntity.ok("AlumnoCurso actualizado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al insertar Asistencia");
        }
    }
    
	@GetMapping("/cursos")
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<List<Curso>> getAllCursos(){
		List<Curso> listaCursos = alumnoService.getAllCursos();
		return new ResponseEntity(listaCursos, HttpStatus.OK);
	}
	
	
	@GetMapping("/estados")
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<List<Curso>> getAllEstados(){
		List<Estado> listaEstados = alumnoService.getAllEstados();
		return new ResponseEntity(listaEstados, HttpStatus.OK);
	}
	
}
