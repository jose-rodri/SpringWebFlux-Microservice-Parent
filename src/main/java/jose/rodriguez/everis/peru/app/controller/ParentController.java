package jose.rodriguez.everis.peru.app.controller;

import java.net.URI;
import java.util.Date;
import jose.rodriguez.everis.peru.app.models.document.Parent;
import jose.rodriguez.everis.peru.app.models.service.ParentService;
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

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/everis/parents")
public class ParentController {

  
  
  @Autowired
  private ParentService service;

  /**.
   * Método listar coment
   */
  @GetMapping
  public Mono<ResponseEntity<Flux<Parent>>> findAll() {
    return Mono.just(

        ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(service.findAll()));
  }


  /**.
   * a
   * Método crear
   */

  @PostMapping
  public Mono<ResponseEntity<Parent>> save(@RequestBody Parent parent) {
    if (parent.getDate() == null) {
      parent.setDate(new Date());
    }
    return service.save(parent)
        .map(p -> ResponseEntity.created(URI.create("/api/everis/parents/".concat(p.getId())))
            .contentType(MediaType.APPLICATION_JSON_UTF8).body(p));
   
  }
  

  /**.
   * Método filtrar por codigo
   * @return
   */
  @GetMapping("/{id}")
  public Mono<ResponseEntity<Parent>> findById(@PathVariable String id) {
    return service.findById(id)
        .map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  /**.
   
   * Método actualizar.
   * 
   */
  @PutMapping("/{id}")
  public Mono<ResponseEntity<Parent>> update(@RequestBody Parent parent, @PathVariable String id) {
    return service.findById(id).flatMap(p -> {
      p.setName(parent.getName());
      p.setLastName(parent.getLastName());
      p.setGender(parent.getGender());
      p.setTypeDocument(parent.getTypeDocument());
      p.setDocument(parent.getDocument());
      return service.save(p);
    }).map(
        p -> ResponseEntity.created(URI.create("/api/everis/parents/".concat(p.getId()))).body(p))
        .defaultIfEmpty(ResponseEntity.notFound().build());


  }


  /**.
   * @return
   */
  @DeleteMapping("/{id}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
    return service.findById(id).flatMap(p -> {
      return service.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));

    }).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
  }


}
