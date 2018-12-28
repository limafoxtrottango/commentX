package fetchcomment;

import com.couchbase.client.java.query.AsyncN1qlQueryResult;
import com.couchbase.client.java.query.N1qlParams;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.consistency.ScanConsistency;
import couchbase.AsyncQueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;
import utils.StringUtils;

public class FetchPageCommentsMinion {

  @Autowired AsyncQueryRunner asyncQueryRunner;

  public String getPageURIHash(final String uri) {
    return StringUtils.getCRC32Hash(uri);
  }

  public Observable<AsyncN1qlQueryResult> fetchPageComments(final String pageURIHash) {

    String query = "SELECT * from default WHERE pageURIHash = \"" + pageURIHash + "\"";

    N1qlParams queryParams =
        N1qlParams.build().adhoc(false).consistency(ScanConsistency.REQUEST_PLUS);
    N1qlQuery n1qlQuery = N1qlQuery.simple(query, queryParams);

    return asyncQueryRunner.runAsyncQuery(n1qlQuery);
  }
}
