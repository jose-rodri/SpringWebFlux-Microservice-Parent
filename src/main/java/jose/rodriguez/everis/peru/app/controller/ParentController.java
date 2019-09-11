package jose.rodriguez.everis.peru.app.controller;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

import jose.rodriguez.everis.peru.app.models.document.Parent;

import jose.rodriguez.everis.peru.app.models.service.ParentService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/everis/parents") 
public class ParentController {
	
	@Autowired
	private ParentService service;
	
	@GetMapping
	public Mono <ResponseEntity<Flux<Parent>>>listar(){
		return Mono.just(
	
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAll()));
	}

	
	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> crear(@Valid @RequestBody Mono<Parent> monoParent){
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
		return monoParent.flatMap(parent ->{
			if(parent.getDate()== null) {
				parent.setDate(new Date());
			}
		
			
			return service.save(parent).map(p ->{
				respuesta.put("Padre ", p);
				respuesta.put("Mensaje", "Padre creado con éxito");
				respuesta.put("Fecha", new Date());
				return ResponseEntity
						.created(URI.create("/api/everis/parents/".concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(respuesta);
				
						
			});
		}).onErrorResume(t -> {
			return Mono.just(t).cast(WebExchangeBindException.class)
					.flatMap(e -> Mono.just(e.getFieldErrors()))
					.flatMapMany(Flux::fromIterable)
					.map(fieldError -> "El campo "+fieldError.getField() + " " + fieldError.getDefaultMessage())
					.collectList()
					.flatMap(list -> {
						respuesta.put("errors", list);
						respuesta.put("timestamp", new Date());
						respuesta.put("status", HttpStatus.BAD_REQUEST.value());
						return Mono.just(ResponseEntity.badRequest().body(respuesta));
					});
							
		});
		
		
	}
	
	
	
	
	
	
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Parent>> ver(@PathVariable String id ){
		return service.findById(id).map(p -> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Parent>>editar(@RequestBody Parent parent , @PathVariable String id){
		return service.findById(id).flatMap(p ->{
			p.setName(parent.getName());
			p.setLastName(parent.getLastName());
			p.setGender(parent.getGender());
			p.setTypeDocument(parent.getTypeDocument());
			p.setDocument(parent.getDocument());
			return service.save(p);
		}).map(p -> ResponseEntity.created(URI.create("/api/everis/parents/".concat(p.getId())))
				.body(p)).defaultIfEmpty(ResponseEntity.notFound().build());
		
		
	}
	
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>>eliminar(@PathVariable String id){
		return service.findById(id).flatMap(
				p ->{
					return service.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
					
				}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
	

}
