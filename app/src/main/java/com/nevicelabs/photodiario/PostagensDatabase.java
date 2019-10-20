package com.nevicelabs.photodiario;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Postagem.class}, version = 1)
public abstract class PostagensDatabase extends RoomDatabase {

    /**
     * @return O DAO para a tabela postagens
     */
    public abstract PostagemDAO postagem();

    private static PostagensDatabase postagemDb;

    public static synchronized PostagensDatabase getInstance(Context context) {
        if (postagemDb == null) {
            postagemDb = Room
                    .databaseBuilder(context.getApplicationContext(), PostagensDatabase.class, "ex")
                    .build();
        }
        return postagemDb;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {}
}
