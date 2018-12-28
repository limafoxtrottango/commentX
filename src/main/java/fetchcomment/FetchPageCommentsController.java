package fetchcomment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FetchPageCommentsController {

  @Autowired FetchPageCommentsHandler fetchPageCommentsHandler;

  @PostMapping(path = "/fetchComments")
  public DeferredResult<ResponseEntity<?>> fetchComments(
      @RequestBody FetchPageCommentsStream fetchPageCommentsStream) {

    DeferredResult dr = new DeferredResult();

    List<String> comments = new ArrayList<>();

    Observable.just(fetchPageCommentsStream)
        .flatMap(a -> fetchPageCommentsHandler.fetchComments(a))
        .flatMap(results -> results.rows())
        .map(a -> a.value())
        .subscribe(a -> comments.add(a.toString()));

    ResponseEntity<FetchPageCommentsResponse> re =
        ResponseEntity.status(HttpStatus.OK)
            .body(FetchPageCommentsResponse.builder().comments(comments).build());
    dr.setResult(re);
    return dr;
  }
}
