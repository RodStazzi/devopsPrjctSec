package cl.rosta.devopsProject;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import cl.rosta.devopsProject.controlador.AlumnoController;
import cl.rosta.devopsProject.controlador.AuthController;
import cl.rosta.devopsProject.dto.AlumnoDTO;
import cl.rosta.devopsProject.modelo.Alumno;
import cl.rosta.devopsProject.modelo.AuthRequest;
import cl.rosta.devopsProject.modelo.Curso;
import cl.rosta.devopsProject.modelo.Estado;
import cl.rosta.devopsProject.servicio.AlumnoService;
import cl.rosta.devopsProject.servicio.UserDetailsServiceImpl;
import cl.rosta.devopsProject.utils.JwtUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureMockMvc
public class DevopsProjectTest1 {


    @Mock
    private AlumnoService alumnoService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AlumnoController alumnoController;

    @InjectMocks
    private AuthController authController;

    private Alumno mockAlumno;
    private AuthRequest authRequest;
    private UserDetails userDetails;
    private Authentication authentication;

    @Before
    public void setup() {
        // Setup mock alumno
        mockAlumno = new Alumno();
        mockAlumno.setId_alumno(1L);
        mockAlumno.setNombre("Test");
        mockAlumno.setApellido("Student");
        mockAlumno.setCorreo("test@test.com");
        mockAlumno.setRut("12345678-9");
        mockAlumno.setFecha_nacimiento(LocalDate.now());
        mockAlumno.setNotas(new ArrayList<>());
        mockAlumno.setAsistencias(new ArrayList<>());

        // Setup auth request
        authRequest = new AuthRequest();
        authRequest.setUsername("admin");
        authRequest.setPassword("pass123");

        // Setup user details with exact matching types
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("admin");
        when(userDetails.getAuthorities()).thenReturn((Collection) authorities);

        // Setup authentication
        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Setup authentication
        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

    }

    @Test
    public void testLogin() throws Exception {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtil.generateAccessToken(any(UserDetails.class))).thenReturn("mock-jwt-token");

        ResponseEntity<?> response = authController.createAuthenticationToken(authRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetAllAlumnosWithAdminRole() {
        List<Alumno> mockAlumnos = Arrays.asList(mockAlumno);
        when(alumnoService.getAllAlumnos()).thenReturn(mockAlumnos);

        ResponseEntity<List<Alumno>> response = alumnoController.getAllAlumnos();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAlumnos, response.getBody());
    }

    @Test
    public void testGetAlumnoByIdWithUserRole() {
        when(alumnoService.getAllAlumnobyId("12345678-9")).thenReturn(mockAlumno);

        ResponseEntity<Alumno> response = alumnoController.getAlumnoById("12345678-9");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAlumno, response.getBody());
    }

    @Test
    public void testInsertAlumnoWithAdminRole() {
        when(alumnoService.insertAlumno(any(Alumno.class))).thenReturn(true);

        ResponseEntity<String> response = alumnoController.insertAlumno(mockAlumno);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Alumno insertado exitosamente", response.getBody());
    }

    @Test
    public void testUpdateAlumnoWithAdminRole() {
        when(alumnoService.updateAlumno(any(Alumno.class))).thenReturn(true);

        ResponseEntity<String> response = alumnoController.updateAlumno(mockAlumno);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Alumno actualizado exitosamente", response.getBody());
    }

    @Test
    public void testDeleteAlumnoWithAdminRole() {
        when(alumnoService.deleteAlumno(anyLong())).thenReturn(true);

        ResponseEntity<String> response = alumnoController.deleteAlumno(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Alumno eliminado exitosamente", response.getBody());
    }

    @Test
    public void testInsertAsistenciaWithAdminRole() {
        when(alumnoService.insertAsistencia(anyLong(), any(LocalDate.class), anyBoolean(), anyBoolean(), anyBoolean()))
                .thenReturn(true);

        ResponseEntity<String> response = alumnoController.insertarAsistencia(1L, LocalDate.now(), true, true, true);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Asistencia insertada exitosamente", response.getBody());
    }

    @Test
    public void testModificarAsistenciaWithAdminRole() {
        when(alumnoService.modificAsistencia(anyLong(), anyLong(), any(LocalDate.class), anyBoolean(), anyBoolean(), anyBoolean()))
                .thenReturn(true);

        ResponseEntity<String> response = alumnoController.modificarAsistencia(1L, 1L, LocalDate.now(), true, true, true);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Asistencia actualizada exitosamente", response.getBody());
    }

    @Test
    public void testInsertNotaWithAdminRole() {
        when(alumnoService.insertNota(anyLong(), anyFloat(), any(LocalDate.class))).thenReturn(true);

        ResponseEntity<String> response = alumnoController.insertarNota(1L, LocalDate.now(), 5.5f);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Nota insertada exitosamente", response.getBody());
    }

    @Test
    public void testModificarNotaWithAdminRole() {
        when(alumnoService.modificNota(anyFloat(), any(LocalDate.class), anyLong())).thenReturn(true);

        ResponseEntity<String> response = alumnoController.modificarNota(1L, 1L, LocalDate.now(), 5.5f);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Nota actualizada exitosamente", response.getBody());
    }

    @Test
    public void testModificarAlumnoWithAdminRole() {
        AlumnoDTO alumnoDto = new AlumnoDTO();
        alumnoDto.setNombre("Test");
        alumnoDto.setApellido("Student");
        alumnoDto.setCorreo("test@test.com");
        alumnoDto.setFechaNacimiento(LocalDate.now());
        alumnoDto.setRut("12345678-9");
        alumnoDto.setCuiper(true);

        when(alumnoService.modificAlumno(anyString(), anyString(), anyString(), any(LocalDate.class), anyBoolean(), anyString(), anyLong()))
                .thenReturn(true);

        ResponseEntity<String> response = alumnoController.modificarAlumno(1L, alumnoDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Alumno actualizado exitosamente", response.getBody());
    }

    @Test
    public void testGetAllCursosWithAdminRole() {
        List<Curso> mockCursos = new ArrayList<>();
        when(alumnoService.getAllCursos()).thenReturn(mockCursos);

        ResponseEntity<List<Curso>> response = alumnoController.getAllCursos();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCursos, response.getBody());
    }

    @Test
    public void testGetAllEstadosWithAdminRole() {
        List<Estado> mockEstados = new ArrayList<>();
        when(alumnoService.getAllEstados()).thenReturn(mockEstados);

        ResponseEntity<List<Curso>> response = alumnoController.getAllEstados();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteNotaWithAdminRole() {
        when(alumnoService.deleteNota(anyLong())).thenReturn(true);

        ResponseEntity<String> response = alumnoController.deleteNota(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Nota eliminada exitosamente", response.getBody());
    }

    @Test
    public void testDeleteAsistenciaWithAdminRole() {
        when(alumnoService.deleteAsistencia(anyLong())).thenReturn(true);

        ResponseEntity<String> response = alumnoController.deleteAsistencia(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Asistencia eliminada exitosamente", response.getBody());
    }

    @Test
    public void testProtectedEndpoint() {
        ResponseEntity<String> response = alumnoController.protectedEndpoint();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("You have access to this protected endpoint", response.getBody());
    }
}
