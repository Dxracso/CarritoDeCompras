package co.prueba.app.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import co.prueba.app.model.dto.User;

@RestController
public class UserController {

	private final long TIEMPO_TOKEN = 300000;
	private final String SECRET = "miLlaveSecreta";
	private final String PREFIX = "miToken ";

	@PostMapping(value = "login")
	public User login(@RequestParam("user") String username, @RequestParam("password") String pwd) {

		//TO DO validar desde una lista 
		String token = getJWTToken(username);	
		User user = new User();
		user.setUsername(username);
		user.setToken(token);
		return user;

	}

	private String getJWTToken(String username) {// valido por 5 minutos
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("IDJWT").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TIEMPO_TOKEN))
				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();

		return PREFIX + token;
	}
}
