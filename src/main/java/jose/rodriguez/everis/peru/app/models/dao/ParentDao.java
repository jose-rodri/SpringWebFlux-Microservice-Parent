package jose.rodriguez.everis.peru.app.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import jose.rodriguez.everis.peru.app.models.document.Parent;

public interface ParentDao extends ReactiveMongoRepository<Parent, String>{

}
