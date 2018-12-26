package newcomment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class NewCommentStream {

    public NewCommentStream() {

    }

    private String pageURI;
    private String pageURIHash;
    private String id;
    private String content;
    private String parentContent;
    private String parentId;
    private String rootComment;
    private String rootCommentId;
    private List<String> children;
}
