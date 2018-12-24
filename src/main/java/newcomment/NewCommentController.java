package newcomment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;
import rx.schedulers.Schedulers;
import utils.StringUtils;

@RestController
public class NewCommentController {

  @Autowired NewCommentHandler newCommentHandler;

  @PostMapping(path = "/newcomment")
  public DeferredResult<ResponseEntity<?>> insertCommentController(
      @RequestBody NewCommentStream newCommentStream) {

    final DeferredResult<ResponseEntity<?>> dr = new DeferredResult<>();

    Observable.just(newCommentStream)
        .map(
            a ->
                NewCommentStream.builder()
                    .parentId(StringUtils.getCRC32Hash(a.getParentContent()))
                    .pageURIHash(StringUtils.getCRC32Hash(a.getPageURI()))
                    .id(StringUtils.getCRC32Hash(a.getContent()))
                    .build())
        .subscribeOn(Schedulers.io())
        .flatMap(a -> newCommentHandler.persistComment(a))
        .subscribe(
            a -> {
              dr.setResult(new ResponseEntity<>(HttpStatus.OK));
            },
            e -> {});

    return dr;
  }
}
