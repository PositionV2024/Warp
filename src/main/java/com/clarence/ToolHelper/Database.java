package com.clarence.ToolHelper;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Database {
    private static final String DatabaseName = "mongodb+srv://bob123:Ra3F4iQloGIx5U0a@warp.bbdm2.mongodb.net/?retryWrites=true&w=majority&appName=Warp";

    public static MongoClient createDataBase() {
        return MongoClients.create(DatabaseName);
    }
    public static MongoDatabase getDatabase(MongoClient mongoClient, String databaseSection) {
        return mongoClient.getDatabase(databaseSection);
    }
}
