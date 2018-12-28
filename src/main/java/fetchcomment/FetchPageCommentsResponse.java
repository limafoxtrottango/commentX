package fetchcomment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class FetchPageCommentsResponse {

  public FetchPageCommentsResponse() {}

  private List<String> comments;
}
