package fetchcomment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FetchPageCommentsController {

    @Autowired
    FetchPageCommentsHandler fetchPageCommentsHandler;

    @CrossOrigin
    @PostMapping(path = "/fetchComments", produces = "application/json")
    public DeferredResult<ResponseEntity<String>> fetchComments(
            @RequestBody FetchPageCommentsStream fetchPageCommentsStream) {

        DeferredResult dr = new DeferredResult();

        List<String> comments = new ArrayList<>();

        Observable.just(fetchPageCommentsStream)
                .flatMap(a -> fetchPageCommentsHandler.fetchComments(a))
                .flatMap(results -> results.rows())
                .map(a -> {
                    System.out.println("Hello hello" + a.value());
                    return a.value();
                })
                .toBlocking()
                .forEach(a -> {
                    comments.add(a.toString().substring(11,a.toString().length()-1));
                });


        System.out.println("The response is: " + comments);

        //create the json
        String res = "";
        for(String comment : comments) {
            res += comment + ",";
        }
        res = res.substring(0,res.length()-1);
        System.out.println("The result is: " + res);
        String res2 = "[" + res + "]";

        ResponseEntity<String> re =
                ResponseEntity.status(HttpStatus.OK)
                        .body(res2);


        dr.setResult(re);
        return dr;
    }
}
