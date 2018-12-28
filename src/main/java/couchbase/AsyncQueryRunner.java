package couchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.query.AsyncN1qlQueryResult;
import com.couchbase.client.java.query.N1qlQuery;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;

public class AsyncQueryRunner {

  @Autowired Bucket bucket;

  public Observable<AsyncN1qlQueryResult> runAsyncQuery(final N1qlQuery n1qlQuery) {
    return bucket.async().query(n1qlQuery);
  }
}
