import couchbase.CouchbaseConfiguration;
import fetchcomment.FetchPageCommentsController;
import newcomment.NewCommentController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
    basePackageClasses = {
      NewCommentController.class,
      CouchbaseConfiguration.class,
      FetchPageCommentsController.class
    })
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
