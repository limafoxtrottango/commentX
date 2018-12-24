package streams;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpvoteDownvoteCommentStream {
    private String pageURIHash;
    private String id;
    private boolean upvote;
    private boolean downvote;
}
