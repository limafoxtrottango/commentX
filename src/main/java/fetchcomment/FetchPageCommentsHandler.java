package fetchcomment;

import com.couchbase.client.java.query.AsyncN1qlQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;

public class FetchPageCommentsHandler {

  @Autowired private FetchPageCommentsMinion fetchPageCommentsMinion;

  public Observable<AsyncN1qlQueryResult> fetchComments(
      final FetchPageCommentsStream fetchPageCommentsStream) {
    System.out.println("Inside now");
    return Observable.just(fetchPageCommentsStream)
        .map(a -> fetchPageCommentsStream.getPageURI())
        .map(a -> fetchPageCommentsMinion.getPageURIHash(a))
        .flatMap(a -> fetchPageCommentsMinion.fetchPageComments(a));
  }
}

