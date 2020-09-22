package co.prueba.app.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import co.prueba.app.model.dto.User;

@RestController // para login
public class UserController {

	private final long TIEMPO_TOKEN = 300000;
	private final String SECRET = "miLlaveSecreta";
	private final String PREFIX = "miToken ";


	@PostMapping(value = "login")
	public User login(@RequestParam("user") String username, @RequestParam("password") String pwd)
			throws IOException, ParseException {

		JSONParser parser = new JSONParser();
		Resource resource = new ClassPathResource("static/usuarios.json");

		// TO DO validar desde una lista
		if (!username.equals("oscar")) {
			return null;
		}
		if (!pwd.equals("mipassword")) {
			return null;
		}
		System.out.println("Se Logueo");
		// TO DO validar desde una lista
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
