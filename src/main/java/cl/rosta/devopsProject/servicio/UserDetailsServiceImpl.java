package cl.rosta.devopsProject.servicio;


import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cl.rosta.devopsProject.modelo.User;
import cl.rosta.devopsProject.repositorio.UserRepository;





@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().toUpperCase()));
        
        System.out.println("Loading user by username: " + user.getUsername());
        System.out.println("User found: " + user.toString());
        System.out.println("Authorities assigned: " + authorities);
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);


    }

    public boolean registerUser(User user) {
        try {
        	boolean rec = false;
        	try {
                String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
                user.setPassword(encodedPassword);
                user.setRole("USER");
                rec = userRepository.saveUser(user);
			} catch (Exception e) {
				System.out.println(e);
			}

            return rec;
        } catch (Exception e) {
            return false;
        }
    }
}