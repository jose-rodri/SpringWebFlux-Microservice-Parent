package jose.rodriguez.everis.peru.app;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import jose.rodriguez.everis.peru.app.handler.ParentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import org.springframework.web.reactive.function.server.ServerResponse;




@Configuration
public class FunctionRouters {

  /**.
   * @author José LQ Rodriguez
   *
   */
  
  @Bean
  public RouterFunction<ServerResponse> routes(ParentHandler handler) {


    return route(GET("/api/parents").or(GET("/api/v1.0.0/parents")), handler::findAll)
        .andRoute(GET("/api/parents/{id}").or(GET("/api/v1.0.0/parents/{id}")), handler::findById)
        .andRoute(POST("/api/parents").or(GET("/api/v1.0.0/parents")), handler::save)
        .andRoute(PUT("/api/parents/{id}").or(PUT("/api/v1.0.0/parents/{id}")), handler::update)
        .andRoute(DELETE("/api/parents/{id}").or(DELETE("/api/v1.0.0/parents/{id}")),
            handler::delete);

  }

}


