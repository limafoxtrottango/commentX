package newcomment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NewCommentConfiguration {

  @Bean
  public NewCommentHandler newCommentHandler() {
    return new NewCommentHandler();
  }
}
