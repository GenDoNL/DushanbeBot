package Data.Loaders;

import Data.DBData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;

public abstract class AbstractLoader {

  public abstract Mono<Void> insert(String query, Object o);

  public abstract DBData retrieve(String id);

  public abstract Mono<Void> delete();

  public String toJson() {
    try {
      return new ObjectMapper().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return ""; // Should we return an empty string or throw the exception?
  }
}
