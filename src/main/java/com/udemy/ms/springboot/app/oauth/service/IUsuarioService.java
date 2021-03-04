package com.udemy.ms.springboot.app.oauth.service;

import com.udemy.ms.springboot.app.commons.usuarios.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario retriveUser(String username) throws Exception;
}
