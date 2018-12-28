package fetchcomment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class FetchPageCommentsStream {

  public FetchPageCommentsStream() {}

  private String pageURI;
}
