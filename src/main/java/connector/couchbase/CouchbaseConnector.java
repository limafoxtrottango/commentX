package connector.couchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.RawJsonDocument;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;

public class CouchbaseConnector {

  @Autowired Bucket bucket;

  public Observable<RawJsonDocument> insertDocument(final String id, final Object object) {
    ObjectMapper objectMapper = new ObjectMapper();
      String json = null;
      try {
          json = objectMapper.writeValueAsString(object);
      } catch (JsonProcessingException e) {
          e.printStackTrace();
      }
      RawJsonDocument rawJsonDocument = RawJsonDocument.create(id, json);
    return bucket.async().upsert(rawJsonDocument);
  }
}
