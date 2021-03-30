package com.udemy.ms.springboot.app.oauth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.udemy.ms.springboot.app.commons.usuarios.models.entity.Usuario;

@FeignClient(name = "servicio-usuarios")
public interface ClienteFeign {

	@GetMapping(path = "/repoUsuarios/search/buscar-username")
	public Usuario getUsername(@RequestParam String uName);	

	@PutMapping(path = "/repoUsuarios/{id}")
	public Usuario updateUsuario(@RequestBody Usuario user, @PathVariable Long id);
	
	
	
}
