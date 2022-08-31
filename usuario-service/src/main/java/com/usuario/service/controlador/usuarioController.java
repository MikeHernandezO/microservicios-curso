package com.usuario.service.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.entidades.Usuario;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.servicio.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class usuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios() {

		List<Usuario> usuarios = usuarioService.getAll();
		if (usuarios.isEmpty()) {

			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id) {
		Usuario usuario = usuarioService.getUsuarioById(id);
		if (usuario == null) {

			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuario);

	}

	@PostMapping

	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) {
		Usuario nuevoUsuario = usuarioService.save(usuario);
		return ResponseEntity.ok(nuevoUsuario);
	}

	@GetMapping("/carro/{userId}")
	public ResponseEntity<List<Carro>> listarCarros(@PathVariable("userId") int id) {

		Usuario usuario = usuarioService.getUsuarioById(id);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}

		List<Carro> carros = usuarioService.getCarros(id);
		return ResponseEntity.ok(carros);

	}

	@GetMapping("/moto/{userId}")
	public ResponseEntity<List<Moto>> listarMoto(@PathVariable("userId") int id) {

		Usuario usuario = usuarioService.getUsuarioById(id);
		if (usuario == null) {
			return ResponseEntity.notFound().build();
		}

		List<Moto> motos = usuarioService.getMotos(id);
		return ResponseEntity.ok(motos);

	}

	@PostMapping("/carro/{userId}")
	public ResponseEntity<Carro> guardarCarro(@PathVariable("userId") int userId, @RequestBody Carro carro) {

		Carro nuevoCarro = usuarioService.saveCarro(userId, carro);
		return ResponseEntity.ok(nuevoCarro);

	}
	
	@PostMapping("/moto/{userId}")
	public ResponseEntity<Moto> guardarMoto(@PathVariable("userId") int userId, @RequestBody Moto moto) {

		Moto nuevoMoto = usuarioService.saveMoto(userId, moto);
		return ResponseEntity.ok(nuevoMoto);

	}
	
	@GetMapping ("/todos/{userId}")
	public ResponseEntity <Map<String, Object>> listarTodosLosVehiculos (@PathVariable("userId") int userId){
		Map<String, Object> resultado = usuarioService.getUsuarioAndVehiculos(userId);
		return ResponseEntity.ok(resultado);
	}
	
	
	
	

}
