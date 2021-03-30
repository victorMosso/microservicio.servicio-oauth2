package com.udemy.ms.springboot.app.oauth.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.udemy.ms.springboot.app.commons.usuarios.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario retriveUser(String username) throws UsernameNotFoundException;
	
	public Usuario updateUsuario(Usuario user, Long idUser) throws Exception;
}
