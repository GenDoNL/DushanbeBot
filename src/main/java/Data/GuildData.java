package Data;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class GuildData implements DBData {

  @BsonProperty("_id")
  @BsonId
  private String id;

  @BsonProperty("key")
  private String key;

  public GuildData() { }

  public GuildData(String id) {
    this.id = id;
    this.key = "!";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }


}
