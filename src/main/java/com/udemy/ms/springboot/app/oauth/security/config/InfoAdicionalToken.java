package com.udemy.ms.springboot.app.oauth.security.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.udemy.ms.springboot.app.commons.usuarios.models.entity.Usuario;
import com.udemy.ms.springboot.app.oauth.service.IUsuarioService;

@Component
public class InfoAdicionalToken implements TokenEnhancer{
	private Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Usuario user = null;
		try {
			user = usuarioService.retriveUser(authentication.getName());
			log.info(String.format("Nombre: %s", user.getNombre()));
			log.info(String.format("apellidos: %s", user.getApellido()));
			log.info(String.format("corre: %s", user.getEmail()));
		} catch (Exception e) {
			e.printStackTrace();			
		}
		Map<String, Object> infoAdicional = new HashMap<String,Object>();
		infoAdicional.put("nombre", user.getNombre());
		infoAdicional.put("apellido", user.getApellido());
		infoAdicional.put("email", user.getEmail());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(infoAdicional);
		return accessToken;
	}

}
