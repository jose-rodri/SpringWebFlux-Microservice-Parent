package jose.rodriguez.everis.peru.app.models.service;

import jose.rodriguez.everis.peru.app.models.dao.ParentDao;
import jose.rodriguez.everis.peru.app.models.document.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ParentServiceImpl implements ParentService {


  @Autowired
  private ParentDao dao;

  @Override
  public Flux<Parent> findAll() {
   
    return dao.findAll();
  }

  @Override
  public Mono<Parent> findByName(String name) {
  
    return dao.findByName(name);
  }


  @Override
  public Mono<Parent> findById(String id) {
   
    return dao.findById(id);
  }

  @Override
  public Mono<Parent> save(Parent parents) {
   
    return dao.save(parents);
  }

  @Override
  public Mono<Void> delete(Parent parents) {
    
    return dao.delete(parents);
  }



}
