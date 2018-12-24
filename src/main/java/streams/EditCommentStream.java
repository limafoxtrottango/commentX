package streams;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EditCommentStream {
    private String pageURIHash;
    private String id;
    private String content;
    private boolean delete;
}
