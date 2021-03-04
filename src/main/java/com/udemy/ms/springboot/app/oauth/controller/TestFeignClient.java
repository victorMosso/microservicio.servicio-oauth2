//package com.udemy.ms.springboot.app.oauth.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.udemy.ms.springboot.app.commons.usuarios.models.entity.Usuario;
//import com.udemy.ms.springboot.app.oauth.service.ServicioUserDetails;
//
//@RestController
//public class TestFeignClient {
//
//	@Autowired
//	private ServicioUserDetails servicioUserDet;
//	
//	@GetMapping("/obtenerUsuario/{user}")
//	public Usuario getUserDetails(@PathVariable String user) throws Exception {
//		return servicioUserDet.retriveUserDetails(user);
//	} 
//	
//	@GetMapping("/getUsuario/{user}")
//	public Usuario getUser(@PathVariable String user) throws Exception {
//		return servicioUserDet.getUserDetails(user);
//	} 
//}
