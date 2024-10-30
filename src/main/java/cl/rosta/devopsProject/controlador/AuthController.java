package cl.rosta.devopsProject.controlador;


import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.rosta.devopsProject.modelo.AuthRequest;
import cl.rosta.devopsProject.modelo.AuthResponse;
import cl.rosta.devopsProject.modelo.User;
import cl.rosta.devopsProject.servicio.UserDetailsServiceImpl;
import cl.rosta.devopsProject.utils.JwtUtil;


@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/auth")
@PermitAll
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {

		try {

	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        authRequest.getUsername(), authRequest.getPassword())
	        );

	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        String accessToken = jwtUtil.generateAccessToken(userDetails);
	        AuthResponse response = new AuthResponse(accessToken);

	        return ResponseEntity.ok().body(response);
		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
    	System.out.println("register "+user.getUsername());
    	System.out.println("register "+user.getPassword());
        boolean isRegistered = userDetailsService.registerUser(user);
        if (isRegistered) {
            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user");
        }
    }
}