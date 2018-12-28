package fetchcomment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FetchPageCommentsConfiguration {
  @Bean
  public FetchPageCommentsHandler fetchPageCommentsHandler() {
    return new FetchPageCommentsHandler();
  }

  @Bean
  public FetchPageCommentsMinion fetchPageCommentsMinion() {
    return new FetchPageCommentsMinion();
  }
}
