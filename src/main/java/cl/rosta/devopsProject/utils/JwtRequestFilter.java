package cl.rosta.devopsProject.utils;

import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cl.rosta.devopsProject.modelo.User;
import io.jsonwebtoken.Claims;



@Component
public class JwtRequestFilter extends OncePerRequestFilter {

		@Autowired
		private JwtUtil jwtUtil;

		private boolean hasAuthorizationBearer(jakarta.servlet.http.HttpServletRequest request) {
			String header = request.getHeader("Authorization");
			if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")) {
				return false;
			}

			return true;
		}

		private String getAccessToken(jakarta.servlet.http.HttpServletRequest request) {
			String header = request.getHeader("Authorization");
			String token = header.split(" ")[1].trim();
			return token;
		}

		private void setAuthenticationContext(String token, jakarta.servlet.http.HttpServletRequest request) {
			UserDetails userDetails = getUserDetails(token);

			UsernamePasswordAuthenticationToken 
				authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

			authentication.setDetails(
					new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		private UserDetails getUserDetails(String token) {
//			User userDetails = new User();
//			Claims claims = jwtUtil.parseClaims(token);
//			String subject = (String) claims.get(Claims.SUBJECT);
//			String roles = (String) claims.get("roles");
//			
//			System.out.println("SUBJECT: " + subject);
//			System.out.println("roles: " + roles);
////			roles = roles.replace("[", "").replace("]", "");
////			String[] roleNames = roles.split(",");
//			
////			for (String aRoleName : roleNames) {
////				userDetails.addRole(new Role(aRoleName));
////			}
//			userDetails.setRole(userDetails.getRole());
//
//			String[] jwtSubject = subject.split(",");
//
////			userDetails.setId(Long.parseLong(jwtSubject[0]));
////			userDetails.setEmail(jwtSubject[1]);
//
//			return (UserDetails) userDetails;
			
		    User userDetails = new User();
		    Claims claims = jwtUtil.parseClaims(token);
		    String username = claims.getSubject();
		    List<String> roles = claims.get("roles", List.class);

		    System.out.println("SUBJECT: " + username);
		    System.out.println("roles: " + roles);

		    userDetails.setUsername(username);
		    userDetails.setRole(roles.get(0)); // Asigna el primer rol, ajusta según tu lógica

		    return userDetails;
		}

		@Override
		protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
				jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
				throws jakarta.servlet.ServletException, IOException {
			if (!hasAuthorizationBearer(request)) {
				filterChain.doFilter(request, response);
				return;
			}

			String token = getAccessToken(request);

			if (!jwtUtil.validateAccessToken(token)) {
				filterChain.doFilter(request, response);
				return;
			}

			setAuthenticationContext(token, request);
			filterChain.doFilter(request, response);
		}
	}