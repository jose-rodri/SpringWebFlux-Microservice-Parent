package jose.rodriguez.everis.peru.app.handler;

import java.net.URI;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;


import jose.rodriguez.everis.peru.app.models.document.Parent;

import jose.rodriguez.everis.peru.app.models.service.ParentService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//programación funcional

@Component
public class ParentHandler {

	@Autowired
	private Validator validator;
	
	@Autowired
	private ParentService service;
	
public Mono<ServerResponse> listar(ServerRequest request){
		
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAll(), Parent.class);
	}
	
	
public Mono<ServerResponse> ver(ServerRequest request){
	String id= request.pathVariable("id");
	return service.findById(id).flatMap(p -> ServerResponse.ok()
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.body(BodyInserters.fromObject(p)))
		.switchIfEmpty(ServerResponse.notFound().build());
	
	}
	
public Mono<ServerResponse> crear(ServerRequest request){
	Mono<Parent> parent = request.bodyToMono(Parent.class);
	return parent.flatMap(p ->{
		Errors errors = new BeanPropertyBindingResult(p, Parent.class.getName());
		validator.validate(p, errors);
		if(errors.hasErrors()) {
			return Flux.fromIterable(errors.getFieldErrors())
					.map(fieldError -> "El campo " + fieldError.getField()+" "+fieldError.getDefaultMessage())
					.collectList()
					.flatMap(list -> ServerResponse.badRequest().body(BodyInserters.fromObject(listar(null))));
			
			
		}else {
			if(p.getDate() == null) {
				p.setDate(new Date());
			}
			return service.save(p).flatMap(pdb -> ServerResponse 
					.created(URI.create("/api/students/".concat(pdb.getId())))
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(BodyInserters.fromObject(pdb)));
		}
		
	
	});
			
}


//actualizar padre


public Mono<ServerResponse> editar(ServerRequest request){
	Mono<Parent> parent = request.bodyToMono(Parent.class); //req
	String id = request.pathVariable("id");
	
	Mono<Parent> studentDb = service.findById(id);
	
	return studentDb.zipWith(parent , (db , req) ->{
		db.setName(req.getName());
		db.setLastName(req.getLastName());
		db.setGender(req.getGender());
		db.setTypeDocument(req.getTypeDocument());
		db.setDocument(req.getDocument());
		
		return db;
		}).flatMap(p -> ServerResponse.created(URI.create("/api/parent/".concat(p.getId())))
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.body(service.save(p), Parent.class))
			.switchIfEmpty(ServerResponse.notFound().build());
}













public Mono<ServerResponse> eliminar(ServerRequest request){
	
	String id = request.pathVariable("id");
	Mono<Parent> parentdb = service.findById(id);
	return parentdb.flatMap(p -> service.delete(p)
			.then(ServerResponse.noContent().build()))
			.switchIfEmpty(ServerResponse.notFound().build());
	
}


	
}
