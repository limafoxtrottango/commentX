package newcomment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class NewCommentStream {

    public NewCommentStream() {

    }

    //these four fields are sent by the client
    private String content;
    private String id;
    private String pageURIHash;
    private String parentId;
    private String rootCommentId;
}
