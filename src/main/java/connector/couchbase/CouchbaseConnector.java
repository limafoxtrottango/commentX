package connector.couchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.RawJsonDocument;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;

public class CouchbaseConnector {

  @Autowired Bucket bucket;

  public Observable<RawJsonDocument> insertDocument(final String id, final String object) {
      RawJsonDocument rawJsonDocument = RawJsonDocument.create(id, object);
    return bucket.async().upsert(rawJsonDocument);
  }
}
