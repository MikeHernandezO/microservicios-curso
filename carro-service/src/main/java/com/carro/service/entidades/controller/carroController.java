package com.carro.service.entidades.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carro.service.entidades.Carro;
import com.carro.service.entidades.servicios.CarroService;

@RestController
@RequestMapping("/carro")
public class carroController {

	@Autowired
	private CarroService carroService;
	
	@GetMapping  
	public ResponseEntity<List<Carro>> listarCarros(){
		
		List<Carro> carro = carroService.getAll();
		if(carro.isEmpty()) {
			
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(carro);
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<Carro> obtenerCarro(@PathVariable("id") int id){
		Carro Carro=carroService.getCarroById(id);
		if(Carro== null) {
			
			return ResponseEntity.notFound().build();
		}
			return ResponseEntity.ok(Carro);
		
	}
	
	@PostMapping
	
	public ResponseEntity<Carro> guardarCarro(@RequestBody Carro carro){
		Carro nuevoCarro= carroService.save(carro);
		return ResponseEntity.ok(nuevoCarro);
}
	
	@GetMapping("/usuario/{userId}")
	public ResponseEntity<List<Carro>> listaCarrosPorUsuarioId(@PathVariable("userId") int id){
		List<Carro> carros= carroService.findCarroByUserId(id);
		if (carros.isEmpty()) {
			
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(carros);
		
		
	}
	
}
