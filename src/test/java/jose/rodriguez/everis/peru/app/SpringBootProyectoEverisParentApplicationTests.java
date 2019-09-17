package jose.rodriguez.everis.peru.app;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import jose.rodriguez.everis.peru.app.models.document.Parent;
import jose.rodriguez.everis.peru.app.models.service.ParentService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SpringBootProyectoEverisParentApplicationTests {

  @Autowired
  private ParentService service;

  @Autowired
  private WebTestClient client;

  @Test
  public void findAllParentTest() {
    client.get().uri("/api/everis/parents").accept(MediaType.APPLICATION_JSON_UTF8).exchange()
        .expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBodyList(Parent.class).consumeWith(response -> {
          List<Parent> parent = response.getResponseBody();
          parent.forEach(p -> {
            System.out.println(p.getName());
          });
          Assertions.assertThat(parent.size() > 0).isTrue();
        });

  }

    /**.
   * Test 
   */
  @Test
  public void findByIdParentTest() {

    Parent parent = service.findByName("Alicia").block();

    client.get().uri("/api/everis/parents/{id}", Collections.singletonMap("id", parent.getId()))
        .accept(MediaType.APPLICATION_JSON_UTF8).exchange().expectStatus().isOk().expectHeader()
        .contentType(MediaType.APPLICATION_JSON_UTF8).expectBody(Parent.class)
        .consumeWith(response -> {
          Parent p = response.getResponseBody();
          Assertions.assertThat(p.getId()).isNotEmpty();
          Assertions.assertThat(p.getId().length() > 0).isTrue();
          Assertions.assertThat(p.getName()).isEqualTo("Alicia");

        });

  }

  /**.
   * Test 
   */
  @Test
  public void updateTest() {
    Parent parent = service.findByName("Arturo").block();
    Parent parentEditado = new Parent("Arturo", "Gady", "M",new Date(), "dni", 58788878);
    client.put().uri("/api/everis/parents/{id}", Collections.singletonMap("id", parent.getId()))
        .contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
        .body(Mono.just(parentEditado), Parent.class).exchange().expectStatus().isCreated()
        .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8).expectBody().jsonPath("$.id")
        .isNotEmpty().jsonPath("$.name").isEqualTo("Arturo").jsonPath("$.lastName")
        .isEqualTo("Gady").jsonPath("$.gender").isEqualTo("M").jsonPath("$.typeDocument")
        .isEqualTo("dni").jsonPath("$.document").isEqualTo(58788878);

  }

  /**.
   * Test 
   */
  @Test
  public void deleteTest() {
    Parent parent = service.findByName("Luciana").block();
    client.delete().uri("/api/everis/parents/{id}", Collections.singletonMap("id", parent.getId()))
        .exchange().expectStatus().isNoContent().expectBody().isEmpty();

  }
  

 
  /**
   * . s
   */
  @Test
  public void saveTest() {
    Parent parent = new Parent("Carlos", "Ron", "M", new Date(), "dni", 98696632);
    client.post().uri("/api/everis/parents").contentType(MediaType.APPLICATION_JSON_UTF8)
        .accept(MediaType.APPLICATION_JSON_UTF8).body(Mono.just(parent), Parent.class).exchange()
        .expectStatus().isCreated().expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
        .expectBody().jsonPath("$.id").isNotEmpty().jsonPath("$.name").isEqualTo("Carlos");

  }
 


}
