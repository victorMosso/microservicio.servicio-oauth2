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

import feign.FeignException;

@Service
public class ServicioUserDetails implements UserDetailsService, IUsuarioService {

	private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private ClienteFeign clientFeign;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = this.retriveUser(username);
		
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> log.info(authority.getAuthority())).collect(Collectors.toList());
		User userDetails = new User(user.getUsername(), user.getPassword(), user.getEnable(), true, true, true,
				authorities);
		return userDetails;
	}

	@Override
	public Usuario retriveUser(String username) throws UsernameNotFoundException {
		Usuario usuario;
		try {
			usuario = clientFeign.getUsername(username);
		} catch (FeignException fe) {
			log.error("Error, no existe el usuario '" + username + "' en la Base de datos!!!");
			fe.getStackTrace();
			throw new UsernameNotFoundException(
					"Error, no existe el usuario '" + username + "' en la Base de datos!!!");
		}

		return usuario;
	}

	@Override
	public Usuario updateUsuario(Usuario user, Long idUser) throws Exception {		
		return clientFeign.updateUsuario(user, idUser);
	}

}
