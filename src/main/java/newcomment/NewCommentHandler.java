package newcomment;

import com.couchbase.client.core.time.Delay;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
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

    JsonObject jsonObject = JsonObject.create();
    jsonObject.put("id", comment.getId());
    jsonObject.put("pageURIHash", comment.getPageURIHash());
    jsonObject.put("parentId", comment.getParentId());
    jsonObject.put("content", comment.getContent());
    jsonObject.put("children", JsonArray.empty());

    // Check if it is a root comment
    if (comment.getParentId() == null) {
      // create a new top-level JSON
      return couchbaseConnector.insertDocument(comment.getId(), jsonObject.toString());
    } else {
      String query =
          "UPDATE default d USE KEYS \""
              + comment.getRootCommentId()
              + "\" SET d.children = CASE WHEN d.id == \""
              + comment.getParentId()
              + "\" THEN ARRAY_APPEND(IFMISSINGORNULL(d.children,[]),"
              + jsonObject.toString()
              + ") ELSE d.children END, p.children = ARRAY_APPEND(IFMISSINGORNULL(p.children,[]),"
              + jsonObject.toString()
              + ") FOR p WITHIN d.children WHEN p.id = \""
              + comment.getParentId()
              + "\" END";

      System.out.println(query);
    }

    //    String query =
    //        "UPDATE default d use keys \"" + comment.getRootCommentId()
    //            + "\" SET p.level4_Attr = “test” FOR p WITHIN d.children WHEN p.id = \"" +
    // comment.getId() + "\" END";
    //
    //    // mutate the parent document
    //    if (!(comment.getParentContent() == null)) {
    //      System.out.println("Updating parent");
    //      bucket
    //          .async()
    //          .get(comment.getParentId())
    //          .doOnNext(
    //              a -> {
    //                JsonArray array = a.content().getArray("childrenIds");
    //
    //                if (array != null) {
    //                  array.add(comment.getId());
    //                  a.content().put("childrenIds", array);
    //                } else {
    //                  a.content().put("childrenIds", JsonArray.create().add(comment.getId()));
    //                }
    //              })
    //          .flatMap(a -> bucket.async().replace(a))
    //          .retryWhen(
    //              RetryBuilder.anyOf(CASMismatchException.class)
    //                  .delay(Delay.fixed(1, TimeUnit.SECONDS))
    //                  .once()
    //                  .build())
    //          .subscribe();
    //    }
    //
    //    System.out.println("Inserting new documents");

    // return couchbaseConnector.insertDocument(comment.getId(), comment);
    return null;
  }
}
