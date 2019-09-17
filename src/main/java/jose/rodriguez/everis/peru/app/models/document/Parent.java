package jose.rodriguez.everis.peru.app.models.document;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**.
 * José Rodriguez
 *
 */
@Data
@Document(collection = "parents")
public class Parent {



  @Id
  private String id;
  @NotEmpty
  private String name;
  @NotEmpty
  private String lastName;
  @NotEmpty
  private String gender;
  @DateTimeFormat(pattern = "yyy-MM-dd")
  private Date date;
  @NotEmpty
  private String typeDocument;
  @NotNull
  private int document;



  public Parent() {

  }

  /**t.
   */
  public Parent(String name, String lastName, String gender, String typeDocument, int document) {

    this.name = name;
    this.lastName = lastName;
    this.gender = gender;
    this.typeDocument = typeDocument;
    this.document = document;
  }





}
