package Data.Loaders;

import Data.DatabaseHandler;
import Data.GuildData;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCursor;
import reactor.core.publisher.Mono;

public class GuildLoader extends AbstractLoader {

    public Mono<Void> insert(GuildData data) {
        BasicDBObject searchQuery = new BasicDBObject("_id", data.getId());
        MongoCursor<GuildData> cursor = DatabaseHandler.getServerCollection().find(searchQuery).iterator();
        if (!cursor.hasNext()) {
            DatabaseHandler.getServerCollection().insertOne(data);
        }

        return Mono.empty();
    }

    @Override
    public Mono<Void> insert(String query, Object o) {
        return null;
    }

    public GuildData retrieve(String id) {
        BasicDBObject searchQuery = new BasicDBObject("_id", id);
        MongoCursor<GuildData> cursor = DatabaseHandler.getServerCollection().find(searchQuery).iterator();
        GuildData data = null;
        if (cursor.hasNext()) {
            data = cursor.next();
        }

        return data;
    }

    public Mono<Void> updateKey(GuildData data, String key) {
        BasicDBObject searchQuery = new BasicDBObject("_id", data.getId());
        BasicDBObject updateQuery = new BasicDBObject("$set", new BasicDBObject("key", key));
        data.setKey(key);

        DatabaseHandler.getServerCollection().updateOne(searchQuery, updateQuery);
        return Mono.empty();
    }

    public Mono<Void> delete() {
        return null;
    }
}
