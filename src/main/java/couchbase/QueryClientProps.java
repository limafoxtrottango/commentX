package couchbase;

import connector.couchbase.CouchbaseContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "commentx")
@Setter
@Getter
@Validated
@Component
public class QueryClientProps {
  private CouchbaseContext couchbase = new CouchbaseContext();
}
