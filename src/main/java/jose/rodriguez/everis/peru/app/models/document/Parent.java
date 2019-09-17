package jose.rodriguez.everis.peru.app.models.document;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

/**.
 * José Rodriguez
 *
 */

@Getter
@Setter
@NoArgsConstructor
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
  @JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
  private Date date;
  @NotEmpty
  private String typeDocument;
  @NotNull
  private int document;
 
  
  /**.
   *f
   */
  public Parent(String name,  String lastName,  String gender,
      Date date,  String typeDocument,  int document) {
   
    this.name = name;
    this.lastName = lastName;
    this.gender = gender;
    this.date = date;
    this.typeDocument = typeDocument;
    this.document = document;
  }



  /**t.
   */
  




}
