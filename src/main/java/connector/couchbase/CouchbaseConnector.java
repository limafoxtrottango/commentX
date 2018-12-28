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
import org.apache.commons.lang.RandomStringUtils;
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
  } //4991,

  public static void main(String[] args) {

    StringBuilder randomString = new StringBuilder(RandomStringUtils.randomAlphanumeric(100000));

    for (int i = 0; i < 100; i++) {

      long startTime = System.currentTimeMillis();
      reverseUsingAppending(randomString);
      long endTime = System.currentTimeMillis();

      System.out.println((endTime - startTime));
    }



//    long startTime1 = System.currentTimeMillis();
//    reverseUsingArray(randomString);
//    long endTime1 = System.currentTimeMillis();
//
//    System.out.println("Using array time taken: " + (endTime1 - startTime1));
  }

  static void reverseUsingAppending(StringBuilder s) {
    StringBuilder answer = new StringBuilder("");
    for(int j = s.length() - 1; j >= 0; j--) {
      answer.append(s.charAt(j));
    }
  }

  static void reverseUsingArray(StringBuilder s) {
    char answer[] = new char[s.length()];
    for(int j = s.length() - 1; j >= 0; j--) {
      answer[s.length() - j - 1] = s.charAt(j);
    }
  }
}
