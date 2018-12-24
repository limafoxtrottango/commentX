import newcomment.NewCommentConfiguration;
import newcomment.NewCommentController;
import configuration.CouchbaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
    basePackageClasses = {
      NewCommentController.class,
      CouchbaseConfiguration.class,
      NewCommentConfiguration.class
    })
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
