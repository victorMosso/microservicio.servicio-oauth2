package com.udemy.ms.springboot.app.oauth.security.config.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.udemy.ms.springboot.app.commons.usuarios.models.entity.Usuario;
import com.udemy.ms.springboot.app.oauth.service.IUsuarioService;

@Component
public class AuthEventPublisher implements AuthenticationEventPublisher {

	Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
	private IUsuarioService userService;
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails)authentication.getPrincipal();
		String mensaje = String.format("El usuario %s realizo login exitoso!", user.getUsername());
		log.info(mensaje);
		//Reseteamos a cero el numero de intentos fallidos, en caso de ser mayor a 0
		Usuario userDB;
		try {
			userDB = userService.retriveUser(user.getUsername());			
			if(userDB.getCountFails() != null) {
				log.info(String.format("Numero previo de intentos fallidos:  %d",userDB.getCountFails()));
				userDB.setCountFails(0);
				userService.updateUsuario(userDB, userDB.getId());	
			}			
		} catch (Exception e) {
			e.getMessage();
		}
		
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String mensaje = String.format("Ocurrio un error en el login usuario: %s, detalle del error: %s", authentication.getName(),exception.getMessage());
		log.error(mensaje);
		
		Usuario usuarioDB;
		try {
			usuarioDB = userService.retriveUser(authentication.getName());
			if(usuarioDB != null) {
				//El usuario existe pero fallo la autericacion
				if(usuarioDB.getCountFails() == null) {
					usuarioDB.setCountFails(0);
				}
				log.info("Numero actual de intentos fallidos: "+usuarioDB.getCountFails());
				//Incrementamos en uno el contador de intentos				;
				usuarioDB.setCountFails(usuarioDB.getCountFails()+1);
				usuarioDB = userService.updateUsuario(usuarioDB, usuarioDB.getId());
				log.info("Numero actualizado de intentos fallidos: "+usuarioDB.getCountFails());
				if(usuarioDB.getCountFails() > 2) {
					//Deshabilitamos al usuario por exeder el numero valido de intentos
					usuarioDB.setEnable(false);
					usuarioDB = userService.updateUsuario(usuarioDB, usuarioDB.getId());			
				}
				log.info(String.format("Estatus del usuario %s : %b ", usuarioDB.getNombre(),usuarioDB.getEnable()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
