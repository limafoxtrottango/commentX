package newcomment;

import com.couchbase.client.core.time.Delay;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.error.CASMismatchException;
import com.couchbase.client.java.util.retry.RetryBuilder;
import connector.couchbase.CouchbaseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;

import java.util.concurrent.TimeUnit;

public class NewCommentHandler {

  @Autowired CouchbaseConnector couchbaseConnector;

  @Autowired Bucket bucket;

  /**
   * Persist a new comment to Couchbase
   *
   * @param comment
   * @return
   */
  public Observable<RawJsonDocument> persistComment(final NewCommentStream comment) {

    // mutate the parent document
    if (!(comment.getParentContent() == null)) {
      System.out.println("Updating parent");
      bucket
          .async()
          .get(comment.getParentId())
          .doOnNext(
              a -> {
                JsonArray array = a.content().getArray("childrenIds");
                if (array != null) {
                  array.add(comment.getId());
                  a.content().put("childrenIds", array);
                } else {
                  a.content().put("childrenIds", JsonArray.create().add(comment.getId()));
                }
              })
          .flatMap(a -> bucket.async().replace(a))
          .retryWhen(
              RetryBuilder.anyOf(CASMismatchException.class)
                  .delay(Delay.fixed(1, TimeUnit.SECONDS))
                  .once()
                  .build())
          .subscribe();
    }

    System.out.println("Inserting new documents");

    return couchbaseConnector.insertDocument(comment.getId(), comment);
  }
}
