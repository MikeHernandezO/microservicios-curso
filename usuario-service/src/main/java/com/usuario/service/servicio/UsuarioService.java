package com.usuario.service.servicio;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.usuario.service.entidades.Usuario;
import com.usuario.service.feignclients.CarroFeignClient;
import com.usuario.service.feignclients.MotoFeignClient;
import com.usuario.service.modelos.Carro;
import com.usuario.service.modelos.Moto;
import com.usuario.service.repositorio.UsuarioRepositorio;

@Service // Creación del servicio
public class UsuarioService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private CarroFeignClient carroFeignClient;
	
	@Autowired
	private MotoFeignClient motoFeignClient;
	
	public List<Carro> getCarros(int userId) {

		List<Carro> carros = restTemplate.getForObject("http://localhost:8002/carro/usuario/" + userId, List.class);
		return carros;

	}
	
	public List<Moto> getMotos(int userId) {

		List<Moto> moto = restTemplate.getForObject("http://localhost:8003/moto/usuario/" + userId, List.class);
		return moto;
	}
	
	public Carro saveCarro ( int userId, Carro carro) {
		carro.setUserId(userId);
		Carro nuevoCarro = carroFeignClient.save(carro);
		return nuevoCarro;
		
	}

	public Moto saveMoto ( int userId, Moto moto) {
		moto.setUserId(userId);
		Moto nuevoMoto = motoFeignClient.save(moto);
		return nuevoMoto;
		
	}	
	
	public List<Usuario> getAll() {
		return usuarioRepositorio.findAll();
	}

	public Usuario getUsuarioById(int id) {
		return usuarioRepositorio.findById(id).orElse(null);

	}

	public Usuario save(Usuario usuario) {

		Usuario nuevousuario = usuarioRepositorio.save(usuario);
		return nuevousuario;

	}
	
	public Map<String, Object> getUsuarioAndVehiculos(int userId){
		Map <String, Object> resultado = new HashMap<>();
		Usuario usuario = usuarioRepositorio.findById(userId).orElse (null);
		
		if (usuario == null) {
			resultado.put("Mensaje", "El usuario no existe");
			return resultado;
		}   
		
		resultado.put("Usuario", usuario);
		List<Carro> carros= carroFeignClient.getCarros(userId);
		
		if(carros.isEmpty()) {
			resultado.put("Carros", "El usuario no tiene carros");
		}
		else {
			
			resultado.put("Carros", carros);
		}
		
		List<Moto> moto= motoFeignClient.getMotos(userId);
		
		if(moto.isEmpty()) {
			resultado.put("Motos", "El usuario no tiene motos");
		}
		else {
			
			resultado.put("Motos", moto);
		}
		
		return resultado;
		
			
		}
		
		
	}
