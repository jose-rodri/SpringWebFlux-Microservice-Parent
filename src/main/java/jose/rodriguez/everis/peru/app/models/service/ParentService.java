package jose.rodriguez.everis.peru.app.models.service;

import jose.rodriguez.everis.peru.app.models.document.Parent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ParentService {
	
	public Flux<Parent>findAll();
	public Mono<Parent>findByName(String name);
	public Mono<Parent>findByDocument(String dni);
	public Mono<Parent>findByBetweenDate();
	//crud

	public Mono<Parent>findById(String id);
	public Mono<Parent>save(Parent parents);
	public Mono<Void>delete(Parent parents);
	
	

}
