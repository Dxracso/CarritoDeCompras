package co.prueba.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import co.prueba.app.model.dto.ErrorGenerico;
import co.prueba.app.model.dto.User;

@RestController
public class LoginController {

	private final long TIEMPO_TOKEN = 300000;// tiempo de vida del token en milis// valido por 5 minutos
	private final String SECRET = "miLlaveSecreta";
	private final String PREFIX = "miToken ";

	@SuppressWarnings("unchecked")
	@PostMapping(value = "login")
	public ResponseEntity<Object> login(@RequestParam("user") String username, @RequestParam("password") String pwd)
			throws IOException, ParseException {

		JSONParser parser = new JSONParser();
		List<User> usuarios = new ArrayList<>();

		try {

			InputStream reader = getClass().getResourceAsStream("/static/usuarios.json");
			JSONArray jsonArray = (JSONArray) parser.parse(new InputStreamReader(reader));
			System.out.println(jsonArray);

			jsonArray.forEach(usr -> usuarios.add(parseUserObject((JSONObject) usr)));
			System.out.println(usuarios.size());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (buscarUsuario(username, pwd, usuarios).isEmpty()) {
			return new ResponseEntity<Object>(new ErrorGenerico("200",
					"Usuario o contraseña no encontrados en el archivo de configuracion", "ER_LGN_01", null),
					HttpStatus.OK);
		} else {
			String token = getJWTToken(username);
			User user = new User();
			user.setUsername(username);
			user.setToken(token);
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		}

	}

	private List<User> buscarUsuario(String username, String password, List<User> usuarios) {
		List<User> usuariosEncontrados = usuarios.stream()
				.filter(u -> u.getUsername().equals(username) && u.getPwd().equals(password))
				.collect(Collectors.toList());
		return usuariosEncontrados;
	}

	private User parseUserObject(JSONObject user) {
		JSONObject userObject = (JSONObject) user.get("user");
		User u = new User();
		u.setUsername((String) userObject.get("username"));
		u.setPwd((String) userObject.get("password"));

		return u;
	}

	private String getJWTToken(String username) {
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
