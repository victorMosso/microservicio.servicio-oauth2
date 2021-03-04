package com.udemy.ms.springboot.app.oauth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.udemy.ms.springboot.app.commons.usuarios.models.entity.Usuario;
import com.udemy.ms.springboot.app.oauth.client.ClienteFeign;

@Service
public class ServicioUserDetails implements UserDetailsService, IUsuarioService{

	private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
    private ClienteFeign clientFeign;
	
	@Override
	public Usuario retriveUser(String username) throws Exception {
		Usuario usuario = clientFeign.getUsername(username);
		if(usuario == null) {
			log.error("El usuario '"+username+"no existe en la Base de Datos!!");
			throw new Exception("El usuario '"+username+"no existe en la Base de Datos!!"); 			
		}
		return usuario;
	}	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = clientFeign.getUsername(username);
		if(user == null) {
			log.error("Error, no existe el usuario '"+username+"' en la Base de datos!!!");
			throw new UsernameNotFoundException("Error, no existe el usuario '"+username+"' en la Base de datos!!!");
		}
		
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> log.info(authority.getAuthority()))
				.collect(Collectors.toList());
		User userDetails = 
				new User(user.getUsername(), user.getPassword(), user.getEnable(), 
						true, true, true, authorities);
		return userDetails;
	}


}
