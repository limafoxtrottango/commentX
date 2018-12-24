package connector.couchbase;

import com.couchbase.client.java.PersistTo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CouchbaseContext {

  private List<String> bootstrapHosts;
  private String sslEnabled;
  private String keystorePath;
  private String keystorePassword;
  private Bucket bucket;
  private String noOfDaysThreshold;

  @Getter
  @Setter
  public static class Bucket {

    private String name;

    private String password;

    private PersistTo persistFactor;
  }
}
