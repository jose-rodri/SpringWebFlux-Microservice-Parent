package jose.rodriguez.everis.peru.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import jose.rodriguez.everis.peru.app.models.document.Parent;
import reactor.core.publisher.Mono;

public interface ParentDao extends ReactiveMongoRepository<Parent, String>{

	Mono<Parent> findByName(String name);
	
	
}
