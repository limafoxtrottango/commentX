package connector.couchbase;

import com.couchbase.client.core.time.Delay;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.error.CASMismatchException;
import com.couchbase.client.java.query.AsyncN1qlQueryResult;
import com.couchbase.client.java.query.N1qlParams;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.consistency.ScanConsistency;
import com.couchbase.client.java.util.retry.RetryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;

import java.util.concurrent.TimeUnit;

public class CouchbaseConnector {

  @Autowired Bucket bucket;

  public Observable<RawJsonDocument> insertDocument(final String id, final String object) {
    RawJsonDocument rawJsonDocument = RawJsonDocument.create(id, object);
    return bucket.async().upsert(rawJsonDocument);
  }

  public Observable<AsyncN1qlQueryResult> runAsyncN1QlQuery(final String query) {
    N1qlParams queryParams =
        N1qlParams.build().adhoc(false).consistency(ScanConsistency.REQUEST_PLUS);
    N1qlQuery n1qlQuery = N1qlQuery.simple(query, queryParams);
    return bucket
        .async()
        .query(n1qlQuery)
        .retryWhen(
            RetryBuilder.anyOf(CASMismatchException.class)
                .delay(Delay.fixed(1, TimeUnit.SECONDS))
                .once()
                .build());
  }
}
