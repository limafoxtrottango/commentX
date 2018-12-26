package newcomment;

import com.couchbase.client.java.document.RawJsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.AsyncN1qlQueryResult;
import connector.couchbase.CouchbaseConnector;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;

public class NewCommentHandler {

  @Autowired CouchbaseConnector couchbaseConnector;

  public Observable<?> insertNewComment(final NewCommentStream newComment) {

    JsonObject jsonObject =
        JsonObject.create()
            .put("id", newComment.getId())
            .put("rootCommentId", newComment.getRootCommentId())
            .put("pageURIHash", newComment.getPageURIHash())
            .put("parentId", newComment.getParentId())
            .put("content", newComment.getContent())
            .put("children", JsonArray.empty());

    if (jsonObject.get("parentId") == null) return insertNewRootComment(jsonObject);

    return insertNewChildComment(jsonObject);
  }

  /**
   * Inserts a new root comment
   *
   * @param jsonObject
   * @return
   */
  public Observable<RawJsonDocument> insertNewRootComment(final JsonObject jsonObject) {

    return Observable.just(jsonObject)
        .flatMap(a -> couchbaseConnector.insertDocument(a.get("id").toString(), a.toString()));
  }

  /**
   * Inserts a new child comment
   *
   * @param jsonObject
   */
  public Observable<AsyncN1qlQueryResult> insertNewChildComment(final JsonObject jsonObject) {

    return Observable.just(jsonObject)
        .flatMap(
            a -> {
              String query =
                  "UPDATE default d USE KEYS \""
                      + a.get("rootCommentId")
                      + "\" SET d.children = CASE WHEN d.id == \""
                      + a.get("parentId")
                      + "\" THEN ARRAY_APPEND(IFMISSINGORNULL(d.children,[]),"
                      + a.toString()
                      + ") ELSE d.children END, p.children = ARRAY_APPEND(IFMISSINGORNULL(p.children,[]),"
                      + a.toString()
                      + ") FOR p WITHIN d.children WHEN p.id = \""
                      + a.get("parentId")
                      + "\" END";

              System.out.println("Query is: " + query);

              return couchbaseConnector.runAsyncN1QlQuery(query);
            });
  }
}
