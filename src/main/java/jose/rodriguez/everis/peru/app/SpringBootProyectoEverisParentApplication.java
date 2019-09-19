package jose.rodriguez.everis.peru.app;

import java.util.Date;
import jose.rodriguez.everis.peru.app.models.document.Parent;
import jose.rodriguez.everis.peru.app.models.service.ParentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@SpringBootApplication
@EnableSwagger2WebFlux
public class SpringBootProyectoEverisParentApplication implements CommandLineRunner {


  @Autowired
  private ParentService service;

  @Autowired
  private ReactiveMongoTemplate mongoTemplate;

  private static final Logger log =
      LoggerFactory.getLogger(SpringBootProyectoEverisParentApplication.class);


  /**.
   * c
   */
  public static void main(String[] args) {
    SpringApplication.run(SpringBootProyectoEverisParentApplication.class, args);
    
    
  }
 

  @Override
  public void run(String... args) throws Exception {
    
   
    /*
   
mongoTemplate.dropCollection("parents").subscribe();
   

    Flux.just(new Parent("Feliciano", "Valdelomar", "M", new Date(), "Dni", 9857123),
        new Parent("Alicia", "Cardenas", "F", new Date(), "Dni", 98574111),
        new Parent("Luciana", "Conde", "F", new Date(), "Dni", 9857487),
        new Parent("Fermín", "Zonas", "M", new Date(), "Dni", 98574887),
        new Parent("Teresa", "Torres", "F", new Date(), "Dni", 98574099),
        new Parent("Eliana", "Ormeg", "F", new Date(), "Dni", 98574333),
        new Parent("Arturo", "Site", "M", new Date(), "Dni", 98574656)).flatMap(std -> {
          std.setDate(new Date());
          return service.save(std);
        })
        .subscribe(st -> log.info("- Id : " + st.getId() + " " + "- Name : " + st.getName() + " "
            + "- LastName : " + st.getLastName() + " " + "- Gender : " + st.getGender() + " "
            + "- Date : " + st.getDate() + " " + "- TypeDocument : " + st.getTypeDocument() + " "
            + "- Document : " + st.getDocument()));
*/
  }

}


