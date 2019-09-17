package jose.rodriguez.everis.peru.app.models.dao;

import jose.rodriguez.everis.peru.app.models.document.Parent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ParentDao extends ReactiveMongoRepository<Parent, String> {

  Mono<Parent> findByName(String name);

}
