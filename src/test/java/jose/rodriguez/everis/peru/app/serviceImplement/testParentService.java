package jose.rodriguez.everis.peru.app.serviceImplement;

import java.util.Date;
import static org.mockito.Mockito.when;
import jose.rodriguez.everis.peru.app.models.dao.ParentDao;
import jose.rodriguez.everis.peru.app.models.document.Parent;
import jose.rodriguez.everis.peru.app.models.service.ParentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * @author jquispro
 *
 */
@RunWith(SpringRunner.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)

public class testParentService {


  @Mock
  private ParentDao parentDao;

  @InjectMocks
  private ParentServiceImpl parentService;

  


  private void assertResults(Publisher<Parent> publisher, Parent... expectedProducts) {
    StepVerifier.create(publisher).expectNext(expectedProducts).verifyComplete();
  }


  @Test
  public void findAll() {
    Parent p = new Parent();
    p.setName("Ángel");
    p.setLastName("Diam");
    p.setGender("M");
    p.setDate(new Date());
    p.setTypeDocument("dni");
    p.setDocument(58485888);
    when(parentService.findAll()).thenReturn(Flux.just(p));
    Flux<Parent> actual = parentService.findAll();
    assertResults(actual, p);
  }


  @Test
  public void idexisting() {
    Parent p = new Parent();
    p.setName("Mae");
    p.setLastName("zoeli");
    p.setGender("F");
    p.setDate(new Date());
    p.setTypeDocument("dni");
    p.setDocument(47232312);
    when(parentDao.findById(p.getId())).thenReturn(Mono.just(p));
    Mono<Parent> actual = parentService.findById(p.getId());
    assertResults(actual, p);
  }


  @Test
  public void findById_when_ID_NO_exist() {
    Parent p = new Parent();
    p.setId("iiiiiiiii");
    p.setName("Mae");
    p.setLastName("zoeli");
    p.setGender("F");
    p.setDate(new Date());
    p.setTypeDocument("dni");
    p.setDocument(96895756);
    when(parentDao.findById(p.getId())).thenReturn(Mono.empty());
    Mono<Parent> actual = parentService.findById(p.getId());
    assertResults(actual);
  }



  @Test
  public void save() {
    Parent p = new Parent();
    p.setId("iiiiiiiii");
    p.setName("Mae");
    p.setLastName("zoeli");
    p.setGender("F");
    p.setDate(new Date());
    p.setTypeDocument("dni");
    p.setDocument(96895756);
    when(parentDao.save(p)).thenReturn(Mono.just(p));
    Mono<Parent> actual = parentService.save(p);
    assertResults(actual, p);
  }



  @Test
  public void delete() {
    Parent p = new Parent();
    p.setId("iiiiii");
    p.setName("Mae");
    p.setLastName("zoeli");
    p.setDate(new Date());
    p.setTypeDocument("dni");
    p.setDocument(96895756);
    when(parentDao.delete(p)).thenReturn(Mono.empty());
  }


  @Test
  public void findFullName() {
    Parent p = new Parent();
    p.setId("iiiiiiii");
    p.setName("Mae");
    p.setLastName("zoeli");
    p.setDate(new Date());
    p.setTypeDocument("dni");
    p.setDocument(96895756);
   
    when(parentDao.findByName("Mae")).thenReturn(Mono.just(p));
    Mono<Parent> actual = parentService.findByName("Mae");
    assertResults(actual, p);
  }



}
