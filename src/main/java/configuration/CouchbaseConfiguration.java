package configuration;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import connector.couchbase.CouchbaseConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import props.QueryClientProps;

import java.util.List;

@Configuration
public class CouchbaseConfiguration {

  @Bean
  public Bucket bucket() {
    Cluster cluster = CouchbaseCluster.create(getEnvironment(), getBootstrapHosts());
    return cluster.openBucket(getBucketName());
  }

  public List<String> getBootstrapHosts() {
    return queryClientProps().getCouchbase().getBootstrapHosts();
  }

  public String getBucketName() {
    return queryClientProps().getCouchbase().getBucket().getName();
  }

  public String getBucketPassword() {
    return queryClientProps().getCouchbase().getBucket().getPassword();
  }

  public CouchbaseEnvironment getEnvironment() {
    return DefaultCouchbaseEnvironment.builder()
        .sslEnabled(Boolean.valueOf(queryClientProps().getCouchbase().getSslEnabled()))
        .sslKeystoreFile(queryClientProps().getCouchbase().getKeystorePath())
        .sslKeystorePassword(queryClientProps().getCouchbase().getKeystorePassword())
        .mutationTokensEnabled(true)
        .build();
  }

  @Bean
  public CouchbaseConnector couchbaseConnector() {
    System.out.println("sfjsgljsglksj");
    return new CouchbaseConnector();
  }

  @Bean
  public QueryClientProps queryClientProps() {
    return new QueryClientProps();
  }
}
