package Data.Database;

import Data.GuildData;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;

public class DatabaseHandler {
  private static DatabaseHandler instance;
  private MongoClient mongoClient;
  private MongoDatabase database;
  private MongoCollection<GuildData> serverCollection;

  private DatabaseHandler() {
    // TODO: Add config file for these settings
    CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
    CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            pojoCodecRegistry);

    MongoClientSettings clientSettings = MongoClientSettings.builder()
            .codecRegistry(codecRegistry)
            .build();

    mongoClient = MongoClients.create(clientSettings);
    database = mongoClient.getDatabase("test");
    serverCollection = database.getCollection("servers", GuildData.class);
  }

  public static DatabaseHandler getInstance() {
    if (instance == null) {
      instance = new DatabaseHandler();
    }
    return instance;
  }

  public static MongoCollection<GuildData> getServerCollection() {
    return getInstance().serverCollection;
  }
}
