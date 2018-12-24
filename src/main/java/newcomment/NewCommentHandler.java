package newcomment;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.RawJsonDocument;
import connector.couchbase.CouchbaseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;

public class NewCommentHandler {

  @Autowired CouchbaseConnector couchbaseConnector;

  @Autowired
  Bucket bucket;

  /**
   * Persist a new comment to Couchbase
   *
   * @param comment
   * @return
   */
  public Observable<RawJsonDocument> persistComment(final NewCommentStream comment) {
      String parentId = comment.getParentId();
      //Update the parent document
      Observable<JsonDocument> parentDocument = bucket.async().getAndLock(parentId,30);



    return couchbaseConnector.insertDocument(comment.getId(), comment);
  }

}
